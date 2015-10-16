package com.cc.myviews.yahooflash;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.cc.myviews.R;
import com.cc.myviews.Utils;
import com.cc.myviews.bean.FloatWrapper;

/**
 * Created by silvercc on 15/10/15.
 */
public class SixBallsView extends View {
    private static final String TAG = "SixBallsView";
    private final Paint mPaint;
    private final Bitmap mBitmap1, mBitmap2, mBitmap3, mBitmap4, mBitmap5, mBitmap6;
    private final int mBallXOffser, mBallYOffser;
    private final int mCenterX;
    private final float mCenterY;
    private final float mRadius;
    private final Path mPath;
    private final PathMeasure mPathMeasure;
    private final float mPathLength;
    private final float[] pos;
    private final Matrix matrix;
    private FloatWrapper[] originBallsPositions;
    private float[] posOfBall1, posOfBall2, posOfBall3, posOfBall4, posOfBall5, posOfBall6;
    private FloatWrapper[] ballsSteps;
    private boolean ballRotate;
    private boolean mIsFirstDraw;

    public SixBallsView(Context context) {
        this(context, null, 0);
    }

    public SixBallsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SixBallsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.TRANSPARENT);
        mPaint.setStyle(Paint.Style.STROKE);

        mBitmap1 = Utils.drawCircleBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.dark_yellow_square));
        mBitmap2 = Utils.drawCircleBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.dark_green_square));
        mBitmap3 = Utils.drawCircleBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.yellow_square));
        mBitmap4 = Utils.drawCircleBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.blue_square));
        mBitmap5 = Utils.drawCircleBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.light_green_square));
        mBitmap6 = Utils.drawCircleBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.pink_square));

        mBallXOffser = mBitmap1.getWidth() / 2;
        mBallYOffser = mBitmap1.getHeight() / 2;

        Display defaultDisplay = ((YahooFlashActivity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        defaultDisplay.getSize(size);

        mCenterX = size.x / 2;
        mCenterY = (float) (size.y / 3.5);
        mRadius = (float) (size.x / 4.5);

        mPath = new Path();
        RectF rectF = new RectF(mCenterX - mRadius, mCenterY - mRadius, mCenterX + mRadius, mCenterY + mRadius);
        mPath.addArc(rectF, 0, 360);
        mPath.close();

        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mPath, false);
        mPathLength = mPathMeasure.getLength();

        pos = new float[2];

        matrix = new Matrix();

        initOriginPosOfBall();
        initOriginBallsPosition();
    }

    private void initOriginPosOfBall() {
        posOfBall1 = new float[2];
        posOfBall2 = new float[2];
        posOfBall3 = new float[2];
        posOfBall4 = new float[2];
        posOfBall5 = new float[2];
        posOfBall6 = new float[2];
    }

    private void initOriginBallsPosition() {
        originBallsPositions = new FloatWrapper[6];
        ballsSteps = new FloatWrapper[6];
        for (int i = 0; i < originBallsPositions.length; i++) {
            originBallsPositions[i] = new FloatWrapper(i * (mPathLength / 6));
            ballsSteps[i] = new FloatWrapper(0f);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
        if (ballRotate || !mIsFirstDraw) {
            posOfBall1 = drawBall(canvas, originBallsPositions[0], ballsSteps[0], mBitmap1);
            posOfBall2 = drawBall(canvas, originBallsPositions[1], ballsSteps[1], mBitmap2);
            posOfBall3 = drawBall(canvas, originBallsPositions[2], ballsSteps[2], mBitmap3);
            posOfBall4 = drawBall(canvas, originBallsPositions[3], ballsSteps[3], mBitmap4);
            posOfBall5 = drawBall(canvas, originBallsPositions[4], ballsSteps[4], mBitmap5);
            posOfBall6 = drawBall(canvas, originBallsPositions[5], ballsSteps[5], mBitmap6);
        }
        if (!ballRotate && mIsFirstDraw) {
            canvas.drawBitmap(mBitmap1, posOfBall1[0] - mBallXOffser, posOfBall1[1] - mBallYOffser, null);
            canvas.drawBitmap(mBitmap2, posOfBall2[0] - mBallXOffser, posOfBall2[1] - mBallYOffser, null);
            canvas.drawBitmap(mBitmap3, posOfBall3[0] - mBallXOffser, posOfBall3[1] - mBallYOffser, null);
            canvas.drawBitmap(mBitmap4, posOfBall4[0] - mBallXOffser, posOfBall4[1] - mBallYOffser, null);
            canvas.drawBitmap(mBitmap5, posOfBall5[0] - mBallXOffser, posOfBall5[1] - mBallYOffser, null);
            canvas.drawBitmap(mBitmap6, posOfBall6[0] - mBallXOffser, posOfBall6[1] - mBallYOffser, null);
        }
        if (ballRotate) {
            invalidate();
        }
        mIsFirstDraw = true;
    }

    private float[] drawBall(Canvas canvas, FloatWrapper distance, FloatWrapper step, Bitmap bitmap) {
        float newDistance = distance.floatValue + step.floatValue;
        float[] ballPos = new float[2];
        if (newDistance < mPathLength) {
            mPathMeasure.getPosTan(newDistance, pos, null);

            matrix.reset();
            matrix.postTranslate(pos[0] - mBallXOffser, pos[1] - mBallYOffser);
            canvas.drawBitmap(bitmap, matrix, null);
            step.floatValue += 1.2f;
        } else {
            distance.floatValue = 0f;
            step.floatValue = 0f;

            mPathMeasure.getPosTan(0, pos, null);
            matrix.reset();
            matrix.postTranslate(pos[0] - mBallXOffser, pos[1] - mBallYOffser);
            canvas.drawBitmap(bitmap, matrix, null);
        }
        ballPos[0] = pos[0];
        ballPos[1] = pos[1];
        return ballPos;
    }

    public void setBallRotate(boolean ballRotate) {
        this.ballRotate = ballRotate;
        invalidate();
    }
}
