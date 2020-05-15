package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.AssistBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.AssistDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
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
            String sql = "insert into assist(user_id,question,answer,create_time,category,weight,status) values (?,?,?,?,?,?,?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql,new String [] {"id"});
                ps.setInt(1,assistBean.getUserId());
                ps.setString(2,assistBean.getQuestion());
                ps.setString(3,assistBean.getAnswer());
                ps.setString(4,assistBean.getCreateTime());
                ps.setString(5, assistBean.getCategory());
                ps.setInt(6, assistBean.getWeight());
                ps.setInt(7, assistBean.getStatus());
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
        String sql = "UPDATE assist SET question=?,answer=?,category=?,status=? WHERE id=?";


        KeyHolder keyHolder = new GeneratedKeyHolder();

       int i =  jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,assistBean.getQuestion());
            ps.setString(2,assistBean.getAnswer());
            ps.setString(3, assistBean.getCategory());
            ps.setInt(4, assistBean.getStatus());
            ps.setInt(5, assistBean.getId());
            return ps;
        }, keyHolder);

        return i;

        }catch (Exception e){
            e.getStackTrace();
            return -1;
        }
    }



    @Override
    // 删除一条信息
    public Integer deleteAssistItem(Integer id) {
        String sql = "delete from assist where id = ?" ;
        int i =  jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            return ps;
        });
        return i;
    }


    // 根据 id 查询某条数据
    @Override
    public AssistBean getAssistItemById(Integer id){
        String sql = "select * from assist where id = ?";
        List<AssistBean> list = jdbcTemplate.query(sql, new Object[]{id}, BeanPropertyRowMapper.newInstance(AssistBean.class));
        return list.size()==0?null:list.get(0);
    }


    // 创建条件筛选的sql
    private String createSelectTypeSQL(String[] categoryList) {
        String selectSql = "and (";
        for (int i = 0; i < categoryList.length; i++) {
            selectSql = selectSql + " " +"category=? or";
        }
        selectSql = selectSql.substring(0, selectSql.length() - 2);

        selectSql = selectSql + ")";

        return selectSql;
    }

    // 创建排序sql
    private String getSortTypeSQL(String createTimeSortType,String weightSortType) {

        String sortSql = "";

        if(createTimeSortType == "" && weightSortType == "") {
            sortSql = "";
        } else {
            if(createTimeSortType != "") {
                if(typeChange(createTimeSortType) == "asc") {
                    sortSql = "order by create_time ASC";
                } else if(typeChange(createTimeSortType) == "desc"){
                    sortSql = "order by create_time DESC";
                } else {
                    sortSql = "";
                }

            } else {
                if(typeChange(weightSortType) == "asc") {
                    sortSql = "order by weight ASC";
                } else if(typeChange(weightSortType) == "desc"){
                    sortSql = "order by weight DESC";
                } else {
                    sortSql = "";
                }
            }
        }
        return sortSql;
    }

    private String typeChange(String sender) {
        String result = "";
        switch (sender){
            case "default":
                break;
            case "descending":
                result =  "desc";
                break;
            case "ascending":
                result = "asc";
                break;
        }
        return result;
    }




    /**
     * 查询分页
     */
    @Override
    public Map<String,Object> getAssistList(Integer page, Integer pageSize,Integer contentType, Integer userId,
                                            String[] categoryList,String createTimeSortType,String weightSortType,int statusValue) {

        // 状态0录入中 1待审核 2审核通过 3被拒绝
        Integer offset = pageSize;

        String selectSql = "";

        // 拼接筛选的参数
        List<Object> params = new ArrayList<>();
        if(categoryList.length != 0){

            selectSql = createSelectTypeSQL(categoryList);
            for (int i = 0; i < categoryList.length; i++) {
                //用append()方法拼接
                params.add(categoryList[i]);
            }

        } else {
            selectSql = "";
        }

        String sortSql = getSortTypeSQL(createTimeSortType,weightSortType);


        int rows = 0;
        List<AssistBean> list = null;

        try{
            // 2-浏览模式  3-审核模式   4-我的发布
            if(contentType == 2) {
                // 查询所有审核通过的数据总行数
                String scanSql = "select count(*) from assist where status=2" + " " +selectSql;
                rows = jdbcTemplate.queryForObject(scanSql, params.toArray(), Integer.class);
                if (page * pageSize > rows) {
                    // 超出总值的时候，需要计算offse的值
                    offset = rows - page * pageSize + pageSize;
                }

                int startIndex = (page-1) * pageSize;//提取分页开始索引

                params.add(offset);
                params.add(startIndex);

                String scanPageSql = "SELECT * FROM assist where status=2" + " " + selectSql + " " + sortSql + " " +" LIMIT ? OFFSET ?";

                list = jdbcTemplate.query(scanPageSql, params.toArray(), new BeanPropertyRowMapper<>(AssistBean.class));


            } else if(contentType == 3) {
                String checkSql = "select count(*) from assist where status=1" + " " +selectSql;

                // 查询所有待审核的数据的总行数
                rows = jdbcTemplate.queryForObject(checkSql, params.toArray(), Integer.class);
                if (page * pageSize > rows) {
                    // 超出总值的时候，需要计算offse的值
                    offset = rows - page * pageSize + pageSize;
                }
                String instring = "'默认分类','图层'";
                String instring1 = "create_time DESC";

                int startIndex = (page-1) * pageSize;//提取分页开始索引

                params.add(offset);
                params.add(startIndex);
                String checkPageSql = "SELECT * FROM assist where status=1" + " " + selectSql + " " + sortSql + " " +" LIMIT ? OFFSET ?";
                list = jdbcTemplate.query(checkPageSql, params.toArray(), new BeanPropertyRowMapper<>(AssistBean.class));


            } else {

                // 查询当前用户下面的数据
                String statusSql = statusValue == 100 ? "": "and status=?";
                String mangerSql = "select count(*) from assist where user_id=?"+ " " + selectSql + " " + statusSql;
                params.add(0,userId);
                if(statusValue != 100)    params.add(statusValue);


                rows = jdbcTemplate.queryForObject(mangerSql, params.toArray(), Integer.class);
                if (page * pageSize > rows) {
                    // 超出总值的时候，需要计算offse的值
                    offset = rows - page * pageSize + pageSize;
                }

                int startIndex = (page-1) * pageSize;//提取分页开始索引

                params.add(offset);
                params.add(startIndex);
                String mangerPageSql = "select * from assist where user_id=?"+ " " + selectSql + " " + statusSql+ " " + sortSql +" "+ "LIMIT ? OFFSET ?";
                list = jdbcTemplate.query(mangerPageSql, params.toArray(), new BeanPropertyRowMapper<>(AssistBean.class));
            }
            Map<String,Object> map = new HashMap<>();
            map.put("totalNumber", rows);
            map.put("dataList", list);
            map.put("pageNumber", page);
            map.put("pageSize", pageSize);

            return map;

        } catch (Exception e){
            e.getStackTrace();
            return null;
        }



    }

    @Override
    public Integer updateAssistItemWeightById(Integer id, Boolean updateType) {
        // 首先根据id去获取数据库中保存的id

        String querySql =  "select weight from assist where id =?";
        int weight = jdbcTemplate.queryForObject(querySql, new Object[]{id}, Integer.class);
        if(updateType) {
            weight=weight+1;
        } else {
            weight=weight-1;
        }
        int weightValue = weight;

        String sql = "UPDATE assist SET weight=? WHERE id=?";
        int i =  jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, weightValue); // ??
            ps.setInt(2, id);
            return ps;
        });

        return i;

    }

    // 审核修改assist的状态
    @Override
    public Integer checkAssistItem(Integer id, Boolean checkType) {
        try {
            // 2审核通过 3被拒绝
            int status = checkType? 2 : 3;
            String sql = "UPDATE assist SET status=? WHERE id=?";

            int i =  jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, status); // ??
                ps.setInt(2, id);
                return ps;
            });

//            int i = jdbcTemplate.update(sql, new Object[]{status, id}, Integer.class);
            return i;


        } catch (Exception e){
            e.getStackTrace();
            return -1;
        }
    }

    // 判断question是否存在
    @Override
    public Integer questionIsCorrect(String question, Integer id) {
        // 当id为-1时
        try {
            String sql = "";
            List<AssistBean> list = null;

            if(id == -1) {
                // 新增
                sql = "select * from assist where question=?";
                list = jdbcTemplate.query(sql, new Object[]{question},BeanPropertyRowMapper.newInstance(AssistBean.class));
            } else {
                sql = "select * from assist where question=? and id!=?";
                list = jdbcTemplate.query(sql, new Object[]{question,id},BeanPropertyRowMapper.newInstance(AssistBean.class));
            }


            if(list.size()!= 0){
                return 1;
            } else {
                return -1;
            }

        } catch (Exception e){
            e.getStackTrace();
            return -1;
        }
    }
}
