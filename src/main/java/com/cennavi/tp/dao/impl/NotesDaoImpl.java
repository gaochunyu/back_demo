package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.NotesBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.NotesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.time.DayOfWeek;
import java.util.List;

@Repository
public class NotesDaoImpl extends BaseDaoImpl<NotesBean> implements NotesDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<NotesBean> getNotesList(Integer limitSize, Integer offsetNum, String notesType, String status, Integer userId) {
        String sql = "";
        if(userId == 0){
            sql = "select notes.id , notes.title,notes.content,notes.notes_state,notes.view_num,notes.favour_num,notes.creat_time,notes.file,type.name ,userinfo.username from notes as notes LEFT JOIN notes_type as type on notes.notes_type = type.id LEFT JOIN userinfo on notes.uid = userinfo.id" +
                    " where notes.notes_type in ("+ notesType + ") and notes.notes_state in (" + status + ")  order by notes.sort desc LIMIT "+ limitSize +" OFFSET " + offsetNum;
        }

        if(userId != 0){
            sql = "select notes.id , notes.title,notes.content,notes.notes_state,notes.view_num,notes.favour_num,notes.creat_time,notes.file,type.name,userinfo.username from notes as notes LEFT JOIN notes_type as type on notes.notes_type = type.id LEFT JOIN userinfo on notes.uid = userinfo.id " +
                    " where notes.notes_type in ("+ notesType + ") and notes.notes_state in (" + status + ") and notes.uid = "+ userId +" order by notes.sort desc LIMIT "+ limitSize +" OFFSET " + offsetNum;
        }
        return jdbcTemplate.query(sql , BeanPropertyRowMapper.newInstance(NotesBean.class));
    }

    @Override
    public Integer getNotesListNum(String notesType, String status) {
        String sql = "select  count(DISTINCT id)  FROM notes where notes_type in ("+ notesType +")  and notes_state in (" + status + ") ";
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }

    @Override
    public List<NotesBean> getNotesInfoById(Integer notesId) {
        String sql = "select title,content,file,view_num,favour_num,creat_time,username from notes LEFT JOIN userinfo on notes.uid = userinfo.id where notes.id = " + notesId;
        return jdbcTemplate.query(sql , BeanPropertyRowMapper.newInstance(NotesBean.class));
    }

    @Override
    public boolean saveNotesInfo(int id, boolean fileIsUpdate, NotesBean notesBean) {
        boolean flag = false;
        if(id == 0){ //新增
            String sql = "insert into notes (title, content, file, sort, notes_state, creat_time, view_num,favour_num,uid ,notes_type) values ('"
                    +notesBean.getTitle()+"','"+notesBean.getContent()+"','"+notesBean.getFile()+"',"+notesBean.getSort()+","+notesBean.getNotes_state()+",'"+notesBean.getCreat_time()+"',"
                    +notesBean.getView_num()+","+notesBean.getFavour_num()+","+notesBean.getUid()+","+notesBean.getNotes_type()+")";

            int result = jdbcTemplate.update(sql);
            if(result > 0){
                flag = true;
            }
        }
        if(id > 0){  //修改
            //System.out.println("修改");
            String sql = "";
            if(fileIsUpdate){  //修改了 文件
                sql = "update notes set  title = '" +notesBean.getTitle() + "', content = '" + notesBean.getContent()
                        + "', sort = " + notesBean.getSort() + ", file = '" + notesBean.getFile() + "', notes_state = " + notesBean.getNotes_state()
                        + ", creat_time = '" + notesBean.getCreat_time() + "' where id = " + id;
            }else{  //未修改文件
                sql = "update notes set  title = '" +notesBean.getTitle() + "', content = '" + notesBean.getContent()
                        + "', sort = " + notesBean.getSort() + ", notes_state = " + notesBean.getNotes_state()
                        + ", creat_time = '" + notesBean.getCreat_time() + "' where id = " + id;
            }
            int result = jdbcTemplate.update(sql);
            if(result > 0){
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean deleteNotesInfo(int id) {
        boolean flag = false;
        String sql = "delete  from notes where id = " + id;
        int result = jdbcTemplate.update(sql);

        if(result > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean updateStatus(int id, int status) {
        boolean flag = false;
        String sql = "update notes set notes_state = "+ status +" where id = " + id;
        int result = jdbcTemplate.update(sql);
        if(result > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean updateViewNum(int id ,int preNum) {
        boolean flag = false;
        Integer newNum = preNum + 1;
        String sql = "update notes set view_num = "+ newNum +" where id = " + id;
        int result = jdbcTemplate.update(sql);
        if(result > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean updateFavourNum(int id, boolean type, int preNum) {
        boolean flag = false;
        Integer newNum = 0;
        if(type){
            newNum = preNum + 1;
        }else{
            if(preNum>0){
                newNum = preNum - 1;
            }
        }

        String sql = "update notes set favour_num = "+ newNum +" where id = " + id;

        int result = jdbcTemplate.update(sql);
        if(result > 0){
            flag = true;
        }
        return flag;
    }
}
