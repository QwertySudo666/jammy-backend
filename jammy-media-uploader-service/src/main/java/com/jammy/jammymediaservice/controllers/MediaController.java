package com.jammy.jammymediaservice.controllers;

import com.jammy.jammymediaservice.kafka.MediaEventProducer;
import com.jammy.jammymediaservice.models.MediaUploadedEvent;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.apache.kafka.common.requests.DeleteAclsResponse.log;

@RestController
@AllArgsConstructor
public class MediaController {
    private MediaEventProducer mediaEventProducer;

    private final Path videoDirectory = Paths.get("testmedia");

    @PostMapping("/upload-media")
    public Mono<String> uploadMedia(
            @RequestParam UUID profileId,
            @RequestPart("file") FilePart file
    ) {
        String fileName = file.filename();
        Path destination = videoDirectory.resolve(fileName);
        String contentType = file.headers().getContentType().toString();
        String fileLink = destination.toString();

        return file.transferTo(destination)
                .then(Mono.defer(() -> {
                    MediaUploadedEvent event = new MediaUploadedEvent(
                            UUID.randomUUID(),
                            profileId,
                            fileName,
                            contentType,
                            fileLink,
                            LocalDateTime.now()
                    );
                    return mediaEventProducer.sendMediaUploaded(event);
                }))
                .then(Mono.defer(() -> Mono.just("OK")))
                .onErrorResume(e -> {
                    log.error("Failed to upload media", e);
                    return Mono.error(new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Failed to upload media"
                    ));
                });
    }
}
