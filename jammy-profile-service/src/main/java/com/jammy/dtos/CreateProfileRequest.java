package com.jammy.dtos;

import com.jammy.models.Genre;
import com.jammy.models.Instrument;
import lombok.Data;

import java.util.List;

@Data
public class CreateProfileRequest {
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
