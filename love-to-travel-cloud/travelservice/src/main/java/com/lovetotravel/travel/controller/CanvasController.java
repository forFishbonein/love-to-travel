package com.lovetotravel.travel.controller;

import com.lovetotravel.travel.entity.Comment;
import com.lovetotravel.travel.entity.canvas.dao.SceneryRepository;
import com.lovetotravel.travel.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/canvas")
public class CanvasController {


    @Autowired
    SceneryRepository sceneryRepository;

    @GetMapping("/{id}")
    public Result<List<Comment>> getById(@PathVariable("id") Long id) {

        sceneryRepository.findById(id);


        return null;
    }

}
