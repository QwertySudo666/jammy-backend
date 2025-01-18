package com.jammy.entities;

import com.jammy.models.Instrument;
import jakarta.persistence.*;

@Entity
@Table("instruments")
public class InstrumentEntity {
    @Id
    @Enumerated(EnumType.STRING)
    private Instrument instrument;
}
