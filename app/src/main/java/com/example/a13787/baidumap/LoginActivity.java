package com.example.a13787.baidumap;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 13787 on 2019/3/11.
 */

public class LoginActivity extends BaseActivity
{
    private MyDatabaseHelper dbHelp = new MyDatabaseHelper(this,"Userinfo.db",null,4);
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;
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
        String result = dbHelp.queryPasswordByEmail(email);
        if (result.equals("Error: Email invalid") || result.equals("Error: Email not exist"))
            Toast.makeText(LoginActivity.this,result,Toast.LENGTH_SHORT).show();
        else
        {
            if (password.equals(result))
            {

                Toast.makeText(LoginActivity.this,"Accepted",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("com.example.a13787.morningcall.FORCE_OFFLINE");
                setUserEmail(email);
                intent.putExtra("Email", userEmail);
                intent.putExtra("IP", userIP);
                sendBroadcast(intent);
                Log.d("Broadcast", "send");
                //into main activity
                Intent intoMain = new Intent(LoginActivity.this,MapActivity.class);
                startActivity(intoMain);
                finish();
            }
            else
            {
                Toast.makeText(LoginActivity.this,"Error: Password incorrect",Toast.LENGTH_SHORT).show();
            }
        }

    }
    @Override
    protected void initView()
    {
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mLoginFormView = findViewById(R.id.login_form);
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
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                attemptLogin();
            }
        });
        TextView mAttempRegister = (TextView) findViewById(R.id.attempt_register);
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


