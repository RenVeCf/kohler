package com.mengyang.kohler.module.bean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/6
 */

public class HomeIndexBean {
    /**
     * kvList : [{"h5Url":"http://ojd06y9cv.bkt.clouddn.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960","clickRedirect":"http://ojd06y9cv.bkt.clouddn.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960","kvUrl":"http://www.baidu.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960"},{"h5Url":"http://111111/h/960","clickRedirect":"http://33333/w/1280/h/960","kvUrl":"22222/1280/h/960"},{"h5Url":"http://ojd06y9cv.bkt.clouddn.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960","clickRedirect":"http://ojd06y9cv.bkt.clouddn.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960","kvUrl":"http://ojd06y9cv.bkt.clouddn.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960"},{"h5Url":"http://ojd06y9cv.bkt.clouddn.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960","clickRedirect":"http://ojd06y9cv.bkt.clouddn.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960","kvUrl":"http://ojd06y9cv.bkt.clouddn.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960"},{"h5Url":"http://www.baidu.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960","clickRedirect":"http://www.baidu.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960","kvUrl":"http://www.baidu.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960"},{"h5Url":"http://ojd06y9cv.bkt.clouddn.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960","clickRedirect":"http://ojd06y9cv.bkt.clouddn.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960","kvUrl":"http://ojd06y9cv.bkt.clouddn.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960"},{"h5Url":"http://ojd06y9cv.bkt.clouddn.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960","clickRedirect":"http://ojd06y9cv.bkt.clouddn.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960","kvUrl":"http://ojd06y9cv.bkt.clouddn.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960"}]
     * kvSize : 7
     * brochure : {"pageNum":0,"pageSize":2147483647,"resultList":[{"createTime":"2018-02-06 15:11:20","id":7,"isShow":1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/2a86fd879b8650e6e9ceeb46703b0a91.jpg?/0/w/1280/h/960","nameCn":"K-2009T-8-0","pdfUrl":"http://ojd06y9cv.bkt.clouddn.com/9e4230e9f78a95f6408f5073e6ba53bd.pdf","weight":4},{"createTime":"2018-02-01 20:26:33","id":1,"isShow":0,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/shouce1.jpg","nameCn":"shouce","pdfUrl":"http://ojd06y9cv.bkt.clouddn.com/9d00c5efd9825ebcb18f9d3906be2474.exe","weight":2},{"createTime":"2018-02-06 10:51:26","id":4,"isShow":0,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/937e472eb6c803e4b271a280ef18b794.jpg?/0/w/1280/h/960","nameCn":"K-2009T-1-0","pdfUrl":"http://ojd06y9cv.bkt.clouddn.com/571ba86d88e6cb9f677555d03c613922.pdf","weight":2},{"createTime":"2018-02-06 10:52:59","id":5,"isShow":1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/7efdb737cbcbf2f039fd45808dd98f60.jpg?/0/w/1280/h/960","nameCn":"K-2009T-4-0","pdfUrl":"http://ojd06y9cv.bkt.clouddn.com/31252c7e5e6ef8e549bfcb1a0071395e.pdf","weight":1},{"createTime":"2018-02-06 12:19:29","id":6,"isShow":0,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/shouce1.jpg","nameCn":"shouce1","pdfUrl":"http://ojd06y9cv.bkt.clouddn.com/160b6e6f54ff026f9719eaf11394870c.pdf","weight":1}],"totalPage":1,"totalSize":5}
     * noRead : false
     */

    private int kvSize;
    private BrochureBean brochure;
    private boolean noRead;
    private List<KvListBean> kvList;

    public int getKvSize() {
        return kvSize;
    }

    public void setKvSize(int kvSize) {
        this.kvSize = kvSize;
    }

    public BrochureBean getBrochure() {
        return brochure;
    }

    public void setBrochure(BrochureBean brochure) {
        this.brochure = brochure;
    }

    public boolean isNoRead() {
        return noRead;
    }

    public void setNoRead(boolean noRead) {
        this.noRead = noRead;
    }

    public List<KvListBean> getKvList() {
        return kvList;
    }

    public void setKvList(List<KvListBean> kvList) {
        this.kvList = kvList;
    }

    public static class BrochureBean {
        /**
         * pageNum : 0
         * pageSize : 2147483647
         * resultList : [{"createTime":"2018-02-06 15:11:20","id":7,"isShow":1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/2a86fd879b8650e6e9ceeb46703b0a91.jpg?/0/w/1280/h/960","nameCn":"K-2009T-8-0","pdfUrl":"http://ojd06y9cv.bkt.clouddn.com/9e4230e9f78a95f6408f5073e6ba53bd.pdf","weight":4},{"createTime":"2018-02-01 20:26:33","id":1,"isShow":0,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/shouce1.jpg","nameCn":"shouce","pdfUrl":"http://ojd06y9cv.bkt.clouddn.com/9d00c5efd9825ebcb18f9d3906be2474.exe","weight":2},{"createTime":"2018-02-06 10:51:26","id":4,"isShow":0,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/937e472eb6c803e4b271a280ef18b794.jpg?/0/w/1280/h/960","nameCn":"K-2009T-1-0","pdfUrl":"http://ojd06y9cv.bkt.clouddn.com/571ba86d88e6cb9f677555d03c613922.pdf","weight":2},{"createTime":"2018-02-06 10:52:59","id":5,"isShow":1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/7efdb737cbcbf2f039fd45808dd98f60.jpg?/0/w/1280/h/960","nameCn":"K-2009T-4-0","pdfUrl":"http://ojd06y9cv.bkt.clouddn.com/31252c7e5e6ef8e549bfcb1a0071395e.pdf","weight":1},{"createTime":"2018-02-06 12:19:29","id":6,"isShow":0,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/shouce1.jpg","nameCn":"shouce1","pdfUrl":"http://ojd06y9cv.bkt.clouddn.com/160b6e6f54ff026f9719eaf11394870c.pdf","weight":1}]
         * totalPage : 1
         * totalSize : 5
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
             * createTime : 2018-02-06 15:11:20
             * id : 7
             * isShow : 1
             * kvUrl : http://ojd06y9cv.bkt.clouddn.com/2a86fd879b8650e6e9ceeb46703b0a91.jpg?/0/w/1280/h/960
             * nameCn : K-2009T-8-0
             * pdfUrl : http://ojd06y9cv.bkt.clouddn.com/9e4230e9f78a95f6408f5073e6ba53bd.pdf
             * weight : 4
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

    public static class KvListBean {
        /**
         * h5Url : http://ojd06y9cv.bkt.clouddn.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960
         * clickRedirect : http://ojd06y9cv.bkt.clouddn.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960
         * kvUrl : http://www.baidu.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960
         */

        private String h5Url;
        private String clickRedirect;
        private String kvUrl;

        public String getH5Url() {
            return h5Url;
        }

        public void setH5Url(String h5Url) {
            this.h5Url = h5Url;
        }

        public String getClickRedirect() {
            return clickRedirect;
        }

        public void setClickRedirect(String clickRedirect) {
            this.clickRedirect = clickRedirect;
        }

        public String getKvUrl() {
            return kvUrl;
        }

        public void setKvUrl(String kvUrl) {
            this.kvUrl = kvUrl;
        }
    }
}
