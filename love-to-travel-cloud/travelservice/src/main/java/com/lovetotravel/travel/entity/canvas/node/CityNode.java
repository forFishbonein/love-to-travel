package com.lovetotravel.travel.entity.canvas.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;


@Node("城市")
@Data
public class CityNode {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public CityNode(String name) {
        this.name = name;
    }




}

