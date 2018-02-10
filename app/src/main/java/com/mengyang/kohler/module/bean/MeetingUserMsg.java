package com.mengyang.kohler.module.bean;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/10
 */

public class MeetingUserMsg {
    /**
     * dataKey : PUSH_MSG
     * id : 14.0
     * pushMsg : true
     * userId : 56.0
     */

    private String dataKey;
    private double id;
    private boolean pushMsg;
    private double userId;

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public boolean isPushMsg() {
        return pushMsg;
    }

    public void setPushMsg(boolean pushMsg) {
        this.pushMsg = pushMsg;
    }

    public double getUserId() {
        return userId;
    }

    public void setUserId(double userId) {
        this.userId = userId;
    }
}
