package com.example.a13787.baidumap;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void initData()
    {
        //search
    }
    @Override
    protected void initView()
    {

    }
    @Override
    protected void initListener()
    {
        final TextView username = (TextView) findViewById(R.id.info_user);
        username.setText("cyq");
        final TextView nickname = (TextView) findViewById(R.id.info_nickname);
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
        final TextView birth = (TextView) findViewById(R.id.info_birth);
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
        final TextView school = (TextView) findViewById(R.id.info_school);
        school.setText("华东师范大学");
        final TextView department = (TextView) findViewById(R.id.info_depart);
        department.setText("软件工程");
        final TextView sex = (TextView) findViewById(R.id.info_sex);
        sex.setText("男");
        final TextView age = (TextView) findViewById(R.id.info_age);
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
        Button back = (Button) findViewById(R.id.info_back);
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
