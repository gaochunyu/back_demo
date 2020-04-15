package com.cennavi.tp.beans;

import com.cennavi.tp.common.MyTable;

/**
 * Created by sunpengyan on 2019/12/10.
 */
@MyTable("base_link")
public class BaseLink {
    private String id;
    private String name;
    private String kind;
    private float length;
    private int direction;
    private String geometry;

    public BaseLink(String id, String name, String kind, float length, int direction, String geometry) {
        this.id = id;
        this.name = name;
        this.kind = kind;
        this.length = length;
        this.direction = direction;
        this.geometry = geometry;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }
}
