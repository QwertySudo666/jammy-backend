package com.jammy.services;

import com.jammy.entities.ProfileEntity;
import com.jammy.entities.ProfileGenreEntity;
import com.jammy.entities.ProfileInstrumentEntity;
import com.jammy.models.Genre;
import com.jammy.models.Instrument;
import com.jammy.models.Profile;
import com.jammy.repos.ProfileGenreRepository;
import com.jammy.repos.ProfileInstrumentRepository;
import com.jammy.repos.ProfileRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.exception.DataException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfileService {
    private ProfileRepository profileRepository;
    private ProfileGenreRepository profileGenreRepository;
    private ProfileInstrumentRepository profileInstrumentRepository;

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
                profile.getGenres().stream().map(it -> new ProfileGenreEntity(profile.getId(), it.name())).toList(),
                profile.getInstruments().stream().map(it -> new ProfileInstrumentEntity(profile.getId(), it.name())).toList()
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
                entity.getGenres().stream().map(it -> Genre.valueOf(it.getGenre())).toList(),
                entity.getInstruments().stream().map(it -> Instrument.valueOf(it.getInstrument())).toList()
        );
    }

    @Transactional
    public Profile updateProfile(UUID id, Profile profile) {
//        profileGenreRepository.deleteByProfileIdAndGenreIn(id, profile.getGenres().stream().map(Enum::name).toList());
//        profileInstrumentRepository.deleteByProfileIdAndInstrumentIn(id, profile.getInstruments().stream().map(Enum::name).toList());

        var entity = profileRepository.findById(id).get();
//        entity.getGenres().clear();
//        entity.getInstruments().clear();

//        var genresToSave = profile.getGenres().stream().map(Enum::name).toList();
//        var genresToDelete = profile.getGenres().stream().map(Enum::name).toList();

        entity.setEmail(profile.getEmail());
        entity.setName(profile.getName());
        entity.setAge(profile.getAge());
        entity.setBio(profile.getBio());
        entity.setLocation(profile.getLocation());
        entity.setAvatarUrl(profile.getAvatarUrl());
        entity.setGenres(profile.getGenres().stream().map(it -> new ProfileGenreEntity(profile.getId(), it.name())).collect(Collectors.toCollection(ArrayList::new)));
        entity.setInstruments(profile.getInstruments().stream().map(it -> new ProfileInstrumentEntity(profile.getId(), it.name())).collect(Collectors.toCollection(ArrayList::new)));
//        try{
        var savedProfile = profileRepository.save(entity);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

        return profile;
    }
}
