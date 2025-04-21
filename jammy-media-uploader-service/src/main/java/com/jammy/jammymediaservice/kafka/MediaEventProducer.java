package com.jammy.jammymediaservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jammy.jammymediaservice.models.MediaUploadedEvent;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class MediaEventProducer {
//        private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, MediaUploadedEvent> kafkaTemplate;
//    private final ObjectMapper objectMapper;

//    public void sendMediaUploaded(MediaUploadedEvent mediaUploadedEvent) {
//        try {
//            var jsonEvent = objectMapper.writeValueAsString(mediaUploadedEvent);
//            kafkaTemplate.send("media-uploaded", jsonEvent);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to serialize MediaUploadedEvent", e);
//        }
//    }

//    public Mono<Void> sendMediaUploaded(MediaUploadedEvent mediaUploadedEvent) {
//        return Mono.fromFuture(() ->
//                kafkaTemplate.send("media-uploaded", mediaUploadedEvent)
//        ).then();
//    }

    public Mono<Void> sendMediaUploaded(MediaUploadedEvent mediaUploadedEvent) {
        return Mono.create(sink -> {
            try {
                kafkaTemplate.send("media-uploaded", mediaUploadedEvent);
                sink.success();
            } catch (Exception e) {
                sink.error(e);
            }
        });
    }
}
