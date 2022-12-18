package com.lovetotravel.travel.entity.canvas;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
//import org.neo4j.omg
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.io.Serializable;


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


//    //景区--地区
//    @Relationship(type = "is_area", direction = Relationship.Direction.INCOMING)
//    private String sceneryLocated = "";
//
//    //景区--推荐食物
//    @Relationship(type = "is_area", direction = Relationship.Direction.INCOMING)
//    private String sceneryReFood = "";
//
//    //景区--历史事件
//    @Relationship(type = "is_area", direction = Relationship.Direction.INCOMING)
//    private String sceneryRelHistory = "";
//
//    //景区--特色
//    @Relationship(type = "is_area", direction = Relationship.Direction.INCOMING)
//    private String sceneryHasDet = "";

}

