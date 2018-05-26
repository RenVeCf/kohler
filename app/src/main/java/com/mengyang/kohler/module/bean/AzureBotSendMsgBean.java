package com.mengyang.kohler.module.bean;

/**
 * Created by MengYang on 2018/5/23.
 */
public class AzureBotSendMsgBean {
    /**
     * type : message
     * from : {"id":"user_name"}
     * text : kddjshsh
     */

    private String type;
    private FromBean from;
    private String text;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FromBean getFrom() {
        return from;
    }

    public void setFrom(FromBean from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static class FromBean {
        /**
         * id : user_name
         */

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
