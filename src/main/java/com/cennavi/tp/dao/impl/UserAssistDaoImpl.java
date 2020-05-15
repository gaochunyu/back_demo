package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.UserAssistBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.UserAssistDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * Created by 姚文帅 on 2020/5/13 14:35.
 */
@Repository
public class UserAssistDaoImpl extends BaseDaoImpl<UserAssistBean> implements UserAssistDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    // 点赞接口,是否存在 user_id 和 assist_id 对应的一条数据
    @Override
    public Integer giveARedHeart(Integer userId, Integer assistId, Boolean type) {

        // 进行点赞，新增一条数据
        if(type) {
            String sql = "insert into user_assist(user_id,assist_id) values (?, ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql,new String [] {"id"});
                ps.setInt(1,userId);
                ps.setInt(2,assistId);
                return ps;
            }, keyHolder);

            long insertId = keyHolder.getKey().longValue();  // 自增主键的id
            return 1;

        } else {
            // 取消点赞，删除数据
            String sql = "delete from user_assist where user_id=? and assist_id=?";

            int i = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1,userId);
                ps.setInt(2,assistId);
                return ps;
            });
            return i;
        }
    }

    // 根据用户id和帮助列表的id获取本条数据的状态，是否已经点赞过
    @Override
    public Boolean getAssistWeightStatus(Integer userId, Integer assistId) {
        String sql = "select * from user_assist where user_id=? and assist_id=?";

        List<UserAssistBean> list = jdbcTemplate.query(sql, new Object[]{userId,assistId},BeanPropertyRowMapper.newInstance(UserAssistBean.class));
        if(list.size()!= 0) {
            // 这条数据存在，证明用户已经进行过了点赞
            return true;
        } else {
            // 数据不存在，证明用户还没有进行过点赞
            return false;
        }
    }
}
