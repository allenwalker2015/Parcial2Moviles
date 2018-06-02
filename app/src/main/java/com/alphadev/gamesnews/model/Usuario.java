package com.alphadev.gamesnews.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usuario {

    @SerializedName("favoriteNews")
    @Expose
    private List<FavoriteNews> favoriteNews = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public List<FavoriteNews> getFavoriteNews() {
        return favoriteNews;
    }

    public void setFavoriteNews(List<FavoriteNews> favoriteNews) {
        this.favoriteNews = favoriteNews;
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

}
