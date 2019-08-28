package com.example.a13787.baidumap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.example.a13787.baidumap.entity.ActivityEntity;
import com.example.a13787.baidumap.R;
import com.example.a13787.baidumap.util.BaseActivity;
import com.example.a13787.baidumap.util.GetData;

public class AddActivity extends BaseActivity
{
    private ActivityEntity activityEntity = null;
    private Button confirm;
    private Button back;
    private ImageView imageView;
    private String _location;
    private LatLng ll = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void initView()
    {
        confirm = (Button) findViewById(R.id.add_confirm);
        imageView = (ImageView) findViewById(R.id.add_map);
        back = (Button) findViewById(R.id.add_back);
    }
    @Override
    protected void initData()
    {

    }
    @Override
    protected void initListener()
    {
        confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                activityEntity = new ActivityEntity();
                final EditText title = (EditText) findViewById(R.id.add_title);
                String tmp;
                tmp = title.getText().toString();
                if (tmp.length() == 0)
                {
                    Toast.makeText(AddActivity.this,new String("Error: Title cannot be null"),Toast.LENGTH_SHORT).show();
                    return;
                }
                activityEntity.setTitle(tmp);
                final EditText content = (EditText) findViewById(R.id.add_content);
                tmp = content.getText().toString();
                if (tmp.length() == 0)
                {
                    Toast.makeText(AddActivity.this,new String("Error: Content cannot be null"),Toast.LENGTH_SHORT).show();
                    return;
                }
                activityEntity.setContent(tmp);
                final EditText time = (EditText) findViewById(R.id.add_time);
                tmp = time.getText().toString();
                if (tmp.length() == 0)
                {
                    Toast.makeText(AddActivity.this,new String("Error: Time cannot be null"),Toast.LENGTH_SHORT).show();
                    return;
                }
                activityEntity.setTime(tmp);
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
                activityEntity.setLongitude(ll.longitude);
                activityEntity.setLatitude(ll.latitude);
                activityEntity.setLocation(_location);
                final RadioGroup type = (RadioGroup) findViewById(R.id.add_type);
                int typeCheck = type.getCheckedRadioButtonId();
                switch (typeCheck)
                {
                    case R.id.add_study:
                        activityEntity.setType("study");
                        break;
                    case R.id.add_enjoyment:
                        activityEntity.setType("enjoyment");
                        break;
                    case R.id.add_food:
                        activityEntity.setType("food");
                        break;
                    case R.id.add_sport:
                        activityEntity.setType("sport");
                        break;
                    default:
                        Toast.makeText(AddActivity.this,new String("请选择类型"),Toast.LENGTH_SHORT).show();
                        return;
                }
                final RadioGroup sex = (RadioGroup) findViewById(R.id.add_sex);
                int sexChecked = sex.getCheckedRadioButtonId();
                switch (sexChecked)
                {
                    case R.id.add_male:
                        activityEntity.setRestrict("仅限男生");
                        break;
                    case R.id.add_female:
                        activityEntity.setRestrict("仅限女生");
                        break;
                    case R.id.add_all:
                        activityEntity.setRestrict("男女皆可");
                        break;
                    default:
                        activityEntity.setRestrict("男女皆可");
                }

                String response = GetData.attemptReleaseActivity(AddActivity.this,activityEntity);
                //submit data to database
                Toast.makeText(AddActivity.this,response,Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AddActivity.this,MapSearchActivity.class);
                startActivityForResult(intent,1);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case 1:
                if (resultCode == RESULT_OK)
                {
                    _location = data.getStringExtra("location");
                    double latitude = data.getDoubleExtra("latitude",0);
                    double longitude = data.getDoubleExtra("longitude",0);
                    ll = new LatLng(latitude,longitude);
                    TextView setlocation = (TextView) findViewById(R.id.add_location);
                    setlocation.setText(_location);
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