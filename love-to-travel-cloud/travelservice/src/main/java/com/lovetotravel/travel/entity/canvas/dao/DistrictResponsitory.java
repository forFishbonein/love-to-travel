package com.lovetotravel.travel.entity.canvas.dao;

import com.lovetotravel.travel.entity.canvas.node.DistrictNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictResponsitory extends Neo4jRepository<DistrictNode, Long> {

    DistrictNode findSceneryNodeById(Long id);

    DistrictNode findSceneryNodeByName(String name);



}
