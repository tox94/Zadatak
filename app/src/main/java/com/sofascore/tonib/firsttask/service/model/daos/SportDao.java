package com.sofascore.tonib.firsttask.service.model.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.sofascore.tonib.firsttask.service.model.entities.Sport;

import java.util.List;

@Dao
public interface SportDao {

    @Query("SELECT * FROM sport")
    List<Sport> getAllSports();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSport(Sport sport);

    @Delete
    void deleteSport(Sport sport);


}
