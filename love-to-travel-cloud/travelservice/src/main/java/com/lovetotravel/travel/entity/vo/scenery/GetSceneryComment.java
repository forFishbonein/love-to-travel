package com.lovetotravel.travel.entity.vo.scenery;

import com.lovetotravel.travel.entity.SceneryComment;
import lombok.Data;

import java.util.List;

@Data
public class GetSceneryComment {

    private Integer total;

    private Double avgScore;

    private List<SceneryComment> sceneryCommentList;

}
