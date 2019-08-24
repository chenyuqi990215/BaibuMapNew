package com.example.a13787.baidumap.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.example.a13787.baidumap.R;
import com.example.a13787.baidumap.util.SlideMenu;
import com.example.a13787.baidumap.entity.MapDataBase;
import com.example.a13787.baidumap.util.BaseActivity;
import com.example.a13787.baidumap.util.MapUtil;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends BaseActivity
{
    private SlideMenu slideMenu = null;
    private Button button = null;
    private Marker markerOnMap = null;
    private MapDataBase eventOnMap = null;
    private MapUtil mapUtil;
    private MapView mapView;
    private BaiduMap baiduMap;
    private boolean isPermitted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void initView()
    {
        slideMenu = (SlideMenu) findViewById(R.id.slideMenu);
        button = (Button) findViewById(R.id.slide_enter);
        mapView=(MapView)findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        mapUtil = new MapUtil(baiduMap,MapActivity.this);
        List<String> permissionList=new ArrayList<>();
        if(ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[] permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MapActivity.this,permissions,1);
        }
        else{
            mapUtil.requestLocation();
            initData();   //模拟数据
            isPermitted = true;
        }
    }
    @Override
    protected int initLayout()
    {
        return R.layout.activity_main;
    }
    @Override
    protected void initData()
    {
        MapDataBase mydataBase= new MapDataBase();
        mydataBase.setLongitude(121.413326);
        mydataBase.setLatitude(31.234196);
        mydataBase.setName("cyq");
        mydataBase.setSchool("ECNU");
        mydataBase.setDepartment("SE");
        mydataBase.setClickable(true);
        mydataBase.setType("study");
        mydataBase.setTitle("Prepare for exam");
        mydataBase.setLocation("ecnu library");
        mydataBase.setContent("I want to study for my comming Examination.");
        mydataBase.setDate("2019.3.15 15:00 - 17:00");
        mydataBase.setSex("all");
        mapUtil.updateOverlay(mydataBase,0);
        mydataBase= new MapDataBase();
        mydataBase.setLongitude(121.411511);
        mydataBase.setLatitude(31.234907);
        mydataBase.setName("cyq");
        mydataBase.setSchool("ECNU");
        mydataBase.setDepartment("SE");
        mydataBase.setClickable(true);
        mydataBase.setType("sport");
        mydataBase.setTitle("Running");
        mydataBase.setLocation("ecnu playground");
        mydataBase.setContent("I want to run for an hour.");
        mydataBase.setDate("2019.3.15 18:00 - 19:00");
        mydataBase.setSex("all");
        mapUtil.updateOverlay(mydataBase,0);
        mydataBase= new MapDataBase();
        mydataBase.setLongitude(121.411991);
        mydataBase.setLatitude(31.23632);
        mydataBase.setName("cyq");
        mydataBase.setSchool("ECNU");
        mydataBase.setDepartment("SE");
        mydataBase.setClickable(true);
        mydataBase.setType("sport");
        mydataBase.setTitle("Badminton");
        mydataBase.setLocation("ecnu activity center");
        mydataBase.setContent("I want to run for an hour.");
        mydataBase.setDate("2019.3.15 18:00 - 19:00");
        mydataBase.setSex("all");
        mapUtil.updateOverlay(mydataBase,0);
        mydataBase= new MapDataBase();
        mydataBase.setLongitude(121.409952);
        mydataBase.setLatitude(31.236264);
        mydataBase.setName("cyq");
        mydataBase.setSchool("ECNU");
        mydataBase.setDepartment("SE");
        mydataBase.setClickable(true);
        mydataBase.setType("food");
        mydataBase.setTitle("Breakfast");
        mydataBase.setLocation("ecnu Hexi canteen");
        mydataBase.setContent("I hope to have breakfast everyday.");
        mydataBase.setDate("2019.3.15 6:00 - 8:00");
        mydataBase.setSex("all");
        mapUtil.updateOverlay(mydataBase,0);
        mydataBase = new MapDataBase();
        mydataBase.setLongitude(121.410079);
        mydataBase.setLatitude(31.235576);
        mydataBase.setName("cyq");
        mydataBase.setSchool("ECNU");
        mydataBase.setDepartment("SE");
        mydataBase.setClickable(true);
        mydataBase.setType("enjoyment");
        mydataBase.setTitle("PlayUNO");
        mydataBase.setLocation("ecnu Fifth dorm");
        mydataBase.setContent("Let's play UNO.");
        mydataBase.setDate("2019.3.15 20:00 - 22:00");
        mydataBase.setSex("all");
        mapUtil.updateOverlay(mydataBase,0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case 1:
                if (resultCode==RESULT_OK)
                {
                    mapUtil.updateOverlay((MapDataBase) data.getSerializableExtra("DataBase"),0);
                }
        }
    }
    @Override
    protected void initListener()
    {
        //对"+"按钮的监听button_add
        TextView footprint = (TextView) findViewById(R.id.menu_footprint);
        footprint.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MapActivity.this,FootprintActivity.class);
                startActivity(intent);
            }
        });
        TextView me = (TextView) findViewById(R.id.menu_me);
        me.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MapActivity.this,InfoActivity.class);
                startActivity(intent);
            }
        });
        TextView activity = (TextView) findViewById(R.id.menu_activity);
        activity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MapActivity.this,ParticipateActivity.class);
                startActivity(intent);
            }
        });
        Button button_add=(Button)findViewById(R.id.button_add);
        button_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MapActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                slideMenu.switchMenu();
            }
        });
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(Marker marker)
            {
                final MapDataBase mydataBase = (MapDataBase) marker.getExtraInfo().get("MapDataBase");
                if (mydataBase.isClickable()==false)
                    return true;
                if (markerOnMap!=null)
                    markerOnMap.remove();
                if (eventOnMap!=null)
                    mapUtil.updateOverlay(eventOnMap,0);
                eventOnMap=mydataBase;
                marker.remove();
                LinearLayout layout=(LinearLayout)findViewById(R.id.layout_infotop);
                layout.setVisibility(View.VISIBLE);
                LatLng ll = new LatLng(mydataBase.getLatitude(),mydataBase.getLongitude());
                TextView infoUsername = (TextView)findViewById(R.id.info_username);
                infoUsername.setText("Name: " + mydataBase.getName());
                TextView infoDepartment = (TextView)findViewById(R.id.info_department);
                infoDepartment.setText("Department: " + mydataBase.getDepartment());
                TextView infoTime = (TextView)findViewById(R.id.info_time);
                infoTime.setText("Time: " + mydataBase.getDate());
                String color;
                if (mydataBase.getType().equals("study"))
                {
                    MapDataBase temp  = new MapDataBase();
                    temp.setClickable(false);
                    temp.setType("study");
                    mapUtil.updateOverlay(temp,1);
                    color="#ff0000";
                }
                else if (mydataBase.getType().equals("food"))
                {
                    MapDataBase temp  = new MapDataBase();
                    temp.setClickable(false);
                    temp.setType("food");
                    mapUtil.updateOverlay(temp,1);
                    color="#00ff00";
                }
                else if (mydataBase.getType().equals("sport"))
                {
                    MapDataBase temp  = new MapDataBase();
                    temp.setClickable(false);
                    temp.setType("sport");
                    mapUtil.updateOverlay(temp,1);
                    color="#0000ff";
                }
                else
                {
                    MapDataBase temp  = new MapDataBase();
                    temp.setClickable(false);
                    temp.setType("enjoyment");
                    mapUtil.updateOverlay(temp,1);
                    color="#ff00ff";
                }
                infoUsername.setTextColor(Color.parseColor(color));
                infoDepartment.setTextColor(Color.parseColor(color));
                infoTime.setTextColor(Color.parseColor(color));
                //Add InfoWindow
                baiduMap.hideInfoWindow();
                Button button = new Button(getApplicationContext());
                button.setText(mydataBase.getTitle());
                button.setBackgroundColor(Color.parseColor("#e9eafd"));
                button.setTextColor(Color.parseColor("#8078e0"));
                button.setAllCaps(false);
                InfoWindow mInfoWindow = new InfoWindow(button, ll, -170);
                baiduMap.showInfoWindow(mInfoWindow);
                button.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(MapActivity.this,JoinActivity.class);
                        intent.putExtra("Activity",mydataBase);
                        startActivity(intent);
                    }
                }
                );
                return true;
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mapUtil.onDestory();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults)
    {
        switch(requestCode){
            case 1:
                if (grantResults.length>0)
                {
                    for (int result: grantResults)
                    {
                        if (result!= PackageManager.PERMISSION_GRANTED)
                        {
                            Toast.makeText(this, "You must grant all the permissions",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    mapUtil.requestLocation();
                    initData();   //模拟数据
                    isPermitted = true;
                }
                else
                {
                    Toast.makeText(this, "Error occur",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
}