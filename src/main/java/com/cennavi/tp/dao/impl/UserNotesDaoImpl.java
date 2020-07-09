package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.UserNotes;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.UserNotesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class UserNotesDaoImpl extends BaseDaoImpl<UserNotes> implements UserNotesDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public boolean saveUserNotes(int notesId, boolean type, int userId) {
        boolean flag = false;
        // 进行点赞，新增一条数据
        if(type) {
            String sql = "insert into user_notes(user_id,notes_id) values (?, ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql,new String [] {"id"});
                ps.setInt(1,userId);
                ps.setInt(2,notesId);
                return ps;
            }, keyHolder);

            long insertId = keyHolder.getKey().longValue();  // 自增主键的id
            System.out.println("主键="+insertId);
            //return 1;
            flag = true;

        } else {
            // 取消点赞，删除数据
            String sql = "delete from user_notes where user_id=? and notes_id=?";

            int i = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1,userId);
                ps.setInt(2,notesId);
                return ps;
            });
            if(i>0){
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean getStatus(int notesId, int userId) {
        String sql = "select * from user_notes where user_id=? and notes_id=?";

        List<UserNotes> list = jdbcTemplate.query(sql, new Object[]{userId,notesId}, BeanPropertyRowMapper.newInstance(UserNotes.class));
        if(list.size()!= 0) {
            // 这条数据存在，证明用户已经进行过了点赞
            return true;
        } else {
            // 数据不存在，证明用户还没有进行过点赞
            return false;
        }
    }
}
