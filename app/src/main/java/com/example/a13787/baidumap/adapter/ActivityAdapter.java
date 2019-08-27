package com.example.a13787.baidumap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.a13787.baidumap.entity.ActivityEntity;
import com.example.a13787.baidumap.R;

import java.util.List;

/**
 * Created by 13787 on 2019/4/4.
 */

public class ActivityAdapter extends ArrayAdapter<ActivityEntity>
{ //创建自定义适配器，继承自ArrayAdapter，并将泛型指定为fruit类
    private int resourceId;

    public ActivityAdapter(Context context, int textViewResourceId, List<ActivityEntity> objects)
    { //重写父类构造函数，获取相关布局
            super(context, textViewResourceId,objects);
            resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    { //重写getView方法
        ActivityEntity cur = getItem(position); //获取当前实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false); //为此子项加载我么传入的布局
        TextView name = (TextView) view.findViewById(R.id.footprint_name); //获取实例的具体项目
        TextView content = (TextView) view.findViewById(R.id.footprint_content);
        name.setText(cur.getName());
        content.setText(cur.getContent());
        TextView type = (TextView) view.findViewById(R.id.footprint_type);
        type.setText(cur.getType());
        TextView title = (TextView) view.findViewById(R.id.footprint_title);
        title.setText(cur.getTitle());
        TextView date = (TextView) view.findViewById(R.id.footprint_date);
        date.setText(cur.getDate());
        TextView school = (TextView) view.findViewById(R.id.footprint_school);
        school.setText(cur.getSchool());
        TextView sex = (TextView) view.findViewById(R.id.footprint_sex);
        sex.setText(cur.getSex());
        TextView location = (TextView) view.findViewById(R.id.footprint_location);
        location.setText(cur.getLocation());
        TextView department = (TextView) view.findViewById(R.id.footprint_department);
        department.setText(cur.getDepartment());
        return view;
    }
}