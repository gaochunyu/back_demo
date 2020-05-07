package com.cennavi.tp.dao;

import com.cennavi.tp.beans.TradeTypeBean;
import com.cennavi.tp.common.base.dao.BaseDao;

import java.util.List;

public interface TradeTypeDataDao extends BaseDao<TradeTypeBean> {
    List<TradeTypeBean> getTradeTypeList();
}
