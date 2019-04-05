package com.example.a13787.baidumap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class JoinActivity extends BaseActivity {

    private List<JoinDataBase> joinList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected int initLayout()
    {
        return R.layout.activity_join;
    }
    @Override
    protected void initView()
    {
        JoinAdapter adapter = new JoinAdapter(JoinActivity.this,R.layout.layout_person,joinList); //创建ArrayAdapter方法
        ListView listView = (ListView) findViewById(R.id.join_listview);
        listView.setAdapter(adapter);//将创建的方法作为适配器传递给listview
        TextView name = (TextView) findViewById(R.id.join_name);
        name.setText("cyq");
        TextView school = (TextView) findViewById(R.id.join_school);
        school.setText("华东师范大学");
        TextView department = (TextView) findViewById(R.id.join_department);
        department.setText("软件工程");
        TextView type = (TextView) findViewById(R.id.join_type);
        type.setText("Sport");
        TextView content = (TextView) findViewById(R.id.join_content);
        content.setText("I want to run in the afternoon");
        TextView date = (TextView) findViewById(R.id.join_date);
        date.setText("2019.3.15 18:00 -- 19:00");
        TextView location = (TextView) findViewById(R.id.join_location);
        location.setText("playgound");
        TextView sex = (TextView) findViewById(R.id.join_sex);
        sex.setText("all");
    }
    @Override
    protected void initData() { //初始化类中所有数据
        JoinDataBase item1 = new JoinDataBase();
        item1.setName("cyq");
        item1.setSchool("华东师范大学");
        item1.setDepartment("软件工程");
        joinList.add(item1);
        JoinDataBase item2 = new JoinDataBase();
        item2.setName("cyq");
        item2.setSchool("华东师范大学");
        item2.setDepartment("软件工程");
        joinList.add(item2);
        JoinDataBase item3 = new JoinDataBase();
        item3.setName("cyq");
        item3.setSchool("华东师范大学");
        item3.setDepartment("软件工程");
        joinList.add(item3);
    }
    @Override
    protected void initListener()
    {
        Button back = (Button) findViewById(R.id.join_back);
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
        Button confirm = (Button) findViewById(R.id.join_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(JoinActivity.this,MapActivity.class);
                startActivity(intent);
                final String useremail = userEmail;
                //add user and activity into data;
                finish();
            }
        });
    }
}

