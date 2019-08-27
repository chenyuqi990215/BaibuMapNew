package com.example.a13787.baidumap.util;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.TypeReference;
import com.example.a13787.baidumap.entity.UserEntity;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 13787 on 2019/8/27.
 */

public class JsonUtil
{
    public static String userToJson(UserEntity userEntity)
    {
        String jsonStr = com.alibaba.fastjson.JSONObject.toJSONString(userEntity);
        return jsonStr;
    }

    public static UserEntity jsonToUser(JSONObject jsonObject)
    {
        UserEntity userEntity = JSON.parseObject(JSON.toJSONString(jsonObject),UserEntity.class);
        return userEntity;
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
}
