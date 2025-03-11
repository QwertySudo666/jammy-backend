package com.jammy.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "filter_subscription")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FilterSubscriptionEntity {
    @Id
    private UUID id;
    private UUID subscriberId;
    private Integer age;
    private String bioSearch;
    private String location;

    @ManyToMany
    @JoinTable(
            name = "filter_subscription_genres",
            joinColumns = @JoinColumn(name = "filter_id"),
            inverseJoinColumns = @JoinColumn(name = "genre")
    )
    private List<GenreEntity> genres;

    @ManyToMany
    @JoinTable(
            name = "filter_subscription_instruments",
            joinColumns = @JoinColumn(name = "filter_id"),
            inverseJoinColumns = @JoinColumn(name = "instrument")
    )
    private List<InstrumentEntity> instruments;
//    @ManyToOne(fetch = FetchType.LAZY)
//    private ProfileEntity profileEntity;
}
