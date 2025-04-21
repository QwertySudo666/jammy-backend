package com.jammy.jammymediaservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaUploadedEvent {
    private UUID mediaId;
    private UUID profileId;
    private String fileName;
    private String mediaType;
    private String filePath;
    private LocalDateTime uploadedAt;
}
