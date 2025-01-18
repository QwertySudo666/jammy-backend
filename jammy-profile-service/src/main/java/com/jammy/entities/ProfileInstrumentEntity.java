package com.jammy.entities;

import com.jammy.models.Genre;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class ProfileInstrumentEntity {
    @Id
    private UUID profileId;

    @Id
    private Genre instrument;
}
