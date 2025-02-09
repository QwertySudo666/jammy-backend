package com.jammy.entities;


import com.jammy.entities.ids.ProfileInstrumentId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "profile_instruments")
@IdClass(ProfileInstrumentId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileInstrumentEntity {
    @Id
    private UUID profileId;
    @Id
    private String instrument;
}
