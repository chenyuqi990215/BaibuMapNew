package com.example.a13787.baidumap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.example.a13787.baidumap.R;
import com.example.a13787.baidumap.entity.SearchEntity;
import com.example.a13787.baidumap.util.BaseActivity;
import com.example.a13787.baidumap.util.MapUtil;

import java.util.List;

public class MapSearchActivity extends BaseActivity
{
    private SuggestionSearch mSuggestionSearch;
    private MapUtil mapUtil;
    private MapView mapView;
    private Marker selectOnMap = null;
    private BaiduMap baiduMap;
    private SearchEntity searchOnMap = null;
    private SearchEntity curSearchData = null;
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

        mapView=(MapView)findViewById(R.id.bmapView2);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        mapUtil = new MapUtil(baiduMap,MapSearchActivity.this);
        mapUtil.requestLocation();
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
                Log.d("Detect", "Marker click");
                final SearchEntity searchEntity = (SearchEntity) marker.getExtraInfo().get("SearchEntity");
                curSearchData = new SearchEntity();
                curSearchData.setLongitude(searchEntity.getLongitude());
                curSearchData.setLatitude(searchEntity.getLatitude());
                curSearchData.setKey(searchEntity.getKey());
                if (searchEntity.isClickable() == false)
                    return true;
                if (searchOnMap!=null)
                    mapUtil.updateOverlay(searchOnMap,0);
                if (selectOnMap!=null)
                    selectOnMap.remove();
                searchOnMap = searchEntity;
                marker.remove();
                LatLng ll = new LatLng(searchEntity.getLatitude(), searchEntity.getLongitude());
                SearchEntity temp = new SearchEntity();
                temp.setClickable(false);
                temp.setKey(searchEntity.getKey());
                temp.setLatitude(searchEntity.getLatitude());
                temp.setLongitude(searchEntity.getLongitude());
                selectOnMap = mapUtil.updateOverlay(temp,1);
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
            baiduMap.clear();
            searchOnMap = null;
            curSearchData = null;
            selectOnMap = null;
            Button buttonconfirm = (Button) findViewById(R.id.search_confirm);
            buttonconfirm.setVisibility(View.GONE);
            if (res == null || res.getAllSuggestions() == null) {
                Log.d("error","error");
                return;
            } else {
                List<SuggestionResult.SuggestionInfo> resl = res.getAllSuggestions();
                for (int i = 0; i < resl.size(); i++)
                {
                    SearchEntity searchEntity = new SearchEntity();
                    searchEntity.setLatitude(resl.get(i).getPt().latitude);
                    searchEntity.setLongitude(resl.get(i).getPt().longitude);
                    searchEntity.setKey(resl.get(i).getKey());
                    searchEntity.setClickable(true);
                    mapUtil.updateOverlay(searchEntity,0);
                }
            }
            //获取在线建议检索结果
        }
    };

    @Override
    protected void onDestroy() {
        mSuggestionSearch.destroy();
        mapUtil.onDestory();
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
}

