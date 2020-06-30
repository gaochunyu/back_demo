package com.cennavi.tp.dao;

import com.cennavi.tp.beans.NotesBean;
import com.cennavi.tp.common.base.dao.BaseDao;


import java.util.List;

public interface NotesDao extends BaseDao<NotesBean> {
    List<NotesBean> getNotesList(Integer limitSize, Integer offsetNum , String notesType ,String status , Integer userId);
    Integer getNotesListNum(String notesType , String status);

    List<NotesBean> getNotesInfoById(Integer notesId);
    boolean saveNotesInfo(int id , boolean fileIsUpdate , NotesBean notesBean);
    boolean deleteNotesInfo(int id);
    boolean updateStatus(int id , int status);
    boolean updateViewNum(int id , int preNum);
    boolean updateFavourNum(int id , boolean type ,int preNum);

}
