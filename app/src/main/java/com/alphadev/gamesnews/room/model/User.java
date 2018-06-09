package com.alphadev.gamesnews.room.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
@Entity(tableName = "user")
public class User implements Serializable {

    @PrimaryKey
    @NonNull
   @ColumnInfo(name ="_id")


    private String id;
   @ColumnInfo(name ="user")
   
    private String user;
   @ColumnInfo(name ="avatar")
   
    private String avatar;
//   @ColumnInfo(name ="password")
//
//    private String password;
//   @ColumnInfo(name ="created_date")
//
//    private String createdDate;
//   @ColumnInfo(name ="__v")
//
//    private Integer v;


    public User(String id, String user, String avatar) {
        this.id = id;
        this.user = user;
        this.avatar = avatar;
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

//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(String createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public Integer getV() {
//        return v;
//    }

//    public void setV(Integer v) {
//        this.v = v;
//    }

    @Override
    public String toString() {
        //return new StringBuilder().append("\nfavoriteNews:"+ News).append("\n").append("id:"+ id).append("\n").append("user:"+ user).append("\n").append("avatar:"+ avatar).append("\n").append("password:"+ password).append("\n").append("createdDate:"+ createdDate).append("\n").append("v:"+ v).append("\n").toString();
        return new StringBuilder().append("id:"+ id).append("\n").append("user:"+ user).append("\n").append("avatar:"+ avatar).append("\n").toString();
    }

}