package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.AssistBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.BaseDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Map;

/**
 * Created by sunpengyan on 2019/9/7.
 */
@Repository
public class
BaseDataDaoImpl extends BaseDaoImpl<Object> implements BaseDataDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> getContentCardInfo(Integer uid){
        String sql = "select \n" +
                "CASE\n" +
                "when status=0 then '编辑中' \n" +
                "when status=1 then '待审核' \n" +
                "when status=2 then '已通过' \n" +
                "when status=3 then '已拒绝' \n" +
                "END \n" +
                "status,\n" +
                "count(*)\n" +
                "from menu where parent !=0 and uid = "+uid+" GROUP BY status ORDER BY status ASC";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> getAssistCardInfo(Integer uid){
        String sql = "select \n" +
                "CASE\n" +
                "when status=0 then '编辑中' \n" +
                "when status=1 then '待审核' \n" +
                "when status=2 then '已通过' \n" +
                "when status=3 then '已拒绝' \n" +
                "END \n" +
                "status,\n" +
                "count(*)\n" +
                "from assist where user_id = "+uid+" GROUP BY status ORDER BY status ASC";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> getComponentCardInfo(Integer uid) {
        String sql = "select \n" +
                "CASE\n" +
                "when status=0 then '编辑中' \n" +
                "when status=1 then '待审核' \n" +
                "when status=2 then '已通过' \n" +
                "when status=3 then '已拒绝' \n" +
                "END \n" +
                "status,\n" +
                "count(*)\n" +
                "from component where uid = "+uid+" GROUP BY status ORDER BY status ASC";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> getProjectCardInfo(Integer uid) {
        String sql = "select \n" +
                "CASE\n" +
                "when status=0 then '编辑中' \n" +
                "when status=1 then '待审核' \n" +
                "when status=2 then '已通过' \n" +
                "when status=3 then '已拒绝' \n" +
                "END \n" +
                "status,\n" +
                "count(*)\n" +
                "from project_info where uid = "+uid+" GROUP BY status ORDER BY status ASC";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> getResourcesCardInfo(Integer uid) {
        String sql = "select \n" +
                "CASE\n" +
                "when status=0 then '编辑中' \n" +
                "when status=1 then '待审核' \n" +
                "when status=2 then '已通过' \n" +
                "when status=3 then '已拒绝' \n" +
                "END \n" +
                "status,\n" +
                "count(*)\n" +
                "from resources where uid = "+uid+" GROUP BY status ORDER BY status ASC";
        return jdbcTemplate.queryForList(sql);
    }

    //以下查询待审核信息
    @Override
    public List<Map<String, Object>> getContentVerify(Integer uid){
        String sql = "select \n" +
                "CASE\n" +
                "when status=1 then '待审核' \n" +
                "END \n" +
                "status,\n" +
                "count(*)\n" +
                "from menu where parent !=0 and status = 1 GROUP BY status ORDER BY status ASC";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> getAssistVerify(Integer uid){
        String sql = "select \n" +
                "CASE\n" +
                "when status=1 then '待审核' \n" +
                "END \n" +
                "status,\n" +
                "count(*)\n" +
                "from assist where status = 1 GROUP BY status ORDER BY status ASC";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> getComponentVerify(Integer uid) {
        String sql = "select \n" +
                "CASE\n" +
                "when status=1 then '待审核' \n" +
                "END \n" +
                "status,\n" +
                "count(*)\n" +
                "from component where status = 1 GROUP BY status ORDER BY status ASC";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> getProjectVerify(Integer uid) {
        String sql = "select \n" +
                "CASE\n" +
                "when status=1 then '待审核' \n" +
                "END \n" +
                "status,\n" +
                "count(*)\n" +
                "from project_info where status = 1 GROUP BY status ORDER BY status ASC";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> getResourcesVerify(Integer uid) {
        String sql = "select \n" +
                "CASE\n" +
                "when status=1 then '待审核' \n" +
                "END \n" +
                "status,\n" +
                "count(*)\n" +
                "from resources where status = 1 GROUP BY status ORDER BY status ASC";
        return jdbcTemplate.queryForList(sql);
    }

}
