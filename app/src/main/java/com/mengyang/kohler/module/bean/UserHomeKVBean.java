package com.mengyang.kohler.module.bean;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/2
 */

public class UserHomeKVBean {
    /**
     * dictCode : KV_CUSTOMER
     * dictDesc : {"h5Url":"http://ojd06y9cv.bkt.clouddn.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960","kvUrl":"http://www.baidu.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960","clickRedirect":"http://ojd06y9cv.bkt.clouddn.com/942750628138130022d54e5ceec227e5.jpg?/0/w/1280/h/960"}
     * dictDescEn :
     * dictName : KV图
     * dictType : KV_PICTURE
     * orderSeq : 0
     * parentDictCode :
     */

    private String dictCode;
    private String dictDesc;
    private String dictDescEn;
    private String dictName;
    private String dictType;
    private int orderSeq;
    private String parentDictCode;

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictDesc() {
        return dictDesc;
    }

    public void setDictDesc(String dictDesc) {
        this.dictDesc = dictDesc;
    }

    public String getDictDescEn() {
        return dictDescEn;
    }

    public void setDictDescEn(String dictDescEn) {
        this.dictDescEn = dictDescEn;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public int getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(int orderSeq) {
        this.orderSeq = orderSeq;
    }

    public String getParentDictCode() {
        return parentDictCode;
    }

    public void setParentDictCode(String parentDictCode) {
        this.parentDictCode = parentDictCode;
    }
}
