package com.jammy.jammymediaservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("media_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaDataEntity {

    @Id
    @Column("id")
    private UUID id;

    @Column("profile_id")
    private UUID profileId;

    @Column("name")
    private String name;

    @Column("type")
    private String type;

    @Column("link")
    private String link;

}