package com.example.a13787.baidumap.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.IDNA;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13787.baidumap.R;
import com.example.a13787.baidumap.entity.UserEntity;
import com.example.a13787.baidumap.util.BaseActivity;
import com.example.a13787.baidumap.util.CircleImageView;
import com.example.a13787.baidumap.util.GetData;
import com.example.a13787.baidumap.util.ImageUtil;
import com.example.a13787.baidumap.util.RequestUtil;

import java.io.FileNotFoundException;

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
    private ImageView portrait;
    private TextView change;
    private static final int CHOOSE_PHOTO = 1;
    private UserEntity userEntity;
    private String email = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }
    @Override
    protected void initData()
    {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        Log.d("email",email);
        if (email == null || email.length() == 0)
            userEntity = GetData.attemptQueryUser(InfoActivity.this);
        else userEntity = GetData.attemptQueryUser(InfoActivity.this,email);
        if (userEntity == null)
        {
            userEntity = new UserEntity();
            Toast.makeText(InfoActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
        }
        username.setText(userEntity.getName());
        String nick = userEntity.getNickname();
        if (nick == null || nick.length() == 0)
            nick = "这个用户比较懒";
        nickname.setText(nick);
        String _birth = userEntity.getBirth();
        if (_birth == null || _birth.length() == 0)
            _birth = "保密哦！";
        birth.setText(_birth);
        school.setText(userEntity.getSchool());
        department.setText(userEntity.getDepartment());
        sex.setText(userEntity.getSex());
        int _age= userEntity.getAge();
        if (_age > 0)
            age.setText(_age+"");
        else age.setText("保密哦！");
        String url = userEntity.getPortraiturl();
        Bitmap bitmap = ImageUtil.getBitmapFromUrl(url);
        portrait.setImageBitmap(bitmap);
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
        change = (TextView) findViewById(R.id.info_change);
        portrait = (ImageView) findViewById(R.id.info_portraint);
    }
    @Override
    protected void initListener()
    {
        change.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if(ContextCompat.checkSelfPermission(InfoActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(InfoActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE },1);
                }
                else
                {
                    openAlbum();
                }
            }
        });
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
                                    String response = GetData.attemptUpdateNickname(InfoActivity.this,et.getText().toString());
                                    //update nickname in database
                                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("取消",null).show();
            }
        });
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
                                    String response = GetData.attemptUpdateBirth(InfoActivity.this,et.getText().toString());
                                    //update birth in database
                                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                                }

                            }
                        })
                        .setNegativeButton("取消",null).show();
            }
        });
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
                                    String response = GetData.attemptUpdateAge(InfoActivity.this,Integer.parseInt(et.getText().toString()));
                                    //update birth in database
                                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
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
                finish();
            }
        });
    }
    @Override
    protected int initLayout()
    {
        return R.layout.activity_info;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch(requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast.makeText(this,"You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode)
        {
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    if(Build.VERSION.SDK_INT >= 19){
                        handleImageOnKitKat(data);
                    }
                    else{
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private  void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID +"="+id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }
            else if("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        }
        else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri,null);
        }
        else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath){
        if(imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            bitmap = CircleImageView.work(bitmap);
            //deal image
            portrait.setImageBitmap(bitmap);
            String pathName = ImageUtil.saveBitmapFile(bitmap);
            String response = GetData.attemptUpdatePortrait(InfoActivity.this,pathName);
            Log.d("response",response);
            Toast.makeText(InfoActivity.this,response,Toast.LENGTH_SHORT).show();
            //upload to database
        }
        else{
            Toast.makeText(this,"获取图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*''");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }


}
