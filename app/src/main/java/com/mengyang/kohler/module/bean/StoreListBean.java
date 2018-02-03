package com.mengyang.kohler.module.bean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/2
 */

public class StoreListBean {
    /**
     * pageNum : 0
     * pageSize : 10
     * resultList : [{"address":"武汉市江岸区花桥街黄孝河特1号","distance":0,"roomname":"红星美凯龙竹叶山店","storeId":843,"tel":"027-51838168"},{"address":"湖北省武汉市江汉区汉口火车站西侧欧亚达国际广场A14-17","distance":2948,"roomname":"汉口欧亚达设计中心","storeId":842,"tel":"027-85308007"},{"address":"湖北省武汉市江汉区常青路284号居然之家武汉店","distance":3366,"roomname":"汉口居然旗舰店","storeId":848,"tel":"027-83568826"}]
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
         * address : 武汉市江岸区花桥街黄孝河特1号
         * distance : 0
         * roomname : 红星美凯龙竹叶山店
         * storeId : 843
         * tel : 027-51838168
         */

        private String address;
        private int distance;
        private String roomname;
        private int storeId;
        private String tel;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public String getRoomname() {
            return roomname;
        }

        public void setRoomname(String roomname) {
            this.roomname = roomname;
        }

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }
    }
}
