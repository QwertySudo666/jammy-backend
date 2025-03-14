package com.jammy.entities.ids;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MatchEntityId implements Serializable {
    private UUID profileId;
    private UUID subscriberId;
    private UUID filterId;
}
