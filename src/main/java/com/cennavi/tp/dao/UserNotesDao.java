package com.cennavi.tp.dao;

import com.cennavi.tp.beans.UserNotes;
import com.cennavi.tp.common.base.dao.BaseDao;

public interface UserNotesDao extends BaseDao<UserNotes> {
    boolean saveUserNotes(int notesId,boolean type ,int userId);

    boolean getStatus(int notesId , int userId);
}
