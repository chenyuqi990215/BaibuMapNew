package com.example.a13787.baidumap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13787.baidumap.adapter.JoinAdapter;
import com.example.a13787.baidumap.entity.ActivityEntity;
import com.example.a13787.baidumap.R;
import com.example.a13787.baidumap.entity.UserEntity;
import com.example.a13787.baidumap.util.BaseActivity;
import com.example.a13787.baidumap.util.GetData;

import java.util.ArrayList;
import java.util.List;

public class JoinActivity extends BaseActivity {

    private ListView listView;
    private TextView name;
    private TextView school;
    private TextView department;
    private TextView type;
    private JoinAdapter adapter;
    private TextView content;
    private TextView date;
    private TextView location;
    private TextView sex;
    private TextView title;
    private Button back;
    private Button confirm;
    private ActivityEntity activityEntity;

    private List<UserEntity> joinList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        if (activityEntity == null)
        {
            Toast.makeText(JoinActivity.this,new String("活动不存在"),Toast.LENGTH_SHORT).show();
            activityEntity = new ActivityEntity();
        }
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
        Intent intent = getIntent();
        int activityid = intent.getIntExtra("activityid",0);
        activityEntity = GetData.attemptQueryActivity(JoinActivity.this,activityid);
        if (activityEntity == null)
        {
            activityEntity = new ActivityEntity();
            Toast.makeText(JoinActivity.this,"活动不存在",Toast.LENGTH_SHORT).show();
        }
        name.setText(activityEntity.getUsername());
        school.setText(activityEntity.getSchool());
        department.setText(activityEntity.getDepartment());
        type.setText(activityEntity.getType());
        content.setText(activityEntity.getContent());
        date.setText(activityEntity.getTime());
        location.setText(activityEntity.getLocation());
        sex.setText(activityEntity.getRestrict());
        title.setText(activityEntity.getTitle());
        joinList = GetData.attemptQueryActivityParticipant(JoinActivity.this,activityEntity.getActivityid());
        if (joinList == null)
            joinList = new ArrayList<>();
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
                String response = GetData.attemptJoinActivity(JoinActivity.this,activityEntity.getActivityid());
                Toast.makeText(JoinActivity.this,response,Toast.LENGTH_SHORT).show();
                //add user and activity into data;
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                String email=joinList.get(pos).getEmail();
                Intent intent=new Intent(JoinActivity.this,InfoActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
                finish();
            }
        });
    }
}

