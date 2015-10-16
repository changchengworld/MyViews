package com.cc.myviews.yahooflash;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.cc.myviews.R;

/**
 * Created by silvercc on 15/10/13.
 */
public class SunMoonView extends View {
    private static final String TAG = "SunMoonView";
    private final Paint mPaint;
    private final int xCoord;
    private final float yCoord;
    private final float radius;
    private final Bitmap mSunBitmap;
    private final Bitmap mMoonBitmap;
    private final Matrix mMatrix;
    private final float[] mPos;
    private final int mSunXOffset;
    private final int mSunYOffset;
    private final int mMoonXOffset;
    private final int mMoonYOffset;
    private Path mPath;
    private float mPathLength;
    private float mDistance;
    private PathMeasure mPathMeasure;
    private Path.Direction mCurrentDirection;

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
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        PathEffect effect = new DashPathEffect(new float[]{1f, 2f, 4f, 8f}, 1f);
        mPaint.setPathEffect(effect);

        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        mSunBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.sun);
        mSunXOffset = mSunBitmap.getWidth() / 2;
        mSunYOffset = mSunBitmap.getHeight() / 2;

        mMoonBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.moon_new);
        mMoonXOffset = mMoonBitmap.getWidth() / 2;
        mMoonYOffset = mMoonBitmap.getHeight() / 2;

        mCurrentDirection = Path.Direction.CW;

        xCoord = size.x / 2;
        yCoord = (float) (size.y * 0.28);
        radius = (float) (7 * 1.0 / 18) * size.x;

        initPath(mCurrentDirection);
        mMatrix = new Matrix();
        mPos = new float[2];
    }

    private void initPath(Path.Direction dir) {
        mPath = new Path();
        RectF rectF = new RectF(xCoord - radius, yCoord - radius, xCoord + radius, yCoord + radius);

        if (dir == Path.Direction.CW) {
            mPath.addArc(rectF, 50, 359);
        } else {
            mPath.addArc(rectF, 50, -359);
        }
        mPath.close();
        mPathMeasure = new PathMeasure(mPath, false);
        mPathLength = mPathMeasure.getLength();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
        drawSun(canvas);
        drawMoon(canvas);
    }

    private void drawMoon(Canvas canvas) {
        if (mDistance < mPathLength) {
            mMatrix.reset();
            if (mDistance + mPathLength / 2 <= mPathLength) {
                mPathMeasure.getPosTan(mDistance + mPathLength / 2, mPos, null);
            } else {
                mPathMeasure.getPosTan(mDistance - mPathLength / 2, mPos, null);
            }
            mMatrix.postTranslate(mPos[0] - mMoonXOffset, mPos[1] - mMoonYOffset);
            canvas.drawBitmap(mMoonBitmap, mMatrix, null);
        }
    }

    private void drawSun(Canvas canvas) {
        if (mDistance < mPathLength) {
            mMatrix.reset();
            mPathMeasure.getPosTan(mDistance, mPos, null);
            mMatrix.postTranslate(mPos[0] - mSunXOffset, mPos[1] - mSunXOffset);
            canvas.drawBitmap(mSunBitmap, mMatrix, null);
        }
    }

    public void doClockWiseAnimation(float position) {
        Log.i(TAG, "position=" + position);
        if (mCurrentDirection == Path.Direction.CCW) {
            mCurrentDirection = Path.Direction.CW;
            initPath(mCurrentDirection);
            invalidate();
        }
        mDistance = mPathLength / 2 * (Math.abs(position));
        invalidate();
    }

    public void doAnitClockWiseAnimation(float position) {
        Log.i(TAG, "position=" + position);
        if (mCurrentDirection == Path.Direction.CW) {
            mCurrentDirection = Path.Direction.CCW;
            initPath(mCurrentDirection);
            invalidate();
        }
        if (Math.abs(position) <= 1) {
            mDistance = mPathLength / 2 * (Math.abs(1 + position));
        }
        invalidate();
    }
}
