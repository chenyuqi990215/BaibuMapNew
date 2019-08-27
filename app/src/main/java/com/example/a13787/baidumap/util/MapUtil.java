package com.example.a13787.baidumap.util;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.a13787.baidumap.R;
import com.example.a13787.baidumap.entity.MapEntity;
import com.example.a13787.baidumap.entity.SearchEntity;

/**
 * Created by 13787 on 2019/8/24.
 */

public class MapUtil
{
    private LocationClient mLocationClient;
    private boolean isFirstLocate = true;
    private BaiduMap baiduMap;
    private Context context;
    private  BDLocation location;
    public MapUtil(BaiduMap baiduMap,Context context)
    {
        this.baiduMap=baiduMap;
        this.context=context;
    }

    private class MyLocationListener implements BDLocationListener
    {
        @Override
        public void onReceiveLocation(final BDLocation location)
        {
            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType()==BDLocation.TypeNetWorkLocation)
            {
                navigateTo(location);
            }
        }
    }
    public void requestLocation()
    {
        initLocation();
        mLocationClient.start();
    }

    public void onDestory()
    {
        mLocationClient.stop();
    }

    public Marker updateOverlay(MapEntity mapEntity, int op)
    {
        BitmapDescriptor bitmap = null;
        if (op == 0)
        {
            if (mapEntity.getType().equals("study"))
                bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_study);
            else if (mapEntity.getType().equals("food"))
                bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_food);
            else if (mapEntity.getType().equals("sport"))
                bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_sport);
            else bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_enjoyment);
        }
        else
        {
            if (mapEntity.getType().equals("study"))
                bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_study_mark);
            else if (mapEntity.getType().equals("food"))
                bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_food_mark);
            else if (mapEntity.getType().equals("sport"))
                bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_sport_mark);
            else bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_enjoyment_mark);
        }
        LatLng point = new LatLng(mapEntity.getLatitude(), mapEntity.getLongitude());
        //构建Marker图标
        Bundle bundle = new Bundle();
        bundle.putSerializable("MapEntity", mapEntity);
        MarkerOptions option = new MarkerOptions().position(point).extraInfo(bundle).icon(bitmap);
        return (Marker)baiduMap.addOverlay(option);
    }

    public Marker updateOverlay(SearchEntity searchEntity, int op)
    {
        LatLng point = new LatLng(searchEntity.getLatitude(), searchEntity.getLongitude());
        //构建Marker图标
        BitmapDescriptor bitmap = null;
        if (op == 0)
            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_start);
        else
            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_start_big);
        Bundle bundle = new Bundle();
        bundle.putSerializable("SearchEntity", searchEntity);
        //构建MarkerOption，用于在地图上添加
        MarkerOptions option = new MarkerOptions().position(point).extraInfo(bundle).icon(bitmap);
        //在地图上添加Marker，并显示
        Log.d("overlay", "add");
        return (Marker)baiduMap.addOverlay(option);
    }

    private void initLocation(){
        mLocationClient=new LocationClient(context.getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        LocationClientOption option=new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);
        option.setIsNeedLocationPoiList(true);
        option.setPriority(LocationClientOption.GpsFirst);
        mLocationClient.setLocOption(option);
    }

    public BDLocation getMyLocation()
    {
        return this.location;
    }
    private void navigateTo(BDLocation location)
    {
        this.location=location;
        if (isFirstLocate)
        {
            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
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
}
