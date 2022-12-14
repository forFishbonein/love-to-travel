package com.lovetotravel.travel.service;

import com.lovetotravel.travel.entity.Note;
import com.lovetotravel.travel.entity.page.PageVo;
import com.lovetotravel.travel.entity.page.QueryPageVo;
import com.lovetotravel.travel.entity.vo.note.NoteLike;
import com.lovetotravel.travel.entity.vo.note.NoteStar;
import com.lovetotravel.travel.entity.vo.note.NoteVo;

import java.util.List;

public interface NoteService {

    Note getById(String id);

    List<Note> getByUserId(String userId);

    List<Note> getAll();

    PageVo<Note> getPage(PageVo pageVo);

    QueryPageVo<Note> fuzzyQuery(QueryPageVo pageVo);

    void insert(NoteVo noteVo);

    void update(NoteVo noteVo);

    void removeById(String id);

    void removeList(String[] id);

    void incrComment(String id);

    void incrView(String noteId);

    void like(NoteLike noteLike);

    Boolean isLike (NoteLike noteLike);

    void unLike(NoteLike noteLike);

    void star(NoteStar noteStar);

    void unStar(NoteStar noteStar);

    Boolean isStar(NoteStar noteStar);

    List<Note> getStarByUserId(Long id);


}
