package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.AssistBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.AssistDao;
import com.cennavi.tp.dao.ContentDao;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 姚文帅 on 2020/4/29 14:33.
 */
@Repository
public class AssistDaoImpl extends BaseDaoImpl<AssistBean> implements AssistDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 插入一条新数据并返回插入数据的自增主键
    public long addAssistItem(AssistBean assistBean) {
//        try {
            String sql = "insert into assist(user_id,question,answer,create_time,category,weight,status) values (" +assistBean.getUserId()+
                    ",'"+assistBean.getQuestion()+
                    "','"+assistBean.getAnswer()+
                    "','"+assistBean.getCreateTime()+
                    "','"+assistBean.getCategory()+
                    "',"+assistBean.getWeight()+
                    ","+assistBean.getStatus()+")";

            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql,new String [] {"id"});
                return ps;
            }, keyHolder);

            long insertId = keyHolder.getKey().longValue();

            return insertId;

    }


    @Override
    // 更新一条信息
    public Integer updateAssistItem(AssistBean assistBean)  {
        try {
        // 文本型字段值两边加引号，日期型两边加#，数字、逻辑两边什么都不用加。
        String sql = "UPDATE assist SET question='"+assistBean.getQuestion()+
                "',answer='"+assistBean.getAnswer()+
                "',category='"+assistBean.getCategory()+
                "',status="+assistBean.getStatus()+
                " WHERE id="+assistBean.getId();

        int i = jdbcTemplate.update(sql);
        return i;
        }catch (Exception e){
            e.getStackTrace();
            return -1;
        }
    }


    @Override
    // 删除一条信息
    public Integer deleteAssistItem(Integer id) {
        String sql = "delete from assist where id = " + id;
        int i = jdbcTemplate.update(sql);
        return i;
    }


    // 根据 id 查询某条数据
    @Override
    public AssistBean getAssistItemById(Integer id){
        String sql = "select * from assist where id = "+id;
        List<AssistBean> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(AssistBean.class));
        return list.size()==0?null:list.get(0);
    }





    // 创建条件筛选的sql
    private String createSelectTypeSQL(String[] categoryList) {
        if(categoryList.length ==0) return "";
        String selectSql =" " +"and" + " " + "(";
        for (int i = 0; i < categoryList.length; i++) {

            selectSql= selectSql + " " +"category='" +categoryList[i] +"'";
            if(i != categoryList.length-1) {
                selectSql = selectSql + " " + "or";
            } else {
                selectSql = selectSql  + ")";
            }

        }
        return selectSql;
    }

    // 创建排序sql
    private String createSingleSortTypeSQL(int type, String typeValue) {
        String sql = "";

        switch (typeValue) {
            case "default":
                break;
            case "ascending":
                sql = type == 1? "create_time ASC" : "weight ASC";
                break;
            case "descending":
                sql = type == 1? "create_time DESC" : "weight DESC";
                break;
        }
        return sql;
    }

    // 拼接排序sql语句
    private String createSelectTypeSQL(String createTimeSortType,String weightSortType) {
        String sortSql = "";
        String createTimeSortSql = createSingleSortTypeSQL(1, createTimeSortType);
        String createWeightSortSql = createSingleSortTypeSQL(2, weightSortType);

        if (createTimeSortSql == "" && createWeightSortSql == "") {

        } else {
            if(createTimeSortSql != "" ) sortSql = "order by"+ " " + createTimeSortSql;
            if(createWeightSortSql != "" ) sortSql = "order by"+ " " + createWeightSortSql;

        }
        return sortSql;
    }
    /**
     * 查询分页
     */
    @Override
    public Map<String,Object> getAssistList(Integer page, Integer pageSize,Integer contentType, Integer userId,
                                            String[] categoryList,String createTimeSortType,String weightSortType,int statusValue) {

        // 共有的  categoryList 分类筛选 createTimeSortType按照创建时间排序  weightSortType按照权重排序
        // 管理员私有的   筛选特定状态的数据

        String selectSql = createSelectTypeSQL(categoryList);
        String sortSql = createSelectTypeSQL(createTimeSortType,weightSortType);


        // 状态0录入中 1待审核 2审核通过 3被拒绝
        Integer offset = pageSize;

        int rows = 0;
        List<AssistBean> list = null;

        // 2-浏览模式  3-审核模式   4-我的发布
        if(contentType == 2) {
            // 查询所有审核通过的数据总行数
            String scanSql = "select count(*) from assist where status=2" + selectSql;
            rows = jdbcTemplate.queryForObject(scanSql, null, Integer.class);
            if (page * pageSize > rows) {
                // 超出总值的时候，需要计算offse的值
                offset = rows - page * pageSize + pageSize;
            }

            int startIndex = (page-1) * pageSize;//提取分页开始索引
            String scanPageSql = "SELECT * FROM assist where status=2" + selectSql + " " + sortSql + " " + " LIMIT ? OFFSET ?";
            list = jdbcTemplate.query(scanPageSql, new Object[]{offset,startIndex}, new BeanPropertyRowMapper<>(AssistBean.class));


        } else if(contentType == 3) {
            String checkSql = "select count(*) from assist where status=1"+ selectSql;

            // 查询所有待审核的数据的总行数
            rows = jdbcTemplate.queryForObject(checkSql, null, Integer.class);
            if (page * pageSize > rows) {
                // 超出总值的时候，需要计算offse的值
                offset = rows - page * pageSize + pageSize;
            }

            int startIndex = (page-1) * pageSize;//提取分页开始索引
            String checkPageSql = "SELECT * FROM assist where status=1" + selectSql + " "+ sortSql +  " " +" LIMIT ? OFFSET ?";
            list = jdbcTemplate.query(checkPageSql, new Object[]{offset,startIndex}, new BeanPropertyRowMapper<>(AssistBean.class));


        } else {
            // 查询当前用户下面的数据
            String statusSql = statusValue == 100 ? "": "and status=" + statusValue;
            String mangerSql = "select count(*) from assist where user_id="+ userId + selectSql + " " + statusSql;
            rows = jdbcTemplate.queryForObject(mangerSql, null, Integer.class);
            if (page * pageSize > rows) {
                // 超出总值的时候，需要计算offse的值
                offset = rows - page * pageSize + pageSize;
            }

            int startIndex = (page-1) * pageSize;//提取分页开始索引
            String mangerPageSql = "SELECT * FROM assist WHERE user_id=?"+selectSql  + " " +  statusSql +sortSql+ " " +"LIMIT ? OFFSET ?";
            list = jdbcTemplate.query(mangerPageSql, new Object[]{userId,offset,startIndex}, new BeanPropertyRowMapper<>(AssistBean.class));
        }

        Map<String,Object> map = new HashMap<>();
        map.put("totalNumber", rows);
        map.put("dataList", list);
        map.put("pageNumber", page);
        map.put("pageSize", pageSize);

        return map;

    }

    @Override
    public Integer updateAssistItemWeightById(Integer id, Boolean updateType) {
        // 首先根据id去获取数据库中保存的id

        String querySql =  "select weight from assist where id = "+id;
        int weight = jdbcTemplate.queryForObject(querySql, null, Integer.class);
        if(updateType) {
            weight=weight+1;
        } else {
            weight=weight-1;
        }

        String sql = "UPDATE assist SET weight="+ weight +
                " WHERE id="+id;

        int i = jdbcTemplate.update(sql);
        return i;

    }

    // 审核修改assist的状态
    @Override
    public Integer checkAssistItem(Integer id, Boolean checkType) {
        try {
            // 2审核通过 3被拒绝
            int status = checkType? 2 : 3;
            String sql = "UPDATE assist SET status="+status+
                    " WHERE id="+id;

            int i = jdbcTemplate.update(sql);
            return i;


        } catch (Exception e){
            e.getStackTrace();
            return -1;
        }
    }
}
