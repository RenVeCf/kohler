package com.mengyang.kohler.module.bean;

import java.util.List;

/**
 * Created by MengYang on 2018/5/22.
 */
public class AzureBotAnswerQuestionBean {
    /**
     * activities : [{"type":"message","id":"2T3PVfYuNYyEyniSoDZwe0|0000001","timestamp":"2018-05-22T14:38:44.5778335Z","localTimestamp":"2018-05-22T14:38:44.18+00:00","channelId":"directline","from":{"id":"WEB_APP","name":"管家阿科"},"conversation":{"id":"2T3PVfYuNYyEyniSoDZwe0"},"text":"我在尝试理解。。。或许你可以换个说法？","inputHint":"acceptingInput","replyToId":"2T3PVfYuNYyEyniSoDZwe0|0000000"}]
     * watermark : 1
     */

    private String watermark;
    private List<ActivitiesBean> activities;

    public String getWatermark() {
        return watermark;
    }

    public void setWatermark(String watermark) {
        this.watermark = watermark;
    }

    public List<ActivitiesBean> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivitiesBean> activities) {
        this.activities = activities;
    }

    public static class ActivitiesBean {
        /**
         * type : message
         * id : 2T3PVfYuNYyEyniSoDZwe0|0000001
         * timestamp : 2018-05-22T14:38:44.5778335Z
         * localTimestamp : 2018-05-22T14:38:44.18+00:00
         * channelId : directline
         * from : {"id":"WEB_APP","name":"管家阿科"}
         * conversation : {"id":"2T3PVfYuNYyEyniSoDZwe0"}
         * text : 我在尝试理解。。。或许你可以换个说法？
         * inputHint : acceptingInput
         * replyToId : 2T3PVfYuNYyEyniSoDZwe0|0000000
         */

        private String type;
        private String id;
        private String timestamp;
        private String localTimestamp;
        private String channelId;
        private FromBean from;
        private ConversationBean conversation;
        private String text;
        private String inputHint;
        private String replyToId;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getLocalTimestamp() {
            return localTimestamp;
        }

        public void setLocalTimestamp(String localTimestamp) {
            this.localTimestamp = localTimestamp;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public FromBean getFrom() {
            return from;
        }

        public void setFrom(FromBean from) {
            this.from = from;
        }

        public ConversationBean getConversation() {
            return conversation;
        }

        public void setConversation(ConversationBean conversation) {
            this.conversation = conversation;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getInputHint() {
            return inputHint;
        }

        public void setInputHint(String inputHint) {
            this.inputHint = inputHint;
        }

        public String getReplyToId() {
            return replyToId;
        }

        public void setReplyToId(String replyToId) {
            this.replyToId = replyToId;
        }

        public static class FromBean {
            /**
             * id : WEB_APP
             * name : 管家阿科
             */

            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class ConversationBean {
            /**
             * id : 2T3PVfYuNYyEyniSoDZwe0
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
}
