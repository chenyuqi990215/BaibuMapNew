package com.example.a13787.baidumap.entity;

import com.example.a13787.baidumap.util.Copyer;

import java.io.Serializable;


public class MapEntity extends ActivityEntity implements Serializable
{
    private boolean clickable;

    public MapEntity()
    {
        super();
        setClickable(true);
    }

    public MapEntity(ActivityEntity activityEntity)
    {
        super();
        try
        {
            Copyer.Copy(activityEntity,this);
        }
        catch (IllegalAccessException e)
        {
            return;
        }
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

}
