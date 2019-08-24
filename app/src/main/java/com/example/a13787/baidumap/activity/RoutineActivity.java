package com.example.a13787.baidumap.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    }

    @Override
    public void initView()
    {
        mMapView = (MapView) findViewById(R.id.bmapView3);
        baiduMap = mMapView.getMap();
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
        BDLocation location=mapUtil.getMyLocation();
        PlanNode stNode = PlanNode.withLocation(new LatLng(location.getLatitude(),location.getLongitude()));
        PlanNode enNode = PlanNode.withCityNameAndPlaceName("上海", "同济大学");
        mSearch.bikingSearch((new BikingRoutePlanOption())
                                .from(stNode)
                                .to(enNode));
        overlay = new BikingRouteOverlay(baiduMap);
    }

    @Override
    public void initListener()
    {
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
                if (result != null)
                {
                    List<BikingRouteLine> routeLineList = result.getRouteLines();
                    if (routeLineList != null && !routeLineList.isEmpty() && routeLineList.size() != 0)
                    {
                        overlay.removeFromMap();
                        overlay.setData(routeLineList.get(0));
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
    }


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
}
