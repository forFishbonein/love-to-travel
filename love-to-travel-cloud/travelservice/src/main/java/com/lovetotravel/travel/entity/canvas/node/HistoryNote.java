package com.lovetotravel.travel.entity.canvas.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.io.Serializable;


@Node("History")
@Data
public class HistoryNote implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public HistoryNote() {}


    public HistoryNote(String name) {
        this.name = name;
    }
}

