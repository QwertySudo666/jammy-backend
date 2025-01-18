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
@NoArgsConstructor
@AllArgsConstructor
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String firstName;

    @Column
    private String secondName;

    @Column
    private String avatarUrl;

    @Column
    private String bio;

    @Column
    private String location;

    @Column
    private Integer age;

    @Column
    private String gender;

    @Column
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<InstrumentEntity> instruments;
}
