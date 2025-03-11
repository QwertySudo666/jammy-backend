package com.jammy.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class MatchFoundEvent {
    private UUID profileId;
    private UUID filterId;
    private UUID userId;
}