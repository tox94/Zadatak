package com.sofascore.tonib.firsttask.service.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "sport")
public class Sport {

    @PrimaryKey
    private int id;

    private String name;

    private String slug;

    public Sport(int id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }
}
