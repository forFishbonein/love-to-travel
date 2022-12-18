package com.lovetotravel.travel.entity.canvas;

import lombok.Data;

@Data
public class CustomLink {

    private String source;

    private String target;

    private int value;

    public CustomLink(String source, String target, int value) {
        this.source = source;
        this.target = target;
        this.value = value;
    }
}
