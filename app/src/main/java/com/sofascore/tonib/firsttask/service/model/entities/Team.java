package com.sofascore.tonib.firsttask.service.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "team")
public class Team {

    @NonNull
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("slug")
    @ColumnInfo(name = "slug")
    private String slug;

    @SerializedName("gender")
    @ColumnInfo(name = "gender")
    private String gender;

    /*@SerializedName("sport")
    @ColumnInfo(name = "sport")
    private Sport sport;*/

    @SerializedName("userCount")
    @ColumnInfo(name = "userCount")
    private int userCount;

    @SerializedName("national")
    @ColumnInfo(name = "national")
    private Boolean national;

    public Team(int id, String name, String slug, String gender, int userCount, Boolean national) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.gender = gender;
        //this.sport = sport;
        this.userCount = userCount;
        this.national = national;
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

    /*public Sport getSport() {
        return sport;
    }*/

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
        return name + " ";
        // + sport.getName();
    }
}
