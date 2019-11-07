package com.example.a13787.baidumap.util;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.a13787.baidumap.entity.ActivityEntity;
import com.example.a13787.baidumap.entity.AnnounceEntity;
import com.example.a13787.baidumap.entity.ImageEntity;
import com.example.a13787.baidumap.entity.UserEntity;



import java.util.ArrayList;

/**
 * Created by 13787 on 2019/8/27.
 */

public class JsonUtil
{
    public static String userToJson(UserEntity userEntity)
    {
        String jsonStr = JSONObject.toJSONString(userEntity);
        return jsonStr;
    }

    public static ArrayList<UserEntity> jsonToUsers(String data)
    {
        ArrayList<UserEntity> userEntities = JSON.parseObject(data, new TypeReference<ArrayList<UserEntity>>() {});
        return userEntities;
    }

    public static UserEntity jsonToUser(String data)
    {
        UserEntity userEntity = JSON.parseObject(data, new TypeReference<UserEntity>() {});
        return userEntity;
    }

    public static String activityToJson(ActivityEntity activityEntity)
    {
        String jsonStr = JSONObject.toJSONString(activityEntity);
        return jsonStr;
    }

    public static ArrayList<ActivityEntity> jsonToActivities(String data)
    {
        ArrayList<ActivityEntity> activityEntities = JSON.parseObject(data, new TypeReference<ArrayList<ActivityEntity>>() {});
        return activityEntities;
    }

    public static ActivityEntity jsonToActivity(String data)
    {
        ActivityEntity activityEntity = JSON.parseObject(data, new TypeReference<ActivityEntity>() {});
        return activityEntity;
    }

    public static String announceToJson(AnnounceEntity announceEntity)
    {
        String jsonStr = JSONObject.toJSONString(announceEntity);
        return jsonStr;
    }

    public static ArrayList<AnnounceEntity> jsonToAnnounces(String data)
    {
        ArrayList<AnnounceEntity> announceEntities = JSON.parseObject(data, new TypeReference<ArrayList<AnnounceEntity>>() {});
        return announceEntities;
    }

    public static AnnounceEntity jsonToAnnounce(String data)
    {
        AnnounceEntity announceEntity = JSON.parseObject(data, new TypeReference<AnnounceEntity>() {});
        return announceEntity;
    }

    public static String imageToJson(ImageEntity imageEntity)
    {
        String jsonStr = JSONObject.toJSONString(imageEntity);
        return jsonStr;
    }

}
