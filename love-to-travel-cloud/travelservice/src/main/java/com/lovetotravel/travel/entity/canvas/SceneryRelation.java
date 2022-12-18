package com.lovetotravel.travel.entity.canvas;

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

    // 参数1是目标关系实体节点 参数2是关系属性
    //    Roles 参数1：Person实体，演员的出生年和姓名；参数2：演员名字列表（考虑到一个演员可能参演多个角色）

    public SceneryRelation(SceneryNode sceneryNode, String location) {
        this.sceneryNode = sceneryNode;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }


}
