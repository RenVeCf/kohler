package com.mengyang.kohler.module.bean;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/5
 */

public class MeetingBean {
    /**
     * id : 1
     * invitationH5Url : http://k.glor.cn/invitation/kele_jxs/index.html
     * invitationKvUrl :
     * kvUrl :
     * lastAgenda : {"agendaDesc":"所有经销商齐聚一堂，一起交流经验和这一年的感悟","briefDateStr":"02月01日 16:25-16:25","dateStr":"2018-02-01","id":2,"kvUrl":"","place":"兰花厅","timeSlot":"16:25~16:25","title":"年会午餐"}
     * meetingDesc : 敢经典，敢未来。2018年科勒中国经销商大会将在三
     月于苏州正式拉开帷幕，届时科勒将向全国经销商伙
     伴们展示逾百款融合了科技与设计的全新卫浴产品，
     共同助力更多中国消费者的美好生活。
     * nextAgenda : {"agendaDesc":"kohler准备于18年上市的全新产品，主打智能、便捷，引领厨卫新时尚","briefDateStr":"02月02日 09:28-21:28","dateStr":"2018-02-02","id":3,"kvUrl":"","place":"11号会议室","timeSlot":"09:28~21:28","title":"新品上市"}
     * title : 科勒中国经销商大会
     * tomorrowAgenda : {"agendaDesc":"在线预约功能向直接预\r\n约门店访问和微装的上门访问，该议程将详细阐\r\n述在线预约系统的功能和实际应用方法。","briefDateStr":"02月03日 16:33-16:33","dateStr":"2018-02-03","id":5,"kvUrl":"","place":"兰花厅","timeSlot":"16:33~16:33","title":"在线预约功能详解下"}
     * weight : 9999
     */

    private int id;
    private String invitationH5Url;
    private String invitationKvUrl;
    private String kvUrl;
    private LastAgendaBean lastAgenda;
    private String meetingDesc;
    private NextAgendaBean nextAgenda;
    private String title;
    private TomorrowAgendaBean tomorrowAgenda;
    private int weight;

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

    public LastAgendaBean getLastAgenda() {
        return lastAgenda;
    }

    public void setLastAgenda(LastAgendaBean lastAgenda) {
        this.lastAgenda = lastAgenda;
    }

    public String getMeetingDesc() {
        return meetingDesc;
    }

    public void setMeetingDesc(String meetingDesc) {
        this.meetingDesc = meetingDesc;
    }

    public NextAgendaBean getNextAgenda() {
        return nextAgenda;
    }

    public void setNextAgenda(NextAgendaBean nextAgenda) {
        this.nextAgenda = nextAgenda;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TomorrowAgendaBean getTomorrowAgenda() {
        return tomorrowAgenda;
    }

    public void setTomorrowAgenda(TomorrowAgendaBean tomorrowAgenda) {
        this.tomorrowAgenda = tomorrowAgenda;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public static class LastAgendaBean {
        /**
         * agendaDesc : 所有经销商齐聚一堂，一起交流经验和这一年的感悟
         * briefDateStr : 02月01日 16:25-16:25
         * dateStr : 2018-02-01
         * id : 2
         * kvUrl :
         * place : 兰花厅
         * timeSlot : 16:25~16:25
         * title : 年会午餐
         */

        private String agendaDesc;
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
    }

    public static class NextAgendaBean {
        /**
         * agendaDesc : kohler准备于18年上市的全新产品，主打智能、便捷，引领厨卫新时尚
         * briefDateStr : 02月02日 09:28-21:28
         * dateStr : 2018-02-02
         * id : 3
         * kvUrl :
         * place : 11号会议室
         * timeSlot : 09:28~21:28
         * title : 新品上市
         */

        private String agendaDesc;
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
    }

    public static class TomorrowAgendaBean {
        /**
         * agendaDesc : 在线预约功能向直接预
         约门店访问和微装的上门访问，该议程将详细阐
         述在线预约系统的功能和实际应用方法。
         * briefDateStr : 02月03日 16:33-16:33
         * dateStr : 2018-02-03
         * id : 5
         * kvUrl :
         * place : 兰花厅
         * timeSlot : 16:33~16:33
         * title : 在线预约功能详解下
         */

        private String agendaDesc;
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
    }
}
