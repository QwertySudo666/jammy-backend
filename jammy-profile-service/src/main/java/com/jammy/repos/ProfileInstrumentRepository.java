package com.jammy.repos;

import com.jammy.entities.ProfileInstrumentEntity;
import com.jammy.entities.ids.ProfileInstrumentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProfileInstrumentRepository extends JpaRepository<ProfileInstrumentEntity, ProfileInstrumentId> {
    void deleteByProfileIdAndInstrumentIn(UUID id, List<String> instruments);
}
