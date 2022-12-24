package com.lovetotravel.travel.controller;


import com.lovetotravel.travel.entity.City;
import com.lovetotravel.travel.entity.canvas.CustomNode;
import com.lovetotravel.travel.entity.canvas.dao.CityRepository;
import com.lovetotravel.travel.entity.canvas.dao.SceneryRepository;
import com.lovetotravel.travel.entity.canvas.node.*;
import com.lovetotravel.travel.result.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Api(tags = "知识图谱 ")
@RestController
@RequestMapping("/canvas")
public class CanvasController {


    final CityRepository cityRepository;
    final SceneryRepository sceneryRepository;

    public CanvasController(CityRepository cityRepository, SceneryRepository sceneryRepository) {
        this.cityRepository = cityRepository;
        this.sceneryRepository = sceneryRepository;
    }

    @GetMapping("/{name}")
    public Result<CustomNode> getByName(@PathVariable("name") String name) {

        SceneryNode sceneryNode = sceneryRepository.findSceneryNodeByName(name);

        return Result.success(new CustomNode(sceneryNode.getName(), 1, sceneryNode));
    }

    @GetMapping("/city/{name}")
    public Result<List<CustomNode>> getRelByCityName(@PathVariable("name") String name) {

        System.out.println(name);

        CityNode cityNode = cityRepository.findCityNodeByName(name);

        System.out.println(cityNode);

        List<CustomNode> customNodes = new ArrayList<>();


        Set<SceneryNode> sceneryNodeSet = cityNode.getScenery();
        for (SceneryNode s : sceneryNodeSet) {
            customNodes.add(new CustomNode(s.getName(), 2, s));
        }


        return Result.success(customNodes);
    }

    @GetMapping("/city")
    public Result<List<CustomNode>> getAllCity() {

        List<CityNode> all = cityRepository.findAll();

        List<CustomNode> customNodes = new ArrayList<>();
        for (CityNode c : all) {
            customNodes.add(new CustomNode(c.getName(), 1, c));
        }
        return Result.success(customNodes);
    }


    @GetMapping
    public Result<List<CustomNode>> getAll() {

        List<SceneryNode> all = sceneryRepository.findAll();

        List<CustomNode> customNodes = new ArrayList<>();
        for (SceneryNode s : all) {
            customNodes.add(new CustomNode(s.getName(), 1, s));
        }
        return Result.success(customNodes);
    }

    @GetMapping("/query/{rel}/{name}")
    public Result<List<CustomNode>> getRelByName(@PathVariable("rel") String rel, @PathVariable("name") String name) {

        SceneryNode sceneryNode = sceneryRepository.findSceneryNodeByName(name);

        System.out.println(sceneryNode);

        List<CustomNode> customNodes = new ArrayList<>();

        if (rel.equals("in_area")) {
            Set<DistrictNode> districtNodeSet = sceneryNode.getSceneryLocated();
            for (DistrictNode d : districtNodeSet) {
                customNodes.add(new CustomNode(d.getName(), 2, d));
            }
        } else if (rel.equals("recommand_food")) {
            Set<FoodNote> foodNoteSet = sceneryNode.getSceneryReFood();
            for (FoodNote d : foodNoteSet) {
                customNodes.add(new CustomNode(d.getName(), 2, d));
            }

        } else if (rel.equals("is_related")) {
            Set<HistoryNote> historyNoteSet = sceneryNode.getSceneryRelHistory();
            for (HistoryNote d : historyNoteSet) {
                customNodes.add(new CustomNode(d.getName(), 2, d));
            }

        } else if (rel.equals("is_detail")) {
            Set<SceDetailNote> sceDetailNoteSet = sceneryNode.getSceneryHasDet();
            for (SceDetailNote d : sceDetailNoteSet) {
                customNodes.add(new CustomNode(d.getName(), 2, d));
            }

        }

        return Result.success(customNodes);
    }



}

