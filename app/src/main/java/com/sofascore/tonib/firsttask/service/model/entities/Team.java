package com.sofascore.tonib.firsttask.service.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "team")
public class Team {

    @NonNull
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "teamId")
    private int teamId;

    @SerializedName("name")
    @ColumnInfo(name = "teamName")
    private String teamName;

    @SerializedName("slug")
    @ColumnInfo(name = "teamSlug")
    private String teamSlug;

    @ColumnInfo(name = "gender")
    private String gender;

    @Embedded
    private Sport sport;

    @ColumnInfo(name = "userCount")
    private int userCount;

    @ColumnInfo(name = "national")
    private Boolean national;

    public Team(@NonNull int teamId, String teamName, String teamSlug, String gender, Sport sport, int userCount, Boolean national) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamSlug = teamSlug;
        this.gender = gender;
        this.sport = sport;
        this.userCount = userCount;
        this.national = national;
    }

    @NonNull
    public int getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamSlug() {
        return teamSlug;
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

    public String getDetails() {
        return teamName + ", ";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Team)) {
            return false;
        }
        Team t = (Team) o;

        if (t.getTeamId() == this.teamId) {
            return true;
        }

        return false;
    }

}
