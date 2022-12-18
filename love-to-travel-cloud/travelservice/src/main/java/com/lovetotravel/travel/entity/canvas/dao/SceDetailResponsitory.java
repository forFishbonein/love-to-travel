package com.lovetotravel.travel.entity.canvas.dao;

import com.lovetotravel.travel.entity.canvas.node.SceDetailNote;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SceDetailResponsitory extends Neo4jRepository<SceDetailNote, Long> {

    SceDetailNote findSceneryNodeById(Long id);

    SceDetailNote findSceneryNodeByName(String name);


}