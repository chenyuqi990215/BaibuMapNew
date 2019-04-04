package com.example.a13787.baidumap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.example.a13787.baidumap.view.SlideMenu;

import java.util.ArrayList;
import java.util.List;

public class MapSearchActivity extends AppCompatActivity
{
    private SuggestionSearch mSuggestionSearch;
    public LocationClient mLocationClient;
    private MapView mapView;
    private Marker selectOnMap = null;
    private BaiduMap baiduMap;
    private SearchDataBase searchOnMap = null;
    private boolean isFirstLocate = true;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        mapView=(MapView)findViewById(R.id.bmapView2);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        requestLocation();
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(listenerSearch);
        mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
                .city("上海")
                .keyword("华东师范大学理科大楼"));
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker)
            {
                final SearchDataBase searchDataBase = (SearchDataBase) marker.getExtraInfo().get("SearchDataBase");
                if (searchDataBase.isClickable() == false)
                    return true;
                if (searchOnMap!=null)
                    updateOverlay(searchOnMap);
                if (selectOnMap!=null)
                    selectOnMap.remove();
                searchOnMap = searchDataBase;
                marker.remove();
                LatLng ll = new LatLng(searchDataBase.getLatitude(),searchDataBase.getLongitude());
                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_start_big);
                SearchDataBase temp = new SearchDataBase();
                temp.setClickable(false);
                Bundle bundle = new Bundle();
                bundle.putSerializable("SearchDataBase",temp);
                OverlayOptions option = new MarkerOptions().position(ll).extraInfo(bundle).icon(bitmap);
                selectOnMap = (Marker) baiduMap.addOverlay(option);
                return true;
            }
        });
    }

    OnGetSuggestionResultListener listenerSearch = new OnGetSuggestionResultListener() {
        public void onGetSuggestionResult(SuggestionResult res) {
            if (res == null || res.getAllSuggestions() == null) {
                Log.d("error","error");
                return;
            } else {
                baiduMap.clear();
                List<SuggestionResult.SuggestionInfo> resl = res.getAllSuggestions();
                for (int i = 0; i < resl.size(); i++)
                {
                    SearchDataBase searchDataBase = new SearchDataBase();
                    searchDataBase.setLatitude(resl.get(i).getPt().latitude);
                    searchDataBase.setLongitude(resl.get(i).getPt().longitude);
                    searchDataBase.setKey(resl.get(i).getKey());
                    searchDataBase.setClickable(true);
                    updateOverlay(searchDataBase);
                    Log.d("geolatitude", resl.get(i).getPt().latitude+"");
                    Log.d("geolongitude", resl.get(i).getPt().longitude+"");
                }
            }
            //获取在线建议检索结果
        }
    };

    private void navigateTo(BDLocation location)
    {
        if (isFirstLocate)
        {
            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        Log.d("Latitude",""+location.getLatitude());
        Log.d("Longitude",""+location.getLongitude());
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
    private void updateOverlay(SearchDataBase searchDataBase)
    {
        LatLng point = new LatLng(searchDataBase.getLatitude(), searchDataBase.getLongitude());
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_start);
        Bundle bundle = new Bundle();
        bundle.putSerializable("SearchDataBase",searchDataBase);
        //构建MarkerOption，用于在地图上添加
        MarkerOptions option = new MarkerOptions().position(point).extraInfo(bundle).icon(bitmap);
        //在地图上添加Marker，并显示
        Log.d("overlay", "add");
        baiduMap.addOverlay(option);
    }
    private void initLocation(){
        LocationClientOption option=new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);
        option.setIsNeedLocationPoiList(true);
        option.setPriority(LocationClientOption.GpsFirst);
        mLocationClient.setLocOption(option);
    }
    public class MyLocationListener implements BDLocationListener
    {
        @Override
        public void onReceiveLocation(final BDLocation location)
        {
            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType()==BDLocation.TypeNetWorkLocation){
                navigateTo(location);

            }
        }
    }


    @Override
    protected void onDestroy() {
        mSuggestionSearch.destroy();
        //	mMapView.onDestroy();
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理


    }

    @Override
    protected void onResume() {
        //   	mMapView.onResume();
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理

    }

    @Override
    protected void onPause() {
        // 	mMapView.onPause();
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        ;
    }
}

