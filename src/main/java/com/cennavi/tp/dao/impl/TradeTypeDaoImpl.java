package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.TradeTypeBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.TradeTypeDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TradeTypeDaoImpl extends BaseDaoImpl<TradeTypeBean> implements TradeTypeDataDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<TradeTypeBean> getTradeTypeList() {
        String sql = "select id , name from trade_type";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(TradeTypeBean.class));
    }
}
