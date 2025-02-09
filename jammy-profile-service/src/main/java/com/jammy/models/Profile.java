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
    private String username = "IVanidZE";
    private String email = "ivanidze@gmail.com";
    private String name = "Ivan";
    private Integer age = 100;
    private String bio = "Ivan - the oldest man in the dorm";
    private String location = "Lviv";
    private String avatarUrl = "https://vanidze.com/avatar.png";
    private List<Genre> genres = List.of(Genre.PROGRESSIVE_METAL, Genre.DEATH_METAL);
    private List<Instrument> instruments = List.of(Instrument.BASS);
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
