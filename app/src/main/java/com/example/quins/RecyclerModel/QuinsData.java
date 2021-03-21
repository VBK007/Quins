package com.example.quins.RecyclerModel;

public class QuinsData {
    private String Url;
    private String Username;
    private String uid;
    private String photoimageurl;

    public QuinsData() {
    }

    public QuinsData(String url, String username, String uid, String photoimageurl) {
        Url = url;
        Username = username;
        this.uid = uid;
        this.photoimageurl = photoimageurl;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
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
}
