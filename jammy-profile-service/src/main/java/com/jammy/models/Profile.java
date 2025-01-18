package com.jammy.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {
    private String username;
    private String firstName;
    private String secondName;
    private String email;
    private String password;
    private Integer age;
    private List<Genre> genres;
    private List<Instrument> instruments;
    private String bio;
    private String location;
    private String avatarUrl;
}
