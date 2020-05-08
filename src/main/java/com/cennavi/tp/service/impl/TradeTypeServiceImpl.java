package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.TradeTypeBean;
import com.cennavi.tp.dao.TradeTypeDataDao;
import com.cennavi.tp.service.TradeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TradeTypeServiceImpl implements TradeDataService {

    @Autowired
    private TradeTypeDataDao tradeTypeDataDao;

    @Override
    public List<TradeTypeBean> getTradeTypeList() {
        return tradeTypeDataDao.getTradeTypeList();
    }
}
