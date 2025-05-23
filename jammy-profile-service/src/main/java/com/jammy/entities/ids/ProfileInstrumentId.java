package com.jammy.entities.ids;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileInstrumentId implements Serializable {
    private UUID profileId;
    private String instrument;
}
