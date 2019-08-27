package com.example.a13787.baidumap.util;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.DeserializeBeanInfo;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
/**
 * Created by 13787 on 2019/8/27.
 */

public class Copyer
{
    public static <B, S extends B> void Copy(B bo, S so) throws IllegalAccessException {

        try
        {
            Class bc = bo.getClass();
            if (bo == null || so == null)
            {
                return;
            }
            DeserializeBeanInfo deserializeBeanInfo = DeserializeBeanInfo.computeSetters(so.getClass(), (Type) so.getClass());
            List<FieldInfo> getters = TypeUtils.computeGetters(bo.getClass(), null);
            List<FieldInfo> setters = deserializeBeanInfo.getFieldList();
            Object v;
            FieldInfo getterfield;
            FieldInfo setterfidld;
            for (int j = 0; j < getters.size(); j++)
            {
                getterfield = getters.get(j);
                for (int i = 0; i < setters.size(); i++)
                {
                    setterfidld = setters.get(i);
                    if (setterfidld.getName().compareTo(getterfield.getName()) == 0)
                    {
                        v = getterfield.getMethod().invoke(bo);
                        setterfidld.getMethod().invoke(so, v);
                        break;
                    }
                }
            }
        }
        catch (Exception ex)
        {
            return;
        }
    }
}
