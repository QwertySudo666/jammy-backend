package com.jammy.controllers;

import com.jammy.kafka.KafkaProducer;
import com.jammy.models.Filter;
import com.jammy.models.Profile;
import com.jammy.services.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
@AllArgsConstructor
public class ProfileController {
    private ProfileService profileService;
    private KafkaProducer kafkaProducer;
    private KafkaTemplate<String, Profile> kafkaTemplate;

    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
        var result = profileService.createProfile(profile);
        kafkaProducer.sendProfile(result);
        return new ResponseEntity<Profile>(result, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfile(@PathVariable("id") UUID id) {
        Profile profile = profileService.getProfile(id);
        return new ResponseEntity<Profile>(profile, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Profile>> searchProfiles(@RequestBody Filter filter) {
        var result = profileService.search(filter);
        return new ResponseEntity<>(result, HttpStatus.OK);
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
