package com.jammy.dtos;

import com.jammy.models.Genre;
import com.jammy.models.Instrument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDto {
    private String id;
    private String username;
    private String firstName;
    private String secondName;
    private String email;
    private Integer age;
    private List<Genre> genres;
    private List<Instrument> instruments;
    private String bio;
    private String location;
    private String avatarUrl;
}
