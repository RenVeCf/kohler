package com.mengyang.kohler.module.bean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/3/13
 */

public class ArtKohlerSelectImgBean {
    /**
     * pageNum : 0
     * pageSize : 2147483647
     * resultList : [{"groupId":2,"id":1062,"likeCount":0,"nameCn":"","nameEn":"","picUrl":"http://ojd06y9cv.bkt.clouddn.com/2d8e935617ca9c6138d6a5c0329f561d.png?/0/w/1280/h/960"}]
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
         * groupId : 2
         * id : 1062
         * likeCount : 0
         * nameCn :
         * nameEn :
         * picUrl : http://ojd06y9cv.bkt.clouddn.com/2d8e935617ca9c6138d6a5c0329f561d.png?/0/w/1280/h/960
         */

        private int groupId;
        private int id;
        private int likeCount;
        private String nameCn;
        private String nameEn;
        private String picUrl;

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
