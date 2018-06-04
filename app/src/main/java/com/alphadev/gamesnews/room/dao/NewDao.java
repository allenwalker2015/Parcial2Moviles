package com.alphadev.gamesnews.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.alphadev.gamesnews.room.model.New;
import com.alphadev.gamesnews.room.model.User;

@Dao
public interface NewDao {

    @Insert
    void insert(New n_new);

    @Query("DELETE FROM new")
    void deleteAll();

    @Query("SELECT * from new WHERE _id=:id")
    New getNewByID(String id);

    @Query("SELECT * from new")
    New getAllNews();



}
