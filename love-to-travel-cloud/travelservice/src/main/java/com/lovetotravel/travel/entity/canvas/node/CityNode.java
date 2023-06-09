package com.lovetotravel.travel.entity.canvas.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Node("城市")
@Data
public class CityNode implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String name;

    public CityNode() {}

    public CityNode(String name) {
        this.name = name;
    }

    //城市--景区
    @Relationship(type = "sceIn", direction = Relationship.Direction.INCOMING)
    private Set<SceneryNode> scenery = new HashSet<>();



}

