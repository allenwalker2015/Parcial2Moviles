package com.alphadev.gamesnews.room.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorite_news")
public class FavoriteNews {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;
    @ColumnInfo(name = "idusuario")
    String idusuario;
    @ColumnInfo(name = "idnoticia")
    String idnoticia;

    public FavoriteNews(String idusuario, String idnoticia) {
        this.idusuario = idusuario;
        this.idnoticia = idnoticia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public String getIdnoticia() {
        return idnoticia;
    }

    public void setIdnoticia(String idnoticia) {
        this.idnoticia = idnoticia;
    }
}
