package com.example.a13787.baidumap.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

/**
 * Created by 13787 on 2019/8/24.
 */

public class CircleImageView
{
    public static Bitmap ImageCrop(Bitmap bitmap, boolean isRecycled)
    {
        if (bitmap == null)
        {
            return null;
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int wh = w > h ? h : w;
        int retX = w > h ? (w - h) / 2 : 0;
        int retY = w > h ? 0 : (h - w) / 2;
        Bitmap bmp = Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null,	false);
        if (isRecycled && bitmap != null && !bitmap.equals(bmp)	&& !bitmap.isRecycled())
        {
            bitmap.recycle();
        }
        return bmp;
    }


    public static Bitmap convert(Bitmap bitmap)
    {
        int w=bitmap.getWidth();
        int h=bitmap.getHeight();
        for (int i=0;i<w;i++)
            for (int j=0;j<h;j++)
            {
                if ((i-w/2)*(i-w/2)+(j-h/2)*(j-h/2)>(w/2)*(h/2))
                {
                    bitmap.setPixel(i,j, Color.WHITE);
                }
            }
        return bitmap;
    }
    public static Bitmap work(Bitmap bitmap)
    {
        Bitmap bitmap2 = ImageCrop(bitmap, true);
        Bitmap bitmap3 = convert(bitmap2);
        return bitmap3;
    }

}
