package com.lovetotravel.travel.entity.canvas.dao;

import com.lovetotravel.travel.entity.canvas.node.FoodNote;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodResponsitory extends Neo4jRepository<FoodNote, Long> {

    FoodNote findSceneryNodeById(Long id);

    FoodNote findSceneryNodeByName(String name);


}
