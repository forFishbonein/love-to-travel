package com.lovetotravel.travel;

import com.lovetotravel.travel.entity.Note;
import com.lovetotravel.travel.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.IOException;


@SpringBootTest
class TravelserviceApplicationTests {

    @Autowired
    NoteService noteService;

    @Autowired
    MongoTemplate mongoTemplate;


    @Test
    void contextLoads() throws IOException {


        Query query = new Query();
        query.addCriteria(Criteria.where("noteId").is("6398747b3dbf3dc4b86cbcf3"));
        Note one = mongoTemplate.findOne(query, Note.class);
        System.out.println("one = " + one);


    }

}