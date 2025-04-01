package com.jammy.services;

import com.jammy.entities.*;
import com.jammy.models.*;
import com.jammy.repos.FilterSubscriptionRepository;
import com.jammy.repos.MatchRepository;
import com.jammy.repos.ProfileRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AllArgsConstructor
class MatchCheckerServiceTest {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired private MatchCheckerService matchCheckerService;
    @Autowired private FilterSubscriptionRepository filterSubscriptionRepository;
    @Autowired private MatchRepository matchRepository;

    @Container
//    private final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:11-alpine");
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.jpa.generate-ddl", () -> true);
    }

    @BeforeEach
    void setUp() {
        profileRepository.deleteAll();
        filterSubscriptionRepository.deleteAll();
        matchRepository.deleteAll();
//        kafkaTestConsumer.clear();
        var profileId = UUID.randomUUID();
        var filterId = UUID.randomUUID();

        // Створення тестових даних
        ProfileEntity profile = new ProfileEntity();
        profile.setId(profileId);
        profile.setAge(25);
        profile.setBio("Test bio");
        profile.setLocation("Test location");
        profile.setGenres(List.of((new GenreEntity(Genre.DEATH_METAL.name()))));
        profile.setInstruments(List.of(new InstrumentEntity(Instrument.GUITAR.name())));
        profileRepository.save(profile);

        FilterSubscriptionEntity filterSubscription = new FilterSubscriptionEntity();
        filterSubscription.setId(filterId);
        filterSubscription.setAge(20);
        filterSubscription.setBioSearch("Test");
        filterSubscription.setLocation("Test");
        filterSubscription.setGenres(List.of(new GenreEntity(Genre.DEATH_METAL.name())));
        filterSubscription.setInstruments(List.of(new InstrumentEntity(Instrument.GUITAR.name())));
        filterSubscription.setSubscriberId(profileId);
        filterSubscriptionRepository.save(filterSubscription);
    }

    @Test
    void checkMatchesForFilter() {
        var h = filterSubscriptionRepository.findAll();
        h.forEach(s -> System.out.println(s.getId()));
        var filterSubscription = toFilterSubscription(h.get(0));
        matchCheckerService.checkMatchesForFilter(filterSubscription);

        List<MatchEntity> matches = matchRepository.findAll();
        assertEquals(1, matches.size());
        assertEquals(filterSubscription.getSubscriberId(), matches.get(0).getId().getSubscriberId());
        assertEquals(filterSubscription.getFilterId(), matches.get(0).getId().getFilterId());

    }

    @Test
    void checkMatchesForProfile() {
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