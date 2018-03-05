package com.mengyang.kohler.module;

import java.io.Serializable;
import java.util.List;


public class PdfBean {
    private List<UserNameBean> list;

    public PdfBean() {
    }

    public PdfBean(List<UserNameBean> list) {
        this.list = list;
    }



    public List<UserNameBean> getList() {
        return list;
    }

    public void setList(List<UserNameBean> list) {
        this.list = list;
    }



    public static class UserNameBean {
        private String userName;
        private List<UserPdfItemBean> userPdfItemBean;

        public UserNameBean() {
        }

        public UserNameBean(String name) {
            this.userName = name;
        }
        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }


        public List<UserPdfItemBean> getPdfItemList() {
            return userPdfItemBean;
        }

        public void setPdfItemList(List<UserPdfItemBean> list) {
            this.userPdfItemBean = list;
        }

        public static class UserPdfItemBean {
            private String bookKVUrl;
            private String pathUrl;
            private String nameCn;

            public String getNameCn() {
                return nameCn;
            }

            public void setNameCn(String nameCn) {
                this.nameCn = nameCn;
            }

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


}
