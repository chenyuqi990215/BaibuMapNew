package com.example.a13787.baidumap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity
{
    public LocationClient mLocationClient;
    private MapView mapView;
    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mapView=(MapView)findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
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
        else {
            requestLocation();
            initData();   //模拟数据
            //对"+"按钮的监听button_add
		/*Button button_add=(Button)findViewById(R.id.button_add);
		button_add.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(MapActivity.this,AddActivity.class);
				startActivityForResult(intent, 1);
			}
		)};*/
            baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener()
            {
                @Override
                public boolean onMarkerClick(Marker marker)
                {
                    final myDataBase mydataBase = (myDataBase) marker.getExtraInfo().get("DataBase");
                    TextView infoUsername = (TextView)findViewById(R.id.info_username);
                    infoUsername.setText("Name: " + mydataBase.getUsername());
                    TextView infoSchoolName = (TextView)findViewById(R.id.info_schoolname);
                    infoSchoolName.setText("School: " + mydataBase.getSchoolName());
                    TextView infoDepartment = (TextView)findViewById(R.id.info_department);
                    infoDepartment.setText("Department: " + mydataBase.getDepartment());
                    TextView infoTime = (TextView)findViewById(R.id.info_time);
                    infoTime.setText("Time: " + mydataBase.getStartTime() + " - " + mydataBase.getEndTime());
                    TextView infoInfo = (TextView)findViewById(R.id.info_info);
                    infoInfo.setText("Info: " + mydataBase.getInfo());
                    String color;
                    if (mydataBase.getType().equals("study"))
                        color="#ff0000";
                    else if (mydataBase.getType().equals("food"))
                        color="#00ff00";
                    else if (mydataBase.getType().equals("sport"))
                        color="#0000ff";
                    else color="#ffff00";
                    infoUsername.setTextColor(Color.parseColor(color));
                    infoSchoolName.setTextColor(Color.parseColor(color));
                    infoDepartment.setTextColor(Color.parseColor(color));
                    infoTime.setTextColor(Color.parseColor(color));
                    infoInfo.setTextColor(Color.parseColor(color));
                    LinearLayout layout=(LinearLayout)findViewById(R.id.layout_infotop);
                    layout.setVisibility(View.VISIBLE);
                    //Add InfoWindow
                    Button button = new Button(getApplicationContext());
                    button.setText(mydataBase.getTitle());
                    button.setAllCaps(false);
                    LatLng ll = new LatLng(mydataBase.getLatitude(),mydataBase.getLongtitude());
                    InfoWindow mInfoWindow = new InfoWindow(button, ll, -120);
                    baiduMap.showInfoWindow(mInfoWindow);

                    return true;
                }
            });
        }
    }
    protected void initData()
    {
        myDataBase mydataBase= new myDataBase();
        mydataBase.setLongtitude(121.41048);
        mydataBase.setLatitude(31.233616);
        mydataBase.setUsername("cyq");
        mydataBase.setSchoolName("ECNU");
        mydataBase.setDepartment("SE");
        mydataBase.setType("study");
        mydataBase.setTitle("Prepare for exam");
        mydataBase.setLocation("ecnu library");
        mydataBase.setStartTime("prepare for exam");
        mydataBase.setInfo("I want to study for my comming Examination.");
        mydataBase.setStartTime("2019.3.9 15:00");
        mydataBase.setEndTime("2019.3.9 17:00");
        mydataBase.setSex("all");
        updateOverlay(mydataBase);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case 1:
                if (resultCode==RESULT_OK)
                {
                    updateOverlay((myDataBase) data.getSerializableExtra("DataBase"));
                }
        }
    }
    private void updateOverlay(myDataBase mydataBase)
    {
        LatLng point = new LatLng(mydataBase.getLatitude(), mydataBase.getLongtitude());
        //构建Marker图标
        BitmapDescriptor bitmap = null;
        if (mydataBase.getType().equals("study"))
            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_study);
        else if (mydataBase.getType().equals("food"))
            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_food);
        else if (mydataBase.getType().equals("sport"))
            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_sport);
        else bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_enjoyment);
        Bundle bundle = new Bundle();
        bundle.putSerializable("DataBase",mydataBase);
        //构建MarkerOption，用于在地图上添加
        MarkerOptions option = new MarkerOptions().position(point).extraInfo(bundle).icon(bitmap);
        //在地图上添加Marker，并显示
        baiduMap.addOverlay(option);
    }
    private void navigateTo(BDLocation location)
    {
        if (isFirstLocate)
        {
            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
            Log.d("Latitude",""+location.getLatitude());
            Log.d("Longitude",""+location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder locationBulider=new MyLocationData.Builder();
        locationBulider.latitude(location.getLatitude());
        locationBulider.longitude(location.getLongitude());
        MyLocationData locationData=locationBulider.build();
        baiduMap.setMyLocationData(locationData);

    }

    private void requestLocation()
    {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation(){
        LocationClientOption option=new LocationClientOption();
        option.setScanSpan(5000);
        option.setCoorType("bd09ll");
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void  onResume(){
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void  onPause(){
        super.onPause();
        mapView.onPause();
    }
    @Override
    protected void  onDestroy(){
        super.onDestroy();
        mLocationClient.stop();
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
                    requestLocation();
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
    public class MyLocationListener implements BDLocationListener
    {
        @Override
        public void onReceiveLocation(final BDLocation location)
        {
            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType()==BDLocation.TypeNetWorkLocation){
                navigateTo(location);

            }
            /*runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StringBuffer currentPosition = new StringBuffer();
                    currentPosition.append("Latitude: ").append(location.getLatitude()).append("\n");
                    currentPosition.append("Longitude: ").append(location.getLongitude()).append("\n");
                    currentPosition.append("Location type: ");
                    if (location.getLocType()==BDLocation.TypeGpsLocation)
                    {
                        currentPosition.append("GPS");
                    }
                    else if (location.getLocType()==BDLocation.TypeNetWorkLocation)
                    {
                        currentPosition.append("Network");
                    }
                    positionText.setText(currentPosition);

                }
            });*/
        }
    }

}
