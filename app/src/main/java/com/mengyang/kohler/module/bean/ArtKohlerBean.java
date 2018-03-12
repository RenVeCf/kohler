package com.mengyang.kohler.module.bean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/3/12
 */

public class ArtKohlerBean {
    /**
     * agendaList : [{"agendaDesc":"","agendaType":3,"briefDateStr":"03月12日 17:02-17:02","dateStr":"2018-03-12","id":88,"kvUrl":"","place":"92号草坪","timeSlot":"17:02~17:02","title":"科勒商务演讲"},{"agendaDesc":"","agendaType":1,"briefDateStr":"03月13日 17:04-17:04","dateStr":"2018-03-13","id":89,"kvUrl":"","place":"9号会议室","timeSlot":"17:04~17:04","title":"科勒市场演讲"},{"agendaDesc":"","agendaType":0,"briefDateStr":"03月14日 17:05-17:05","dateStr":"2018-03-14","id":90,"kvUrl":"","place":"9楼","timeSlot":"17:05~17:05","title":"科勒厨卫演讲"}]
     * designers : [{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/0f2d75d34b80cf0dac5c4d389567bd51.png?/0/w/1280/h/960","title":"Amy Zhong 钟琴","weight":1},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/de37530821b355a6db33f6f4c477df23.png?/0/w/1280/h/960","title":"D.B. Kim","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/d6d2c6cecab86abc9d4378f4a9ae4ae2.png?/0/w/1280/h/960","title":"Di Zhang & Jack Young 张迪 & 杨杰克","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/f94c3958d2b93479e30b009f88657a89.png?/0/w/1280/h/960","title":"Humberto Campana","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/a95c2bc1af2e6c8cb8bf1f84a81ea4cd.png?/0/w/1280/h/960","title":"Jamy Yang 杨明洁","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/72b90a5f4865d16973e50250611c57dc.png?/0/w/1280/h/960","title":"Jan Plechac & Henry Wielgus","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/85c9acfd9b476d398798c5a8793345c4.png?/0/w/1280/h/960","title":"Joe Cheng 郑忠","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/02577327d5365b5399c5f5e391828d85.png?/0/w/1280/h/960","title":"Ken Hu 胡伟坚","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/f748e72b1c1e2b6e4dc9c2fdc73b859a.png?/0/w/1280/h/960","title":"Marisa Yiu 姚嘉珊","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/2a273befd743449cff411307fba53bb7.png?/0/w/1280/h/960","title":"Noé Duchaufour-Lawrance","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/560d372fa51b99ccb6ccd3a2c20eb037.png?/0/w/1280/h/960","title":"Paolo Pininfarina","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/f2abab763825d95c7842421ca09c3777.png?/0/w/1280/h/960","title":"Rabih Hage","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/8aa3d3fb2150b5979b2a3044b2ff2c1d.png?/0/w/1280/h/960","title":"Seçki̇n Pi̇ri̇m","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/7f42e8e093598068d60b1d522d54d64a.png?/0/w/1280/h/960","title":"Simon Rawlings","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/7c4f1c2beb53989f408d54eba15f9c8c.png?/0/w/1280/h/960","title":"Steven Yu于鹏","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/d6a5423203767ae7d53bc37e6448fb43.png?/0/w/1280/h/960","title":"Tord Boontje","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/3c4ed032b5b92ece04507cbc99ac5ff3.png?/0/w/1280/h/960","title":"Xu Wang 王旭","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/b36202fe046cfbc1621af7ec5a1b5252.png?/0/w/1280/h/960","title":"Yuri Xavier","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/2800f2b4cad5c460bab2cad64f18a2ba.png?/0/w/1280/h/960","title":"黄晓靖","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/1e312bd26eb7396dcfe3684d81195f8d.png?/0/w/1280/h/960","title":"卢曼子 & 林振华","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/5e923d19e6b018ffb423ab2f155999be.png?/0/w/1280/h/960","title":"吕永中","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/8746398b5f8a53842bd4b753d3469fe2.png?/0/w/1280/h/960","title":"钱苘茼","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/4d7e6b3a257492325763ac91b79eddce.png?/0/w/1280/h/960","title":"深泽直人","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/deec25766b65eb219f08a278b661aad0.png?/0/w/1280/h/960","title":"吴燕玲","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/733f2cd17b41585293e8aa57c5110370.png?/0/w/1280/h/960","title":"张兆强 ","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/b4dac60221b994df55e43063f15f2286.png?/0/w/1280/h/960","title":"周宸宸","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/8c02fe74ab3883eba5fa9a6026746bba.png?/0/w/1280/h/960","title":"朱晓涓","weight":0},{"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/8d38ae2d8677e6e5e3312cf671cb5aed.png?/0/w/1280/h/960","title":"Allyson Rees"}]
     * gallery : [{"elementDesc":"无跨界，不好玩","elementType":"image","kvUrl":"http://ojd06y9cv.bkt.clouddn.com/33bcf3389892e9f3589552df8e0e4367.png?/0/w/1280/h/960","title":"关于时尚的新定义","weight":9},{"elementDesc":"无跨界，不好玩","elementType":"image","kvUrl":"http://ojd06y9cv.bkt.clouddn.com/fdec3d9d5bfa4fdf34cf0841a4d9e0bf.png?/0/w/1280/h/960","title":"关于时尚的新定义","weight":8},{"elementDesc":"无跨界，不好玩","elementType":"image","kvUrl":"http://ojd06y9cv.bkt.clouddn.com/8ecf60934e8c993153bdcc6c62afd762.png?/0/w/1280/h/960","title":"关于时尚的新定义","weight":7},{"elementDesc":"无跨界，不好玩","elementType":"image","kvUrl":"http://ojd06y9cv.bkt.clouddn.com/ccf430b323d5e7e6ce8aa016f525fe15.png?/0/w/1280/h/960","title":"关于时尚的新定义","weight":6}]
     * id : 2
     * invitationH5Url :
     * invitationKvUrl :
     * kvUrl :
     * live : {"elementDesc":"","elementType":"video","h5Url":"http://www.baidu.com","kvUrl":"http://ojd06y9cv.bkt.clouddn.com/0347c235def1f2ef96c12c8d3017dade.png?/0/w/1280/h/960","title":"一群来自香港的新锐设计师手作","weight":0}
     * meetingDesc :
     * nextAgenda : {"agendaDesc":"","agendaType":2,"briefDateStr":"03月13日 17:04-17:04","dateStr":"2018-03-13","id":89,"kvUrl":"","place":"9号会议室","timeSlot":"17:04~17:04","title":"科勒市场演讲"}
     * title : 亚洲顶级国际设计展
     * video : {"elementDesc":"","elementType":"video","h5Url":"http://www.youku.com","kvUrl":"http://ojd06y9cv.bkt.clouddn.com/2253398f6fc3edc8d12f9236e3d57ca7.png?/0/w/1280/h/960","title":"钢琴家李天帝演奏会","weight":0}
     * weight : 0
     */

