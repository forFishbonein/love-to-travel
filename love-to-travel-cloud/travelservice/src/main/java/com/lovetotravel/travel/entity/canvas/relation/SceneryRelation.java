package com.lovetotravel.travel.entity.canvas.relation;

import com.lovetotravel.travel.entity.canvas.node.SceneryNode;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Data
@RelationshipProperties
public class SceneryRelation {

    @RelationshipId
    private Long id;

    private String location;

    @TargetNode // 相当于@StartNode
    private final SceneryNode sceneryNode;

    public SceneryRelation(SceneryNode sceneryNode, String location) {
        this.sceneryNode = sceneryNode;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }


}
