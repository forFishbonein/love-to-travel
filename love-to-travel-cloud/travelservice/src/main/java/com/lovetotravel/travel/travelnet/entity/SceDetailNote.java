package com.lovetotravel.travel.travelnet.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;


@Node("SceDetail")
@Data
public class SceDetailNote {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public SceDetailNote(String name) {
        this.name = name;
    }
}

