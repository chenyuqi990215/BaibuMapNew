package com.example.a13787.baidumap.entity;

import java.io.Serializable;

/**
 * Created by 13787 on 2019/4/4.
 */

public class SearchEntity implements Serializable
{
    private double Latitude;
    private double Longitude;
    private String key;
    private boolean clickable;

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }
}
