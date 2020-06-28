package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.NotesBean;
import com.cennavi.tp.beans.ProjectBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.NotesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public class NotesDaoImpl extends BaseDaoImpl<NotesBean> implements NotesDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<NotesBean> getNotesList(Integer limitSize, Integer offsetNum, String notesType, String status, Integer userId) {
        String sql = "";
        sql = "select id,notes.title,notes.content,notes.view_num,notes.favour_num,notes.creat_time,notes.user_name ,notes.file,type.name from notes as notes \n" +
                "LEFT JOIN notes_type as type on notes.notes_type = type.id " +
                " where notes_type in ("+ notesType + ") and notes_state in (" + status + ") GROUP BY notes.id order by notes.sort desc LIMIT "+ limitSize +" OFFSET " + offsetNum;

        if(userId != 0){
            sql = "select notes.title,notes.content,notes.view_num,notes.favour_num,notes.creat_time,notes.user_name ,notes.file,type.name from notes as notes \n" +
                    " where trade_type_id in ("+ notesType + ") and status in (" + status + ") and uid = "+ userId +" GROUP BY notes.id order by notes.sort desc LIMIT "+ limitSize +" OFFSET " + offsetNum;
        }
        //System.out.println("查询"+sql);

        return jdbcTemplate.query(sql , BeanPropertyRowMapper.newInstance(NotesBean.class));
    }

    @Override
    public Integer getNotesListNum(String notesType, String status) {
        return null;
    }
}
