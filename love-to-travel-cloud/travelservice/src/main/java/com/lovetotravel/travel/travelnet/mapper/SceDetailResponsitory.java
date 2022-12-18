package com.lovetotravel.travel.travelnet.mapper;

import com.lovetotravel.travel.travelnet.entity.SceDetailNote;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SceDetailResponsitory extends Neo4jRepository<SceDetailNote, Long> {

    SceDetailNote findSceneryNodeById(Long id);

    SceDetailNote findSceneryNodeByName(String name);


}