package com.jammy.repos;

import com.jammy.entities.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, UUID> {
    @Query(value = """
            SELECT DISTINCT p.*
            FROM profiles p
            JOIN profile_genres pg ON p.id = pg.profile_id
            JOIN profile_instruments pi ON p.id = pi.profile_id
            WHERE (:age IS NULL OR p.age = :age)
              AND (:bioSearch IS NULL OR p.bio LIKE CONCAT('%', :bioSearch, '%'))
              AND (:location IS NULL OR p.location = :location)
              AND (COALESCE(:genres) IS NULL OR pg.genre IN (:genres))
              AND (COALESCE(:instruments) IS NULL OR pi.instrument IN (:instruments))
            """, nativeQuery = true)
    List<ProfileEntity> search(
            @Param("age") Integer age,
            @Param("bioSearch") String bioSearch,
            @Param("location") String location,
            @Param("genres") List<String> genres,
            @Param("instruments") List<String> instruments);

    @Query(value = """
            WITH checked_profiles AS (SELECT DISTINCT m.profile_id
                                      FROM matches m
                                      WHERE m.filter_id = :filterId)
            SELECT DISTINCT p.*
            FROM profiles p
            JOIN profile_genres pg ON p.id = pg.profile_id
            JOIN profile_instruments pi ON p.id = pi.profile_id
            WHERE (:age IS NULL OR p.age = :age)
              AND (:bioSearch IS NULL OR p.bio LIKE CONCAT('%', :bioSearch, '%'))
              AND (:location IS NULL OR p.location = :location)
              AND (COALESCE(:genres) IS NULL OR pg.genre IN (:genres))
              AND (COALESCE(:instruments) IS NULL OR pi.instrument IN (:instruments))
              AND p.id NOT IN (SELECT * FROM checked_profiles)
            """, nativeQuery = true)
    List<ProfileEntity> search(
            @Param("filterId") UUID filterId,
            @Param("age") Integer age,
            @Param("bioSearch") String bioSearch,
            @Param("location") String location,
            @Param("genres") List<String> genres,
            @Param("instruments") List<String> instruments);
}
