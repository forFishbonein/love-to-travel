package com.lovetotravel.travel.entity.vo.scenery;

import lombok.Data;

@Data
public class UserScenery {

    private String userId;

    private String sceneryId;

    private String sceneryName;

    public UserScenery() {

    }

    public UserScenery(String userId, String sceneryId) {
        this.userId = userId;
        this.sceneryId = sceneryId;
    }

}
