package com.cc.myviews.youtubeDragView;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cc.myviews.R;

/**
 * Created by silvercc on 15/10/9.
 */
public class YoutubeDragView extends FrameLayout {
    private static final String TAG = "YoutubeDragView";
    private final ViewDragHelper mViewDragHelper;
    private TextView mViewHeader;
    private TextView mViewDesc;
    private int mDragRange;
    private int mTop;
    private float offset;
    private float mInitialX;
    private float mInitialY;

    public YoutubeDragView(Context context) {
        this(context, null, 0);
    }

    public YoutubeDragView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YoutubeDragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewDragHelper = ViewDragHelper.create(this, 1f, new MyViewDragHelperCallback());
    }

    /**
     * 移动首部控件
     * @param f {offset}
     */
    public void smoothSlideto(float f) {
        float finalTop = mDragRange * f;
        if (mViewDragHelper.smoothSlideViewTo(mViewHeader, mViewHeader.getLeft(), (int) finalTop)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mViewHeader = (TextView) findViewById(R.id.viewHeader);
        mViewDesc = (TextView) findViewById(R.id.viewDesc);
    }

    /**
     * 全屏展开
     */
    public void maximize() {
        smoothSlideto(0);
    }

    class MyViewDragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            mTop = top;
            Log.i(TAG, "top=" + top);
            //移动的比例
            offset = (float) top / mDragRange;
            //设置mViewHeader的缩放轴
            mViewHeader.setPivotX(mViewHeader.getMeasuredWidth());
            mViewHeader.setPivotY(mViewHeader.getMeasuredHeight());
            //设置mViewHeader的缩放比
            mViewHeader.setScaleX(1 - offset / 2);
            mViewHeader.setScaleY(1 - offset / 2);
            //设置mViewDesc的透明度
            mViewDesc.setAlpha(1 - offset);
            //重新刷新控件的Layout，会调用onLayout方法
            requestLayout();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int top = 0;
            if (yvel > 0 || (yvel == 0 && offset > 0.5f)) {//当y方向速度大于0或者y方向停止移动且y方向移动过下半屏时
                top += mDragRange;
            }
            mViewDragHelper.settleCapturedViewAt(releasedChild.getLeft(), top);
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            if (child == mViewHeader) {
                return true;
            }
            return false;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            /**限制y方向的移动位置。如果返回值为0，则无法在纵向拖动，重写此方法目的在于限制拖动的范围
             * 当被拖动控件的top为0时，表明此控件已经全屏展开，当bottomBond为0时，表示控件已缩到mViewHeader.getHeight()大小。
             * */
            int bottomBond = getHeight() - mViewHeader.getHeight() - mViewHeader.getPaddingBottom();
            //Math.max(top, 0)此为限制top永远>=0
            return Math.min(Math.max(top, 0), bottomBond);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            //控件拖动范围
            return mDragRange;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int actionMasked = ev.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            float initialX = ev.getX();
            float initialY = ev.getY();
            //判断点击的位置是否在mViewHeader此控件的范围内
            boolean isViewUnder = mViewDragHelper.isViewUnder(mViewHeader, (int) initialX, (int) initialY);
            if (isViewUnder) {
                //如果在mViewHeader此控件的范围内，将事件传递给mViewDragHelper，并返回true拦截此事件
                mViewDragHelper.processTouchEvent(ev);
                return true;
            }
        }
        //如果点击事件不满足按下，或者点击位置不在mViewHeader此控件的范围内，将事件放弃，super.onInterceptTouchEvent(ev)默认是false
        mViewDragHelper.cancel();
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        boolean isViewUnder = mViewDragHelper.isViewUnder(mViewHeader, (int) x, (int) y);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mInitialX = x;
                mInitialY = y;
                break;
            case MotionEvent.ACTION_UP:
                float dx = x - mInitialX;
                float dy = y - mInitialY;
                //getTouchSlop()此API为获取ViewDragHelper最小有效拖拽范围，此偏移量大小由ViewDragHelper.creat中的参数sensitivity决定，1f的slop为16
                int slop = mViewDragHelper.getTouchSlop();
                //当按下抬起的偏移量范围小于ViewDragHelper最小有效拖拽范围，可视其为点击
                if (dx * dx + dy * dy < slop * slop && isViewUnder) {
                    if (offset == 0) {
                        smoothSlideto(1);
                    } else {
                        smoothSlideto(0);
                    }
                }
                break;
        }
        return isViewUnder;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //上下的有效拖动范围是控件高度减去头控件高度
        mDragRange = getHeight() - mViewHeader.getHeight();
        //通过mTop这个变量和不断变化的mViewHeader的高度重新设置mViewHeader及mViewDesc的位置
        mViewHeader.layout(0, mTop, right, mTop + mViewHeader.getMeasuredHeight());
        mViewDesc.layout(0, mTop + mViewHeader.getMeasuredHeight(), right, mTop + bottom);
    }
}