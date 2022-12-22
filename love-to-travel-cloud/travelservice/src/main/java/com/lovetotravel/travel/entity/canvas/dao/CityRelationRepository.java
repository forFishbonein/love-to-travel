package com.lovetotravel.travel.entity.canvas.dao;

import com.lovetotravel.travel.entity.canvas.relation.CityRelation;
import com.lovetotravel.travel.entity.canvas.relation.SceneryRelation;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRelationRepository extends Neo4jRepository<CityRelation, Long> {

//    @Query("MATCH p=(n:景区)-[r:in_area]->(m:区域) " +
//            "WHERE id(n)={startNode} and id(m)={endNode} and r.relation={relation}" +
//            "RETURN p")
//    List<SceneryRelation> findRelation(@Param("startNode") SceneryNode startNode,
//                                @Param("endNode") SceneryNode endNode,
//                                @Param("relation") String relation);

        @Query("MATCH p=(n:城市)-[r:sceln]->(m:景区) " +
            "RETURN p,r,m")
        List<CityRelation> findRelation();

}
