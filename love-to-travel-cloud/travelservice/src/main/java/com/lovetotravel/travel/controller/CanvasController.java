package com.lovetotravel.travel.controller;


import com.lovetotravel.travel.entity.canvas.CustomNode;
import com.lovetotravel.travel.entity.canvas.dao.SceneryRepository;
import com.lovetotravel.travel.entity.canvas.node.DistrictNode;
import com.lovetotravel.travel.entity.canvas.node.SceneryNode;
import com.lovetotravel.travel.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

        Set<DistrictNode> districtNodeSet = sceneryNode.getSceneryLocated();

        for(DistrictNode d : districtNodeSet){
            customNodes.add(new CustomNode(d.getName(),2, d));
        }

        return Result.success(customNodes);
    }



}

