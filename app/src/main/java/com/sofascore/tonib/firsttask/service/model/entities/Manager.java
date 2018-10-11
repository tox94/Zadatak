package com.sofascore.tonib.firsttask.service.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "manager")
public class Manager {

    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "managerId")
    private int managerId;

    @SerializedName("name")
    @ColumnInfo(name = "managerName")
    private String managerName;

    @SerializedName("slug")
    @ColumnInfo(name = "managerSlug")
    private String managerSlug;

    @SerializedName("nationalityIOC")
    @ColumnInfo(name = "managerNationalityIOC")
    private String managerNationalityIOC;

    public Manager(int managerId, String managerName, String managerSlug, String managerNationalityIOC) {
        this.managerId = managerId;
        this.managerName = managerName;
        this.managerSlug = managerSlug;
        this.managerNationalityIOC = managerNationalityIOC;
    }

    public int getManagerId() {
        return managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public String getManagerSlug() {
        return managerSlug;
    }

    public String getManagerNationalityIOC() {
        return managerNationalityIOC;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Manager)) {
            return false;
        }
        Manager t = (Manager) o;

        if (t.getManagerId() == this.managerId) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode(){
        return managerId + managerName.hashCode() + managerSlug.hashCode();
    }
}
