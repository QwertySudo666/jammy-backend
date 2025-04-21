package com.jammy.jammyimagereader.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MediaUploadedEvent {
    private UUID mediaId;
    private UUID profileId;
    private String mediaType; // "image", "video", "audio"
    private String fileName;
    private String filePath;
    private LocalDateTime uploadedAt;
}
