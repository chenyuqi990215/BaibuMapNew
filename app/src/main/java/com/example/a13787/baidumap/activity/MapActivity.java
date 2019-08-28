package com.example.a13787.baidumap.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
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
import com.example.a13787.baidumap.entity.ActivityEntity;
import com.example.a13787.baidumap.entity.MapEntity;
import com.example.a13787.baidumap.service.TimerService;
import com.example.a13787.baidumap.util.GetData;
import com.example.a13787.baidumap.util.SlideMenu;
import com.example.a13787.baidumap.util.BaseActivity;
import com.example.a13787.baidumap.util.MapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapActivity extends BaseActivity
{
    private SlideMenu slideMenu = null;
    private Button button = null;
    private Marker markerOnMap = null;
    private MapEntity eventOnMap = null;
    private MapUtil mapUtil = null;
    private MapView mapView;
    private BaiduMap baiduMap;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayout layout;
    private boolean isPermitted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void initView()
    {
        layout=(LinearLayout)findViewById(R.id.layout_infotop);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.map_refresh);
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
        if(ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if(!permissionList.isEmpty()){
            String[] permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MapActivity.this,permissions,1);
        }
        else{
            mapUtil = new MapUtil(baiduMap,MapActivity.this);
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
        baiduMap.clear();
        layout.setVisibility(View.GONE);
        ArrayList<ActivityEntity> tmp = GetData.attempQueryActivies(MapActivity.this);
        if (tmp != null)
        {
            for (int i = 0; i< tmp.size();i++)
            {
                MapEntity mapEntity = new MapEntity(tmp.get(i));
                mapUtil.updateOverlay(mapEntity,0);
            }
        }
    }
    @Override
    protected void initListener()
    {
        //SwipeRefreshLayout设置
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);//设置下拉刷新进度条颜色
        //下拉刷新监听
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                refreshLayout.setRefreshing(false);
            }
        });
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
                intent.putExtra("email","");
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
                final MapEntity curMapEntity = (MapEntity) marker.getExtraInfo().get("MapEntity");
                if (curMapEntity.isClickable()==false)
                    return true;
                if (markerOnMap!=null)
                    markerOnMap.remove();
                if (eventOnMap!=null)
                    mapUtil.updateOverlay(eventOnMap,0);
                eventOnMap=curMapEntity;
                marker.remove();
                LinearLayout layout=(LinearLayout)findViewById(R.id.layout_infotop);
                layout.setVisibility(View.VISIBLE);
                LatLng ll = new LatLng(curMapEntity.getLatitude(),curMapEntity.getLongitude());
                TextView infoUsername = (TextView)findViewById(R.id.info_username);
                infoUsername.setText("Name: " + curMapEntity.getUsername());
                TextView infoDepartment = (TextView)findViewById(R.id.info_department);
                infoDepartment.setText("Department: " + curMapEntity.getDepartment());
                TextView infoTime = (TextView)findViewById(R.id.info_time);
                infoTime.setText("Time: " + curMapEntity.getTime());
                String color;
                if (curMapEntity.getType().equals("study"))
                {
                    MapEntity mapEntity  = new MapEntity();
                    mapEntity.setClickable(false);
                    mapEntity.setType("study");
                    mapEntity.setLongitude(curMapEntity.getLongitude());
                    mapEntity.setLatitude(curMapEntity.getLatitude());
                    mapUtil.updateOverlay(mapEntity,1);
                    color="#ff0000";
                }
                else if (curMapEntity.getType().equals("food"))
                {
                    MapEntity mapEntity  = new MapEntity();
                    mapEntity.setClickable(false);
                    mapEntity.setType("food");
                    mapEntity.setLongitude(curMapEntity.getLongitude());
                    mapEntity.setLatitude(curMapEntity.getLatitude());
                    mapUtil.updateOverlay(mapEntity,1);
                    color="#00ff00";
                }
                else if (curMapEntity.getType().equals("sport"))
                {
                    MapEntity mapEntity  = new MapEntity();
                    mapEntity.setClickable(false);
                    mapEntity.setType("sport");
                    mapEntity.setLongitude(curMapEntity.getLongitude());
                    mapEntity.setLatitude(curMapEntity.getLatitude());
                    mapUtil.updateOverlay(mapEntity,1);
                    color="#0000ff";
                }
                else
                {
                    MapEntity mapEntity  = new MapEntity();
                    mapEntity.setClickable(false);
                    mapEntity.setType("enjoyment");
                    mapEntity.setLongitude(curMapEntity.getLongitude());
                    mapEntity.setLatitude(curMapEntity.getLatitude());
                    mapUtil.updateOverlay(mapEntity,1);
                    color="#ff00ff";
                }
                infoUsername.setTextColor(Color.parseColor(color));
                infoDepartment.setTextColor(Color.parseColor(color));
                infoTime.setTextColor(Color.parseColor(color));
                //Add InfoWindow
                baiduMap.hideInfoWindow();
                Button button = new Button(getApplicationContext());
                button.setText(curMapEntity.getTitle());
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
                        intent.putExtra("activityid",curMapEntity.getActivityid());
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