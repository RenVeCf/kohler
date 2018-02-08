package com.mengyang.kohler.module.bean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/3
 */

public class SystemMsgBean {

    /**
     * pageNum : 0
     * pageSize : 10
     * resultList : [{"msgType":"commonUser","msgContent":"123456","createTime":"2018-02-08 16:08:58","msgTitle":"123"},{"msgType":"commonUser","msgContent":"55","createTime":"2018-02-08 13:00:55","msgTitle":"aa"},{"msgType":"commonUser","msgContent":"1","createTime":"2018-02-08 12:32:21","msgTitle":"1"},{"msgType":"all","msgContent":"推送2","createTime":"2018-02-07 21:39:43","msgTitle":"推送1"},{"msgType":"all","msgContent":"fffffff","createTime":"2018-02-06 17:54:35","msgTitle":"fff"},{"msgType":"all","msgContent":"rr","createTime":"2018-02-06 17:53:10","msgTitle":"ff"},{"msgType":"all","msgContent":"ss","createTime":"2018-02-05 16:38:28","msgTitle":"ss"}]
     * totalPage : 1
     * totalSize : 7
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
         * msgType : commonUser
         * msgContent : 123456
         * createTime : 2018-02-08 16:08:58
         * msgTitle : 123
         */

        private String msgType;
        private String msgContent;
        private String createTime;
        private String msgTitle;

        public String getMsgType() {
            return msgType;
        }

        public void setMsgType(String msgType) {
            this.msgType = msgType;
        }

        public String getMsgContent() {
            return msgContent;
        }

        public void setMsgContent(String msgContent) {
            this.msgContent = msgContent;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getMsgTitle() {
            return msgTitle;
        }

        public void setMsgTitle(String msgTitle) {
            this.msgTitle = msgTitle;
        }
    }
}
