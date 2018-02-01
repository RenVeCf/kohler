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
     * pageSize : 2
     * resultList : [{"createTime":"2018-01-27 19:24:04","id":1,"isDelete":0,"skuCode":"K-8888-22","skuId":0,"updateTime":"2018-01-27 19:45:13","userId":1},{"createTime":"2018-01-27 19:39:45","id":2,"isDelete":0,"skuCode":"K-8888-33","skuId":0,"updateTime":"2018-01-27 19:39:45","userId":1}]
     * totalPage : 2
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
         * id : 1
         * isDelete : 0
         * skuCode : K-8888-22
         * skuId : 0
         * updateTime : 2018-01-27 19:45:13
         * userId : 1
         */

        private String createTime;
        private int id;
        private int isDelete;
        private String skuCode;
        private int skuId;
        private String updateTime;
        private int userId;

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

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public String getSkuCode() {
            return skuCode;
        }

        public void setSkuCode(String skuCode) {
            this.skuCode = skuCode;
        }

        public int getSkuId() {
            return skuId;
        }

        public void setSkuId(int skuId) {
            this.skuId = skuId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
