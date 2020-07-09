package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.FeedbackBean;
import com.cennavi.tp.beans.ResourcesBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.FeedbackDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FeedbackDaoImpl extends BaseDaoImpl<FeedbackBean>  implements FeedbackDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int getFeedbackCount(Integer page, Integer pageSize, String keyword,Integer userId) {
        String sql = "select count(*) from feedback where feedback_by = ? ";
        List<Object> params = new ArrayList<Object>();
        params.add(userId);
        if (keyword != ""){
            sql = sql + "and description like concat('%',?,'%')";
            params.add(keyword);
        }
        return jdbcTemplate.queryForObject(sql, params.toArray(), Integer.class);
    }

    @Override
    public List<FeedbackBean> getFeedbackList(Integer start, Integer pageSize, String keyword,Integer userId) {

        String sql = "select f.*,u.username as username" +
                " from feedback f left join userinfo u on f.feedback_by = u.id where u.id = ? ";
        List<Object> params = new ArrayList<Object>();
        params.add(userId);
        if (keyword != ""){
            sql = sql + "and description like concat('%',?,'%')";
            params.add(keyword);
        }
        sql = sql + "order by createtime desc limit " + pageSize + " offset "+start;

        List<FeedbackBean> list = jdbcTemplate.query(sql, params.toArray(),BeanPropertyRowMapper.newInstance(FeedbackBean.class));
        return list;
    }

    @Override
    public int updateFeedbackState(FeedbackBean feedbackBean) {
        String sql = "update feedback set state =?,updatetime=? where id = ?";
        return jdbcTemplate.update( conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(feedbackBean.getState()));
            ps.setString(2,feedbackBean.getUpdatetime());
            ps.setInt(3, feedbackBean.getId());
            return ps;
        });
    }

    @Override
    public FeedbackBean getFeedbackDetail(Integer id) {
        String sql = "select * from feedback where id = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        List<FeedbackBean> list = jdbcTemplate.query(sql, params.toArray(),BeanPropertyRowMapper.newInstance(FeedbackBean.class));
        return list.size() == 0 ? null : list.get(0);
    }
}
