package com.example.a13787.baidumap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;

import org.w3c.dom.Text;

public class AddActivity extends BaseActivity
{
    private ActivityDataBase activityDataBase = null;
    private LatLng ll = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void initView()
    {

    }
    @Override
    protected void initData()
    {

    }
    @Override
    protected void initListener()
    {
        Button confirm = (Button) findViewById(R.id.add_confirm);
        confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                activityDataBase = new ActivityDataBase();
                final EditText title = (EditText) findViewById(R.id.add_title);
                String tmp;
                tmp = title.getText().toString();
                if (tmp.length() == 0)
                {
                    Toast.makeText(AddActivity.this,new String("Error: Title cannot be null"),Toast.LENGTH_SHORT).show();
                    return;
                }
                activityDataBase.setTitle(tmp);
                final EditText content = (EditText) findViewById(R.id.add_content);
                tmp = title.getText().toString();
                if (tmp.length() == 0)
                {
                    Toast.makeText(AddActivity.this,new String("Error: Content cannot be null"),Toast.LENGTH_SHORT).show();
                    return;
                }
                activityDataBase.setContent(tmp);
                final EditText time = (EditText) findViewById(R.id.add_time);
                tmp = time.getText().toString();
                if (tmp.length() == 0)
                {
                    Toast.makeText(AddActivity.this,new String("Error: Time cannot be null"),Toast.LENGTH_SHORT).show();
                    return;
                }
                activityDataBase.setDate(tmp);
                final TextView location = (TextView) findViewById(R.id.add_location);
                tmp = location.getText().toString();
                if (tmp.length() == 0)
                {
                    Toast.makeText(AddActivity.this,new String("Error: Please select location"),Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ll == null)
                {
                    Toast.makeText(AddActivity.this,new String("Error: Please select location"),Toast.LENGTH_SHORT).show();
                    return;
                }
                activityDataBase.setLongitude(ll.longitude);
                activityDataBase.setLatitude(ll.latitude);
                final RadioGroup type = (RadioGroup) findViewById(R.id.add_type);
                int typeCheck = type.getCheckedRadioButtonId();
                switch (typeCheck)
                {
                    case R.id.add_study:
                        activityDataBase.setType("study");
                        break;
                    case R.id.add_enjoyment:
                        activityDataBase.setType("enjoyment");
                        break;
                    case R.id.add_food:
                        activityDataBase.setType("food");
                        break;
                    case R.id.add_sport:
                        activityDataBase.setType("sport");
                        break;
                    default:
                        Toast.makeText(AddActivity.this,new String("Error: Type cannot be null"),Toast.LENGTH_SHORT).show();
                        return;
                }
                final RadioGroup sex = (RadioGroup) findViewById(R.id.add_sex);
                int sexChecked = sex.getCheckedRadioButtonId();
                switch (sexChecked)
                {
                    case R.id.add_male:
                        activityDataBase.setSex("male only");
                        break;
                    case R.id.add_female:
                        activityDataBase.setSex("female only");
                        break;
                    case R.id.add_all:
                        activityDataBase.setSex("all");
                        break;
                    default:
                        activityDataBase.setSex("all");
                }
                //submit data to database
                Intent intent = new Intent(AddActivity.this,MapActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.add_map);
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AddActivity.this,MapSearchActivity.class);
                startActivityForResult(intent,1);

            }
        });
        Button back = (Button) findViewById(R.id.add_back);
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AddActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case 1:
                if (resultCode == RESULT_OK)
                {
                    String location = data.getStringExtra("location");
                    double latitude = data.getDoubleExtra("latitude",0);
                    double longitude = data.getDoubleExtra("longitude",0);
                    ll = new LatLng(latitude,longitude);
                    TextView setlocation = (TextView) findViewById(R.id.add_location);
                    setlocation.setText(location);
                }
                break;
        }
    }
    @Override
    protected int initLayout()
    {
        return R.layout.activity_add;
    }
}