package com.lovetotravel.travel.controller;


import com.lovetotravel.travel.entity.canvas.CustomNode;
import com.lovetotravel.travel.entity.canvas.dao.CityResponsitory;
import com.lovetotravel.travel.entity.canvas.dao.SceneryRepository;
import com.lovetotravel.travel.entity.canvas.node.*;
import com.lovetotravel.travel.result.Result;
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


    final SceneryRepository sceneryRepository;
    final CityResponsitory cityResponsitory;

    public CanvasController(SceneryRepository sceneryRepository, CityResponsitory cityResponsitory) {
        this.sceneryRepository = sceneryRepository;
        this.cityResponsitory = cityResponsitory;
    }

    @GetMapping("/{name}")
    public Result<CustomNode> getByName(@PathVariable("name") String name) {

        SceneryNode sceneryNode = sceneryRepository.findSceneryNodeByName(name);

        return Result.success(new CustomNode(sceneryNode.getName(), 1, sceneryNode));
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

        } else if (rel.equals("sceln")) {
            Set<CityNode> cityNodeSet = sceneryNode.getCity();
            for (CityNode c : cityNodeSet) {
                customNodes.add(new CustomNode(c.getName(), 2,c));
            }

        }

        return Result.success(customNodes);
    }

    @GetMapping("/query/city/{name}")
    public Result<List<CustomNode>> getRelByCityName(@PathVariable("name") String name) {

        System.out.println(name);

        CityNode cityNode = cityResponsitory.findCityNodeByName(name);

        System.out.println(cityNode);

        List<CustomNode> customNodes = new ArrayList<>();


        Set<SceneryNode> sceneryNodeSet = cityNode.getScenery();
        for (SceneryNode s : sceneryNodeSet) {
            customNodes.add(new CustomNode(s.getName(), 2, s));
        }


        return Result.success(customNodes);
    }


}

