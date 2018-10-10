package com.sofascore.tonib.firsttask.service.model.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.sofascore.tonib.firsttask.service.model.entities.DetailedPositions;
import com.sofascore.tonib.firsttask.service.model.entities.Sport;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface DetailedPositionsDao {

    @Query("SELECT * FROM detailedPositions")
    Single<List<DetailedPositions>> getAllDetailedPositions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDetailedPositions(DetailedPositions detailedPositions);

    @Delete
    void deleteDetailedPositions(DetailedPositions detailedPositions);


}
