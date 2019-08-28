package com.example.a13787.baidumap.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a13787.baidumap.R;
import com.example.a13787.baidumap.adapter.ActivityAdapter;
import com.example.a13787.baidumap.entity.ActivityEntity;
import com.example.a13787.baidumap.util.BaseActivity;
import com.example.a13787.baidumap.util.GetData;

import java.util.ArrayList;
import java.util.List;

public class ParticipateActivity extends BaseActivity
{
    private boolean button_me = true;
    private boolean button_join = false;
    private ListView listView = null;
    private ActivityAdapter adapter = null;
    private boolean first = true;
    private List<ActivityEntity> activityList = new ArrayList<>();
    private Button back;
    private LinearLayout layout_my;
    private LinearLayout layout_join;

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
                Intent intent=new Intent(ParticipateActivity.this, MapActivity.class);
                startActivity(intent);
                finish();
            }
        });
        layout_my.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                button_me = true;
                button_join = false;
                initView();
                initData();
            }
        });
        layout_join.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                button_me = false;
                button_join = true;
                initView();
                initData();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l)
            {
                final ActivityEntity activityEntity = activityList.get(pos);
                AlertDialog.Builder builder=new AlertDialog.Builder(ParticipateActivity.this)
                        .setTitle("提示：")
                        .setMessage("是否想要进入导航模式")
                        .setCancelable(true)
                        .setPositiveButton("确定",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                double latitude = activityEntity.getLatitude();
                                double longitude = activityEntity.getLongitude();
                                Intent intent = new Intent(ParticipateActivity.this,RouteActivity.class);
                                intent.putExtra("Latitude",latitude);
                                intent.putExtra("Longitude",longitude);
                                startActivity(intent);
                                finish();
                            }
                        })
                         .setNegativeButton("取消",null);
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
    }
    @Override
    protected int initLayout()
    {
        return R.layout.activity_participate;
    }
    @Override
    protected void initView()
    {
        back = (Button) findViewById(R.id.participate_back);
        layout_my = (LinearLayout) findViewById(R.id.participate_my);
        layout_join = (LinearLayout) findViewById(R.id.participate_join);
        if (first == true)
        {
            adapter = new ActivityAdapter(ParticipateActivity.this,R.layout.layout_activity,activityList); //创建ArrayAdapter方法
            listView = (ListView) findViewById(R.id.participate_listview);
            listView.setAdapter(adapter);//将创建的方法作为适配器传递给listview
            first = false;
        }
        if (button_me == true)
        {
            ImageView imageView = (ImageView) findViewById(R.id.participate_icon_my);
            imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_love));
            imageView = (ImageView) findViewById(R.id.participate_icon_join);
            imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_star_unclick));
            TextView textView = (TextView) findViewById(R.id.participate_text_my);
            textView.setTextColor(Color.parseColor("#8078e0"));
            textView = (TextView) findViewById(R.id.participate_text_join);
            textView.setTextColor(Color.parseColor("#c8c8c8"));
        }
        else if (button_join == true)
        {
            ImageView imageView = (ImageView) findViewById(R.id.participate_icon_join);
            imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_star));
            imageView = (ImageView) findViewById(R.id.participate_icon_my);
            imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_love_unclick));
            TextView textView = (TextView) findViewById(R.id.participate_text_join);
            textView.setTextColor(Color.parseColor("#8078e0"));
            textView = (TextView) findViewById(R.id.participate_text_my);
            textView.setTextColor(Color.parseColor("#c8c8c8"));
        }
    }
    @Override
    protected void initData()
    {
        // 初始化类中所有数据
        if (button_me == true)
        {
            activityList = GetData.attemptQueryMyRelease(ParticipateActivity.this);
            if (activityList == null)
                activityList = new ArrayList<>();
            adapter.notifyDataSetChanged();
        }
        else if (button_join)
        {
            activityList = GetData.attemptQueryMyParticipate(ParticipateActivity.this);
            if (activityList == null)
                activityList = new ArrayList<>();
            adapter.notifyDataSetChanged();
        }
    }
}