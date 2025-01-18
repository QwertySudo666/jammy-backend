package com.jammy.entities;

import com.jammy.models.Genre;
import jakarta.persistence.*;

@Entity
@Table("genres")
public class GenreEntity {
    @Id
    @Enumerated(EnumType.STRING)
    private Genre genre;
}
