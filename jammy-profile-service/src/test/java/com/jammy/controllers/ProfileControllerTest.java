package com.jammy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jammy.models.Genre;
import com.jammy.models.Instrument;
import com.jammy.models.Profile;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(
        properties = {
                "spring.kafka.consumer.auto-offset-reset=earliest",
        }
)
class ProfileControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
    //    static KafkaContainer kafka = new KafkaContainer("apache/kafka:latest");
//    @Container
//    static final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("apache/kafka:latest"));
    static final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("apache/kafka-native:3.8.0"));
//    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.5.1"));
//    @Container
//    static final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.5.1"));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
//        postgres
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

//        kafka
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @BeforeAll
    static void beforeAll() {
        postgres.start();
        kafka.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
        kafka.stop();
    }

    @Test
    void createProfile() throws Exception {
// given
        var profile = new Profile();
        profile.setUsername("IVanidZE");
        profile.setEmail("ivanidze@gmail.com");
        profile.setName("Ivan");
        profile.setAge(100);
        profile.setBio("Ivan - the oldest man in the dorm");
        profile.setLocation("Lviv");
        profile.setAvatarUrl("https://vanidze.com/avatar.png");
        profile.setGenres(List.of(Genre.PROGRESSIVE_METAL, Genre.DEATH_METAL));
        profile.setInstruments(List.of(Instrument.BASS));

        // when + then
        mockMvc.perform(post("/api/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profile)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(profile.getId().toString()))
                .andExpect(jsonPath("$.username").value("IVanidZE"))
                .andExpect(jsonPath("$.email").value("ivanidze@gmail.com"))
                .andExpect(jsonPath("$.name").value("Ivan"))
                .andExpect(jsonPath("$.age").value(100))
                .andExpect(jsonPath("$.bio").value("Ivan - the oldest man in the dorm"))
                .andExpect(jsonPath("$.location").value("Lviv"))
                .andExpect(jsonPath("$.avatarUrl").value("https://vanidze.com/avatar.png"))
                .andExpect(jsonPath("$.genres[0]").value("PROGRESSIVE_METAL"))
                .andExpect(jsonPath("$.genres[1]").value("DEATH_METAL"))
                .andExpect(jsonPath("$.instruments[0]").value("BASS"));
    }

    @Test
    void getProfile() {
    }

    @Test
    void searchProfiles() {
    }

    @Test
    void updateProfile() {
    }
}