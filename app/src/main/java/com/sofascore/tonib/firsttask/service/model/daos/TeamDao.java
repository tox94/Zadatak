package com.sofascore.tonib.firsttask.service.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.sofascore.tonib.firsttask.service.model.entities.Team;

import java.util.HashMap;
import java.util.List;

@Dao
public interface TeamDao {

    @Query("SELECT * FROM team")
    LiveData<List<Team>> getAllTeams();

    @Query("SELECT * FROM team WHERE id LIKE :id")
    Team getTeamById(int id);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTeam(Team team);

    @Query("DELETE FROM team WHERE teamId = :teamId")
    void deleteTeam(int teamId);


}
