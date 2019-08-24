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
import com.example.a13787.baidumap.util.BaseActivity;
import com.example.a13787.baidumap.util.UserDatabaseHelper;

public class RegisterActivity extends BaseActivity
{
    private UserDatabaseHelper dbHelp = new UserDatabaseHelper(this,"Userinfo.db",null,4);  //update on 2019.3.1
    private RadioGroup sex;
    private EditText userName;
    private EditText email;
    private EditText password;
    private EditText check;
    private EditText tel;
    private EditText schoolName;
    private EditText studid;
    private Button btn_register;

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
                    Toast.makeText(RegisterActivity.this,"Error: Password not equal",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ContentValues contentValues = new ContentValues();
                    int sexChoseId = sex.getCheckedRadioButtonId();
                    switch (sexChoseId) {
                        case R.id.mainRegisterRdBtnFemale:
                            contentValues.put("sex", "Female");
                            break;
                        case R.id.mainRegisterRdBtnMale:
                            contentValues.put("sex", "Male");
                            break;
                        default:
                            contentValues.put("sex", "Default");
                            break;
                    }
                    String info;
                    info = userName.getText().toString();
                    contentValues.put("username", info);
                    info = email.getText().toString();
                    contentValues.put("email", info);
                    info = password.getText().toString();
                    contentValues.put("password", info);
                    info = tel.getText().toString();
                    contentValues.put("tel", info);
                    info = schoolName.getText().toString();
                    contentValues.put("schoolname", info);
                    info = studid.getText().toString();
                    contentValues.put("studid", info);
                    String result = dbHelp.addData(contentValues);
                    Toast.makeText(RegisterActivity.this,result,Toast.LENGTH_SHORT).show();
                    if (result.equals("Accepted"))
                    {
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }
    @Override
    protected void initData()
    {

    }
}
