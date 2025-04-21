package com.jammy.jammyimagereader.kafka;

import com.jammy.jammyimagereader.models.MediaUploadedEvent;
import com.jammy.jammyimagereader.services.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaEventListener {

    private final MediaService mediaService;

    @KafkaListener(topics = "media-uploaded", groupId = "image-provider-group")
    public void handleMediaUploaded(MediaUploadedEvent event) {
        log.info("Received event: {}", event);

        if (event.getMediaType().contains("image")) {
            mediaService.processImage(event);
        }
    }
}
