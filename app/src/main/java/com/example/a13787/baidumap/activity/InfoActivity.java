package com.example.a13787.baidumap.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13787.baidumap.R;
import com.example.a13787.baidumap.util.BaseActivity;

public class InfoActivity extends BaseActivity
{
    private TextView username;
    private TextView nickname;
    private TextView school;
    private TextView birth;
    private TextView department;
    private TextView sex;
    private TextView age;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void initData()
    {
        //search from database by username
    }
    @Override
    protected void initView()
    {
        username = (TextView) findViewById(R.id.info_user);
        nickname = (TextView) findViewById(R.id.info_nickname);
        birth = (TextView) findViewById(R.id.info_birth);
        school = (TextView) findViewById(R.id.info_school);
        department = (TextView) findViewById(R.id.info_depart);
        sex = (TextView) findViewById(R.id.info_sex);
        age = (TextView) findViewById(R.id.info_age);
        back = (Button) findViewById(R.id.info_back);
    }
    @Override
    protected void initListener()
    {
        username.setText("cyq");
        nickname.setText("not known");
        nickname.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final EditText et = new EditText(InfoActivity.this);
                new AlertDialog.Builder(InfoActivity.this).setTitle("请输入昵称")
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                if (et.getText().toString().length()>0)
                                {
                                    nickname.setText(et.getText().toString());
                                    //update nickname in database
                                    Toast.makeText(getApplicationContext(),new String("修改成功"),Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("取消",null).show();
            }
        });
        birth.setText("secret");
        birth.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final EditText et = new EditText(InfoActivity.this);
                new AlertDialog.Builder(InfoActivity.this).setTitle("请输入生日(yyyy-mm-dd)")
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                if (et.getText().toString().length()>0)
                                {
                                    birth.setText(et.getText().toString());
                                    //update birth in database
                                    Toast.makeText(getApplicationContext(),new String("修改成功"),Toast.LENGTH_LONG).show();
                                }

                            }
                        })
                        .setNegativeButton("取消",null).show();
            }
        });
        school.setText("华东师范大学");
        department.setText("软件工程");
        sex.setText("男");
        age.setText("secret");
        age.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final EditText et = new EditText(InfoActivity.this);
                new AlertDialog.Builder(InfoActivity.this).setTitle("请输入年龄")
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                if (et.getText().toString().length()>0)
                                {
                                    age.setText(et.getText().toString());
                                    //update birth in database
                                    Toast.makeText(getApplicationContext(),new String("修改成功"),Toast.LENGTH_LONG).show();
                                }

                            }
                        })
                        .setNegativeButton("取消",null).show();
            }
        });
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(InfoActivity.this,MapActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    protected int initLayout()
    {
        return R.layout.activity_info;
    }
}
