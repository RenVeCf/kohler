package com.mengyang.kohler.module.bean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/6
 */

public class BooksListBean {
    /**
     * pageNum : 0
     * pageSize : 10
     * resultList : [{"createTime":"2018-02-01 20:26:33","id":1,"isShow":0,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/shouce1.jpg","nameCn":"shouce","pdfUrl":"http://ojd06y9cv.bkt.clouddn.com/shouce1.jpg","weight":2}]
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
         * createTime : 2018-02-01 20:26:33
         * id : 1
         * isShow : 0
         * kvUrl : http://ojd06y9cv.bkt.clouddn.com/shouce1.jpg
         * nameCn : shouce
         * pdfUrl : http://ojd06y9cv.bkt.clouddn.com/shouce1.jpg
         * weight : 2
         */

        private String createTime;
        private int id;
        private int isShow;
        private String kvUrl;
        private String nameCn;
        private String pdfUrl;
        private int weight;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsShow() {
            return isShow;
        }

        public void setIsShow(int isShow) {
            this.isShow = isShow;
        }

        public String getKvUrl() {
            return kvUrl;
        }

        public void setKvUrl(String kvUrl) {
            this.kvUrl = kvUrl;
        }

        public String getNameCn() {
            return nameCn;
        }

        public void setNameCn(String nameCn) {
            this.nameCn = nameCn;
        }

        public String getPdfUrl() {
            return pdfUrl;
        }

        public void setPdfUrl(String pdfUrl) {
            this.pdfUrl = pdfUrl;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }
}
