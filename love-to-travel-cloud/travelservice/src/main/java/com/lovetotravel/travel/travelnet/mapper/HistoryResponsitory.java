package com.lovetotravel.travel.travelnet.mapper;

import com.lovetotravel.travel.travelnet.entity.HistoryNote;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryResponsitory extends Neo4jRepository<HistoryNote, Long> {

    HistoryNote findSceneryNodeById(Long id);

    HistoryNote findSceneryNodeByName(String name);


}