package com.mengyang.kohler.module.bean;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/9
 */

public class LiveRealTimeBean {
    /**
     * id : 6
     * isLike : true
     * likeCount : 10
     * nameCn : 6
     * nameEn : 3
     * picUrl : http://s7d4.scene7.com/is/image/kohlerchina/1120x920-560x460-1?$560x460$&$Gradient=kohlerchina%2Fgradient%20-%201120x920&$Shadow=kohlerchina%2FBlank%20-%202&defaultImage=defaultsquare1&$Product=is{kohlerchina%2FK-3940-0_01}
     */

    private int id;
    private boolean isLike;
    private int likeCount;
    private String nameCn;
    private String nameEn;
    private String picUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIsLike() {
        return isLike;
    }

    public void setIsLike(boolean isLike) {
        this.isLike = isLike;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
