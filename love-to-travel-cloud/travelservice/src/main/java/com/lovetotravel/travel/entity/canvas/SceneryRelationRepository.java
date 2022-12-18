package com.lovetotravel.travel.entity.canvas;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.management.relation.Relation;
import java.util.List;

@Repository
public interface SceneryRelationRepository extends Neo4jRepository<SceneryRelation, Long> {

//    @Query("MATCH p=(n:景区)-[r:in_area]->(m:区域) " +
//            "WHERE id(n)={startNode} and id(m)={endNode} and r.relation={relation}" +
//            "RETURN p")
//    List<SceneryRelation> findRelation(@Param("startNode") SceneryNode startNode,
//                                @Param("endNode") SceneryNode endNode,
//                                @Param("relation") String relation);

        @Query("MATCH p=(n:景区)-[r:in_area]->(m:区域) " +
            "RETURN p,r,m")
        List<SceneryRelation> findRelation();

}
