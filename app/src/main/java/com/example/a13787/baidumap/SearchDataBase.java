package com.example.a13787.baidumap;

import java.io.Serializable;

/**
 * Created by 13787 on 2019/4/4.
 */

public class SearchDataBase implements Serializable
{
    private double Latitude;
    private double Longitude;
    private String key;

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
}
