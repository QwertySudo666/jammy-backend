package com.jammy.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jammy.models.FilterSubscription;
import com.jammy.models.MatchEvent;
import com.jammy.models.Profile;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaProducer {
    private KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendProfile(Profile profile) {
        try {
            String profileJson = objectMapper.writeValueAsString(profile);
            kafkaTemplate.send("profiles", profileJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize Profile", e);
        }
    }

    public void sendFilterSubscription(FilterSubscription filter) {
        if (filter == null) {
            throw new IllegalArgumentException("FilterSubscription cannot be null!");
        }

        try {
            String filterJson = objectMapper.writeValueAsString(filter);
            if (filterJson == null || filterJson.isBlank()) {
                throw new IllegalArgumentException("Serialized FilterSubscription is empty!");
            }
            System.out.println("Sending filter to Kafka: " + filterJson);
            kafkaTemplate.send("filters", filterJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize FilterSubscription", e);
        }
    }

    public void sendMatches(MatchEvent matchEvent) {
        kafkaTemplate.send("matches", matchEvent.toString());
        System.out.println("Sending matches to Kafka: " + matchEvent.toString());
    }


//    public void sendFilterSubscription(FilterSubscription filter) {
//        try {
//            String filterJson = objectMapper.writeValueAsString(filter);
//            kafkaTemplate.send("filters", filterJson);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Failed to serialize FilterSubscription", e);
//        }
//    }
}
