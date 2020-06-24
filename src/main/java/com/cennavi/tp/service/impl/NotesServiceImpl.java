package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.NotesBean;
import com.cennavi.tp.dao.NotesDao;
import com.cennavi.tp.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotesServiceImpl implements NotesService{
    @Resource
    private NotesDao notesDao;

    @Override
    public Map<String , Object> getNotesList(Integer limitSize, Integer curPage, String notesType, String status, Integer userId) {
        Integer offsetNum = limitSize * (curPage - 1); //每页开始的条数  从第 offsetNum+1条开始查询，总共查询limitSize条
        List<NotesBean> list = notesDao.getNotesList(limitSize,offsetNum,notesType ,status , userId);

        Integer num = notesDao.getNotesListNum(notesType,status);
        Map<String , Object> newMap = new HashMap<>();
        newMap.put("list",list);
        newMap.put("totalSize",num);
        return newMap;
    }
}
