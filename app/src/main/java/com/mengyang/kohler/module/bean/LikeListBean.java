package com.mengyang.kohler.module.bean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/1
 */

public class LikeListBean {

    /**
     * pageNum : 0
     * pageSize : 10
     * resultList : [{"createTime":"2018-01-27 19:24:04","name":"MARGAUX® 玛尔戈 挂墙式浴缸花洒龙头","updateTime":"2018-02-02 17:59:22","id":1,"userId":1,"skuCode":"K-72627T-9-CP","picture":"http://s7d4.scene7.com/is/image/kohlerchina/232x200%2D1?$232x200$&$Gradient=kohlerchina%2Fgradient%20%2D%20232x200&$Shadow=kohlerchina%2FBlank%20%2D%202&defaultImage=defaultsquare4&$Product=is{kohlerchina%2FK-72627T-9-CP_01}&$Badge=kohlerchina%2FBlank%20%2D%202"},{"createTime":"2018-01-27 19:39:45","name":"MARGAUX® 玛尔戈 挂墙式浴缸花洒龙头","updateTime":"2018-02-02 17:59:25","id":2,"userId":1,"skuCode":"K-72627T-9-CP","picture":"http://s7d4.scene7.com/is/image/kohlerchina/232x200%2D1?$232x200$&$Gradient=kohlerchina%2Fgradient%20%2D%20232x200&$Shadow=kohlerchina%2FBlank%20%2D%202&defaultImage=defaultsquare4&$Product=is{kohlerchina%2FK-72627T-9-CP_01}&$Badge=kohlerchina%2FBlank%20%2D%202"},{"createTime":"2018-01-27 19:39:52","name":"MARGAUX® 玛尔戈 挂墙式浴缸花洒龙头","updateTime":"2018-02-02 17:59:29","id":3,"userId":1,"skuCode":"K-72627T-9-CP","picture":"http://s7d4.scene7.com/is/image/kohlerchina/232x200%2D1?$232x200$&$Gradient=kohlerchina%2Fgradient%20%2D%20232x200&$Shadow=kohlerchina%2FBlank%20%2D%202&defaultImage=defaultsquare4&$Product=is{kohlerchina%2FK-72627T-9-CP_01}&$Badge=kohlerchina%2FBlank%20%2D%202"}]
     * totalPage : 1
     * totalSize : 3
     */

    private int pageNum;
    private int pageSize;
    private int totalPage;
    private int totalSize;
    private List<ResultListBean> resultList;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<ResultListBean> getResultList() {
        return resultList;
    }

    public void setResultList(List<ResultListBean> resultList) {
        this.resultList = resultList;
    }

    public static class ResultListBean {
        /**
         * createTime : 2018-01-27 19:24:04
         * name : MARGAUX® 玛尔戈 挂墙式浴缸花洒龙头
         * updateTime : 2018-02-02 17:59:22
         * id : 1
         * userId : 1
         * skuCode : K-72627T-9-CP
         * picture : http://s7d4.scene7.com/is/image/kohlerchina/232x200%2D1?$232x200$&$Gradient=kohlerchina%2Fgradient%20%2D%20232x200&$Shadow=kohlerchina%2FBlank%20%2D%202&defaultImage=defaultsquare4&$Product=is{kohlerchina%2FK-72627T-9-CP_01}&$Badge=kohlerchina%2FBlank%20%2D%202
         */

        private String createTime;
        private String name;
        private String updateTime;
        private int id;
        private int userId;
        private String skuCode;
        private String picture;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getSkuCode() {
            return skuCode;
        }

        public void setSkuCode(String skuCode) {
            this.skuCode = skuCode;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
    }
}
