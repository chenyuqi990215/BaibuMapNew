package com.example.a13787.baidumap.entity;

import java.io.Serializable;


public class MapEntity extends ActivityEntity implements Serializable
{
    private boolean clickable;

    public MapEntity()
    {
        setClickable(true);
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

}
