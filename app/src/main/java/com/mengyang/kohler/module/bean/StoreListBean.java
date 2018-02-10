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
     * resultList : [{"address":"徐虹中路21号","distance":777,"latitude":31.194329,"longitude":121.431996,"roomname":"徐汇设计中心","storeId":932,"tel":"64867200"},{"address":"宜山路407号","distance":1108,"latitude":31.192982,"longitude":121.435359,"roomname":"喜盈门国际馆","storeId":933,"tel":"64867200"},{"address":"宜山路450号","distance":996,"latitude":31.190264,"longitude":121.433119,"roomname":"家饰佳","storeId":931,"tel":"64382548"},{"address":"漕溪路198号","distance":2340,"latitude":31.179596,"longitude":121.441297,"roomname":"好饰家装饰精品城","storeId":927,"tel":"64872224"}]
     * totalPage : 1
     * totalSize : 4
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
         * address : 徐虹中路21号
         * distance : 777
         * latitude : 31.194329
         * longitude : 121.431996
         * roomname : 徐汇设计中心
         * storeId : 932
         * tel : 64867200
         */

        private String address;
        private int distance;
        private double latitude;
        private double longitude;
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

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
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
