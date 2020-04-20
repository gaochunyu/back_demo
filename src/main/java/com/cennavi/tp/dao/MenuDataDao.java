package com.cennavi.tp.dao;


import com.cennavi.tp.beans.MenuSubtitle;
import com.cennavi.tp.common.dao.BaseDao;

import java.util.List;

public interface MenuDataDao {
    List<MenuSubtitle> getMenuSubtitles();
}
