package com.mengyang.kohler.module.bean;

/**
 * Created by MengYang on 2018/5/22.
 */
public class AzureBotWartBean {
    /**
     * id : 0001
     */

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "WartBean{" +
                "id='" + id + '\'' +
                '}';
    }
}
