package com.mengyang.kohler.module.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MengYang on 2018/5/22.
 */
public class KbisBean implements Serializable {
    /**
     * agendaList : [{"agendaDesc":"","agendaType":3,"briefDateStr":"05月22日 19:00-19:05","dateStr":"2018-05-22","id":121,"kvUrl":"","onlyDateStr":"05月22日","place":"","timeSlot":"19:00~19:05","title":"开场表演"},{"agendaDesc":"","agendaType":3,"briefDateStr":"05月22日 19:10-19:15","dateStr":"2018-05-22","id":123,"kvUrl":"","onlyDateStr":"05月22日","place":"","timeSlot":"19:10~19:15","title":"欢迎致辞 \u2014\u2014 科勒厨卫集团全球总裁 阮家明"},{"agendaDesc":"","agendaType":3,"briefDateStr":"05月22日 19:15-19:20","dateStr":"2018-05-22","id":124,"kvUrl":"","onlyDateStr":"05月22日","place":"","timeSlot":"19:15~19:20","title":"祝酒 \u2014\u2014 科勒公司总裁兼首席执行官 大卫·科勒、科勒厨卫集团全球总裁 阮家明、科勒厨卫集团中国区总裁 王振颜"},{"agendaDesc":"","agendaType":1,"briefDateStr":"05月23日 18:00-19:00","dateStr":"2018-05-23","id":120,"kvUrl":"","onlyDateStr":"05月23日","place":"","timeSlot":"18:00~19:00","title":"欢迎晚宴入场"},{"agendaDesc":"","agendaType":0,"briefDateStr":"05月24日 19:05-19:10","dateStr":"2018-05-24","id":122,"kvUrl":"","onlyDateStr":"05月24日","place":"","timeSlot":"19:05~19:10","title":"欢迎致辞 \u2014\u2014 科勒公司总裁兼首席执行官 大卫·科勒"},{"agendaDesc":"","agendaType":0,"briefDateStr":"05月24日 19:20-20:30","dateStr":"2018-05-24","id":125,"kvUrl":"","onlyDateStr":"05月24日","place":"","timeSlot":"19:20~20:30","title":"欢迎晚宴"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月07日 20:30-20:30","dateStr":"2018-03-07","id":126,"kvUrl":"","onlyDateStr":"03月07日","place":"","timeSlot":"20:30~20:30","title":"结束"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 09:00-09:05","dateStr":"2018-03-08","id":127,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"09:00~09:05","title":"开场视频"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 09:05-09:15","dateStr":"2018-03-08","id":128,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"09:05~09:15","title":"开场致辞 \u2014\u2014 科勒公司总裁兼首席执行官 大卫·科勒"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 09:15-09:50","dateStr":"2018-03-08","id":129,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"09:15~09:50","title":"开场演讲 \u2014\u2014 科勒厨卫集团全球总裁 阮家明"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 09:50-10:20","dateStr":"2018-03-08","id":130,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"09:50~10:20","title":"开场演讲 \u2014\u2014 科勒厨卫集团中国区总裁 王振颜"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 10:20-10:35","dateStr":"2018-03-08","id":131,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"10:20~10:35","title":"茶歇"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 10:35-11:10","dateStr":"2018-03-08","id":132,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"10:35~11:10","title":"主题演讲：2018年中国房地产前景分析及发展趋势 \u2014\u2014 钜派投资集团董事长兼首席执行官、上海市青年企业家协会副会长 倪建达"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 11:10-11:45","dateStr":"2018-03-08","id":133,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"11:10~11:45","title":"主题演讲：智能家居的智慧 \u2014\u2014 微软（中国）有限公司首席技术顾问 管震"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 11:45-12:00","dateStr":"2018-03-08","id":134,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"11:45~12:00","title":"团体照"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 12:00-13:30","dateStr":"2018-03-08","id":135,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"12:00~13:30","title":"午宴"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 13:30-14:00","dateStr":"2018-03-08","id":136,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"13:30~14:00","title":"科勒商务演讲 \u2014\u2014 科勒（中国）投资有限公司商务副总裁  徐赣华"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 14:00-14:30","dateStr":"2018-03-08","id":137,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"14:00~14:30","title":"科勒市场演讲 \u2014\u2014 科勒（中国）投资有限公司市场部副总裁 李泓"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 14:30-15:00","dateStr":"2018-03-08","id":138,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"14:30~15:00","title":"科勒厨卫演讲 -- 科勒（中国）投资有限公司整体橱柜总经理  成添祥"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 15:00-15:30","dateStr":"2018-03-08","id":139,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"15:00~15:30","title":"科勒供应链演讲 \u2014\u2014 科勒（中国）投资有限公司供应链总监 柴卫东"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 15:30-15:45","dateStr":"2018-03-08","id":140,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"15:30~15:45","title":"茶歇"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 15:45-16:15","dateStr":"2018-03-08","id":141,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"15:45~16:15","title":"Kallista & Jacob Delafon业务演讲 \u2014\u2014 科勒厨卫集团亚太区Kallista / Jacob Delafon 总经理兼工程销售总监 苏嘉亮"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 16:15-16:45","dateStr":"2018-03-08","id":142,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"16:15~16:45","title":"经销商最佳案例分享(TBD) -- 苏州金屋"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 16:45-17:15","dateStr":"2018-03-08","id":143,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"16:45~17:15","title":"经销商最佳案例 -- 经销商代表 2(TBD)"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 17:15-17:45","dateStr":"2018-03-08","id":144,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"17:15~17:45","title":"颁奖 Ⅰ"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 19:00-19:10","dateStr":"2018-03-08","id":145,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"19:00~19:10","title":"开场秀"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 19:10-19:20","dateStr":"2018-03-08","id":146,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"19:10~19:20","title":"祝酒  \u2014\u2014 科勒公司总裁兼首席执行官 大卫·科勒、科勒厨卫集团全球总裁 阮家明、科勒厨卫集团中国区总裁 王振颜"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 19:35-20:15","dateStr":"2018-03-08","id":147,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"19:35~20:15","title":"颁奖 Ⅱ"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 20:30-21:00","dateStr":"2018-03-08","id":148,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"20:30~21:00","title":"\u201c科勒未来之城\u201dVR PK赛"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月08日 21:00-21:00","dateStr":"2018-03-08","id":149,"kvUrl":"","onlyDateStr":"03月08日","place":"","timeSlot":"21:00~21:00","title":"结束"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月09日 10:30-10:30","dateStr":"2018-03-09","id":150,"kvUrl":"","onlyDateStr":"03月09日","place":"","timeSlot":"10:30~10:30","title":"班车前往上海（经销商）"},{"agendaDesc":"","agendaType":-1,"briefDateStr":"03月09日 19:00-21:30","dateStr":"2018-03-09","id":151,"kvUrl":"","onlyDateStr":"03月09日","place":"","timeSlot":"19:00~21:30","title":"科勒145周年音乐会"}]
     * artworks : []
     * designers : []
     * gallery : []
     * id : 4
     * invitationH5Url :
     * invitationKvUrl :
     * kvUrl :
     * meetingDesc :
     * nextAgenda : {"agendaDesc":"","agendaType":2,"briefDateStr":"05月22日 19:00-19:05","dateStr":"2018-05-22","id":121,"kvUrl":"","onlyDateStr":"05月22日","place":"","timeSlot":"19:00~19:05","title":"开场表演"}
     * pdfList : [{"elementDesc":"设计智慧","elementType":"pdf","h5Url":"http://ojd06y9cv.bkt.clouddn.com/571ba86d88e6cb9f677555d03c613922.pdf","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/0d03b03388c7c08207317b7a60fa8ad4.png?/0/w/1280/h/960","params":"","title":"pdf1","weight":9},{"elementDesc":"为美执着","elementType":"pdf","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/ecde55867485ff8fb85082a2633a57ac.png?/0/w/1280/h/960","params":"","title":"科勒展厅","weight":8},{"elementDesc":"跨界灵感","elementType":"pdf","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/fdec3d9d5bfa4fdf34cf0841a4d9e0bf.png?/0/w/1280/h/960","params":"","title":"其他品牌","weight":7},{"elementDesc":"设计上海盛况","elementType":"pdf","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/aa94284f0a66937cf2a64945f8713a52.png?/0/w/1280/h/960","params":"","title":"会场","weight":6}]
     * photoList : [{"elementDesc":"设计智慧","elementType":"image","h5Url":"http://www.vphotos.cn/","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/0d03b03388c7c08207317b7a60fa8ad4.png?/0/w/1280/h/960","params":"","title":"photo1","weight":9},{"elementDesc":"为美执着","elementType":"image","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/ecde55867485ff8fb85082a2633a57ac.png?/0/w/1280/h/960","params":"","title":"科勒展厅","weight":8},{"elementDesc":"跨界灵感","elementType":"image","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/fdec3d9d5bfa4fdf34cf0841a4d9e0bf.png?/0/w/1280/h/960","params":"","title":"其他品牌","weight":7},{"elementDesc":"设计上海盛况","elementType":"image","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/aa94284f0a66937cf2a64945f8713a52.png?/0/w/1280/h/960","params":"","title":"会场","weight":6}]
     * title : 2018科勒上海厨卫展
     * videoList : [{"elementDesc":"设计智慧","elementType":"video","h5Url":"https://v.qq.com/x/page/s06261rxrej.html","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/0d03b03388c7c08207317b7a60fa8ad4.png?/0/w/1280/h/960","params":"","title":"video1","weight":9},{"elementDesc":"为美执着","elementType":"video","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/ecde55867485ff8fb85082a2633a57ac.png?/0/w/1280/h/960","params":"","title":"科勒展厅","weight":8},{"elementDesc":"跨界灵感","elementType":"video","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/fdec3d9d5bfa4fdf34cf0841a4d9e0bf.png?/0/w/1280/h/960","params":"","title":"其他品牌","weight":7},{"elementDesc":"设计上海盛况","elementType":"video","h5Url":"","id":-1,"kvUrl":"http://ojd06y9cv.bkt.clouddn.com/aa94284f0a66937cf2a64945f8713a52.png?/0/w/1280/h/960","params":"","title":"会场","weight":6}]
     * weight : 0
     */

