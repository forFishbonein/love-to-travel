package com.lovetotravel.travel.entity.canvas.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;


@Node("Food")
@Data
public class FoodNote {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    public FoodNote(String name) {
        this.name = name;
    }

}

