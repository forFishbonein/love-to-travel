package com.lovetotravel.travel.entity.canvas.relation;

import com.lovetotravel.travel.entity.canvas.node.CityNode;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Data
@RelationshipProperties
public class CityRelation {

    @RelationshipId
    private Long id;

    private String location;

    @TargetNode // 相当于@StartNode
    private final CityNode cityNode;

    public CityRelation(CityNode cityNode, String location) {
        this.cityNode = cityNode;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }


}
