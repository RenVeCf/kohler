package com.mengyang.kohler.module.bean;

import java.util.List;

/**
 * Created by hasee on 2018/5/11.
 */
public class VisionBean {

    /**
     * pageNum : 0
     * pageSize : 10
     * resultList : [{"dictCode":"01","dictDesc":"ios","dictDescEn":"","dictId":82,"dictName":"1.0.8","dictType":"APP_VERSION","isDeleted":0,"orderSeq":0,"parentDictCode":"","updateTime":"2018-04-27 17:24:45"},{"dictCode":"02","dictDesc":"android","dictDescEn":"","dictId":83,"dictName":"1.0.6","dictType":"APP_VERSION","isDeleted":0,"orderSeq":0,"parentDictCode":"","updateTime":"2018-04-27 17:25:05"}]
     * totalPage : 3
     * totalSize : 21
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
         * dictCode : 01
         * dictDesc : ios
         * dictDescEn :
         * dictId : 82
         * dictName : 1.0.8
         * dictType : APP_VERSION
         * isDeleted : 0
         * orderSeq : 0
         * parentDictCode :
         * updateTime : 2018-04-27 17:24:45
         */

        private String dictCode;
        private String dictDesc;
        private String dictDescEn;
        private int dictId;
        private String dictName;
        private String dictType;
        private int isDeleted;
        private int orderSeq;
        private String parentDictCode;
        private String updateTime;

        public String getDictCode() {
            return dictCode;
        }

        public void setDictCode(String dictCode) {
            this.dictCode = dictCode;
        }

        public String getDictDesc() {
            return dictDesc;
        }

        public void setDictDesc(String dictDesc) {
            this.dictDesc = dictDesc;
        }

        public String getDictDescEn() {
            return dictDescEn;
        }

        public void setDictDescEn(String dictDescEn) {
            this.dictDescEn = dictDescEn;
        }

        public int getDictId() {
            return dictId;
        }

        public void setDictId(int dictId) {
            this.dictId = dictId;
        }

        public String getDictName() {
            return dictName;
        }

        public void setDictName(String dictName) {
            this.dictName = dictName;
        }

        public String getDictType() {
            return dictType;
        }

        public void setDictType(String dictType) {
            this.dictType = dictType;
        }

        public int getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(int isDeleted) {
            this.isDeleted = isDeleted;
        }

        public int getOrderSeq() {
            return orderSeq;
        }

        public void setOrderSeq(int orderSeq) {
            this.orderSeq = orderSeq;
        }

        public String getParentDictCode() {
            return parentDictCode;
        }

        public void setParentDictCode(String parentDictCode) {
            this.parentDictCode = parentDictCode;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
