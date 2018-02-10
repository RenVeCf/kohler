package com.mengyang.kohler.module.bean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/9
 */

public class LiveRealTimeBean {
    /**
     * pageNum : 0
     * pageSize : 10
     * resultList : [{"id":24,"isLike":false,"likeCount":1,"nameCn":"","nameEn":"","picUrl":"http://ojd06y9cv.bkt.clouddn.com/85b434cbf98949cf0353c4d1e3c04704.jpg?/0/w/1280/h/960"},{"id":22,"isLike":false,"likeCount":0,"nameCn":"","nameEn":"","picUrl":"http://ojd06y9cv.bkt.clouddn.com/5ac2732057e33abeba76756cf6de74c4.jpg?/0/w/1280/h/960"},{"id":27,"isLike":false,"likeCount":0,"nameCn":"","nameEn":"","picUrl":"http://ojd06y9cv.bkt.clouddn.com/4af47da802480b97c7d1ac27886b25ff.jpg?/0/w/1280/h/960"},{"id":28,"isLike":false,"likeCount":0,"nameCn":"","nameEn":"","picUrl":"http://ojd06y9cv.bkt.clouddn.com/24e6472e8a2b4bde9aa79f006a24a518.jpg?/0/w/1280/h/960"},{"id":29,"isLike":false,"likeCount":0,"nameCn":"","nameEn":"","picUrl":"http://ojd06y9cv.bkt.clouddn.com/426b8b428219464f7cac1ca8011c84b4.jpg?/0/w/1280/h/960"},{"id":30,"isLike":false,"likeCount":0,"nameCn":"","nameEn":"","picUrl":"http://ojd06y9cv.bkt.clouddn.com/dbdd2e2cc12b19ca04dcc20ecce9f100.jpg?/0/w/1280/h/960"},{"id":32,"isLike":false,"likeCount":0,"nameCn":"","nameEn":"","picUrl":"http://ojd06y9cv.bkt.clouddn.com/7d4cecf60cdc12a016a85c19519eb080.png?/0/w/1280/h/960"},{"id":33,"isLike":false,"likeCount":0,"nameCn":"","nameEn":"","picUrl":"http://ojd06y9cv.bkt.clouddn.com/67d4b5f89156b4f3e736a898db3d7dba.png?/0/w/1280/h/960"},{"id":34,"isLike":false,"likeCount":0,"nameCn":"","nameEn":"","picUrl":"http://ojd06y9cv.bkt.clouddn.com/a001f78504273fb8850ab65137fe9ab7.png?/0/w/1280/h/960"},{"id":36,"isLike":false,"likeCount":0,"nameCn":"","nameEn":"","picUrl":"http://ojd06y9cv.bkt.clouddn.com/8900903155f14bbe629194c5ee485592.png?/0/w/1280/h/960"}]
     * totalPage : 2
     * totalSize : 11
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
         * id : 24
         * isLike : false
         * likeCount : 1
         * nameCn :
         * nameEn :
         * picUrl : http://ojd06y9cv.bkt.clouddn.com/85b434cbf98949cf0353c4d1e3c04704.jpg?/0/w/1280/h/960
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
}
