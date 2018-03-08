package com.mengyang.kohler.module.bean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/3/8
 */

public class WeeklyRadioConcertBean {

    /**
     * pageNum : 0
     * pageSize : 10
     * resultList : [{"createTime":"2018-03-07 17:25:23","description":"茶可夫斯基是俄罗斯作曲家","id":2,"isDelete":0,"isHidden":0,"kvUrl":"http://www.111.com","redirectUrl":"http://www.baidu.com","title":"又见柴可夫斯基","updateTime":"2018-03-07 17:25:23","weight":0}]
     * totalPage : 1
     * totalSize : 1
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
         * createTime : 2018-03-07 17:25:23
         * description : 茶可夫斯基是俄罗斯作曲家
         * id : 2
         * isDelete : 0
         * isHidden : 0
         * kvUrl : http://www.111.com
         * redirectUrl : http://www.baidu.com
         * title : 又见柴可夫斯基
         * updateTime : 2018-03-07 17:25:23
         * weight : 0
         */

        private String createTime;
        private String description;
        private int id;
        private int isDelete;
        private int isHidden;
        private String kvUrl;
        private String redirectUrl;
        private String title;
        private String updateTime;
        private int weight;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public int getIsHidden() {
            return isHidden;
        }

        public void setIsHidden(int isHidden) {
            this.isHidden = isHidden;
        }

        public String getKvUrl() {
            return kvUrl;
        }

        public void setKvUrl(String kvUrl) {
            this.kvUrl = kvUrl;
        }

        public String getRedirectUrl() {
            return redirectUrl;
        }

        public void setRedirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }
}
