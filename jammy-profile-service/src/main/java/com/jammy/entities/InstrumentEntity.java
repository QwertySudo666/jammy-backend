package com.jammy.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "instruments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstrumentEntity {
    @Id
    @Column(name = "instrument")
    private String instrument;

//    @ManyToMany(mappedBy = "instruments")
//    private List<ProfileEntity> profiles;
}
