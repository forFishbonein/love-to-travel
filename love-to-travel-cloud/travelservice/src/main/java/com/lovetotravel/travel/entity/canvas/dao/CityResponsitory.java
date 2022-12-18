package com.lovetotravel.travel.entity.canvas.dao;

import com.lovetotravel.travel.entity.canvas.node.CityNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityResponsitory extends Neo4jRepository<CityNode, Long> {

    CityNode findSceneryNodeById(Long id);

    CityNode findSceneryNodeByName(String name);


}