package com.sofascore.tonib.firsttask.service.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.sofascore.tonib.firsttask.service.model.daos.SportDao;
import com.sofascore.tonib.firsttask.service.model.daos.TeamDao;
import com.sofascore.tonib.firsttask.service.model.entities.Sport;
import com.sofascore.tonib.firsttask.service.model.entities.Team;

@Database(entities = {Sport.class, Team.class},
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract SportDao sportDao();

    public abstract TeamDao teamDao();

    private static final Object sLock = new Object();

    public static AppDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "teams.db")
                        .allowMainThreadQueries()
                        .build();
            }

            return INSTANCE;
        }
    }

}