package com.jammy.controllers;

import com.jammy.kafka.KafkaProducer;
import com.jammy.models.FilterSubscription;
import com.jammy.services.FilterSubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/filters")
@AllArgsConstructor
public class FilterSubscriptionController {
    private FilterSubscriptionService filterSubscriptionService;
    private KafkaProducer kafkaProducer;

    @PostMapping
    public void subscribe(@RequestBody FilterSubscription filterSubscription) {
        var saved = filterSubscriptionService.subscribe(filterSubscription);
        kafkaProducer.sendFilterSubscription(saved);
    }

    @GetMapping("/{filterSubscriptionId}")
    public FilterSubscription getFilterSubscription(@PathVariable UUID filterSubscriptionId) {
        return filterSubscriptionService.getFilterSubscriptionById(filterSubscriptionId);
    }

    @GetMapping("/{subscriberId}")
    public List<FilterSubscription> getAllFilterSubscription(@PathVariable UUID subscriberId) {
        return filterSubscriptionService.getAllFilterSubscriptionById(subscriberId);
    }

    @DeleteMapping("/{filterSubscriptionId}")
    public void unsubscribe(@PathVariable UUID filterSubscriptionId) {
        filterSubscriptionService.unsubscribe(filterSubscriptionId);
    }
}
