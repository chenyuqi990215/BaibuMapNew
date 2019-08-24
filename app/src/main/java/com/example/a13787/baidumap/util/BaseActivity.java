package com.example.a13787.baidumap.util;

/**
 * Created by 13787 on 2019/3/11.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import android.view.Window;

public abstract class BaseActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity", getClass().getSimpleName());
        ActivityCollector.addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(initLayout());
        initView();
        initListener();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
    }
    @Override
    protected void onPause()
    {
        super.onPause();
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initListener();
    protected abstract int initLayout();
}
