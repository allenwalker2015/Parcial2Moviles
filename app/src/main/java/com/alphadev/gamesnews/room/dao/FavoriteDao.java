package com.alphadev.gamesnews.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.alphadev.gamesnews.room.model.Favorite;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Query("SELECT idnew from favorite")
    List<String> getAllNewsID();

    @Query("DELETE  FROM favorite ")
    void deleteAll();

    @Insert
    void insert(Favorite favorite);

    @Query("DELETE FROM favorite where idnew=:idnew and iduser=:iduser")
    void delete(String idnew, String iduser);
}
