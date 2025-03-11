package com.jammy.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class MatchEvent {
    private UUID profileId;
    private UUID subscriberId;
    private UUID filterId;
}
