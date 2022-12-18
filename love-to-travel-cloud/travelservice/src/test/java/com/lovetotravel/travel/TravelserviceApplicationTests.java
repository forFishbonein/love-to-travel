package com.lovetotravel.travel;

import com.lovetotravel.travel.controller.DetectController;
import com.lovetotravel.travel.entity.Scenery;
import com.lovetotravel.travel.travelnet.entity.SceneryNode;
import com.lovetotravel.travel.travelnet.entity.SceneryRelationShip;
import com.lovetotravel.travel.travelnet.mapper.SceneryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.repository.query.QueryFragmentsAndParameters;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@SpringBootTest
class TravelserviceApplicationTests {


//    @Autowired
//    SceneryRepository sceneryRepository;


//    @Test
//    void contextLoads() throws IOException {
//
//        List<SceneryNode> all = sceneryRepository.findAll();
//        System.out.println("all = " + all);
//
//        List<SceneryRelationShip> allin = sceneryRepository.getSceneryLocated();
//        System.out.println("allin = " + allin);
//
//    }

    DetectController detectController;


    @Autowired
    public void setDetectController(DetectController detectController) {
        this.detectController = detectController;
    }

    @Test
    void detect() {
        HttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("usrNo","81");
        detectController.detect(request);
    }
}





