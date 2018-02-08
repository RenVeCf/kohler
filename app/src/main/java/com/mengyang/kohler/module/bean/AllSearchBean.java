package com.mengyang.kohler.module.bean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/3
 */

public class AllSearchBean {

    /**
     * pageNum : 0
     * pageSize : 3
     * resultList : [{"productName":"ADAIR™艾迪儿丽裙版4.0升五级旋风绿能分体座便器","skuCode":" K-77244T-0","listImageUrl":"http://s7d4.scene7.com/is/image/kohlerchina/K-77244T-0_01"},{"productName":"OBLO® 欧宝 缸边式浴缸龙头","skuCode":"K-10059T-9-CP","listImageUrl":"http://s7d4.scene7.com/is/image/kohlerchina/K-10059T-9-CP_01"},{"productName":"OBLO® 欧宝 双把单孔脸盆龙头","skuCode":"K-10085T-9-CP","listImageUrl":"http://s7d4.scene7.com/is/image/kohlerchina/K-10085T-9-CP_01"}]
     * totalPage : 1176
     * totalSize : 3528
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
         * productName : ADAIR™艾迪儿丽裙版4.0升五级旋风绿能分体座便器
         * skuCode :  K-77244T-0
         * listImageUrl : http://s7d4.scene7.com/is/image/kohlerchina/K-77244T-0_01
         */

        private String productName;
        private String skuCode;
        private String listImageUrl;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getSkuCode() {
            return skuCode;
        }

        public void setSkuCode(String skuCode) {
            this.skuCode = skuCode;
        }

        public String getListImageUrl() {
            return listImageUrl;
        }

        public void setListImageUrl(String listImageUrl) {
            this.listImageUrl = listImageUrl;
        }
    }
}
