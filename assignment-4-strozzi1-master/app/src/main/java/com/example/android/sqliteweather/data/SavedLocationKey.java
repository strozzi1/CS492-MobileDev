package com.example.android.sqliteweather.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "locations")
public class SavedLocationKey implements Serializable {
    @PrimaryKey
    @NonNull
    public String locationName;

    public SavedLocationKey(String locationName){
        this.locationName = locationName;
    }


}
