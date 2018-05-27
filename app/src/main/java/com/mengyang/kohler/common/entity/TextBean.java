package com.mengyang.kohler.common.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mengyang.kohler.common.adapter.AzureCustomerServiceAdapter;

/**
 * Created by liusong on 2018/5/27.
 */

public class TextBean implements MultiItemEntity {
    private String h5Url;
    private String description;
    private boolean isTable;

    public TextBean(String description) {
        this.description = description;
    }

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

    @Override
    public int getItemType() {
        return AzureCustomerServiceAdapter.TYPE_TEXT;
    }
}
