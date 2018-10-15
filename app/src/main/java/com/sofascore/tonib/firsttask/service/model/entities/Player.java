package com.sofascore.tonib.firsttask.service.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "player", primaryKeys = {"playerName", "playerId"})
public class Player {

    @NonNull
    @SerializedName("id")
    @ColumnInfo(name = "playerId")
    private int playerId;

    @NonNull
    @SerializedName("name")
    @ColumnInfo(name = "playerName")
    private String playerName;

    @SerializedName("slug")
    @ColumnInfo(name = "playerSlug")
    private String playerSlug;

    @ColumnInfo(name = "shortName")
    private String shortName;

    @NonNull
    @Embedded
    private Team team;

    @SerializedName("gender")
    @ColumnInfo(name = "playerGender")
    private String playerGender;

    @ColumnInfo(name = "position")
    private String position;

    @ColumnInfo(name = "weight")
    private int weight;

    @ColumnInfo(name = "shirtNumber")
    private int shirtNumber;

    @ColumnInfo(name = "height")
    private int height;

    @ColumnInfo(name = "preferredFoot")
    private String preferredFoot;

    @ColumnInfo(name = "nationality")
    private String nationality;

    @ColumnInfo(name = "marketValueCurrency")
    private String marketValueCurrency;

    @ColumnInfo(name = "hasTransferHistory")
    private Boolean hasTransferHistory;

    @SerializedName("userCount")
    @ColumnInfo(name = "playerUserCount")
    private int playerUserCount;

    @ColumnInfo(name = "heightMeters")
    private float heightMeters;

    @ColumnInfo(name = "hasImage")
    private Boolean hasImage;

    @ColumnInfo(name = "age")
    private int age;

    @ColumnInfo(name = "dateOfBirthFormated")
    private String dateOfBirthFormated;

    @ColumnInfo(name = "dateOfBirthTimestamp")
    private int dateOfBirthTimestamp;

    @ColumnInfo(name = "flag")
    private String flag;

    @ColumnInfo(name = "nationalityIOC")
    private String nationalityIOC;

    @ColumnInfo(name = "marketValue")
    private int marketValue;

    @ColumnInfo(name = "contractUntilTimestamp")
    private String contractUntilTimestamp;

    public String getDetails() {
        return playerName;
    }

    public Player(@NonNull int playerId, @NonNull String playerName, String playerSlug, String shortName, @NonNull Team team, String playerGender, String position, int weight, int shirtNumber, int height, String preferredFoot, String nationality, String marketValueCurrency, Boolean hasTransferHistory, int playerUserCount, float heightMeters, Boolean hasImage, int age, String dateOfBirthFormated, int dateOfBirthTimestamp, String flag, String nationalityIOC, int marketValue, String contractUntilTimestamp) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerSlug = playerSlug;
        this.shortName = shortName;
        this.team = team;
        this.playerGender = playerGender;
        this.position = position;
        this.weight = weight;
        this.shirtNumber = shirtNumber;
        this.height = height;
        this.preferredFoot = preferredFoot;
        this.nationality = nationality;
        this.marketValueCurrency = marketValueCurrency;
        this.hasTransferHistory = hasTransferHistory;
        this.playerUserCount = playerUserCount;
        this.heightMeters = heightMeters;
        this.hasImage = hasImage;
        this.age = age;
        this.dateOfBirthFormated = dateOfBirthFormated;
        this.dateOfBirthTimestamp = dateOfBirthTimestamp;
        this.flag = flag;
        this.nationalityIOC = nationalityIOC;
        this.marketValue = marketValue;
        this.contractUntilTimestamp = contractUntilTimestamp;
    }

    @NonNull
    public int getPlayerId() {
        return playerId;
    }

    @NonNull
    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerSlug() {
        return playerSlug;
    }

    public String getShortName() {
        return shortName;
    }

    @NonNull
    public Team getTeam() {
        return team;
    }

    public String getPlayerGender() {
        return playerGender;
    }

    public String getPosition() {
        return position;
    }

    public int getWeight() {
        return weight;
    }

    public int getShirtNumber() {
        return shirtNumber;
    }

    public int getHeight() {
        return height;
    }

    public String getPreferredFoot() {
        return preferredFoot;
    }

    public String getNationality() {
        return nationality;
    }

    public String getMarketValueCurrency() {
        return marketValueCurrency;
    }

    public Boolean getHasTransferHistory() {
        return hasTransferHistory;
    }

    public int getPlayerUserCount() {
        return playerUserCount;
    }

    public float getHeightMeters() {
        return heightMeters;
    }

    public Boolean getHasImage() {
        return hasImage;
    }

    public int getAge() {
        return age;
    }

    public String getDateOfBirthFormated() {
        return dateOfBirthFormated;
    }

    public int getDateOfBirthTimestamp() {
        return dateOfBirthTimestamp;
    }

    public String getFlag() {
        return flag;
    }

    public String getNationalityIOC() {
        return nationalityIOC;
    }

    public int getMarketValue() {
        return marketValue;
    }

    public String getContractUntilTimestamp() {
        return contractUntilTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Player)) {
            return false;
        }
        Player p = (Player) o;

        if (p.getPlayerName().equals(this.playerName) && p.getPlayerId() == this.playerId) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return playerName.hashCode() + team.getTeamId() * playerSlug.hashCode() + team.hashCode();
    }
}
