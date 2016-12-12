package com.felixmasila.v2b.activity;

/**
 * Created by felixmasila on 11/20/16.
 */

public class LocationConstructor {
    int ID, level;
    String Name;
    Double longitude, latitude;

    public int getId()
    {
        return ID;
    }
    public void setId(int ID)
    {
        this.ID = ID;
    }
    public int getLevel()
    {
        return level;
    }
    public void setLevel(int level)
    {
        this.level = level;
    }

    public Double getLongitude()
    {
        return longitude;
    }
    public void setLongitude(Double longitude)
    {
        this.longitude = longitude;
    }
    public Double getLatitude()
    {
        return latitude;
    }
    public void setLatitude(Double latitude)
    {
        this.latitude = latitude;
    }
    public String getName()
    {
        return Name;
    }
    public void setName(String Name)
    {
        this.Name = Name;
    }

}
