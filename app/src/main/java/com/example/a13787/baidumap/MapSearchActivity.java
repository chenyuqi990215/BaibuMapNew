package com.example.a13787.baidumap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

import java.util.List;

public class MapSearchActivity extends BaseActivity
{
    private SuggestionSearch mSuggestionSearch;
    public LocationClient mLocationClient;
    private MapView mapView;
    private Marker selectOnMap = null;
    private BaiduMap baiduMap;
    private SearchDataBase searchOnMap = null;
    private boolean isFirstLocate = true;
    private SearchDataBase curSearchData = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
    }
    @Override
    protected int initLayout()
    {
        return R.layout.activity_search;
    }
    @Override
    protected void initView()
    {
        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        mapView=(MapView)findViewById(R.id.bmapView2);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        requestLocation();
    }
    @Override
    protected void initListener()
    {
        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AutoCompleteTextView textCity = (AutoCompleteTextView) findViewById(R.id.search_city);
                AutoCompleteTextView textKey = (AutoCompleteTextView) findViewById(R.id.search_key);
                if (textCity.getText().toString().length() == 0)
                {
                    Toast.makeText(MapSearchActivity.this,"Error: City cannot be null",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (textKey.getText().toString().length() == 0)
                {
                    Toast.makeText(MapSearchActivity.this,"Error: Address cannot be null",Toast.LENGTH_SHORT).show();
                    return;
                }
                mSuggestionSearch = SuggestionSearch.newInstance();
                mSuggestionSearch.setOnGetSuggestionResultListener(listenerSearch);
                mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
                        .city(textCity.getText().toString())
                        .keyword(textKey.getText().toString()));

            }
        });
        final Button buttonConfirm = (Button) findViewById(R.id.search_confirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if (buttonConfirm.getVisibility() == View.GONE)
                    return;
                if (curSearchData == null)
                    return;
                Log.d("Confirm Key",curSearchData.getKey());
                Log.d("Confirm longitude",curSearchData.getLongitude()+"");
                Log.d("Confirm latitude",curSearchData.getLatitude()+"");
                Intent intent = new Intent();
                intent.putExtra("location",curSearchData.getKey());
                intent.putExtra("longitude",curSearchData.getLongitude());
                intent.putExtra("latitude",curSearchData.getLatitude());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        final Button buttonBack = (Button) findViewById(R.id.search_back);
        buttonBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker)
            {
                final SearchDataBase searchDataBase = (SearchDataBase) marker.getExtraInfo().get("SearchDataBase");
                curSearchData = new SearchDataBase();
                curSearchData.setLongitude(searchDataBase.getLongitude());
                curSearchData.setLatitude(searchDataBase.getLatitude());
                curSearchData.setKey(searchDataBase.getKey());
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
                temp.setKey(searchDataBase.getKey());
                temp.setLatitude(searchDataBase.getLatitude());
                temp.setLongitude(searchDataBase.getLongitude());
                Bundle bundle = new Bundle();
                bundle.putSerializable("SearchDataBase",temp);
                OverlayOptions option = new MarkerOptions().position(ll).extraInfo(bundle).icon(bitmap);
                selectOnMap = (Marker) baiduMap.addOverlay(option);
                Button buttonConfirm = (Button) findViewById(R.id.search_confirm);
                buttonConfirm.setVisibility(View.VISIBLE);
                return true;
            }
        });
    }
    @Override
    protected void initData()
    {

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
        ///Log.d("Latitude",""+location.getLatitude());
        //Log.d("Longitude",""+location.getLongitude());
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

