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
        //TODO: send kafka event(profile.created)
        return new ResponseEntity<Profile>(profile, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable("id") UUID id, @RequestBody Profile profile) {
        var updatedProfile = profileService.updateProfile(id, profile);
        //TODO: send kafka event(profile.updated)
        return ResponseEntity.ok(updatedProfile);
    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteProfile(@PathVariable UUID id) {
//        profileService.deleteProfile(id);
//        return ResponseEntity.noContent().build();
//    }
}
