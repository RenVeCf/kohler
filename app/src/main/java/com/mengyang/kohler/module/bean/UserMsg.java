package com.mengyang.kohler.module.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by liusong on 2018/2/9.
 */

public class UserMsg implements MultiItemEntity {
    public static final int RECEIVED = 0;//收到一条消息

    public static final int SENT = 1;//发出一条消息

    private String  content;//消息的内容

    private int type;//消息的类型

    public  UserMsg(String content,int type){
        this.content = content;
        this.type = type;
    }

    public  UserMsg(String content){
        this.content = content;
        this.type = type;
    }

    public String getContent(){
        return content;
    }

    public int getType(){
        return type;
    }

    @Override
    public int getItemType() {
        return 0;
    }
}
