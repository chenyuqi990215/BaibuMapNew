package com.example.a13787.baidumap.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.a13787.baidumap.R;
import com.example.a13787.baidumap.activity.MapActivity;
import com.example.a13787.baidumap.activity.ParticipateActivity;

import java.util.Timer;
import java.util.TimerTask;

public class TimerService extends Service {
    Timer timer;  //定时器
    TimerTask task;//定时执行的任务
    @RequiresApi(api = 26)   //可以注释掉
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onCreate();
        timer = new Timer();
        task = new TimerTask()
        {
            @Override
            public void run()
            {
                try
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        String id = "channel_1";
                        String name = "test";
                        NotificationManager mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                        int importance = NotificationManager.IMPORTANCE_HIGH;
                        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
                        mNotificationManager.createNotificationChannel(mChannel);
                        Intent notificationIntent = new Intent(TimerService.this,ParticipateActivity.class);//点击跳转位置
                        PendingIntent contentIntent = PendingIntent.getActivity(TimerService.this,0,notificationIntent,0);
                        Notification notification = new Notification.Builder(TimerService.this,id)
                                .setContentIntent(contentIntent)
                                .setSmallIcon(R.mipmap.my_ic_launcher)
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.my_ic_launcher))
                                .setContentTitle("提醒")
                                .setContentText("你有一个活动即将开始，请点击查看")
                                .setAutoCancel(true)
                                .build();
                        mNotificationManager.notify(1, notification);
                    }
                    else
                    {
                        NotificationManager mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                        Intent notificationIntent = new Intent(TimerService.this,ParticipateActivity.class);//点击跳转位置
                        PendingIntent contentIntent = PendingIntent.getActivity(TimerService.this,0,notificationIntent,0);
                        Notification.Builder builder = new Notification.Builder(TimerService.this)
                                .setContentIntent(contentIntent)
                                .setSmallIcon(R.mipmap.my_ic_launcher)
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.my_ic_launcher))
                                .setContentText("你有一个活动即将开始，请点击查看") //下拉通知啦内容
                                .setContentTitle("提醒")//下拉通知栏标题
                                .setAutoCancel(true);
                        Notification notification = builder.build();
                        mNotificationManager.notify((int)System.currentTimeMillis(),notification);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        // 参数说明：1、任务2、延迟指定的时间后开始执行3、以指定的频率重复执行任务
        timer.scheduleAtFixedRate(task, 1000, 1000 * 20);  //30分钟一次
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
    }
    @Override
    public void onDestroy()
    {
        Log.d("TimerService: ","停止了定时任务");
        timer.cancel();
        task.cancel();
        super.onDestroy();
    }
}
