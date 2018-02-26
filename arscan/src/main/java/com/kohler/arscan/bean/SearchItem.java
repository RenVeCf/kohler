package com.kohler.arscan.bean;

/**
 * Created by chenchen on 18-2-24.
 */

public class SearchItem {

    private int image;
    private String content;

    public SearchItem(int image, String content) {
        this.image = image;
        this.content = content;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
