package com.lovetotravel.travel.service;

import com.lovetotravel.travel.entity.Note;
import com.lovetotravel.travel.entity.vo.NoteVo;

import java.util.List;

public interface NoteService {

    Note getById(String id);

    List<Note> getByUserId(String userId);

    List<Note> getAll();

    void insert(NoteVo noteVo);

    void update(NoteVo noteVo);

    void removeById(String id);


}
