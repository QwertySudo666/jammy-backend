package com.jammy.jammyimagereader.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "media_data")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaEntity {
    @Id
    private UUID id;
    @NotNull
    private UUID profileId;
    @NotNull
    private String fileName;
    @NotNull
    private String path;
    @NotNull
    private LocalDateTime uploadedAt;
}
