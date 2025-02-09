package com.jammy.controllers;

import com.jammy.dto.RegisterUserRequest;
import com.jammy.dto.UserModel;
import com.jammy.kafka.KafkaProducer;
import com.jammy.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final KafkaProducer kafkaProducer;

    @GetMapping("/")
    public String hello(Authentication authentication) {
        return "Hello, " + authentication.getName() + "!";
    }

    @PostMapping("/register")
    public ResponseEntity<UserModel> register(@RequestBody RegisterUserRequest request) {
        var user = userService.register(request);
        kafkaProducer.send("topic1", user.toString());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}