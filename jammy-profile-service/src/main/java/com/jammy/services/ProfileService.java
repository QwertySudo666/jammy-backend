package com.jammy.services;

import com.jammy.entities.ProfileEntity;
import com.jammy.entities.ProfileGenreEntity;
import com.jammy.entities.ProfileInstrumentEntity;
import com.jammy.models.Profile;
import com.jammy.repos.ProfileRepository;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public Profile createProfile(Profile profile) {
        var entity = new ProfileEntity(
                profile.getId(),
                profile.getUsername(),
                profile.getEmail(),
                profile.getName(),
                profile.getAge(),
                profile.getBio(),
                profile.getLocation(),
                profile.getAvatarUrl(),
                profile.getGenres().stream().map(it -> new ProfileGenreEntity(profile.getId(), it)).toList(),
                profile.getInstruments().stream().map(it -> new ProfileInstrumentEntity(profile.getId(), it)).toList()
        );
        var savedProfile = profileRepository.save(entity);
        return profile;
    }

    public Profile getProfile(UUID id) throws DataException {
        var entity = profileRepository.findById(id).get();
        return new Profile(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getName(),
                entity.getAge(),
                entity.getBio(),
                entity.getLocation(),
                entity.getAvatarUrl(),
                entity.getGenres().stream().map(ProfileGenreEntity::getGenre).toList(),
                entity.getInstruments().stream().map(ProfileInstrumentEntity::getInstrument).toList()
        );
    }
}
