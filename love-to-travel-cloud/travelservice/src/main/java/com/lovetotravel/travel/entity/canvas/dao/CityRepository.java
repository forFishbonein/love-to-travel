package com.lovetotravel.travel.entity.canvas.dao;

import com.lovetotravel.travel.entity.canvas.node.CityNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends Neo4jRepository<CityNode, Long> {

    CityNode findCityNodeById(Long id);

    CityNode findCityNodeByName(String name);



}