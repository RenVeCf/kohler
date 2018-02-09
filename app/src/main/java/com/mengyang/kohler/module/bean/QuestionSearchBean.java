package com.mengyang.kohler.module.bean;

/**
 * Created by liusong on 2018/2/9.
 */

public class QuestionSearchBean {

    /**
     * h5Url : http://www.baidu.com
     * description : 您好,您的桌号可能是1,或请查看桌号名册
     * isTable : true
     */

    private String h5Url;
    private String description;
    private boolean isTable;

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIsTable() {
        return isTable;
    }

    public void setIsTable(boolean isTable) {
        this.isTable = isTable;
    }
}
