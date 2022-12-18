package com.lovetotravel.travel.entity.canvas;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SceneryRepository extends Neo4jRepository<SceneryNode, Long> {

    SceneryNode findSceneryNodeById(Long id);

    SceneryNode findSceneryNodeByName(String name);



}
