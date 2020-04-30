package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.ComponentBean;
import com.cennavi.tp.dao.ComponentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author: chenfeng
 * @date: 2020/4/30 10:42
 */
@Repository
public class ComponentDaoImpl implements ComponentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 添加组件
    public boolean addComponent(ComponentBean componentBean) {
        String sql = "insert into component(uid, name, tags, cover, content, create_time, test_url, file_url) values ('"+ componentBean.getUid() + "','" + componentBean.getName() + "','" + componentBean.getTags() + "','" + componentBean.getCover() + "','" + componentBean.getContent() + "','" + componentBean.getCreate_time() + "','" + componentBean.getTest_url() + "'," + componentBean.getFile_url() + ")";
        int result = jdbcTemplate.update(sql);
        System.out.println(result);
        return result == 1;
    }
}
