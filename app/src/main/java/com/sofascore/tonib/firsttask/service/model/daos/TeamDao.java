package com.sofascore.tonib.firsttask.service.model.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.sofascore.tonib.firsttask.service.model.entities.Sport;
import com.sofascore.tonib.firsttask.service.model.entities.Team;

import java.util.List;

@Dao
public interface TeamDao {

    @Query("SELECT * FROM team")
    List<Team> getAllTeams();

    @Query("SELECT * FROM team WHERE id LIKE :id")
    Team getTeamById(int id);


    @Insert
    void insertTeam(Team team);

    @Delete
    void deleteTeam(Team team);


}
