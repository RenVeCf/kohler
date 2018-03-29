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
     * agendaList : [{"agendaDesc":"","agendaType":0,"briefDateStr":"03月30日 17:15-17:15","dateStr":"2018-03-30","id":118,"kvUrl":"","onlyDateStr":"03月30日","place":"厦门","timeSlot":"17:15~17:15","title":"厦门艺术展"},{"agendaDesc":"","agendaType":0,"briefDateStr":"04月30日 17:16-17:16","dateStr":"2018-04-30","id":119,"kvUrl":"","onlyDateStr":"04月30日-05月04日","place":"广州","timeSlot":"17:16~17:16","title":"广州艺术展"}]
     * designers : [{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/0f2d75d34b80cf0dac5c4d389567bd51.png?/0/w/1280/h/960","params":"","title":"Amy Zhong 钟琴","weight":1},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/8d38ae2d8677e6e5e3312cf671cb5aed.png?/0/w/1280/h/960","params":"","title":"Allyson Rees","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/de37530821b355a6db33f6f4c477df23.png?/0/w/1280/h/960","params":"","title":"D.B. Kim","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/d6d2c6cecab86abc9d4378f4a9ae4ae2.png?/0/w/1280/h/960","params":"","title":"Di Zhang & Jack Young 张迪 & 杨杰克","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/f94c3958d2b93479e30b009f88657a89.png?/0/w/1280/h/960","params":"","title":"Humberto Campana","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/a95c2bc1af2e6c8cb8bf1f84a81ea4cd.png?/0/w/1280/h/960","params":"","title":"Jamy Yang 杨明洁","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/72b90a5f4865d16973e50250611c57dc.png?/0/w/1280/h/960","params":"","title":"Jan Plechac & Henry Wielgus","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/85c9acfd9b476d398798c5a8793345c4.png?/0/w/1280/h/960","params":"","title":"Joe Cheng 郑忠","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/02577327d5365b5399c5f5e391828d85.png?/0/w/1280/h/960","params":"","title":"Ken Hu 胡伟坚","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/f748e72b1c1e2b6e4dc9c2fdc73b859a.png?/0/w/1280/h/960","params":"","title":"Marisa Yiu 姚嘉珊","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/2a273befd743449cff411307fba53bb7.png?/0/w/1280/h/960","params":"","title":"Noé Duchaufour-Lawrance","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/560d372fa51b99ccb6ccd3a2c20eb037.png?/0/w/1280/h/960","params":"","title":"Paolo Pininfarina","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/f2abab763825d95c7842421ca09c3777.png?/0/w/1280/h/960","params":"","title":"Rabih Hage","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/8aa3d3fb2150b5979b2a3044b2ff2c1d.png?/0/w/1280/h/960","params":"","title":"Seçki̇n Pi̇ri̇m","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/7f42e8e093598068d60b1d522d54d64a.png?/0/w/1280/h/960","params":"","title":"Simon Rawlings","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/7c4f1c2beb53989f408d54eba15f9c8c.png?/0/w/1280/h/960","params":"","title":"Steven Yu于鹏","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/d6a5423203767ae7d53bc37e6448fb43.png?/0/w/1280/h/960","params":"","title":"Tord Boontje","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/3c4ed032b5b92ece04507cbc99ac5ff3.png?/0/w/1280/h/960","params":"","title":"Xu Wang 王旭","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/b36202fe046cfbc1621af7ec5a1b5252.png?/0/w/1280/h/960","params":"","title":"Yuri Xavier","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/2800f2b4cad5c460bab2cad64f18a2ba.png?/0/w/1280/h/960","params":"","title":"黄晓靖","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/1e312bd26eb7396dcfe3684d81195f8d.png?/0/w/1280/h/960","params":"","title":"卢曼子 & 林振华","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/5e923d19e6b018ffb423ab2f155999be.png?/0/w/1280/h/960","params":"","title":"吕永中","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/8746398b5f8a53842bd4b753d3469fe2.png?/0/w/1280/h/960","params":"","title":"钱苘茼","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/4d7e6b3a257492325763ac91b79eddce.png?/0/w/1280/h/960","params":"","title":"深泽直人","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/deec25766b65eb219f08a278b661aad0.png?/0/w/1280/h/960","params":"","title":"吴燕玲","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/733f2cd17b41585293e8aa57c5110370.png?/0/w/1280/h/960","params":"","title":"张兆强 ","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/b4dac60221b994df55e43063f15f2286.png?/0/w/1280/h/960","params":"","title":"周宸宸","weight":0},{"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/8c02fe74ab3883eba5fa9a6026746bba.png?/0/w/1280/h/960","params":"","title":"朱晓涓","weight":0}]
     * gallery : [{"elementDesc":"Works Of Art","elementType":"image","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/0d03b03388c7c08207317b7a60fa8ad4.png?/0/w/1280/h/960","params":"6","title":"参展珍品","weight":9},{"elementDesc":"Exhibiton Visitors","elementType":"image","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/ecde55867485ff8fb85082a2633a57ac.png?/0/w/1280/h/960","params":"7","title":"现场看展","weight":8},{"elementDesc":"Interaction","elementType":"image","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/fdec3d9d5bfa4fdf34cf0841a4d9e0bf.png?/0/w/1280/h/960","params":"8","title":"互动墙","weight":7},{"elementDesc":"Designer Saloon","elementType":"image","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/aa94284f0a66937cf2a64945f8713a52.png?/0/w/1280/h/960","params":"9","title":"设计师沙龙","weight":6}]
     * id : 3
     * invitationH5Url :
     * invitationKvUrl :
     * kvUrl :
     * live : {"elementDesc":"","elementType":"video","h5Url":"https://v.qq.com/x/page/c0607816t3p.html","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/0347c235def1f2ef96c12c8d3017dade.png?/0/w/1280/h/906","params":"","title":"科勒多元文化","weight":0}
     * meetingDesc :
     * nextAgenda : {"agendaDesc":"","agendaType":2,"briefDateStr":"03月30日 17:15-17:15","dateStr":"2018-03-30","id":118,"kvUrl":"","onlyDateStr":"03月30日","place":"厦门","timeSlot":"17:15~17:15","title":"厦门艺术展"}
     * title : 敢创●科勒亚太艺术展
     * video : {"elementDesc":"","elementType":"","h5Url":"","id":-1,"kvUrl":"","params":"","title":"","weight":0}
     * weight : 0
     * works : {"elementDesc":"","elementType":"image","h5Url":"https://v.qq.com/x/page/c0607816t3p.html","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/0347c235def1f2ef96c12c8d3017dade.png?/0/w/1280/h/906","params":"","title":"展品介绍","weight":0}
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
    private WorksBean works;
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

    public WorksBean getWorks() {
        return works;
    }

    public void setWorks(WorksBean works) {
        this.works = works;
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
         * h5Url : https://v.qq.com/x/page/c0607816t3p.html
         * id : -1
         * kvUrl : http://ojd06y9cv.bkt.clouddn.com/0347c235def1f2ef96c12c8d3017dade.png?/0/w/1280/h/906
         * params :
         * title : 科勒多元文化
         * weight : 0
         */

        private String elementDesc;
        private String elementType;
        private String h5Url;
        private int id;
        private String kvUrl;
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

        public String getKvUrl() {
            return kvUrl;
        }

        public void setKvUrl(String kvUrl) {
            this.kvUrl = kvUrl;
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

    public static class NextAgendaBean {
        /**
         * agendaDesc :
         * agendaType : 2
         * briefDateStr : 03月30日 17:15-17:15
         * dateStr : 2018-03-30
         * id : 118
         * kvUrl :
         * onlyDateStr : 03月30日
         * place : 厦门
         * timeSlot : 17:15~17:15
         * title : 厦门艺术展
         */

        private String agendaDesc;
        private int agendaType;
        private String briefDateStr;
        private String dateStr;
        private int id;
        private String kvUrl;
        private String onlyDateStr;
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

        public String getOnlyDateStr() {
            return onlyDateStr;
        }

        public void setOnlyDateStr(String onlyDateStr) {
            this.onlyDateStr = onlyDateStr;
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
         * elementType :
         * h5Url :
         * id : -1
         * kvUrl :
         * params :
         * title :
         * weight : 0
         */

        private String elementDesc;
        private String elementType;
        private String h5Url;
        private int id;
        private String kvUrl;
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

        public String getKvUrl() {
            return kvUrl;
        }

        public void setKvUrl(String kvUrl) {
            this.kvUrl = kvUrl;
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

    public static class WorksBean {
        /**
         * elementDesc :
         * elementType : image
         * h5Url : https://v.qq.com/x/page/c0607816t3p.html
         * id : -1
         * kvUrl : http://ojd06y9cv.bkt.clouddn.com/0347c235def1f2ef96c12c8d3017dade.png?/0/w/1280/h/906
         * params :
         * title : 展品介绍
         * weight : 0
         */

        private String elementDesc;
        private String elementType;
        private String h5Url;
        private int id;
        private String kvUrl;
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

        public String getKvUrl() {
            return kvUrl;
        }

        public void setKvUrl(String kvUrl) {
            this.kvUrl = kvUrl;
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

    public static class AgendaListBean {
        /**
         * agendaDesc :
         * agendaType : 0
         * briefDateStr : 03月30日 17:15-17:15
         * dateStr : 2018-03-30
         * id : 118
         * kvUrl :
         * onlyDateStr : 03月30日
         * place : 厦门
         * timeSlot : 17:15~17:15
         * title : 厦门艺术展
         */

        private String agendaDesc;
        private int agendaType;
        private String briefDateStr;
        private String dateStr;
        private int id;
        private String kvUrl;
        private String onlyDateStr;
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

        public String getOnlyDateStr() {
            return onlyDateStr;
        }

        public void setOnlyDateStr(String onlyDateStr) {
            this.onlyDateStr = onlyDateStr;
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
         * elementDesc :
         * elementType :
         * h5Url :
         * id : -1
         * kvUrl : http://ojd06y9cv.bkt.clouddn.com/0f2d75d34b80cf0dac5c4d389567bd51.png?/0/w/1280/h/960
         * params :
         * title : Amy Zhong 钟琴
         * weight : 1
         */

        private String elementDesc;
        private String elementType;
        private String h5Url;
        private int id;
        private String kvUrl;
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

        public String getKvUrl() {
            return kvUrl;
        }

        public void setKvUrl(String kvUrl) {
            this.kvUrl = kvUrl;
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

    public static class GalleryBean {
        /**
         * elementDesc : Works Of Art
         * elementType : image
         * h5Url :
         * id : -1
         * kvUrl : http://ojd06y9cv.bkt.clouddn.com/0d03b03388c7c08207317b7a60fa8ad4.png?/0/w/1280/h/960
         * params : 6
         * title : 参展珍品
         * weight : 9
         */

        private String elementDesc;
        private String elementType;
        private String h5Url;
        private int id;
        private String kvUrl;
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

        public String getKvUrl() {
            return kvUrl;
        }

        public void setKvUrl(String kvUrl) {
            this.kvUrl = kvUrl;
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
