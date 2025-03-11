package com.jammy.entities;

import com.jammy.entities.ids.ProfileGenreId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "profile_genres")
@IdClass(ProfileGenreId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileGenreEntity {
    @Id @Column(name = "profile_id")
    private UUID profileId;
    @Id
    private String genre;
}
