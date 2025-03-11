package com.jammy.repos;

import com.jammy.entities.ProfileGenreEntity;
import com.jammy.entities.ids.ProfileGenreId;
import com.jammy.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProfileGenreRepository extends JpaRepository<ProfileGenreEntity, ProfileGenreId> {
    void deleteByProfileIdAndGenreIn(UUID id, List<String> genres);

    List<ProfileGenreEntity> findByProfileIdAndGenreIn(UUID profileId, List<Genre> genres);

    void deleteByProfileId(UUID profileId);
}
