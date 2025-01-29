package com.jammy.controllers;

import com.jammy.models.Profile;
import com.jammy.services.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
@AllArgsConstructor
public class ProfileController {
    private ProfileService profileService;

    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
        return new ResponseEntity<Profile>(profileService.createProfile(profile), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfile(@PathVariable("id") UUID id) {
        Profile profile = profileService.getProfile(id);
        return new ResponseEntity<Profile>(profile, HttpStatus.OK);
    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ProfileDto> updateProfile(@PathVariable UUID id, @RequestBody UpdateProfileRequest request) {
//        ProfileDto Profile = profileService.updateProfile(id, request);
//        return ResponseEntity.ok(Profile);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteProfile(@PathVariable UUID id) {
//        profileService.deleteProfile(id);
//        return ResponseEntity.noContent().build();
//    }
}
