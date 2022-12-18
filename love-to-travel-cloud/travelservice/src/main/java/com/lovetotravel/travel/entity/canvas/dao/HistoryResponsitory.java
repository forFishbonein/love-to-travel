package com.lovetotravel.travel.entity.canvas.dao;

import com.lovetotravel.travel.entity.canvas.node.HistoryNote;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryResponsitory extends Neo4jRepository<HistoryNote, Long> {

    HistoryNote findSceneryNodeById(Long id);

    HistoryNote findSceneryNodeByName(String name);


}