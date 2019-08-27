package com.example.a13787.baidumap.util;

import com.example.a13787.baidumap.entity.UserEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 13787 on 2019/8/27.
 */

public class JsonUtil
{
    public static String userToJson(UserEntity userEntity)
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("age",userEntity.getAge());
            jsonObject.put("birth",userEntity.getBirth());
            jsonObject.put("department",userEntity.getDepartment());
            jsonObject.put("email",userEntity.getEmail());
            jsonObject.put("name",userEntity.getName());
            jsonObject.put("nickname",userEntity.getNickname());
            jsonObject.put("password",userEntity.getPassword());
            jsonObject.put("phone",userEntity.getPhone());
            jsonObject.put("portraiturl",userEntity.getPortraiturl());
            jsonObject.put("school",userEntity.getSchool());
            jsonObject.put("schoolid",userEntity.getSchoolid());
            jsonObject.put("sex",userEntity.getSex());
            jsonObject.put("userid",userEntity.getUserid());
        }
        catch (Exception e)
        {
            return null;
        }
        finally
        {
            return jsonObject.toString();
        }
    }

    public static UserEntity jsonToUser(JSONObject jsonObject)
    {
        UserEntity userEntity = new UserEntity();
        try
        {
            userEntity.setAge(jsonObject.getInt("age"));
            userEntity.setBirth(jsonObject.getString("birth"));
            userEntity.setDepartment(jsonObject.getString("department"));
            userEntity.setEmail(jsonObject.getString("email"));
            userEntity.setName(jsonObject.getString("name"));
            userEntity.setNickname(jsonObject.getString("nickname"));
            userEntity.setPassword(jsonObject.getString("password"));
            userEntity.setPhone(jsonObject.getString("phone"));
            userEntity.setPortraiturl(jsonObject.getString("portraiturl"));
            userEntity.setSchool(jsonObject.getString("school"));
            userEntity.setSchoolid(jsonObject.getString("schoolid"));
            userEntity.setSex(jsonObject.getString("sex"));
            userEntity.setUserid(jsonObject.getInt("userid"));
        }
        catch (Exception e)
        {
            return null;
        }
        finally
        {
            return userEntity;
        }
    }

    public static ArrayList<UserEntity> jsonToUsers(String data)
    {
        ArrayList<UserEntity> ret = new ArrayList<>();
        try
        {
            JSONArray jsonArray = new JSONArray(data);
            for( int i=0; i<jsonArray.length(); i++ )
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                UserEntity userEntity = jsonToUser(jsonObject);
                ret.add(userEntity);
            }
        }
        catch (Exception e)
        {
            return null;
        }
        finally
        {
            return ret;
        }
    }

    public static UserEntity jsonToUser(String data)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(data);
            return jsonToUser(jsonObject);
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
