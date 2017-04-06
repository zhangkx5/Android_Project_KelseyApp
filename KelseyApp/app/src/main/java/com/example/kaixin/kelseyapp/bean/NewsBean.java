package com.example.kaixin.kelseyapp.bean;

import java.io.Serializable;

/**
 * Created by kaixin on 2017/3/21.
 */

public class NewsBean implements Serializable {
    private String newsId;
    private String newsTitle;
    private String topImg;
    private String textImg0;
    private String textImg1;
    private String newsSource;
    private String newsContent;
    private String newsDigest;
    private String newsTime;

    public String getNewsId() {
        return newsId;
    }
    public String getNewsTitle() {
        return newsTitle;
    }
    public String getNewsSource() {
        return newsSource;
    }
    public String getNewsContent() {
        return newsContent;
    }
    public String getTopImg() {
        return topImg;
    }
    public String getTextImg0() {
        return textImg0;
    }
    public String getTextImg1() {
        return textImg1;
    }
    public String getNewsDigest() {
        return newsDigest;
    }
    public String getNewsTime() {
        return newsTime;
    }
    public void setNewsId(String id) {
        this.newsId = id;
    }
    public void setNewsTitle(String title) {
        this.newsTitle = title;
    }
    public void setNewsSource(String source) {
        this.newsSource = source;
    }
    public void setNewsContent(String content) {
        this.newsContent = content;
    }
    public void setTopImg(String topImg) {
        this.topImg = topImg;
    }
    public void setTextImg0(String textImg0) {
        this.textImg0 = textImg0;
    }
    public void setTextImg1(String textImg1) {
        this.textImg1 = textImg1;
    }
    public void setNewsDigest(String digest) {
        this.newsDigest = digest;
    }
    public void setNewsTime(String time) {
        this.newsTime = time;
    }
}
