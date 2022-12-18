package com.lovetotravel.travel;

import com.lovetotravel.travel.entity.canvas.node.SceneryNode;
import com.lovetotravel.travel.entity.canvas.dao.SceneryRelationRepository;
import com.lovetotravel.travel.entity.canvas.dao.SceneryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;


@SpringBootTest
class TravelserviceApplicationTests {


    @Autowired
    SceneryRepository sceneryRepository;

    @Autowired
    SceneryRelationRepository sceneryRelationRepository;

    @Test
    void contextLoads() throws IOException {
        List<SceneryNode> all = sceneryRepository.findAll();
        System.out.println("all = " + all);

//        List<SceneryRelation> allin = sceneryRelationRepository.findRelation();
//        System.out.println("allin = " + allin);

    }

//    DetectController detectController;
//
//
//    @Autowired
//    public void setDetectController(DetectController detectController) {
//        this.detectController = detectController;
//    }
//
//    @Test
//    void detect() {
//        HttpServletRequest request = new MockHttpServletRequest();
//        request.setAttribute("usrNo","81");
//        detectController.detect(request);
//    }

    }





