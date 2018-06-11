package com.alphadev.gamesnews.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserPOJO {

    @SerializedName("favoriteNews")
    @Expose
    private List<String> News = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public List<String> getNews() {
        return News;
    }

    public void setNews(List<String> News) {
        this.News = News;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("\nfavoriteNews:"+ News).append("\n").append("id:"+ id).append("\n").append("user:"+ user).append("\n").append("avatar:"+ avatar).append("\n").append("password:"+ password).append("\n").append("createdDate:"+ createdDate).append("\n").append("v:"+ v).append("\n").toString();
    }

}