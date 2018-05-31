package com.mengyang.kohler.module.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/*
 * Created by hasee on 2018/5/30.
 */public class AzureServiceMultimediaBean implements MultiItemEntity {
    private String elementDesc;
    private String elementType;
    private String h5Url;
    private int id;
    private String imageUrl;
    private String params;
    private String title;
    private int weight;
    private int itemType;

    public AzureServiceMultimediaBean(String elementDesc, String elementType, String imageUrl, String title, int itemType) {
        this.elementDesc = elementDesc;
        this.elementType = elementType;
        this.imageUrl = imageUrl;
        this.title = title;
        this.itemType = itemType;
    }

    public String getElementDesc() {
        return elementDesc;
    }

    public void setElementDesc(String elementDesc) {
        this.elementDesc = elementDesc;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
