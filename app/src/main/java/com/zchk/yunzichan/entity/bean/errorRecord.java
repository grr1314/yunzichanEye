package com.zchk.yunzichan.entity.bean;

import java.io.Serializable;

/**
 * Created by admin on 2016/12/5.
 */
public class errorRecord implements Serializable{

    private String name;
    private int state;
    private int id;
    private String time;
    private String Type;

    public String getTing() {
        return ting;
    }

    public void setTing(String ting) {
        this.ting = ting;
    }

    private String ting;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
