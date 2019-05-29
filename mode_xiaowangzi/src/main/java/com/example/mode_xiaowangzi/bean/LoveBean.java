package com.example.mode_xiaowangzi.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class LoveBean {

    @Id
    private Long longid;


    private String publishedAt;
    private String url;
    @Generated(hash = 1896658251)
    public LoveBean(Long longid, String publishedAt, String url) {
        this.longid = longid;
        this.publishedAt = publishedAt;
        this.url = url;
    }
    @Generated(hash = 461774961)
    public LoveBean() {
    }
    public Long getLongid() {
        return this.longid;
    }
    public void setLongid(Long longid) {
        this.longid = longid;
    }
    public String getPublishedAt() {
        return this.publishedAt;
    }
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

}
