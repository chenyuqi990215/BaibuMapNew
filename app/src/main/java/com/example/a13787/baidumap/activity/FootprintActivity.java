package com.example.a13787.baidumap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.a13787.baidumap.R;
import com.example.a13787.baidumap.adapter.ActivityAdapter;
import com.example.a13787.baidumap.entity.ActivityEntity;
import com.example.a13787.baidumap.util.BaseActivity;
import com.example.a13787.baidumap.util.GetData;

import java.util.ArrayList;
import java.util.List;

public class FootprintActivity extends BaseActivity
{
    private List<ActivityEntity> activityList = new ArrayList<>();
    private ActivityAdapter adapter;
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
        adapter = new ActivityAdapter(FootprintActivity.this,R.layout.layout_activity,activityList);
        ListView listView = (ListView) findViewById(R.id.footprint_listview);
        listView.setAdapter(adapter);
    }
    @Override
    protected void initData()
    {
        activityList.clear();
        ArrayList<ActivityEntity> tmp = GetData.attemptQueryMyFootprint(FootprintActivity.this);
        if (tmp != null)
        {
            for (int i = 0;i < tmp.size(); i++)
            {
                activityList.add(tmp.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }
}
