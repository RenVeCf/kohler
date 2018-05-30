package com.mengyang.kohler.module.bean;

import java.util.List;

public class AzureServiceBean {
    /**
     * data : {"clickVos":[{"id":-1,"params":"","text":"","weight":0},{"id":-1,"params":"","text":"","weight":0},{"id":-1,"params":"","text":"","weight":0}],"message":"你好，欢迎使用智能客服系统！我是小科，请多指教。","multimedia":[{"elementDesc":"","elementType":"","h5Url":"","id":-1,"imageUrl":"","params":"","title":"","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"imageUrl":"","params":"","title":"","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"imageUrl":"","params":"","title":"","weight":0}]}
     * error : 200
     * message : successful
     * status : 1
     */

    private DataBean data;
    private String error;
    private String message;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * clickVos : [{"id":-1,"params":"","text":"","weight":0},{"id":-1,"params":"","text":"","weight":0},{"id":-1,"params":"","text":"","weight":0}]
         * message : 你好，欢迎使用智能客服系统！我是小科，请多指教。
         * multimedia : [{"elementDesc":"","elementType":"","h5Url":"","id":-1,"imageUrl":"","params":"","title":"","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"imageUrl":"","params":"","title":"","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"imageUrl":"","params":"","title":"","weight":0}]
         */

        private String message;
        private List<ClickVosBean> clickVos;
        private List<MultimediaBean> multimedia;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<ClickVosBean> getClickVos() {
            return clickVos;
        }

        public void setClickVos(List<ClickVosBean> clickVos) {
            this.clickVos = clickVos;
        }

        public List<MultimediaBean> getMultimedia() {
            return multimedia;
        }

        public void setMultimedia(List<MultimediaBean> multimedia) {
            this.multimedia = multimedia;
        }

        public static class ClickVosBean {
            /**
             * id : -1
             * params :
             * text :
             * weight : 0
             */

            private int id;
            private String params;
            private String text;
            private int weight;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getParams() {
                return params;
            }

            public void setParams(String params) {
                this.params = params;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public int getWeight() {
                return weight;
            }

            public void setWeight(int weight) {
                this.weight = weight;
            }
        }

        public static class MultimediaBean {
            /**
             * elementDesc :
             * elementType :
             * h5Url :
             * id : -1
             * imageUrl :
             * params :
             * title :
             * weight : 0
             */

            private String elementDesc;
            private String elementType;
            private String h5Url;
            private int id;
            private String imageUrl;
            private String params;
            private String title;
            private int weight;

            public String getElementDesc() {
                return elementDesc;
            }

            public void setElementDesc(String elementDesc) {
                this.elementDesc = elementDesc;
            }

            public String getElementType() {
                return elementType;
            }

            public void setElementType(String elementType) {
                this.elementType = elementType;
            }

            public String getH5Url() {
                return h5Url;
            }

            public void setH5Url(String h5Url) {
                this.h5Url = h5Url;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getParams() {
                return params;
            }

            public void setParams(String params) {
                this.params = params;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getWeight() {
                return weight;
            }

            public void setWeight(int weight) {
                this.weight = weight;
            }
        }
    }
}
