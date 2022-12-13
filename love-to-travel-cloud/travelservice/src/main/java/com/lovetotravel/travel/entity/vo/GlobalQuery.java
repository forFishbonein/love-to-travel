package com.lovetotravel.travel.entity.vo;

import com.lovetotravel.travel.entity.City;
import com.lovetotravel.travel.entity.Note;
import com.lovetotravel.travel.entity.Scenery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GlobalQuery {

    @ApiModelProperty("游记列表")
    private List<Note> noteList;

    @ApiModelProperty("城市列表")
    private List<City> cityList;

    @ApiModelProperty("景点列表")
    private List<Scenery> sceneryList;

}
