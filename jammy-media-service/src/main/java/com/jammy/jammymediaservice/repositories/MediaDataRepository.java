package com.jammy.jammymediaservice.repositories;

import com.jammy.jammymediaservice.entities.MediaDataEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MediaDataRepository extends ReactiveCrudRepository<MediaDataEntity, UUID> {
}
