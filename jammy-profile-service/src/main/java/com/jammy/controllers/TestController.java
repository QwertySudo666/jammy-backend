package com.jammy.controllers;

import com.jammy.models.*;
import com.jammy.services.FilterSubscriptionService;
import com.jammy.services.ProfileService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {
    private FilterSubscriptionService filterSubscriptionService;
    private ProfileService profileService;
    private KafkaTemplate<String, Profile> kafkaTemplate;

    @GetMapping
    public String test1() {
        // Створення списку профілів
        List<Profile> profiles = TestData.createProfiles();

        // Збереження профілів
        profiles.forEach(profileService::createProfile);

        // Створення списку фільтрів та підписок
        List<FilterSubscription> filterSubscriptions = createFilterSubscriptions(profiles);

        // Збереження підписок
        filterSubscriptions.forEach(filterSubscriptionService::subscribe);

        // Публікуємо профіль в Kafka
//        kafkaTemplate.send("profiles", profiles.get(1));

        return "Test completed!";
    }

    private List<FilterSubscription> createFilterSubscriptions(List<Profile> profiles) {
        List<FilterSubscription> filterSubscriptions = new ArrayList<>();
        List<Filter> filters = TestData.createFilters();

        // Створюємо підписки на фільтри, прив'язуючи їх до профілів
        for (int i = 0; i < profiles.size(); i++) {
            filterSubscriptions.add(new FilterSubscription(UUID.randomUUID(), profiles.get(i).getId(), filters.get(i)));
        }

        return filterSubscriptions;
    }
}

class TestData {

    public static List<Profile> createProfiles() {
        List<Profile> profiles = new ArrayList<>();

        profiles.add(new Profile(UUID.randomUUID(), "MetalHead123", "metalhead@example.com", "Alex", 28, "Experienced guitarist and vocalist, looking for bandmates.", "Kyiv", "https://example.com/alex_avatar.jpg", List.of(Genre.DEATH_METAL, Genre.THRASH_METAL), List.of(Instrument.GUITAR, Instrument.VOCALS)));
        profiles.add(new Profile(UUID.randomUUID(), "BassMaster42", "bassmaster@example.com", "Maria", 32, "Bass player with a passion for progressive metal.", "Lviv", "https://example.com/maria_avatar.png", List.of(Genre.PROGRESSIVE_METAL, Genre.MELODIC_DEATH_METAL), List.of(Instrument.BASS)));
        profiles.add(new Profile(UUID.randomUUID(), "DrummerDave", "drummerdave@example.com", "David", 25, "Drummer with experience in metalcore and post-hardcore.", "Odesa", "https://example.com/david_avatar.jpeg", List.of(Genre.METALCORE, Genre.POST_HARDCORE), List.of(Instrument.DRUMS)));
        profiles.add(new Profile(UUID.randomUUID(), "KeysQueen", "keysqueen@example.com", "Olga", 30, "Keyboardist and pianist, specializing in symphonic metal.", "Kharkiv", "https://example.com/olga_avatar.gif", List.of(Genre.SYMPHONIC_METAL, Genre.CLASSICAL), List.of(Instrument.KEYBOARD, Instrument.PIANO)));
        profiles.add(new Profile(UUID.randomUUID(), "IvanidZE", "ivanidze@gmail.com", "Ivan", 100, "Ivan - the oldest man in the dorm", "Lviv", "https://vanidze.com/avatar.png", List.of(Genre.PROGRESSIVE_METAL, Genre.DEATH_METAL), List.of(Instrument.BASS)));

        return profiles;
    }

    public static List<Filter> createFilters() {
        List<Filter> filters = new ArrayList<>();

        filters.add(new Filter(25, "metalcore", "Odesa", List.of(Genre.METALCORE.toString()), List.of(Instrument.DRUMS.toString())));
        filters.add(new Filter(28, "guitarist", "Kyiv", List.of(Genre.DEATH_METAL.toString()), List.of(Instrument.GUITAR.toString())));
        filters.add(new Filter(32, "progressive", "Lviv", List.of(Genre.PROGRESSIVE_METAL.toString()), List.of(Instrument.BASS.toString())));
        filters.add(new Filter(100, "oldest", "Lviv", List.of(Genre.PROGRESSIVE_METAL.toString(), Genre.DEATH_METAL.toString()), List.of(Instrument.BASS.toString())));
        filters.add(new Filter(30, "keyboardist", "Kharkiv", List.of(Genre.SYMPHONIC_METAL.toString()), List.of(Instrument.KEYBOARD.toString())));

        return filters;
    }
}