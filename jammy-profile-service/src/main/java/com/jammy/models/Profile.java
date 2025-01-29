package com.jammy.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    private UUID id = UUID.randomUUID();
    private String username;
    private String email;
    private String name = "Ivan";
    private Integer age;
    private String bio;
    private String location;
    private String avatarUrl;
    private List<String> genres = List.of("METAL");
    private List<String> instruments = List.of("GUITAR");
}

//{
//        "username": "rocker123",
//        "firstName": "John",
//        "secondName": "Doe",
//        "email": "john.doe@example.com",
//        "password": "securePassword123",
//        "age": 25,
//        "genres": [
//        "PUNK_ROCK",
//        "HARDCORE"
//        ],
//        "instruments": [
//        "GUITAR",
//        "DRUMS"
//        ],
//        "bio": "I love jamming and creating new tunes!",
//        "location": "New York, USA",
//        "avatarUrl": "http://example.com/avatar.jpg"
//        }
