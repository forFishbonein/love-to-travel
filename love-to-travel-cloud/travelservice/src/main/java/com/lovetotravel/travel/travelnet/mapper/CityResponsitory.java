package com.lovetotravel.travel.travelnet.mapper;

import com.lovetotravel.travel.travelnet.entity.CityNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityResponsitory extends Neo4jRepository<CityNode, Long> {

    CityNode findSceneryNodeById(Long id);

    CityNode findSceneryNodeByName(String name);


}