package com.jammy.services;

import com.jammy.entities.GenreEntity;
import com.jammy.entities.InstrumentEntity;
import com.jammy.entities.ProfileEntity;
import com.jammy.models.Filter;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfileService {
    private ProfileRepository profileRepository;
    private ProfileGenreRepository profileGenreRepository;
    private ProfileInstrumentRepository profileInstrumentRepository;
    private MatchCheckerService matchCheckerService;

    public Profile createProfile(Profile profile) {
        var entity = businessModelToEntity(profile);
        var savedEntity = profileRepository.save(entity);
        var savedProfile = entityToBusinessModel(savedEntity);
        matchCheckerService.checkMatchesForProfile(savedProfile);
        return savedProfile;
    }

    public Profile getProfile(UUID id) throws DataException {
        var entity = profileRepository.findById(id).get();
        return entityToBusinessModel(entity);
    }

    @Transactional
    public Profile updateProfile(UUID id, Profile profile) {
//        profileGenreRepository.deleteByProfileId(id);
//        profileInstrumentRepository.deleteByProfileId(id);

        var entity = profileRepository.findById(id).orElseThrow();
        entity.setEmail(profile.getEmail());
        entity.setName(profile.getName());
        entity.setAge(profile.getAge());
        entity.setBio(profile.getBio());
        entity.setLocation(profile.getLocation());
        entity.setAvatarUrl(profile.getAvatarUrl());
        entity.setGenres(profile.getGenres().stream().map(it -> new GenreEntity(it.name())).collect(Collectors.toCollection(ArrayList::new)));
        entity.setInstruments(profile.getInstruments().stream().map(it -> new InstrumentEntity(it.name())).collect(Collectors.toCollection(ArrayList::new)));

        var savedEntity = profileRepository.save(entity);
        var savedProfile = entityToBusinessModel(savedEntity);
        matchCheckerService.checkMatchesForProfile(profile);
        return savedProfile;
    }

    @Transactional
    public List<Profile> search(Filter filter) {
        var profileEntities = profileRepository.search(
                filter.getAge(),
                filter.getBioSearch(),
                filter.getLocation(),
                filter.getGenres(),
                filter.getInstruments()
        );

        return profileEntities.stream().map(this::entityToBusinessModel).collect(Collectors.toCollection(ArrayList::new));
    }

    private ProfileEntity businessModelToEntity(Profile profile) {
        return new ProfileEntity(
                profile.getId(),
                profile.getUsername(),
                profile.getEmail(),
                profile.getName(),
                profile.getAge(),
                profile.getBio(),
                profile.getLocation(),
                profile.getAvatarUrl(),
                profile.getGenres().stream().map(it -> new GenreEntity(it.name())).collect(Collectors.toList()),
                profile.getInstruments().stream().map(it -> new InstrumentEntity(it.name())).collect(Collectors.toList())
//                null,
//                null
//                profile.getGenres().stream().map(it -> new ProfileGenreEntity(profile.getId(), it.name())).toList(),
//                profile.getInstruments().stream().map(it -> new ProfileInstrumentEntity(profile.getId(), it.name())).toList()
        );
    }

    private Profile entityToBusinessModel(ProfileEntity profileEntity) {
        return new Profile(
                profileEntity.getId(),
                profileEntity.getUsername(),
                profileEntity.getEmail(),
                profileEntity.getName(),
                profileEntity.getAge(),
                profileEntity.getBio(),
                profileEntity.getLocation(),
                profileEntity.getAvatarUrl(),
//                null,
//                null
                profileEntity.getGenres().stream().map(it -> Genre.valueOf(it.getGenre())).collect(Collectors.toCollection(ArrayList::new)),
                profileEntity.getInstruments().stream().map(it -> Instrument.valueOf(it.getInstrument())).collect(Collectors.toCollection(ArrayList::new))
        );
    }
}
