package com.jammy.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @KafkaListener(topics = "profiles", groupId = "jammy-group")
    public void listenProfiles(String message) {
        System.out.println("Received profile: " + message);
    }

    @KafkaListener(topics = "filters", groupId = "jammy-group")
    public void listenFilters(String message) {
        System.out.println("Received filter: " + message);
    }

    @KafkaListener(topics = "matches", groupId = "jammy-group")
    public void listenMatches(String message) {
        System.out.println("Received match: " + message);
    }
}
