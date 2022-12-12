package com.lovetotravel.travel.service;

import com.lovetotravel.travel.entity.Note;
import com.lovetotravel.travel.entity.page.PageVo;
import com.lovetotravel.travel.entity.vo.NoteVo;

import java.util.List;

public interface NoteService {

    Note getById(String id);

    List<Note> getByUserId(String userId);

    List<Note> getAll();

    PageVo<Note> getPage(PageVo pageVo);

    void insert(NoteVo noteVo);

    void update(NoteVo noteVo);

    void removeById(String id);

    void removeList(String[] id);

    void incrComment(String id);

    void incrLike(String id);

    void incrView(String noteId);


}
