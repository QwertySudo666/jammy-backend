package com.jammy.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Filter {
    private Integer age;
    private String bioSearch;
    private String location;
    private List<String> genres;
    private List<String> instruments;
}
