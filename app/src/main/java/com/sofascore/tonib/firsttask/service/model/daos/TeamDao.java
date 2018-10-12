package com.sofascore.tonib.firsttask.service.model.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.sofascore.tonib.firsttask.service.model.entities.Team;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface TeamDao {

    @Query("SELECT * FROM team")
    Flowable<List<Team>> getAllTeams();

    @Query("SELECT * FROM team WHERE id LIKE :id")
    Single<Team> getTeamById(int id);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTeam(Team team);

    @Query("DELETE FROM team WHERE teamId = :teamId")
    void deleteTeam(int teamId);


}
