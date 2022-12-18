package com.lovetotravel.travel.travelnet.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;


@Node("区域")
@Data
public class DistrictNode {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public DistrictNode(String name) {
        this.name = name;
    }
}

