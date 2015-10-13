package com.cc.myviews.yahooflash;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;

import com.cc.myviews.R;

/**
 * Created by silvercc on 15/10/13.
 */
public class SunMoonView extends View {
    private final Paint mPaint;
    private final int xCoord;
    private final float yCoord;
    private final float radius;
    private Path mPath;
    private float mPathLength;

    public SunMoonView(Context context) {
        this(context, null, 0);
    }

    public SunMoonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SunMoonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.BLACK);
        PathEffect effect = new DashPathEffect(new float[]{1f, 2f, 4f, 8f}, 1f);
        mPaint.setPathEffect(effect);

        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        Bitmap mySunBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.sun);
        Bitmap myMoonBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.moon_new);

        xCoord = size.x / 2;
        yCoord = (float) (size.y * 0.28);
        radius = (float) (7 * 1.0 / 18) * size.x;

        initPath(Path.Direction.CW);
    }

    private void initPath(Path.Direction dir) {
        mPath = new Path();
        RectF rectF = new RectF(xCoord - radius, yCoord - radius, xCoord + radius, yCoord + radius);

        if(dir == Path.Direction.CW){
            mPath.addArc(rectF, 30, 360);
        } else {
            mPath.addArc(rectF, 30, -360);
        }
        mPath.close();
        PathMeasure pathMeasure = new PathMeasure(mPath, false);
        mPathLength = pathMeasure.getLength();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }
}
