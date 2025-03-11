package com.jammy.services;

import com.jammy.entities.FilterSubscriptionEntity;
import com.jammy.entities.GenreEntity;
import com.jammy.entities.InstrumentEntity;
import com.jammy.models.Filter;
import com.jammy.models.FilterSubscription;
import com.jammy.repos.FilterSubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FilterSubscriptionService {
    private FilterSubscriptionRepository filterSubscriptionRepository;
    private MatchCheckerService matchCheckerService;

    public FilterSubscription subscribe(FilterSubscription filterSubscription) {
        var entity = new FilterSubscriptionEntity(
                filterSubscription.getFilterId(),
                filterSubscription.getSubscriberId(),
                filterSubscription.getFilter().getAge(),
                filterSubscription.getFilter().getBioSearch(),
                filterSubscription.getFilter().getLocation(),
                filterSubscription.getFilter().getGenres().stream().map(GenreEntity::new).collect(Collectors.toCollection(ArrayList::new)),
                filterSubscription.getFilter().getInstruments().stream().map(InstrumentEntity::new).collect(Collectors.toList())
        );
        var savedEntity = filterSubscriptionRepository.save(entity);
        var savedFilterSubscription = toFilterSubscription(savedEntity);
        matchCheckerService.checkMatchesForFilter(savedFilterSubscription);
        return savedFilterSubscription;
    }

    public FilterSubscription getFilterSubscriptionById(UUID filterSubscriptionId) {
        var entity = filterSubscriptionRepository.findById(filterSubscriptionId).get();
        return toFilterSubscription(entity);
    }

    public List<FilterSubscription> getAllFilterSubscriptionById(UUID subscriberId) {
        var entities = filterSubscriptionRepository.findAllBySubscriberId(subscriberId);
        return entities.stream().map(this::toFilterSubscription).collect(Collectors.toList());
    }

    public void unsubscribe(UUID filterSubscriptionId) {
        filterSubscriptionRepository.deleteById(filterSubscriptionId);
    }

    private FilterSubscription toFilterSubscription(FilterSubscriptionEntity entity) {
        return new FilterSubscription(
                entity.getId(),
                entity.getSubscriberId(),
                new Filter(
                        entity.getAge(),
                        entity.getBioSearch(),
                        entity.getLocation(),
                        entity.getGenres().stream().map(GenreEntity::getGenre).collect(Collectors.toList()),
                        entity.getInstruments().stream().map(InstrumentEntity::getInstrument).collect(Collectors.toList())
                )
        );
    }
}
