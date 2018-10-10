package com.sofascore.tonib.firsttask.service.model.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.sofascore.tonib.firsttask.service.model.entities.Player;
import com.sofascore.tonib.firsttask.service.model.entities.Team;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface PlayerDao {

    @Query("SELECT * FROM player")
    Single<List<Player>> getAllPlayers();

    @Query("SELECT * FROM player WHERE id LIKE :id")
    Single<Team> getPlayerById(int id);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlayer(Player player);

    @Query("DELETE FROM player WHERE playerId = :playerId")
    void deletePlayer(int playerId);

}
