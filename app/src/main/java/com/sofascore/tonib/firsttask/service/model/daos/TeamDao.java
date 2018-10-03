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
    List<Sport> getAllTeams();

    @Insert
    void insertAllTeams(List<Team> teams);

    @Delete
    void deleteTeam(Team team);


}