    private int id;
    private String invitationH5Url;
    private String invitationKvUrl;
    private String kvUrl;
    private LiveBean live;
    private String meetingDesc;
    private NextAgendaBean nextAgenda;
    private String title;
    private VideoBean video;
    private int weight;
    private List<AgendaListBean> agendaList;
    private List<DesignersBean> designers;
    private List<GalleryBean> gallery;

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

    public LiveBean getLive() {
        return live;
    }

    public void setLive(LiveBean live) {
        this.live = live;
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

    public VideoBean getVideo() {
        return video;
    }

    public void setVideo(VideoBean video) {
        this.video = video;
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

    public List<DesignersBean> getDesigners() {
        return designers;
    }

    public void setDesigners(List<DesignersBean> designers) {
        this.designers = designers;
    }

    public List<GalleryBean> getGallery() {
        return gallery;
    }

    public void setGallery(List<GalleryBean> gallery) {
        this.gallery = gallery;
    }

    public static class LiveBean {
        /**
         * elementDesc :
         * elementType : video
         * h5Url : http://www.baidu.com
         * kvUrl : http://ojd06y9cv.bkt.clouddn.com/0347c235def1f2ef96c12c8d3017dade.png?/0/w/1280/h/960
         * title : 一群来自香港的新锐设计师手作
         * weight : 0
         */

        private String elementDesc;
        private String elementType;
        private String h5Url;
        private String kvUrl;
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

        public String getKvUrl() {
            return kvUrl;
        }

        public void setKvUrl(String kvUrl) {
            this.kvUrl = kvUrl;
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

    public static class NextAgendaBean {
        /**
         * agendaDesc :
         * agendaType : 2
         * briefDateStr : 03月13日 17:04-17:04
         * dateStr : 2018-03-13
         * id : 89
         * kvUrl :
         * place : 9号会议室
         * timeSlot : 17:04~17:04
         * title : 科勒市场演讲
         */

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
    }

    public static class VideoBean {
        /**
         * elementDesc :
         * elementType : video
         * h5Url : http://www.youku.com
         * kvUrl : http://ojd06y9cv.bkt.clouddn.com/2253398f6fc3edc8d12f9236e3d57ca7.png?/0/w/1280/h/960
         * title : 钢琴家李天帝演奏会
         * weight : 0
         */

        private String elementDesc;
        private String elementType;
        private String h5Url;
        private String kvUrl;
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

        public String getKvUrl() {
            return kvUrl;
        }

        public void setKvUrl(String kvUrl) {
            this.kvUrl = kvUrl;
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

    public static class AgendaListBean {
        /**
         * agendaDesc :
         * agendaType : 3
         * briefDateStr : 03月12日 17:02-17:02
         * dateStr : 2018-03-12
         * id : 88
         * kvUrl :
         * place : 92号草坪
         * timeSlot : 17:02~17:02
         * title : 科勒商务演讲
         */

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
    }

    public static class DesignersBean {
        /**
         * kvUrl : http://ojd06y9cv.bkt.clouddn.com/0f2d75d34b80cf0dac5c4d389567bd51.png?/0/w/1280/h/960
         * title : Amy Zhong 钟琴
         * weight : 1
         */

        private String kvUrl;
        private String title;
        private int weight;

        public String getKvUrl() {
            return kvUrl;
        }

        public void setKvUrl(String kvUrl) {
            this.kvUrl = kvUrl;
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

    public static class GalleryBean {
        /**
         * elementDesc : 无跨界，不好玩
         * elementType : image
         * kvUrl : http://ojd06y9cv.bkt.clouddn.com/33bcf3389892e9f3589552df8e0e4367.png?/0/w/1280/h/960
         * title : 关于时尚的新定义
         * weight : 9
         */

        private String elementDesc;
        private String elementType;
        private String kvUrl;
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

        public String getKvUrl() {
            return kvUrl;
        }

        public void setKvUrl(String kvUrl) {
            this.kvUrl = kvUrl;
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
