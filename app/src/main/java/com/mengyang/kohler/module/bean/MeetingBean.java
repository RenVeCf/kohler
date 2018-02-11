package com.mengyang.kohler.module.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/5
 */

public class MeetingBean {

    /**
     * agendaList : [{"agendaDesc":"kohler准备于18年上市的全新产品，主打智能、便捷，引领厨卫新时尚","agendaType":0,"briefDateStr":"02月05日 09:28-21:28","dateStr":"2018-02-05","id":3,"kvUrl":"","place":"11号会议室","timeSlot":"09:28~21:28","title":"新品上市"},{"agendaDesc":"所有经销商齐聚一堂，一起交流经验和这一年的感悟","agendaType":0,"briefDateStr":"02月05日 16:25-16:25","dateStr":"2018-02-05","id":2,"kvUrl":"","place":"兰花厅","timeSlot":"16:25~16:25","title":"年会午餐"},{"agendaDesc":"用户可以通过官网在线预约功能向直接预\r\n约门店访问和微装的上门访问，该议程将详细阐\r\n述在线预约系统的功能和实际应用方法。","agendaType":2,"briefDateStr":"02月08日 01:33-20:33","dateStr":"2018-02-08","id":4,"kvUrl":"","place":"多功能厅","timeSlot":"01:33~20:33","title":"在线预约功能详解中"},{"agendaDesc":"用户可以通过官网在线预约功能向直接预\r\n约门店访问和微装的上门访问，该议程将详细阐\r\n述在线预约系统的功能和实际应用方法。","agendaType":3,"briefDateStr":"02月08日 01:33-20:33","dateStr":"2018-02-08","id":4,"kvUrl":"","place":"多功能厅","timeSlot":"01:33~20:33","title":"在线预约功能详解中"},{"agendaDesc":"在线预约功能向直接预\r\n约门店访问和微装的上门访问，该议程将详细阐\r\n述在线预约系统的功能和实际应用方法。","agendaType":3,"briefDateStr":"02月08日 17:57-20:27","dateStr":"2018-02-08","id":5,"kvUrl":"","place":"兰花厅","timeSlot":"17:57~20:27","title":"在线预约功能详解下"}]
     * id : 1
     * invitationH5Url : http://k.glor.cn/invitation/kele_jxs/index.html
     * invitationKvUrl :
     * kvUrl :
     * meetingDesc : 敢经典，敢未来。2018年科勒中国经销商大会将在三
     月于苏州正式拉开帷幕，届时科勒将向全国经销商伙
     伴们展示逾百款融合了科技与设计的全新卫浴产品，
     共同助力更多中国消费者的美好生活。
     * title : 科勒中国经销商大会
     * weight : 9999
     */

    private int id;
    private String invitationH5Url;
    private String invitationKvUrl;
    private String kvUrl;
    private String meetingDesc;
    private String title;
    private int weight;
    private List<AgendaListBean> agendaList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvitationH5Url() {
        return invitationH5Url;
    }

    public void setInvitationH5Url(String invitationH5Url) {
        this.invitationH5Url = invitationH5Url;
    }

    public String getInvitationKvUrl() {
        return invitationKvUrl;
    }

    public void setInvitationKvUrl(String invitationKvUrl) {
        this.invitationKvUrl = invitationKvUrl;
    }

    public String getKvUrl() {
        return kvUrl;
    }

    public void setKvUrl(String kvUrl) {
        this.kvUrl = kvUrl;
    }

    public String getMeetingDesc() {
        return meetingDesc;
    }

    public void setMeetingDesc(String meetingDesc) {
        this.meetingDesc = meetingDesc;
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

    public List<AgendaListBean> getAgendaList() {
        return agendaList;
    }

    public void setAgendaList(List<AgendaListBean> agendaList) {
        this.agendaList = agendaList;
    }

    public static class AgendaListBean implements MultiItemEntity {
        /**
         * agendaDesc : kohler准备于18年上市的全新产品，主打智能、便捷，引领厨卫新时尚
         * agendaType : 0
         * briefDateStr : 02月05日 09:28-21:28
         * dateStr : 2018-02-05
         * id : 3
         * kvUrl :
         * place : 11号会议室
         * timeSlot : 09:28~21:28
         * title : 新品上市
         */
        private int itemType;
        private String agendaDesc;
        private int agendaType;
        private String briefDateStr;
        private String dateStr;
        private int id;
        private String kvUrl;
        private String place;
        private String timeSlot;
        private String title;

        public String getAgendaDesc() {
            return agendaDesc;
        }

        public void setAgendaDesc(String agendaDesc) {
            this.agendaDesc = agendaDesc;
        }

        public int getAgendaType() {
            return agendaType;
        }

        public void setAgendaType(int agendaType) {
            this.agendaType = agendaType;
        }

        public String getBriefDateStr() {
            return briefDateStr;
        }

        public void setBriefDateStr(String briefDateStr) {
            this.briefDateStr = briefDateStr;
        }

        public String getDateStr() {
            return dateStr;
        }

        public void setDateStr(String dateStr) {
            this.dateStr = dateStr;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getKvUrl() {
            return kvUrl;
        }

        public void setKvUrl(String kvUrl) {
            this.kvUrl = kvUrl;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getTimeSlot() {
            return timeSlot;
        }

        public void setTimeSlot(String timeSlot) {
            this.timeSlot = timeSlot;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }
    }
}
