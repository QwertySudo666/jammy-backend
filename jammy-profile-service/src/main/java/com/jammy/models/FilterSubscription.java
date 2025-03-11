package com.jammy.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class FilterSubscription {
    private UUID filterId = UUID.randomUUID();
    private UUID subscriberId;
    private Filter filter;
}
