package com.jammy.entities;

import com.jammy.entities.ids.ProfileGenreId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "profile_genre")
@IdClass(ProfileGenreId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileGenreEntity {
    @Id
    private UUID profileId;
    @Id
    private String genre;
}
