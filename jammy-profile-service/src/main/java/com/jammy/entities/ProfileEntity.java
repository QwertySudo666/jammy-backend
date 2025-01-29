package com.jammy.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "profile")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileEntity {
    @Id
    private UUID id;
    private String username;
    private String email;
    private String name = "Ivan";
    private Integer age;
    private String bio;
    private String location;
    private String avatarUrl;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ProfileGenreEntity> genres;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ProfileInstrumentEntity> instruments;
}
