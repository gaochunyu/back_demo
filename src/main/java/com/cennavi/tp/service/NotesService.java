package com.cennavi.tp.service;

import com.cennavi.tp.beans.NotesBean;

import java.util.List;
import java.util.Map;

public interface NotesService {

    Map<String , Object> getNotesList(Integer limitSize, Integer curPage , String notesType , String status , Integer userId);

}
