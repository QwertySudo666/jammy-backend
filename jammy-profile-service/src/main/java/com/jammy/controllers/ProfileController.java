package com.jammy.controllers;

import com.jammy.dtos.CreateProfileRequest;
import com.jammy.dtos.UpdateProfileRequest;
import com.jammy.dtos.ProfileDto;
import com.jammy.services.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/Profiles")
@AllArgsConstructor
public class ProfileController {
    private ProfileService profileService;

    @PostMapping
    public ResponseEntity<ProfileDto> createProfile(@RequestBody CreateProfileRequest request) {
        ProfileDto Profile = profileService.createUserProfile(request);
        return ResponseEntity.ok(Profile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable UUID id) {
        Optional<ProfileDto> Profile = profileService.getProfileById(id);
        return Profile.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileDto> updateProfile(@PathVariable UUID id, @RequestBody UpdateProfileRequest request) {
        ProfileDto Profile = profileService.updateProfile(id, request);
        return ResponseEntity.ok(Profile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable UUID id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
}
