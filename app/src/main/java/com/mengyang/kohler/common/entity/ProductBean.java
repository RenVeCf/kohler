package com.mengyang.kohler.common.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mengyang.kohler.common.adapter.AzureCustomerServiceAdapter;

/**
 * Created by liusong on 2018/5/27.
 */

public class ProductBean implements MultiItemEntity {
    @Override
    public int getItemType() {
        return AzureCustomerServiceAdapter.TYPE_PRODUCT;
    }
}
