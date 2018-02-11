package com.mengyang.kohler.module;

import java.io.Serializable;
import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/6
 */

public class PdfBean {

    private String userName;
    private List<userPdfListBean> list;

    public PdfBean() {
    }
    public PdfBean(String userName) {
        this.userName = userName;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<userPdfListBean> getList() {
        return list;
    }

    public void setList(List<userPdfListBean> list) {
        this.list = list;
    }



    public static class userPdfListBean {
        private String bookKVUrl;
        private String pathUrl;


        public String getBookKVUrl() {
            return bookKVUrl;
        }

        public String getPathUrl() {
            return pathUrl;
        }

        public void setPathUrl(String pathUrl) {
            this.pathUrl = pathUrl;
        }

        public void setBookKVUrl(String bookKVUrl) {
            this.bookKVUrl = bookKVUrl;
        }
    }
}
