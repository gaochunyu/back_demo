package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.FeedbackBean;
import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.FeedbackDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeedbackDaoImpl extends BaseDaoImpl<FeedbackBean>  implements FeedbackDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int getFeedbackCount(Integer page, Integer pageSize, String keyword) {
        String sql = "select count(*) from feedback where description like '%" + keyword + "%'";
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }

    @Override
    public List<FeedbackBean> getFeedbackList(Integer start, Integer pageSize, String keyword) {
        String sql = "select f.*,u.username as username" +
                " from feedback f left join userinfo u on f.feedback_by = u.id where description like '%"
                + keyword + "%' order by createtime desc limit " + pageSize + " offset "+start;
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(FeedbackBean.class));
    }
}
