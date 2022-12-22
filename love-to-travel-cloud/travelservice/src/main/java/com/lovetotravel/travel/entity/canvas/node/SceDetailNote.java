package com.lovetotravel.travel.entity.canvas.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.io.Serializable;


@Node("景区特色")
@Data
public class SceDetailNote implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public SceDetailNote() {}


    public SceDetailNote(String name) {
        this.name = name;
    }
}

