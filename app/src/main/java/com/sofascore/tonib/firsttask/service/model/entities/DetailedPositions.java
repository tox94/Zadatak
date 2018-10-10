package com.sofascore.tonib.firsttask.service.model.entities;

import android.arch.persistence.room.Entity;


@Entity(tableName = "detailedPositions")
public class DetailedPositions {

    private String main;

    private String[] side;

    public DetailedPositions(String main, String[] side) {
        this.main = main;
        this.side = side;
    }

    public String getMain() {
        return main;
    }

    public String[] getSide() {
        return side;
    }
}
