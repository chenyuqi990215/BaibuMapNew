package com.example.a13787.baidumap.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;

import com.example.a13787.baidumap.entity.UserEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 13787 on 2019/8/27.
 */

public class GetData
{
    public static String attemptLogin(Context context, String username, String password)
    {
        try
        {
            RequestBody requestBody = new FormBody.Builder()
                    .add("username",username)
                    .add("password",password)
                    .build();
            String response  = RequestUtil.postRequestGetSession(context,new String("http://47.103.14.204:8080/login/signIn"),requestBody);
            if (response == null || response.equals("1"))
                return "登录失败";
            else return "登陆成功";
        }
        catch (Exception e)
        {
            return "登陆失败";
        }
    }

    public static String attempRegister(Context context, UserEntity userEntity)
    {
        try
        {
            MediaType type = MediaType.parse("application/json;charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(type,JsonUtil.userToJson(userEntity));
            String response = RequestUtil.postRequest(context,new String("http://47.103.14.204:8080/register"),requestBody);
            if (response == null)
                return "注册失败";
            else return response;
        }
        catch (Exception e)
        {
            return "注册失败";
        }
    }

    public static UserEntity attemptQueryUser(Context context)
    {
        try
        {
            String response = RequestUtil.getWithSession(context,new String("http://47.103.14.204:8080/user/query/info"));
            if (response == null)
            {
                return null;
            }
            else
            {
                return JsonUtil.jsonToUser(response);
            }
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static UserEntity attemptQueryUser(Context context,String email)
    {
        try {
            String param = String.format("%s=%s", "email", email, "utf-8");
            String url = "http://47.103.14.204:8080/user/query?" + param;
            String response = RequestUtil.getWithSession(context, url);
            if (response == null) {
                return null;
            } else {
                return JsonUtil.jsonToUser(response);
            }
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public ArrayList<UserEntity> attemptQueryActivityParticipant(Context context,int activityid)
    {
        try
        {
            String param = String.format("%s=%s","activityid",activityid,"utf-8");
            String url = "http://47.103.14.204:8080/user/query/activity/participant?" + param;
            String response = RequestUtil.getWithSession(context,url);
            if (response == null) {
                return null;
            } else {
                return JsonUtil.jsonToUsers(response);
            }
        }
        catch (Exception e)
        {
            return null;
        }
    }

}
