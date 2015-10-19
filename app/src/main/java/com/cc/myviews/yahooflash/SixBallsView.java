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
    //每一个小球的半径
    private final int mBallXOffser, mBallYOffser;
    //圆心x坐标
    private final int mCenterX;
    //圆心y坐标
    private final float mCenterY;
    //圆path半径
    private final float mRadius;
    //圆path
    private final Path mPath;
    //圆PathMeasure
    private final PathMeasure mPathMeasure;
    //路径的总长度，这里就是圆周长
    private final float mPathLength;
    private final float[] pos;
    private final Matrix matrix;
    //小圆球缩放时超过半径的x平移量
    private final float mTempDistanceX;
    //小圆球缩放时超过半径的y平移量
    private final float mTempDistanceY;
    private final Context mContext;
    private FloatWrapper[] originBallsPositions;
    private float[] posOfBall1, posOfBall2, posOfBall3, posOfBall4, posOfBall5, posOfBall6;
    private FloatWrapper[] ballsSteps;
    private boolean ballRotate;
    private boolean mIsFirstDraw;
    private float mPosition;
    //点击进入app的button时会置为true
    private boolean mGoNext;
    //小球缩放的path数组
    private Path mPathLinesArr[] = new Path[6];
    //小球缩放的PathMeasure数组
    private PathMeasure mLinePmArr[] = new PathMeasure[6];
    //小球缩放的Path长度数组
    private FloatWrapper mLinesPlArr[] = new FloatWrapper[6];
    //小球缩放时的位置（相对pathlength）数组
    private FloatWrapper mLinesDistArr[] = new FloatWrapper[6];
    //小球缩放的缩放比数组
    private FloatWrapper mLinesScArr[] = new FloatWrapper[6];
    //小球缩放的缩放的矩阵数组
    private Matrix mLineMxArr[] = new Matrix[6];
    private boolean mIsInitialLinePath;

    public SixBallsView(Context context) {
        this(context, null, 0);
    }

    public SixBallsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SixBallsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
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

        //获取屏幕尺寸，y方向不包括物理按键
        Display defaultDisplay = ((YahooFlashActivity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        defaultDisplay.getSize(size);

        mCenterX = size.x / 2;
        mCenterY = (float) (size.y / 3.5);
        mRadius = (float) (size.x / 4.5);

        mPath = new Path();
        RectF rectF = new RectF(mCenterX - mRadius, mCenterY - mRadius, mCenterX + mRadius, mCenterY + mRadius);
        //路径为一个360的圆弧
        mPath.addArc(rectF, 0, 360);
        mPath.close();

        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mPath, false);//第二个参数为false时不用Path强制关闭
        mPathLength = mPathMeasure.getLength();

        mTempDistanceX = (float) (1.0 / 14) * size.x;
        mTempDistanceY = (float) (1.0 / 14) * size.x;

        pos = new float[2];

        matrix = new Matrix();

        initOriginPosOfBall();
        initOriginBallsPosition();
        initLinePaths();
    }

    /**
     * 初始化小球缩放的变量
     */
    private void initLinePaths() {
        for (int i = 0; i < 6; i++) {

            mPathLinesArr[i] = new Path();
            //小球将要移动到的坐标，为圆心
            mPathLinesArr[i].moveTo(mCenterX, mCenterY);

            mLinePmArr[i] = new PathMeasure();
            mLineMxArr[i] = new Matrix();
            mLinesPlArr[i] = new FloatWrapper(0f);
            mLinesDistArr[i] = new FloatWrapper(mRadius);
            mLinesScArr[i] = new FloatWrapper(0f);
        }

    }

    /**
     * 初始化纪录所有小球的坐标数组
     */
    private void initOriginPosOfBall() {
        posOfBall1 = new float[2];
        posOfBall2 = new float[2];
        posOfBall3 = new float[2];
        posOfBall4 = new float[2];
        posOfBall5 = new float[2];
        posOfBall6 = new float[2];
    }

    /**
     * 初始化所有小球位于路径的位置，及初始化每次重绘移动距离的数组
     */
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
        if (ballRotate || !mIsFirstDraw) {//当允许旋转时或是第一次加载此view时
            posOfBall1 = drawBall(canvas, originBallsPositions[0], ballsSteps[0], mBitmap1);
            posOfBall2 = drawBall(canvas, originBallsPositions[1], ballsSteps[1], mBitmap2);
            posOfBall3 = drawBall(canvas, originBallsPositions[2], ballsSteps[2], mBitmap3);
            posOfBall4 = drawBall(canvas, originBallsPositions[3], ballsSteps[3], mBitmap4);
            posOfBall5 = drawBall(canvas, originBallsPositions[4], ballsSteps[4], mBitmap5);
            posOfBall6 = drawBall(canvas, originBallsPositions[5], ballsSteps[5], mBitmap6);
        }
        if (mGoNext) {//点击进入app首页button时
            if (!mIsInitialLinePath) {
                initBallsOutPath(mPathLinesArr[0], posOfBall1, mLinePmArr[0], mLinesPlArr[0]);
                initBallsOutPath(mPathLinesArr[1], posOfBall2, mLinePmArr[1], mLinesPlArr[1]);
                initBallsOutPath(mPathLinesArr[2], posOfBall3, mLinePmArr[2], mLinesPlArr[2]);
                initBallsOutPath(mPathLinesArr[3], posOfBall4, mLinePmArr[3], mLinesPlArr[3]);
                initBallsOutPath(mPathLinesArr[4], posOfBall5, mLinePmArr[4], mLinesPlArr[4]);
                initBallsOutPath(mPathLinesArr[5], posOfBall6, mLinePmArr[5], mLinesPlArr[5]);
                mIsInitialLinePath = false;
            }
            boolean isBall1Finished = animateBallsOutAndIn(mLinesDistArr[0], mLinesPlArr[0], canvas, mLineMxArr[0], mLinePmArr[0], mBitmap1, mLinesScArr[0]);
            boolean isBall2Finished = animateBallsOutAndIn(mLinesDistArr[1], mLinesPlArr[1], canvas, mLineMxArr[1], mLinePmArr[1], mBitmap2, mLinesScArr[1]);
            boolean isBall3Finished = animateBallsOutAndIn(mLinesDistArr[2], mLinesPlArr[2], canvas, mLineMxArr[2], mLinePmArr[2], mBitmap3, mLinesScArr[2]);
            boolean isBall4Finished = animateBallsOutAndIn(mLinesDistArr[3], mLinesPlArr[3], canvas, mLineMxArr[3], mLinePmArr[3], mBitmap4, mLinesScArr[3]);
            boolean isBall5Finished = animateBallsOutAndIn(mLinesDistArr[4], mLinesPlArr[4], canvas, mLineMxArr[4], mLinePmArr[4], mBitmap5, mLinesScArr[4]);
            boolean isBall6Finished = animateBallsOutAndIn(mLinesDistArr[5], mLinesPlArr[5], canvas, mLineMxArr[5], mLinePmArr[5], mBitmap6, mLinesScArr[5]);
            if (!isBall1Finished && !isBall2Finished && !isBall3Finished && !isBall4Finished && !isBall5Finished && !isBall6Finished) {
                ((YahooFlashActivity) mContext).finish();
                return;
            }
            invalidate();
        }
        if (!ballRotate && mIsFirstDraw && !mGoNext) {//禁止小球转动，同时不是第一次绘制，同时没有点击进入app首页button时
            //小球停止转动时，以及小球随viewpager滑动时移动的距离，这里mPosition的范围是[0,1]
            float xOffset1 = mPosition * 100;
            float xOffset2 = mPosition * 400;
            float xOffset3 = mPosition * 100;
            float xOffset4 = mPosition * 800;
            float xOffset5 = mPosition * 200;
            float xOffset6 = mPosition * 1100;
            canvas.drawBitmap(mBitmap1, posOfBall1[0] + xOffset1 - mBallXOffser, posOfBall1[1] - mBallYOffser, null);
            canvas.drawBitmap(mBitmap2, posOfBall2[0] + xOffset2 - mBallXOffser, posOfBall2[1] - mBallYOffser, null);
            canvas.drawBitmap(mBitmap3, posOfBall3[0] + xOffset3 - mBallXOffser, posOfBall3[1] - mBallYOffser, null);
            canvas.drawBitmap(mBitmap4, posOfBall4[0] + xOffset4 - mBallXOffser, posOfBall4[1] - mBallYOffser, null);
            canvas.drawBitmap(mBitmap5, posOfBall5[0] + xOffset5 - mBallXOffser, posOfBall5[1] - mBallYOffser, null);
            canvas.drawBitmap(mBitmap6, posOfBall6[0] + xOffset6 - mBallXOffser, posOfBall6[1] - mBallYOffser, null);
        }
        if (ballRotate) {
            invalidate();
        }
        mIsFirstDraw = true;
    }

    /**
     * 小球缩放
     *
     * @param distance
     * @param pathLength
     * @param canvas
     * @param bitmapMatrix
     * @param measure
     * @param bitmap
     * @param scale
     * @return 缩放是否完成，即是否moveTo圆心位置；
     */
    private boolean animateBallsOutAndIn(FloatWrapper distance, FloatWrapper pathLength, Canvas canvas, Matrix bitmapMatrix, PathMeasure measure, Bitmap bitmap, FloatWrapper scale) {
        boolean mUpperBoundHit = false;
        float[] pos = new float[2];
        if (distance.floatValue >= pathLength.floatValue / 2) {
            mUpperBoundHit = true;
        }
        if (mUpperBoundHit) {
            int newWidth = (int) (bitmap.getWidth() - 8 * scale.floatValue);
            int newHeight = (int) (bitmap.getHeight() - 8 * scale.floatValue);
            if (newWidth >= 5 && newHeight >= 5) {
                scale.floatValue += 1;
            }
            bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        }
        if (distance.floatValue < pathLength.floatValue) {
            measure.getPosTan(distance.floatValue, pos, null);
            bitmapMatrix.reset();
            bitmapMatrix.postTranslate(pos[0] - bitmap.getWidth() / 2, pos[1] - bitmap.getHeight() / 2);
            canvas.drawBitmap(bitmap, bitmapMatrix, null);
            if (mUpperBoundHit) {
                //快速缩小
                distance.floatValue += 20;
            } else {
                //慢慢扩大
                distance.floatValue += 2;
            }
            return true;
        } else {
            //当distance == pathLength时，说明已经走完全路径
            return false;
        }
    }

    /**
     * 初始化小球缩放的路径长度，以及移动的初始位置
     *
     * @param path
     * @param pos
     * @param measure
     * @param wrapper
     */
    private void initBallsOutPath(Path path, float[] pos, PathMeasure measure, FloatWrapper wrapper) {
        float x = 0, y = 0;
        double len = Math.hypot(pos[0] - mCenterX, pos[1] - mCenterY);
        double dx = (pos[0] - mCenterX) / len;
        double dy = (pos[1] - mCenterY) / len;

        x = (float) (pos[0] + mTempDistanceX * dx);
        y = (float) (pos[1] + mTempDistanceY * dy);
        path.lineTo(x, y);
        path.close();
        measure.setPath(path, false);
        wrapper.floatValue = measure.getLength();
    }

    /**
     * 绘制小球转动
     *
     * @param canvas
     * @param distance 位于mPathLength上的位置
     * @param step     每次绘制移动的距离
     * @param bitmap
     * @return 每次转动后小球的位置（x,y）
     */
    private float[] drawBall(Canvas canvas, FloatWrapper distance, FloatWrapper step, Bitmap bitmap) {
        float newDistance = distance.floatValue + step.floatValue;
        float[] ballPos = new float[2];
        if (newDistance < mPathLength) {
            //当小球当前所处的位置（相对路径长度来说）小于路径长度时，小球转动
            mPathMeasure.getPosTan(newDistance, pos, null);

            matrix.reset();
            matrix.postTranslate(pos[0] - mBallXOffser, pos[1] - mBallYOffser);
            canvas.drawBitmap(bitmap, matrix, null);
            step.floatValue += 1.2f;
        } else {
            //当小球当前所处的位置（相对路径长度来说）等于路径长度时，此时小球会回到原点，
            //我们只需将当前位置重置为0，小球就会重新走上面的判断条件，继续转动
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

    /**
     * 设置是否允许小球转动
     *
     * @param ballRotate
     */
    public void setBallRotate(boolean ballRotate) {
        this.ballRotate = ballRotate;
        invalidate();
    }

    /**
     * 小球右移
     *
     * @param position
     */
    public void moveToRight(float position) {
        mPosition = position;
        invalidate();
    }

    /**
     * 点击进入app首页button时调用
     *
     * @param goNext
     */
    public void setIsGoNext(boolean goNext) {
        mGoNext = goNext;
        ballRotate = false;
        invalidate();
    }
}
