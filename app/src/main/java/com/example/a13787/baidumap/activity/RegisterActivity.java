package com.example.a13787.baidumap.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.a13787.baidumap.R;
import com.example.a13787.baidumap.entity.UserEntity;
import com.example.a13787.baidumap.util.BaseActivity;
import com.example.a13787.baidumap.util.GetData;
import com.example.a13787.baidumap.util.UserDatabaseHelper;

public class RegisterActivity extends BaseActivity
{
    private RadioGroup sex;
    private EditText userName;
    private EditText email;
    private EditText password;
    private EditText check;
    private EditText tel;
    private EditText schoolName;
    private EditText studid;
    private Button btn_register;
    private UserEntity userEntity = new UserEntity();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    @Override
    protected void initView()
    {
        sex = (RadioGroup) findViewById(R.id.register_sex);
        userName = (EditText) findViewById(R.id.register_username);
        email = (EditText) findViewById(R.id.register_email);
        password = (EditText) findViewById(R.id.register_password);
        check = (EditText) findViewById(R.id.register_confirm);
        tel = (EditText) findViewById(R.id.register_tel);
        schoolName = (EditText) findViewById(R.id.register_schoolname);
        studid = (EditText) findViewById(R.id.register_studid);
        btn_register = (Button) findViewById(R.id.email_sign_in_button);
    }
    @Override
    protected int initLayout()
    {
        return R.layout.activity_register;
    }
    @Override
    protected void initListener()
    {
        EditText pass = (EditText) findViewById(R.id.register_password);
        pass.setTypeface(Typeface.DEFAULT);
        pass.setTransformationMethod(new PasswordTransformationMethod());
        pass = (EditText) findViewById(R.id.register_confirm);
        pass.setTypeface(Typeface.DEFAULT);
        pass.setTransformationMethod(new PasswordTransformationMethod());
        btn_register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String password1 = password.getText().toString();
                String password2 = check.getText().toString();
                if (!password1.equals(password2))
                {
                    Toast.makeText(RegisterActivity.this,"密码错误：密码不一致",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    int sexChoseId = sex.getCheckedRadioButtonId();
                    switch (sexChoseId) {
                        case R.id.mainRegisterRdBtnFemale:
                            userEntity.setSex("Female");
                            break;
                        case R.id.mainRegisterRdBtnMale:
                            userEntity.setSex("Male");
                            break;
                        default:
                            userEntity.setSex("Secret");
                            break;
                    }
                    String info;
                    info = userName.getText().toString();
                    userEntity.setName(info);
                    info = email.getText().toString();
                    userEntity.setEmail(info);
                    info = password.getText().toString();
                    userEntity.setPassword(info);
                    info = tel.getText().toString();
                    userEntity.setPhone(info);
                    info = schoolName.getText().toString();
                    userEntity.setSchool(info);
                    info = studid.getText().toString();
                    userEntity.setSchoolid(info);
                    String result = GetData.attempRegister(RegisterActivity.this,userEntity);
                    Toast.makeText(RegisterActivity.this,result,Toast.LENGTH_SHORT).show();
                    if (result.equals("注册成功"))
                    {
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                        return;
                }
            }
        });
    }
    @Override
    protected void initData()
    {

    }
}
