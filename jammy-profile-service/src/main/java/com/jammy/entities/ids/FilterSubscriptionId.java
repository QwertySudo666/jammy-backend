package com.jammy.entities.ids;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class FilterSubscriptionId implements Serializable {
    private UUID subscriberId;
}
