package com.example.a13787.baidumap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.a13787.baidumap.R;
import com.example.a13787.baidumap.adapter.ActivityAdapter;
import com.example.a13787.baidumap.entity.ActivityDataBase;
import com.example.a13787.baidumap.util.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class FootprintActivity extends BaseActivity
{
    private List<ActivityDataBase> activityList = new ArrayList<>();
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initData();
    }
    @Override
    protected void initListener()
    {
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(FootprintActivity.this, MapActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    protected int initLayout()
    {
        return R.layout.activity_footprint;
    }
    @Override
    protected void initView()
    {
        back = (Button) findViewById(R.id.footprint_back);
        ActivityAdapter adapter = new ActivityAdapter(FootprintActivity.this,R.layout.layout_activity,activityList);
        ListView listView = (ListView) findViewById(R.id.footprint_listview);
        listView.setAdapter(adapter);
    }
    @Override
    protected void initData()
    {
        // 初始化类中所有数据
        ActivityDataBase item1 = new ActivityDataBase();
        item1.setName("cyq");
        item1.setSchool("华东师范大学");
        item1.setDepartment("软件工程");
        item1.setType("Sport");
        item1.setTitle("Run");
        item1.setContent("I want to run in the afternoon");
        item1.setDate("2019.3.15 18:00 -- 19:00");
        item1.setLocation("playgound");
        item1.setSex("all");
        activityList.add(item1);
        ActivityDataBase item2 = new ActivityDataBase();
        item2.setName("cyq");
        item2.setSchool("华东师范大学");
        item2.setDepartment("软件工程");
        item2.setType("Food");
        item2.setTitle("Breakfast");
        item2.setContent("I want to find a partner to eat breakfast together");
        item2.setDate("2019.3.15 6:00 -- 8:00");
        item2.setLocation("Hexi restaurant");
        item2.setSex("all");
        activityList.add(item2);
        ActivityDataBase item3 = new ActivityDataBase();
        item3.setName("cyq");
        item3.setSchool("华东师范大学");
        item3.setDepartment("软件工程");
        item3.setType("Study");
        item3.setTitle("Study together");
        item3.setContent("I want to find a partner to study together");
        item3.setDate("2019.3.15 6:30 -- 9:30");
        item3.setLocation("ECNU library");
        item3.setSex("all");
        activityList.add(item3);
    }
}
