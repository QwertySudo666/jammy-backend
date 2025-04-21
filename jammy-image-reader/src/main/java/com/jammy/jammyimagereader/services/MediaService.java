package com.jammy.jammyimagereader.services;

import com.jammy.jammyimagereader.entities.MediaEntity;
import com.jammy.jammyimagereader.models.MediaUploadedEvent;
import com.jammy.jammyimagereader.repositories.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;

    public void processImage(MediaUploadedEvent event) {
        var savedEntity = mediaRepository.save(MediaEntity.builder()
                .id(event.getMediaId())
                .profileId(event.getProfileId())
                .fileName(event.getFileName())
                .path(event.getFilePath())
                .uploadedAt(event.getUploadedAt())
                .build()
        );
        System.out.println(savedEntity);
    }
}
