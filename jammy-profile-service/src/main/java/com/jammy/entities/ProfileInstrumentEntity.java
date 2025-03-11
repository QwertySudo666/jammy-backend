package com.jammy.entities;


import com.jammy.entities.ids.ProfileInstrumentId;
import jakarta.persistence.*;
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
    @Id @Column(name = "profile_id")
    private UUID profileId;
    @Id
    private String instrument;
}
