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
//    @OneToMany(mappedBy = "profileId", cascade = CascadeType.ALL)
//    private List<ProfileGenreEntity> genres;
//    @OneToMany(mappedBy = "profileId", cascade = CascadeType.ALL)
//    private List<ProfileInstrumentEntity> instruments;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "profile_genres",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "genre")
    )
    private List<GenreEntity> genres;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "profile_instruments",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "instrument")
    )
    private List<InstrumentEntity> instruments;
}
