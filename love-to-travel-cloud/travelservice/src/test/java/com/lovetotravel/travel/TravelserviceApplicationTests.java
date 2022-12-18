package com.lovetotravel.travel;

import com.baomidou.mybatisplus.annotation.TableId;
import com.lovetotravel.travel.entity.canvas.SceneryRelationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class TravelserviceApplicationTests {



    @Autowired
    SceneryRelationRepository sceneryRelationRepository;

    @Test
    void testRelation() {
//        sceneryRelationRepository.findRelation()
    }



}





