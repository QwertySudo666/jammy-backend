package com.jammy.services;


import com.jammy.entities.MatchEntity;
import com.jammy.entities.ids.MatchEntityId;
import com.jammy.kafka.KafkaProducer;
import com.jammy.models.FilterSubscription;
import com.jammy.models.MatchEvent;
import com.jammy.models.Profile;
import com.jammy.repos.FilterSubscriptionRepository;
import com.jammy.repos.MatchRepository;
import com.jammy.repos.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MatchCheckerService {
    private final ProfileRepository profileRepository;
    private final FilterSubscriptionRepository filterRepository;
    private final KafkaProducer kafkaProducer;
    private final MatchRepository matchRepository;

    @Async("taskExecutor")
    public void checkMatchesForFilter(FilterSubscription filterSubscription) {
        var filter = filterSubscription.getFilter();
        var matchedProfiles = profileRepository.search(
                filterSubscription.getFilterId(),
                filter.getAge(),
                filter.getBioSearch(),
                filter.getLocation(),
                filter.getGenres(),
                filter.getInstruments()
        );

        var matchEntities = matchedProfiles.stream().map(it ->
                new MatchEntity(
                        new MatchEntityId(
                                it.getId(),
                                filterSubscription.getSubscriberId(),
                                filterSubscription.getFilterId()
                        )
                )
        ).toList();
        var savedMatches = matchRepository.saveAll(matchEntities);
        sendMatchesEvents(savedMatches);
    }

    @Async("taskExecutor")
    public void checkMatchesForProfile(Profile profile) {
        var subscriptions = filterRepository.searchSubscriptions(
                profile.getId(),
                profile.getAge(),
                profile.getBio(),
                profile.getLocation(),
                profile.getGenres().stream().map(Enum::name).collect(Collectors.toList()),
                profile.getInstruments().stream().map(Enum::name).collect(Collectors.toList())
        );

        var matchEntities = subscriptions.stream().map(it ->
                new MatchEntity(
                        new MatchEntityId(
                                profile.getId(),
                                it.getSubscriberId(),
                                it.getId()
                        )
                )
        ).toList();
        var savedMatches = matchRepository.saveAll(matchEntities);
        sendMatchesEvents(savedMatches);
    }

    private void sendMatchesEvents(List<MatchEntity> matches) {
        for (var m : matches) {
            kafkaProducer.sendMatches(
                    new MatchEvent(
                            m.getId().getProfileId(),
                            m.getId().getSubscriberId(),
                            m.getId().getFilterId()
                    )
            );
        }
    }
}
