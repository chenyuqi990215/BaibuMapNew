package com.example.a13787.baidumap.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.BikingRouteLine;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.a13787.baidumap.R;
import com.example.a13787.baidumap.util.BaseActivity;
import com.example.a13787.baidumap.util.BikingRouteOverlay;
import com.example.a13787.baidumap.util.MapUtil;

import java.util.ArrayList;
import java.util.List;

public class RoutineActivity extends BaseActivity
{

    private RoutePlanSearch mSearch;
    private MapView mMapView = null;
    private MapUtil mapUtil;
    BikingRouteOverlay overlay;
    BaiduMap baiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        List<String> permissionList=new ArrayList<>();
        if(ContextCompat.checkSelfPermission(RoutineActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(RoutineActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if(!permissionList.isEmpty()){
            String[] permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(RoutineActivity.this,permissions,1);
        }
    }

    @Override
    public void initView()
    {
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(listener);
        mMapView = (MapView) findViewById(R.id.bmapView3);
        baiduMap = mMapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        mapUtil = new MapUtil(baiduMap,RoutineActivity.this);
        mapUtil.requestLocation();
        setElement();
    }

    @Override
    public int initLayout()
    {
        return R.layout.activity_routine;
    }

    @Override
    public void initData()
    {

    }

    private void setElement()
    {
        PlanNode stNode = PlanNode.withLocation(new LatLng(31.235576,121.410079));
        PlanNode enNode = PlanNode.withLocation(new LatLng(31.286563,121.518233));
        //PlanNode stNode = PlanNode.withCityNameAndPlaceName("北京", "西二旗地铁站");
        //PlanNode enNode = PlanNode.withCityNameAndPlaceName("北京", "百度科技园");
        boolean tmp = mSearch.bikingSearch((new BikingRoutePlanOption())
                                .from(stNode)
                                .to(enNode));
        overlay = new BikingRouteOverlay(baiduMap);
    }

    @Override
    public void initListener()
    {

    }

    OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult)
        {
            return;
        }
        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult)
        {
            return;
        }


        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult)
        {
            return;
        }


        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult)
        {
            return;
        }


        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult)
        {
            return;
        }


        public void onGetBikingRouteResult(BikingRouteResult result)
        {
            //Log.d("routine",result.getSuggestAddrInfo().toString());
            if (result != null)
            {
                List<BikingRouteLine> routeLineList = result.getRouteLines();
                //Log.d("routine",routeLineList.size()+"");
                if (routeLineList != null && !routeLineList.isEmpty() && routeLineList.size() != 0)
                {
                    overlay.removeFromMap();
                    overlay.setData(routeLineList.get(0));
                    Log.d("routine",routeLineList.get(0).toString());
                    overlay.addToMap();
                    overlay.zoomToSpan();
                }
                else
                {
                    return;
                }
            }
            else
            {
                return;
            }
        }
    };

    @Override
    protected void onResume(){
        super.onResume();
        mMapView.onResume();
    }
    @Override
    protected void onPause(){
        super.onPause();
        mMapView.onPause();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mapUtil.onDestory();
        mMapView.onDestroy();
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
