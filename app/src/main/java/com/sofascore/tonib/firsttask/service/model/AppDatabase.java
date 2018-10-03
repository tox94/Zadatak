package com.sofascore.tonib.firsttask.service.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.sofascore.tonib.firsttask.service.model.daos.SportDao;
import com.sofascore.tonib.firsttask.service.model.daos.TeamDao;
import com.sofascore.tonib.firsttask.service.model.entities.Sport;
import com.sofascore.tonib.firsttask.service.model.entities.Team;

@Database(entities = {Sport.class, Team.class},
            version = 1,
            exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SportDao sportDao();
    public abstract TeamDao teamDao();



}