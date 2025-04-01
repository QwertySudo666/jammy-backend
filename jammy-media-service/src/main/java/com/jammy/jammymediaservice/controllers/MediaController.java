package com.jammy.jammymediaservice.controllers;

import com.jammy.jammymediaservice.entities.MediaDataEntity;
import com.jammy.jammymediaservice.repositories.MediaDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class MediaController {
    private final MediaDataRepository mediaDataRepository;
    private R2dbcEntityTemplate entityTemplate;

    private final Path videoDirectory = Paths.get("testmedia");

    @PostMapping("/upload-media")
    public Mono uploadMedia(@RequestParam UUID profileId, @RequestPart("file") Flux<FilePart> fileParts) {
        return fileParts.flatMap(part -> {
            var name = part.filename();
            Path destination = videoDirectory.resolve(name);
            var type = part.headers().getContentType().toString();
            var link = destination.toString();
            return part.transferTo(destination).then(entityTemplate.insert(new MediaDataEntity(UUID.randomUUID(), profileId, name, type, link)));
        }).then().then(Mono.just("OK"));
    }
}
