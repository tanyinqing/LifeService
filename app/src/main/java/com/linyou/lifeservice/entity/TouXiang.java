package com.linyou.lifeservice.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/2/15 0015.
 * 主要是接受头像返回的网址信息
 */
public class TouXiang implements Serializable {
    private static final long serialVersionUID=1L;
    private String wangZhi;

    public String getWangZhi() {
        return wangZhi;
    }

    public void setWangZhi(String wangZhi) {
        this.wangZhi = wangZhi;
    }
}
