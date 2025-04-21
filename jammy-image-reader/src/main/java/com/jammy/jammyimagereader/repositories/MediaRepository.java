package com.jammy.jammyimagereader.repositories;

import com.jammy.jammyimagereader.entities.MediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MediaRepository extends JpaRepository<MediaEntity, UUID> {
    List<MediaEntity> findByProfileId(UUID profileId);
}
