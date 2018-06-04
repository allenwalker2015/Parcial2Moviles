package com.alphadev.gamesnews.room.model;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "new")
public class New {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name ="_id")
    private String id;

    @ColumnInfo(name ="title")
    private String title;

    @ColumnInfo(name ="body")
    private String body;

    @ColumnInfo(name ="game")
    private String game;

    @ColumnInfo(name ="coverImage")
    private String coverImage;
    @ColumnInfo(name ="description")
    private String description;
//    @ColumnInfo(name ="created_date")
//
//    private String createdDate;
//    @ColumnInfo(name ="__v")
//
//    private Integer v;

    public New(){

    }

    public New(@NonNull String id, String title, String body, String game, String coverImage, String description) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.game = game;
        this.coverImage = coverImage;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public String getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(String createdDate) {
//        this.createdDate = createdDate;
//    }

//    public Integer getV() {
//        return v;
//    }
//
//    public void setV(Integer v) {
//        this.v = v;
//    }

}