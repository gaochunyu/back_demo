package com.cennavi.tp.service;

import com.cennavi.tp.beans.NotesBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface NotesService {

    Map<String , Object> getNotesList(Integer limitSize, Integer curPage , String notesType , String status , Integer userId);
    Map<String , Object> getNotesInfoById(int notesId , int userId);

    boolean saveNotesInfo(int operation , int id , String title , String content , int sort  , MultipartFile file , boolean isUpdate ,int uid);

    boolean deleteNotesInfo(int id);

    boolean updateStatus(int id , int status);

    boolean updateViewNum(int id);
    boolean updateFavourNum(int id , boolean type , int userId);

}
