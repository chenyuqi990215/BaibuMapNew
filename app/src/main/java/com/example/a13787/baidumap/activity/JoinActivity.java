package com.example.a13787.baidumap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a13787.baidumap.adapter.JoinAdapter;
import com.example.a13787.baidumap.entity.JoinEntity;
import com.example.a13787.baidumap.R;
import com.example.a13787.baidumap.util.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class JoinActivity extends BaseActivity {

    private ListView listView;
    private TextView name;
    private TextView school;
    private TextView department;
    private TextView type;
    private JoinAdapter adapter;
    TextView content;
    TextView date;
    TextView location;
    TextView sex;
    TextView title;
    Button back;
    Button confirm;

    private List<JoinEntity> joinList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }
    @Override
    protected int initLayout()
    {
        return R.layout.activity_join;
    }
    @Override
    protected void initView()
    {
        back = (Button) findViewById(R.id.join_back);
        confirm = (Button) findViewById(R.id.join_confirm);
        adapter = new JoinAdapter(JoinActivity.this,R.layout.layout_person,joinList);
        listView = (ListView) findViewById(R.id.join_listview);
        listView.setAdapter(adapter);
        name = (TextView) findViewById(R.id.join_name);
        school = (TextView) findViewById(R.id.join_school);
        department = (TextView) findViewById(R.id.join_department);
        type = (TextView) findViewById(R.id.join_type);
        content = (TextView) findViewById(R.id.join_content);
        date = (TextView) findViewById(R.id.join_date);
        location = (TextView) findViewById(R.id.join_location);
        sex = (TextView) findViewById(R.id.join_sex);
        title = (TextView) findViewById(R.id.join_title);
    }
    @Override
    protected void initData() { //初始化类中所有数据
        name.setText("cyq");
        school.setText("华东师范大学");
        department.setText("软件工程");
        type.setText("Sport");
        content.setText("I want to run in the afternoon");
        date.setText("2019.3.15 18:00 -- 19:00");
        location.setText("playgound");
        sex.setText("all");
        title.setText("run");
        JoinEntity item1 = new JoinEntity();
        item1.setName("cyq");
        item1.setSchool("华东师范大学");
        item1.setDepartment("软件工程");
        joinList.add(item1);
        JoinEntity item2 = new JoinEntity();
        item2.setName("cyq");
        item2.setSchool("华东师范大学");
        item2.setDepartment("软件工程");
        joinList.add(item2);
        JoinEntity item3 = new JoinEntity();
        item3.setName("cyq");
        item3.setSchool("华东师范大学");
        item3.setDepartment("软件工程");
        joinList.add(item3);
    }
    @Override
    protected void initListener()
    {
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(JoinActivity.this,MapActivity.class);
                startActivity(intent);
                finish();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(JoinActivity.this,MapActivity.class);
                startActivity(intent);
                //add user and activity into data;
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                String username=joinList.get(pos).getName();
                Intent intent=new Intent(JoinActivity.this,InfoActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }
        });
    }
}

