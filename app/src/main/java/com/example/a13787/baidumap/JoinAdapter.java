package com.example.a13787.baidumap;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 13787 on 2019/4/5.
 */

public class JoinAdapter extends ArrayAdapter<JoinDataBase> { //创建自定义适配器，继承自ArrayAdapter，并将泛型指定为fruit类
    private int resourceId;

    public JoinAdapter(Context context, int textViewResourceId, List<JoinDataBase> objects) { //重写父类构造函数，获取相关布局
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { //重写getView方法
        JoinDataBase cur = getItem(position); //获取当前实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false); //为此子项加载我么传入的布局
        TextView name = (TextView) view.findViewById(R.id.person_name); //获取实例的具体项目
        name.setText(cur.getName());
        TextView school = (TextView) view.findViewById(R.id.person_school);
        school.setText(cur.getSchool());
        TextView department = (TextView) view.findViewById(R.id.person_department);
        department.setText(cur.getDepartment());
        return view;
    }
}
