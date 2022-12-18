package com.lovetotravel.travel.entity.canvas.node;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
//import org.neo4j.omg
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Node("景区")
@Data
public class SceneryNode implements Serializable {

    //景区节点属性
    @Id
    //id
    @GeneratedValue
    private Long id;

    @Property
    private String name;

    @Property
    private String score;

    @Property
    private String level;

    @Property
    private String opentime;

    @Property
    private String season;

    @Property
    private String ticket;

    public SceneryNode() {

    }

    public SceneryNode(Long id, String name, String score, String level, String opentime, String season, String ticket) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.level = level;
        this.opentime = opentime;
        this.season = season;
        this.ticket = ticket;
    }

    //景区--地区
    @Relationship(type = "is_area", direction = Relationship.Direction.INCOMING)
    private Set<DistrictNode> sceneryLocated = new HashSet<>();

    //景区--推荐食物
    @Relationship(type = "recommand_food", direction = Relationship.Direction.INCOMING)
    private Set<FoodNote> sceneryReFood = new HashSet<>();

    //景区--历史事件
    @Relationship(type = "is_related", direction = Relationship.Direction.INCOMING)
    private Set<HistoryNote> sceneryRelHistory = new HashSet<>();

    //景区--特色
    @Relationship(type = "is_detail", direction = Relationship.Direction.INCOMING)
    private Set<SceDetailNote> sceneryHasDet = new HashSet<>();

}

