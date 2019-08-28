package com.example.a13787.baidumap.util;

import android.content.Context;

import com.example.a13787.baidumap.entity.ActivityEntity;
import com.example.a13787.baidumap.entity.AnnounceEntity;
import com.example.a13787.baidumap.entity.UserEntity;

import java.util.ArrayList;


import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

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
            String response  = RequestUtil.postRequestGetSession(context,new String("http://47.103.14.204:8080/signIn"),requestBody);
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
            if (response == null)
                return null;
            else
                return JsonUtil.jsonToUser(response);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static ArrayList<UserEntity> attemptQueryActivityParticipant(Context context,int activityid)
    {
        try
        {
            String param = String.format("%s=%s","activityid",activityid,"utf-8");
            String url = "http://47.103.14.204:8080/user/query/activity/participant?" + param;
            String response = RequestUtil.getWithSession(context,url);
            if (response == null)
                return null;
            else
                return JsonUtil.jsonToUsers(response);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static String attemptUpdateBirth(Context context,String birth)
    {
        try
        {
            RequestBody requestBody = new FormBody.Builder()
                    .add("birth",birth)
                    .build();
            String response = RequestUtil.postRequest(context,new String("http://47.103.14.204:8080/user/update/birth"),requestBody);
            if (response == null)
                return "更新失败";
            else return response;
        }
        catch (Exception e)
        {
            return "更新失败";
        }
    }

    public static String attemptUpdateAge(Context context,int age)
    {
        try
        {
            RequestBody requestBody = new FormBody.Builder()
                    .add("age",age+"")
                    .build();
            String response = RequestUtil.postRequest(context,new String("http://47.103.14.204:8080/user/update/age"),requestBody);
            if (response == null)
                return "更新失败";
            else return response;
        }
        catch (Exception e)
        {
            return "更新失败";
        }
    }

    public static String attemptUpdateNickname(Context context,String nickname)
    {
        try
        {
            RequestBody requestBody = new FormBody.Builder()
                    .add("nickname",nickname)
                    .build();
            String response = RequestUtil.postRequest(context,new String("http://47.103.14.204:8080/user/update/nickname"),requestBody);
            if (response == null)
                return "更新失败";
            else return response;
        }
        catch (Exception e)
        {
            return "更新失败";
        }
    }

    public static ArrayList<ActivityEntity> attempQueryActivies(Context context)
    {
        try
        {
            String url = "http://47.103.14.204:8080/user/query/activities";
            String response = RequestUtil.getWithSession(context,url);
            if (response == null)
                return null;
            else
                return JsonUtil.jsonToActivities(response);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static String attemptReleaseActivity(Context context,ActivityEntity activityEntity)
    {
        try
        {
            MediaType type = MediaType.parse("application/json;charset=utf-8");
            RequestBody requestBody = RequestBody.create(type,JsonUtil.activityToJson(activityEntity));
            String response = RequestUtil.postRequest(context,new String("http://47.103.14.204:8080/register"),requestBody);
            if (response == null)
                return "发布失败";
            else return response;
        }
        catch (Exception e)
        {
            return "发布失败";
        }
    }

    public static ArrayList<ActivityEntity> attemptQueryMyParticipate(Context context)
    {
        try
        {
            String url = "http://47.103.14.204:8080/user/query/my/participate";
            String response = RequestUtil.getWithSession(context,url);
            if (response == null)
                return null;
            else
                return JsonUtil.jsonToActivities(response);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static ArrayList<ActivityEntity> attemptQueryMyRelease(Context context)
    {
        try
        {
            String url = "http://47.103.14.204:8080/user/query/my/release";
            String response = RequestUtil.getWithSession(context,url);
            if (response == null)
                return null;
            else
                return JsonUtil.jsonToActivities(response);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static ArrayList<ActivityEntity> attemptQueryMyFootprint(Context context)
    {
        try
        {
            String url = "http://47.103.14.204:8080/user/query/my/footprint";
            String response = RequestUtil.getWithSession(context,url);
            if (response == null)
                return null;
            else
                return JsonUtil.jsonToActivities(response);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static ActivityEntity attemptQueryActivity(Context context,int activityid) {
        try {
            String param = String.format("%s=%s", "activityid", activityid, "utf-8");
            String url = "http://47.103.14.204:8080/user/query/my/activity?" + param;
            String response = RequestUtil.getWithSession(context, url);
            if (response == null)
                return null;
            else
                return JsonUtil.jsonToActivity(response);
        } catch (Exception e) {
            return null;
        }
    }

    public static String attemptJoinActivity(Context context,int activityid)
    {
        try
        {
            RequestBody requestBody = new FormBody.Builder()
                    .add("activityid",activityid+"")
                    .build();
            String response = RequestUtil.postRequest(context,new String("http://47.103.14.204:8080/user/join"),requestBody);
            if (response == null)
                return "加入失败";
            else return response;
        }
        catch (Exception e)
        {
            return "加入失败";
        }
    }

    public static String attemptDismissActivity(Context context,int activityid)
    {
        try
        {
            RequestBody requestBody = new FormBody.Builder()
                    .add("activityid",activityid+"")
                    .build();
            String response = RequestUtil.postRequest(context,new String("http://47.103.14.204:8080/user/dismiss"),requestBody);
            if (response == null)
                return "删除失败";
            else return response;
        }
        catch (Exception e)
        {
            return "删除失败";
        }
    }

    public static String attemotQuitAcivity(Context context,int activityid)
    {
        try
        {
            RequestBody requestBody = new FormBody.Builder()
                    .add("activityid",activityid+"")
                    .build();
            String response = RequestUtil.postRequest(context,new String("http://47.103.14.204:8080/user/quit"),requestBody);
            if (response == null)
                return "删除失败";
            else return response;
        }
        catch (Exception e)
        {
            return "删除失败";
        }
    }

    public static ArrayList<AnnounceEntity> attemptQueryAnnounce(Context context)
    {
        try {
            String url = "http://47.103.14.204:8080/user/announce";
            String response = RequestUtil.getWithSession(context, url);
            if (response == null)
                return null;
            else
                return JsonUtil.jsonToAnnounces(response);
        } catch (Exception e) {
            return null;
        }
    }

}
