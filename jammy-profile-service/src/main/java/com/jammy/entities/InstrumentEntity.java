package com.jammy.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "instrument")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstrumentEntity {
    @Id
    private String instrument;
}
