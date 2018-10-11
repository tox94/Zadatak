package com.sofascore.tonib.firsttask.service.model.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.sofascore.tonib.firsttask.service.model.entities.Manager;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ManagerDao {

    @Query("SELECT * FROM manager")
    Single<List<Manager>> getAllManagers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertManager(Manager manager);

    @Delete
    void deleteManager(Manager manager);


}
