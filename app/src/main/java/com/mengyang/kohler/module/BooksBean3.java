package com.mengyang.kohler.module;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/6
 */

public class BooksBean3 {
    private String bookKVUrl;
    private String pathUrl;

    public BooksBean3() {
    }

    public BooksBean3(String bookKVUrl,String pathUrl) {
        this.bookKVUrl = bookKVUrl;
        this.pathUrl = pathUrl;
    }

    public String getBookKVUrl() {
        return bookKVUrl;
    }

    public void setBookKVUrl(String bookKVUrl) {
        this.bookKVUrl = bookKVUrl;
    }

    public String getPathUrl() {
        return pathUrl;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
    }
}
