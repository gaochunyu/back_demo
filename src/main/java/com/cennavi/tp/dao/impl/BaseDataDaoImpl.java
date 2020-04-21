package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.BaseRtic;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.BaseDataDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
    public String getRoadIdByLinkid(String linkid, String city) {
        String sql = "select distinct road_id from "+city+"_base_area_road_rtic t1,"+city+"_base_rtic_link t2 where t1.rtic_id = t2. rticid " +
                "and t2.linkid ='"+linkid+"'";
        List<String> list = jdbcTemplate.queryForList(sql,String.class);
        String road_id = "";
        if(list!=null && list.size()>0){
            for(String s:list){
                road_id += s;
            }
        }
        return road_id;
    }

    @Override
    public List<Map<String,Object>> listLinkByRoadid(String roadid,String geometry, String city){
        String roadParam = "";
        int flag=0;
        if(StringUtils.isNotBlank(roadid)){
            String replace = roadid.replace(",", "','");
            roadParam = " and id in (select linkid from "+city+"_base_area_road_rtic t1,"+city+"_base_rtic_link t2 where t1.rtic_id = t2. rticid " +
                    "and road_id in ('"+replace+"'))";
            flag=1;
        }
        String geoParam = "";
        if(StringUtils.isNotBlank(geometry)){
            geoParam=" and St_intersects(st_geomfromgeojson(geometry),st_geomfromgeojson('"+geometry+"'))";
            flag=1;
        }
        if(flag==0)return null;//一个参数都没有，返回null
        String sql = "select * from "+city+"_base_link where 1=1 "+roadParam +geoParam+" order by id";
        return getJdbcTemplate().queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> getLinkById(String id, String city) {
        String sql = "select * from "+city+"_base_link where id = '"+id+"'";
        return getJdbcTemplate().queryForList(sql);
    }

    @Override
    public List<Map<String,Object>> listRticByRoadid(String roadid,String geometry, String city){
        String roadParam = "";
        int flag=0;
        if(StringUtils.isNotBlank(roadid)){
            String replace = roadid.replace(",", "','");
            roadParam = " and id in (select rtic_id from "+city+"_base_area_road_rtic where road_id in ('"+replace+"'))";
            flag=1;
        }
        String geoParam = "";
        if(StringUtils.isNotBlank(geometry)){
            geoParam=" and St_intersects(st_geomfromgeojson(geometry),st_geomfromgeojson('"+geometry+"'))";
            flag=1;
        }
        if(flag==0)return null;//一个参数都没有，返回null
        String sql = "select * from "+city+"_base_rtic where 1=1 "+roadParam +geoParam+" order by id";
        return getJdbcTemplate().queryForList(sql);
    }

    @Override
    public List<BaseRtic> getRticById(String id, String city) {
        String sql = "select * from "+city+"_base_rtic where id = '"+id+"'";
        return getJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(BaseRtic.class));
    }

    @Override
    public void createTable(String tablename,String type) {
        String sql = "";
        if(type.equals("area_road_rtic")) {
            sql = "CREATE TABLE "+tablename+" ( area_id varchar(50)," +
                    "road_id varchar(50)," +
                    "seq_no int4," +
                    "rtic_id varchar(50) );" +
                    "CREATE INDEX inx_area_id_"+tablename+" ON "+tablename+" USING btree (area_id);" +
                    "CREATE INDEX inx_road_id_"+tablename+" ON "+tablename+" USING btree (road_id);" +
                    "CREATE INDEX inx_rtic_id_"+tablename+" ON "+tablename+" USING btree (rtic_id);";
        } else if(type.equals("rtic")) {
            sql = "CREATE TABLE "+tablename+" (" +
                    "id varchar(50) NOT NULL,name varchar(20)," +
                    "startname varchar(20), endname varchar(20)," +
                    "length float4, kind varchar(10)," +
                    "width varchar(10), speedlimit varchar(20)," +
                    "direction varchar(10), startpoint varchar(100)," +
                    "endpoint varchar(100), geometry text," +
                    "center varchar(100),PRIMARY KEY (id) );";
        } else if(type.equals("link")) {
            sql = "CREATE TABLE "+tablename+" ( id varchar(20) NOT NULL," +
                    "name varchar(20),kind varchar(10)," +
                    "length float4,direction int4," +
                    "geometry text,PRIMARY KEY (id) );";
        } else if(type.equals("rtic_link")) {
            sql = "CREATE TABLE "+tablename+" (" +
                    "rticid varchar(50),linkid varchar(50),sort int4);";
        }
        jdbcTemplate.execute(sql);
    }
}
