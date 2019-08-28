package com.example.a13787.baidumap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13787.baidumap.R;
import com.example.a13787.baidumap.service.TimerService;
import com.example.a13787.baidumap.util.BaseActivity;
import com.example.a13787.baidumap.util.GetData;

/**
 * Created by 13787 on 2019/3/11.
 */

public class LoginActivity extends BaseActivity
{
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button mEmailSignInButton;
    private TextView mAttempRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    private void attemptLogin()
    {
        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String result = GetData.attemptLogin(LoginActivity.this,email,password);
        if (result.equals("登陆失败"))
            Toast.makeText(LoginActivity.this,result,Toast.LENGTH_SHORT).show();
        else
        {
            Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
            Intent intoMain = new Intent(LoginActivity.this,MapActivity.class);
            Intent service = new Intent(LoginActivity.this, TimerService.class);
            startService(service);
            startActivity(intoMain);
        }

    }
    @Override
    protected void initView()
    {
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mAttempRegister = (TextView) findViewById(R.id.attempt_register);
    }
    @Override
    protected int initLayout()
    {
        return R.layout.activity_login;
    }
    @Override
    protected void initListener()
    {
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if (id == R.id.login || id == EditorInfo.IME_NULL)
                {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        mEmailSignInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                attemptLogin();
            }
        });
        mAttempRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    protected void initData()
    {

    }
}


