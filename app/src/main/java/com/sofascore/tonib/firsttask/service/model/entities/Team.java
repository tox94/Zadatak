package com.sofascore.tonib.firsttask.service.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "team", primaryKeys = {"teamId", "teamName", "id"})
public class Team {

    @NonNull
    @SerializedName("id")
    @ColumnInfo(name = "teamId")
    private int teamId;

    @NonNull
    @SerializedName("name")
    @ColumnInfo(name = "teamName")
    private String teamName;

    @SerializedName("slug")
    @ColumnInfo(name = "teamSlug")
    private String teamSlug;

    @ColumnInfo(name = "gender")
    private String gender;

    @NonNull
    @Embedded
    private Sport sport;

    @ColumnInfo(name = "userCount")
    private int userCount;

    @ColumnInfo(name = "national")
    private Boolean national;

    public Team(@NonNull int teamId, @NonNull String teamName, String teamSlug, String gender, @NonNull Sport sport, int userCount, Boolean national) {
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

    @NonNull
    public String getTeamName() {
        return teamName;
    }

    public String getTeamSlug() {
        return teamSlug;
    }

    public String getGender() {
        return gender;
    }

    @NonNull
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
        return teamName + " - " + sport.getSlug();
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

        if (t.getTeamId() == this.teamId && t.getTeamName().equals(this.teamName)
                && (t.getSport().getId() == this.getSport().getId())) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode(){
        return teamName.hashCode() + teamId * teamSlug.hashCode() + sport.hashCode();
    }
}
