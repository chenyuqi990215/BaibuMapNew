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
import com.example.a13787.baidumap.entity.ActivityDataBase;
import com.example.a13787.baidumap.util.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ParticipateActivity extends BaseActivity
{
    private boolean button_me = true;
    private boolean button_join = false;
    private ListView listView = null;
    private ActivityAdapter adapter = null;
    private boolean first = true;
    private List<ActivityDataBase> activityList = new ArrayList<>();
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
                final ActivityDataBase activityDataBase = activityList.get(pos);
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
                                double latitude = activityDataBase.getLatitude();
                                double longitude = activityDataBase.getLongitude();
                                Intent intent = new Intent(ParticipateActivity.this,RouteActivity.class);
                                intent.putExtra("Latitude",latitude);
                                intent.putExtra("Longitude",longitude);
                                startActivity(intent);
                                finish();
                            }
                        });
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
            activityList.clear();
            ActivityDataBase item1 = new ActivityDataBase();
            item1.setName("cyq");
            item1.setSchool("华东师范大学");
            item1.setDepartment("软件工程");
            item1.setType("Sport");
            item1.setTitle("Run");
            item1.setContent("I want to run in the afternoon");
            item1.setDate("2019.3.15 18:00 -- 19:00");
            item1.setLocation("playgound");
            item1.setLongitude(121.411511);
            item1.setLatitude(31.234907);
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
            item2.setLongitude(121.409952);
            item2.setLatitude(31.236264);
            item2.setSex("all");
            activityList.add(item2);
            adapter.notifyDataSetChanged();
        }
        else if (button_join)
        {
            activityList.clear();
            ActivityDataBase item3 = new ActivityDataBase();
            item3.setName("cyq");
            item3.setSchool("华东师范大学");
            item3.setDepartment("软件工程");
            item3.setType("Study");
            item3.setTitle("Study together");
            item3.setContent("I want to find a partner to study together");
            item3.setDate("2019.3.15 6:30 -- 9:30");
            item3.setLocation("ECNU library");
            item3.setLongitude(121.413326);
            item3.setLatitude(31.234196);
            item3.setSex("all");
            activityList.add(item3);
            adapter.notifyDataSetChanged();
        }
    }
}