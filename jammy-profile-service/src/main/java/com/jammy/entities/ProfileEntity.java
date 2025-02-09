package com.jammy.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "profiles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileEntity {
    @Id
    private UUID id;
    private String username = "IVanidZE";
    private String email = "ivanidze@gmail.com";
    private String name = "Ivan";
    private Integer age = 100;
    private String bio = "Ivan - the oldest man in the dorm";
    private String location = "Lviv";
    private String avatarUrl = "https://vanidze.com/avatar.png";
//    @OneToMany(cascade = CascadeType.ALL)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profileId")
    private List<ProfileGenreEntity> genres;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profileId")
    private List<ProfileInstrumentEntity> instruments;
}
