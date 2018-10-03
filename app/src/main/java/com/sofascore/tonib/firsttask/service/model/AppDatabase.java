package com.sofascore.tonib.firsttask.service.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.sofascore.tonib.firsttask.service.model.daos.SportDao;
import com.sofascore.tonib.firsttask.service.model.daos.TeamDao;
import com.sofascore.tonib.firsttask.service.model.entities.Sport;

@Database(entities = Sport.class,
            version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SportDao sportDao();
    public abstract TeamDao teamDao();



}