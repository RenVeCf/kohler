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
     * resultList : [{"createTime":"2018-02-07 20:12:30","id":8,"isShow":0,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/4969f7828af22e1ec8d30ecc9e7d2c22.png?/0/w/1280/h/960   \t\t\t\t","nameCn":"经销商手册","pdfUrl":"http://ojd06y9cv.bkt.clouddn.com/619820641c5890217b99e5cc968e526c.pdf   \t\t\t\t","videoUrl":"","weight":103},{"createTime":"2018-03-01 16:06:49","id":10,"isShow":0,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/f408b773150fd3fde06b78d74be11084.jpg?/0/w/1280/h/960   \t\t\t\t","nameCn":"淋浴龙头","pdfUrl":"","videoUrl":"https://v.qq.com/x/page/i056136eut0.html   \t\t\t\t","weight":102},{"createTime":"2018-03-01 16:00:26","id":9,"isShow":0,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/6097371b2204ded4a8e0972a49c04968.jpg?/0/w/1280/h/960   \t\t\t\t","nameCn":"c3座便器盖板","pdfUrl":"","videoUrl":"https://v.qq.com/x/page/x13037qe46q.html   \t\t\t\t","weight":101}]
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
         * createTime : 2018-02-07 20:12:30
         * id : 8
         * isShow : 0
         * kvUrl : http://ojd06y9cv.bkt.clouddn.com/4969f7828af22e1ec8d30ecc9e7d2c22.png?/0/w/1280/h/960
         * nameCn : 经销商手册
         * pdfUrl : http://ojd06y9cv.bkt.clouddn.com/619820641c5890217b99e5cc968e526c.pdf
         * videoUrl :
         * weight : 103
         */

        private String createTime;
        private int id;
        private int isShow;
        private String kvUrl;
        private String nameCn;
        private String pdfUrl;
        private String videoUrl;
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

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }
}
