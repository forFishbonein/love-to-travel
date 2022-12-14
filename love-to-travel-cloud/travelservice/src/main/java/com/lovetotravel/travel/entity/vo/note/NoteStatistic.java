package com.lovetotravel.travel.entity.vo.note;

import com.lovetotravel.travel.entity.Note;
import lombok.Data;

import java.util.List;

@Data
public class NoteStatistic {

    private List<Note> likeList;

    private List<Note> starList;

    private List<Note> viewList;

}
