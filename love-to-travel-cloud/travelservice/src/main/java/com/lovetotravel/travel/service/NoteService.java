package com.lovetotravel.travel.service;

import com.lovetotravel.travel.entity.Note;
import com.lovetotravel.travel.entity.vo.NoteVo;

public interface NoteService {

    Note getById(String id);

    void insert(NoteVo noteVo);

    void update(NoteVo noteVo);

    void removeById(String id);


}
