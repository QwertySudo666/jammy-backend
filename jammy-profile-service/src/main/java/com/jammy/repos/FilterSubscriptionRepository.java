package com.jammy.repos;

import com.jammy.entities.FilterSubscriptionEntity;
import com.jammy.entities.ProfileEntity;
import com.jammy.models.Genre;
import com.jammy.models.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FilterSubscriptionRepository extends JpaRepository<FilterSubscriptionEntity, UUID> {
    List<FilterSubscriptionEntity> findAllBySubscriberId(UUID subscriberId);

    @Query(value = """
            SELECT DISTINCT fs.*
            FROM filter_subscription fs
            JOIN filter_subscription_genres fsg ON fs.id = fsg.filter_id
            JOIN filter_subscription_instruments fsi ON fs.id = fsi.filter_id
            WHERE (:age IS NULL OR fs.age = :age)
              AND (:bioSearch IS NULL OR fs.bio_search LIKE CONCAT('%', :bioSearch, '%'))
              AND (:location IS NULL OR fs.location = :location)
              AND (COALESCE(:genres) IS NULL OR fsg.genre IN (:genres))
              AND (COALESCE(:instruments) IS NULL OR fsi.instrument IN (:instruments))
            """, nativeQuery = true)
    List<FilterSubscriptionEntity> search(
            @Param("age") Integer age,
            @Param("bioSearch") String bioSearch,
            @Param("location") String location,
            @Param("genres") List<String> genres,
            @Param("instruments") List<String> instruments);

    @Query(value = """
            WITH checked_filters AS (SELECT DISTINCT m.filter_id
                                     FROM matches m
                                     WHERE m.profile_id = :id)
            SELECT DISTINCT fs.*
            FROM filter_subscription fs
            JOIN filter_subscription_genres fsg ON fs.id = fsg.filter_id
            JOIN filter_subscription_instruments fsi ON fs.id = fsi.filter_id
            WHERE (:age IS NULL OR fs.age = :age)
              AND (:bioSearch IS NULL OR fs.bio_search LIKE CONCAT('%', :bioSearch, '%'))
              AND (:location IS NULL OR fs.location = :location)
              AND (COALESCE(:genres) IS NULL OR fsg.genre IN (:genres))
              AND (COALESCE(:instruments) IS NULL OR fsi.instrument IN (:instruments))
              AND fs.id NOT IN (SELECT * FROM checked_filters)
            """, nativeQuery = true)
    List<FilterSubscriptionEntity> searchSubscriptions(
            @Param("id") UUID id,
            @Param("age") Integer age,
            @Param("bioSearch") String bioSearch, //should be :bioSearch like %filter.bio_search%
            @Param("location") String location,
            @Param("genres") List<String> genres,
            @Param("instruments") List<String> instruments);
}
