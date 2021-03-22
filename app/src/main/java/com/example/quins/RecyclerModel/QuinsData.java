package com.example.quins.RecyclerModel;

public class QuinsData {
    private String url;
    private String username;
    private String uid;
    private String photoimageurl;
    private String desc;

    public QuinsData() {
    }

    public QuinsData(String url, String username, String uid, String photoimageurl, String desc) {
        this.url = url;
        this.username = username;
        this.uid = uid;
        this.photoimageurl = photoimageurl;
        this.desc = desc;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhotoimageurl() {
        return photoimageurl;
    }

    public void setPhotoimageurl(String photoimageurl) {
        this.photoimageurl = photoimageurl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
