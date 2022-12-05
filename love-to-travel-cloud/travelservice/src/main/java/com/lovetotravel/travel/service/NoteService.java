package com.lovetotravel.travel.service;

import com.lovetotravel.travel.entity.Note;

public interface NoteService {

    Note getById(String id);

    void insert(Note note);

    void update(Note note);

    void removeById(String id);


}
