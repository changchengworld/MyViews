package com.cc.myviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by silvercc on 15/10/15.
 */
public class Utils {

    /**
     * 画圆
     * @param bitmap
     * @return
     */
    public static Bitmap drawCircleBitmap(Bitmap bitmap) {
        //首先创建出一个只有大小没有具体样式的bitmap
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //将这个bitmap作为canvas的画布背景
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        //画一个无色的圆
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawOval(rectF, paint);
        //设置图片重叠效果，取交集显示上层图片
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //将rect嵌入rectf
        canvas.drawBitmap(bitmap, rect, rectF, paint);
        bitmap.recycle();
        return output;
    }
}
