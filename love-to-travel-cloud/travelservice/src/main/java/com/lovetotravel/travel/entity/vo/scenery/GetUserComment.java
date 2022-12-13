package com.lovetotravel.travel.entity.vo.scenery;

import com.lovetotravel.travel.entity.SceneryComment;
import lombok.Data;

import java.util.List;

@Data
public class GetUserComment {

    private Integer total;

    private List<SceneryComment> sceneryCommentList;

}
