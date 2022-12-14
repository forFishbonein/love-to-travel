package com.lovetotravel.travel;

import com.lovetotravel.travel.entity.Note;
import com.lovetotravel.travel.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.IOException;
import java.util.List;


@SpringBootTest
class TravelserviceApplicationTests {

    @Autowired
    NoteService noteService;

    @Autowired
    MongoTemplate mongoTemplate;


    @Test
    void contextLoads() throws IOException {

        List<Note> all = noteService.getAll();

        for (Note n : all) {
            Query query = new Query();
            query.addCriteria(Criteria.where("deleted").is("0"));
            query.addCriteria(Criteria.where("id").is(n.getId()));
            query.addCriteria(Criteria.where("star").is(null));
            query.limit(100);
            Update update = new Update();
            update.set("star", 0L);
            mongoTemplate.upsert(query, update, Note.class);

        }



    }

}