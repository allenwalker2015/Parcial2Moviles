package com.alphadev.gamesnews.room.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "favorite")
public class Favorite implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "_id")
    private int id;

    @ColumnInfo(name = "iduser")
    private String iduser;
    @ColumnInfo(name = "idnew")

    private String idnew;

    public Favorite(String idnew, String iduser) {
        this.idnew = idnew;
        this.iduser = iduser;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getIdnew() {
        return idnew;
    }

    public void setIdnew(String idnew) {
        this.idnew = idnew;
    }
}