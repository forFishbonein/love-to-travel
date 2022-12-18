package com.lovetotravel.travel.controller;

import com.lovetotravel.travel.entity.Comment;
import com.lovetotravel.travel.entity.canvas.CustomNode;
import com.lovetotravel.travel.entity.canvas.SceneryNode;
import com.lovetotravel.travel.entity.canvas.SceneryRepository;
import com.lovetotravel.travel.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/canvas")
public class CanvasController {


    @Autowired
    SceneryRepository sceneryRepository;

    @GetMapping("/{name}")
    public Result<CustomNode> getByName(@PathVariable("name") String name) {

        SceneryNode sceneryNode = sceneryRepository.findSceneryNodeByName(name);

        return Result.success(new CustomNode(sceneryNode.getName(),1, sceneryNode));
    }

    @GetMapping
    public Result<List<CustomNode>> getAll() {

        List<SceneryNode> all = sceneryRepository.findAll();

        List<CustomNode> customNodes = new ArrayList<>();
        for(SceneryNode s : all){
            customNodes.add(new CustomNode(s.getName(),1, s));
        }
        return Result.success(customNodes);
    }

    @GetMapping("/query/{relationship}/{name}")
    public Result<List<CustomNode>> getRelByName(@PathVariable("relationship") String rel,@PathVariable("name") String name) {

        SceneryNode sceneryNode = sceneryRepository.findSceneryNodeByName(name);

        List<CustomNode> customNodes = new ArrayList<>();

//        Set<Movie> movieSet = personService.getMoviesByRelationship(person,rel);
//
//        for(Movie movie : movieSet){
//            customNodes.add(new CustomNode(movie.getTitle(),2, movie));
//            //customLinks.add(new CustomLink(person.getName(),movie.getTitle(),5));
//        }
//
//
//        return Result.success(customNodes);

        return null;
    }




}

