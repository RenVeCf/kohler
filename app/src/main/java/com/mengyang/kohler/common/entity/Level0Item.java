package com.mengyang.kohler.common.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mengyang.kohler.common.adapter.AzureCustomerServiceAdapter;

/**
 * Created by liusong on 2018/5/27.
 */

public class Level0Item extends AbstractExpandableItem<Level1Item> implements MultiItemEntity {

    public String parrentLeft;
    public String parrentRight;

    public String getParrentLeft() {
        return parrentLeft;
    }

    public void setParrentLeft(String parrentLeft) {
        this.parrentLeft = parrentLeft;
    }

    public String getParrentRight() {
        return parrentRight;
    }

    public void setParrentRight(String parrentRight) {
        this.parrentRight = parrentRight;
    }

    public Level0Item(String parrentLeft, String parrentRight) {
        this.parrentLeft = parrentLeft;
        this.parrentRight = parrentRight;
    }

    @Override
    public int getItemType() {
        return AzureCustomerServiceAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
