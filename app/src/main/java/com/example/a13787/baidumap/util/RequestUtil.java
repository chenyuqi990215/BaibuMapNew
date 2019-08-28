package com.example.a13787.baidumap.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.util.Log;

import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 13787 on 2019/8/27.
 */

public class RequestUtil
{
    public static String postRequestGetSession(Context context, String url, RequestBody requestBody)
    {
        try
        {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().
                    detectDiskReads().
                    detectDiskWrites().
                    detectNetwork().
                    penaltyLog().
                    build());
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().toString();
            Headers headers =response.headers();
            List cookies = headers.values("Set-Cookie");
            String session = (String)cookies.get(0);
            String sessionid = session.substring(0,session.indexOf(";"));
            SharedPreferences share = context.getSharedPreferences("Session",MODE_PRIVATE);
            SharedPreferences.Editor edit = share.edit();
            edit.putString("sessionid",sessionid);
            edit.commit();
            return responseData;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static String postRequestWithSession(Context context, String url, RequestBody requestBody)
    {
        try
        {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().
                    detectDiskReads().
                    detectDiskWrites().
                    detectNetwork().
                    penaltyLog().
                    build());
            OkHttpClient client = new OkHttpClient();
            SharedPreferences share = context.getSharedPreferences("Session",MODE_PRIVATE);
            String sessionid= share.getString("sessionid","null");
            Request request = new Request.Builder()
                    .addHeader("cookie",sessionid)
                    .url(url)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            Log.d("response",responseData);
            return responseData;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static String postRequestWithoutSession(String url, RequestBody requestBody)
    {
        try
        {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().
                    detectDiskReads().
                    detectDiskWrites().
                    detectNetwork().
                    penaltyLog().
                    build());
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            Log.d("response",responseData);
            return responseData;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static String getWithSession(Context context,String url)
    {
        try
        {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().
                    detectDiskReads().
                    detectDiskWrites().
                    detectNetwork().
                    penaltyLog().
                    build());
            OkHttpClient client = new OkHttpClient();
            SharedPreferences share = context.getSharedPreferences("Session",MODE_PRIVATE);
            String sessionid= share.getString("sessionid","null");
            Request request = new Request.Builder()
                    .addHeader("cookie",sessionid)
                    .url(url)
                    .build();
            Call call = client.newCall(request);
            Response response = call.execute();
            if (response.isSuccessful())
            {
                return response.body().string();
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static String getWithoutSession(String url)
    {
        try
        {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().
                    detectDiskReads().
                    detectDiskWrites().
                    detectNetwork().
                    penaltyLog().
                    build());
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Call call = client.newCall(request);
            Response response = call.execute();
            if (response.isSuccessful())
            {
                return response.body().string();
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
