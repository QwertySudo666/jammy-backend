package com.jammy.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "genres")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreEntity {
    @Id
    @Column(name = "genre")
    private String genre;

//    @ManyToMany(mappedBy = "genres")
//    private List<ProfileEntity> profiles;
}
