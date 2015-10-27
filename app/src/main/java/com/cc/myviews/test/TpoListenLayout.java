package com.cc.myviews.test;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.cc.myviews.R;

/**
 * Created by silvercc on 15/10/25.
 */
public class TpoListenLayout extends RelativeLayout {
    private static final String TAG = "TpoListenLayout";
    private final ViewDragHelper mHelper;
    private RelativeLayout image_container, pb_container;
    private TestLayout content_container;
    private int mTop;
    private int mYRange;
    private float mOffset;
    private OnPositionChangeListner mListener;

    public TpoListenLayout(Context context) {
        this(context, null, 0);
    }

    public TpoListenLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TpoListenLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHelper = ViewDragHelper.create(this, 1f, new MyCallBack());
    }

    public void smoothSlideTo(float offset) {
        int finalTop = (int) offset * mYRange;
        if (mHelper.smoothSlideViewTo(pb_container, 0, finalTop)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        image_container = (RelativeLayout) findViewById(R.id.image_container);
        pb_container = (RelativeLayout) findViewById(R.id.pb_container);
        content_container = (TestLayout) findViewById(R.id.content_container);
        image_container.measure(0, 0);
        mTop = image_container.getMeasuredHeight();
    }

    class MyCallBack extends ViewDragHelper.Callback {

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            mTop = top;
            mOffset = (float) mTop / mYRange;
            image_container.setPivotX(0);
            image_container.setPivotY(0);
            image_container.setScaleX(mOffset);
            image_container.setScaleY(mOffset);
            image_container.setAlpha(mOffset);
            mListener.positionChange(mOffset);
            requestLayout();
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            if (child == pb_container) {
                return true;
            }
            return false;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mYRange;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return Math.min(Math.max(0, top), image_container.getMeasuredHeight());
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return child.getLeft();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mYRange = image_container.getHeight();
        pb_container.layout(0, mTop, r, pb_container.getHeight() + mTop);
        content_container.layout(0, mTop + pb_container.getHeight(), r, b);
    }

    public void setOnPositionChangeListner(OnPositionChangeListner listener){
        mListener = listener;
    }
}
