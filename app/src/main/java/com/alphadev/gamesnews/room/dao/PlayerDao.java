package com.alphadev.gamesnews.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.alphadev.gamesnews.room.model.Player;

@Dao
public interface PlayerDao {

    @Insert
    void insert(Player player);

    @Query("DELETE FROM player")
    void deleteAll();

    @Query("SELECT * from player WHERE _id=:id")
    Player getPlayerByID(String id);
}
