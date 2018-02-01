package com.mengyang.kohler.module.bean;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/1
 */

public class UploadHeadPortraitBean {
    /**
     * fileUrl : http://ojd06y9cv.bkt.clouddn.com/f78af3474e15824b3785b45402612906.png
     * mediaId : 2
     * mediaType : 1
     * thumbFileUrl : http://ojd06y9cv.bkt.clouddn.com/f78af3474e15824b3785b45402612906.png?/0/w/1280/h/960
     */

    private String fileUrl;
    private String mediaId;
    private String mediaType;
    private String thumbFileUrl;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getThumbFileUrl() {
        return thumbFileUrl;
    }

    public void setThumbFileUrl(String thumbFileUrl) {
        this.thumbFileUrl = thumbFileUrl;
    }
}
