package com.sofascore.tonib.firsttask.service.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.sofascore.tonib.firsttask.service.model.entities.Sport;

@Entity
public class Team {

    @PrimaryKey
    private int uid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "slug")
    private String slug;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "sport")
    private Sport sport;

    @ColumnInfo(name = "userCount")
    private int userCount;

    @ColumnInfo(name = "national")
    private Boolean national;

    @ColumnInfo(name = "id")
    private int id;

    public Team(int uid, String name, String slug, String gender, Sport sport, int userCount, Boolean national, int id) {
        this.uid = uid;
        this.name = name;
        this.slug = slug;
        this.gender = gender;
        this.sport = sport;
        this.userCount = userCount;
        this.national = national;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getGender() {
        return gender;
    }

    public Sport getSport() {
        return sport;
    }

    public int getUserCount() {
        return userCount;
    }

    public Boolean getNational() {
        return national;
    }

    public int getId() {
        return id;
    }

    public String getDetails(){
        return name + " " + sport.getName();
    }
}
