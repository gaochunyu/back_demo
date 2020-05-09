package com.cennavi.tp.dao;

import com.cennavi.tp.beans.ComponentBean;

public interface ComponentDao {
    Integer addComponent(ComponentBean componentBean);

    Integer delComponent(Integer id, Integer uid);


}
