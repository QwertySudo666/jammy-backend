package com.jammy.services;

import com.jammy.dtos.CreateProfileRequest;
import com.jammy.dtos.UpdateProfileRequest;
import com.jammy.dtos.ProfileDto;
import com.jammy.entities.ProfileEntity;
import com.jammy.repos.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileDto createUserProfile(CreateProfileRequest request) {
        ProfileEntity profileEntity = ProfileServiceMapper.mapToEntity(request);
        ProfileEntity savedProfile = profileRepository.save(profileEntity);
        return mapToDto(savedProfile);
    }

    public Optional<ProfileDto> getProfileById(UUID id) {
        return profileRepository.findById(id).map(this::mapToDto);
    }

    public ProfileDto updateProfile(UUID id, UpdateProfileRequest request) {
        ProfileEntity profileEntity = profileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));

        profileEntity.setUsername(request.getUsername());
        profileEntity.setAvatarUrl(request.getAvatarUrl());
        profileEntity.setBio(request.getBio());
        profileEntity.setLocation(request.getLocation());

        ProfileEntity updatedProfile = profileRepository.save(profileEntity);

        return mapToDto(updatedProfile);
    }

    public void deleteProfile(UUID id) {
        profileRepository.deleteById(id);
    }

    private ProfileDto mapToDto(ProfileEntity entity) {
        return new ProfileDto(
                entity.getId(),
                entity.getUsername(),
                entity.getFirstName(),
                entity.getSecondName(),
                entity.getEmail(),
                entity.getAge,
                entity.getAvatarUrl(),
                entity.getBio(),
                entity.getLocation()
        );
    }

    private static class ProfileServiceMapper {
        static ProfileEntity mapToEntity(CreateProfileRequest request) {
            ProfileEntity profileEntity = new ProfileEntity(
                    UUID.randomUUID(),
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword(), // TODO: Hash the password
                    request.getFirstName(),
                    request.getSecondName(),
                    request.getAvatarUrl(),
                    request.getBio(),
                    request.getLocation()
            );
            return profileEntity;
        }
    }
}
