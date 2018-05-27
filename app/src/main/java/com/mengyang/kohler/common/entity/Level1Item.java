package com.mengyang.kohler.common.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mengyang.kohler.common.adapter.AzureCustomerServiceAdapter;

/**
 * Created by liusong on 2018/5/27.
 */

public class Level1Item implements MultiItemEntity {
    public String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Level1Item(String content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return AzureCustomerServiceAdapter.TYPE_LEVEL_1;
    }
}
