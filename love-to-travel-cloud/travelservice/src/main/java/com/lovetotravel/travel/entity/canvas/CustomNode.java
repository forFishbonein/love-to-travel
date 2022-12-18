package com.lovetotravel.travel.entity.canvas;

import lombok.Data;

@Data
public class CustomNode {

    private String id;

    private int group;

    private Object obj;

    public CustomNode(String id, int group, Object obj) {
        this.id = id;
        this.group = group;
        this.obj = obj;
    }

}
