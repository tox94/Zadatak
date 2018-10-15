package com.sofascore.tonib.firsttask.service.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

    @SerializedName("userCount")
    @ColumnInfo(name = "teamUserCount")
    private int teamUserCount;

    @ColumnInfo(name = "national")
    private Boolean national;

    @Nullable
    @Embedded
    private Manager manager;

    public Team(@NonNull int teamId, @NonNull String teamName, String teamSlug, String gender, @NonNull Sport sport, int teamUserCount, Boolean national, @Nullable Manager manager) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamSlug = teamSlug;
        this.gender = gender;
        this.sport = sport;
        this.teamUserCount = teamUserCount;
        this.national = national;
        this.manager = manager;
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

    public int getTeamUserCount() {
        return teamUserCount;
    }

    public Boolean getNational() {
        return national;
    }

    public String getDetails() {
        return teamName + " - " + getSport().getName();
    }

    @Nullable
    public Manager getManager() {
        return manager;
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
    public int hashCode() {
        return teamName.hashCode() + teamId * teamSlug.hashCode() + sport.hashCode();
    }
}
