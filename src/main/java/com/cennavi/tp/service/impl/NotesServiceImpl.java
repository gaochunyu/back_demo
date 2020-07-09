package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.NotesBean;
import com.cennavi.tp.beans.ProjectBean;
import com.cennavi.tp.dao.NotesDao;
import com.cennavi.tp.dao.UserNotesDao;
import com.cennavi.tp.service.NotesService;
import com.cennavi.tp.utils.MyDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NotesServiceImpl implements NotesService{
    @Resource
    private NotesDao notesDao;

    @Resource
    private UserNotesDao userNotesDao;

    @Value("${file_path}")
    private String fileSavePath;

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

    @Override
    public Map<String , Object> getNotesInfoById(int notesId , int userId) {
        List<NotesBean> list = notesDao.getNotesInfoById(notesId);

        boolean flag = userNotesDao.getStatus(notesId , userId);
        Map<String , Object> newMap = new HashMap<>();
        newMap.put("list",list);
        newMap.put("status",flag);
        return newMap;
    }

    @Override
    public boolean saveNotesInfo(int operation, int id, String title, String content, int sort, MultipartFile file, boolean isUpdate ,int uid) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String createTime = formatter.format(date);

        Integer status = 1;  //是否展示  0-录入中  1-待审核   2-审核成功   3-审核失败

        NotesBean notesBean = new NotesBean();
        notesBean.setTitle(title);
        notesBean.setContent(content);
        notesBean.setSort(sort);
        notesBean.setNotes_state(status);
        notesBean.setCreat_time(createTime);
        notesBean.setUid(uid);
        notesBean.setView_num(0);
        notesBean.setFavour_num(0);
        notesBean.setNotes_type(1);

        String format = "yyyy-MM-ddHHmmsss";
        String tpath = MyDateUtils.format(new Date(),format);
        if(file != null && !file.isEmpty()){
            // 创建保存图片的文件夹    D://test
            String rootPath = fileSavePath + "notes/"+tpath;
            File test = new File(rootPath + "/");
            if (!test.getParentFile().exists()) {
                // 判断 文件夹是否存在，如果不存在就新建
                test.getParentFile().mkdir();
            }

            String fileName = file.getOriginalFilename();
            // 拼接文件从存放的路径，创建project/img文件夹，保存对应的文件进去
            String path = fileSavePath + "notes/"+tpath;
            File f = new File(path + "/" + fileName);

            if (!f.getParentFile().exists()) {
                //判断文件父目录是否存在
                f.getParentFile().mkdir();
            }

            try{
                file.transferTo(f); //保存文件
                notesBean.setFile("notes/" + tpath + "/" + fileName);
            }catch (IllegalStateException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else{
            notesBean.setFile("");
        }

        boolean fileIsUpdate = true;
        if(id > 0 && file == null && !isUpdate){  //修改操作
            fileIsUpdate = false;// 不去修改 项目 图片地址
        }

        boolean flag = notesDao.saveNotesInfo(id, fileIsUpdate , notesBean);
        return flag;
    }

    @Override
    public boolean deleteNotesInfo(int id) {
        //删除封面
        NotesBean notesBean = notesDao.findById(id);
        if(notesBean!=null){
            String path = notesBean.getFile();
            File file = new File(fileSavePath+path);
            if(file.exists()){
                file.delete();
            }
        }
        boolean flag = notesDao.deleteNotesInfo(id);
        return flag;
    }

    @Override
    public boolean updateStatus(int id, int status) {
        boolean flag = notesDao.updateStatus(id, status);
        return flag;
    }

    @Override
    public boolean updateViewNum(int id) {
        boolean flag = false;
        NotesBean notesBean = notesDao.findById(id);
        if(notesBean!=null){
            Integer view_num = notesBean.getView_num();
            flag = notesDao.updateViewNum(id,view_num);
        }

        return flag;
    }

    @Override
    public boolean updateFavourNum(int id, boolean type,int userId) {
        boolean flag = false;
        NotesBean notesBean = notesDao.findById(id);
        if(notesBean!=null){
            Integer favour_num = notesBean.getFavour_num();
            flag = notesDao.updateFavourNum(id,type,favour_num);
            userNotesDao.saveUserNotes(id,type,userId);
        }

        return flag;
    }

}