    private int id;
    private String invitationH5Url;
    private String invitationKvUrl;
    private String kvUrl;
    private String meetingDesc;
    private NextAgendaBean nextAgenda;
    private String title;
    private int weight;
    private List<AgendaListBean> agendaList;
    private List<?> artworks;
    private List<?> designers;
    private List<?> gallery;
    private List<PdfListBean> pdfList;
    private List<PhotoListBean> photoList;
    private List<VideoListBean> videoList;

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

    public List<?> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<?> artworks) {
        this.artworks = artworks;
    }

    public List<?> getDesigners() {
        return designers;
    }

    public void setDesigners(List<?> designers) {
        this.designers = designers;
    }

    public List<?> getGallery() {
        return gallery;
    }

    public void setGallery(List<?> gallery) {
        this.gallery = gallery;
    }

    public List<PdfListBean> getPdfList() {
        return pdfList;
    }

    public void setPdfList(List<PdfListBean> pdfList) {
        this.pdfList = pdfList;
    }

    public List<PhotoListBean> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<PhotoListBean> photoList) {
        this.photoList = photoList;
    }

    public List<VideoListBean> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoListBean> videoList) {
        this.videoList = videoList;
    }

    public static class NextAgendaBean {
        /**
         * agendaDesc :
         * agendaType : 2
         * briefDateStr : 05月22日 19:00-19:05
         * dateStr : 2018-05-22
         * id : 121
         * kvUrl :
         * onlyDateStr : 05月22日
         * place :
         * timeSlot : 19:00~19:05
         * title : 开场表演
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

    public static class AgendaListBean {
        /**
         * agendaDesc :
         * agendaType : 3
         * briefDateStr : 05月22日 19:00-19:05
         * dateStr : 2018-05-22
         * id : 121
         * kvUrl :
         * onlyDateStr : 05月22日
         * place :
         * timeSlot : 19:00~19:05
         * title : 开场表演
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

    public static class PdfListBean implements MultiItemEntity {
        /**
         * elementDesc : 设计智慧
         * elementType : pdf
         * h5Url : http://ojd06y9cv.bkt.clouddn.com/571ba86d88e6cb9f677555d03c613922.pdf
         * id : -1
         * kvUrl : http://ojd06y9cv.bkt.clouddn.com/0d03b03388c7c08207317b7a60fa8ad4.png?/0/w/1280/h/960
         * params :
         * title : pdf1
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
        private int itemType;

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }

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

    public static class PhotoListBean {
        /**
         * elementDesc : 设计智慧
         * elementType : image
         * h5Url : http://www.vphotos.cn/
         * id : -1
         * kvUrl : http://ojd06y9cv.bkt.clouddn.com/0d03b03388c7c08207317b7a60fa8ad4.png?/0/w/1280/h/960
         * params :
         * title : photo1
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

    public static class VideoListBean {
        /**
         * elementDesc : 设计智慧
         * elementType : video
         * h5Url : https://v.qq.com/x/page/s06261rxrej.html
         * id : -1
         * kvUrl : http://ojd06y9cv.bkt.clouddn.com/0d03b03388c7c08207317b7a60fa8ad4.png?/0/w/1280/h/960
         * params :
         * title : video1
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
