package com.cc.myviews.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by silvercc on 15/10/25.
 */
public class TestLayout extends RelativeLayout {

    private long lastDown = -1;
    private final static long DOUBLE_TIME = 200;
    private boolean isIntercept;

    public TestLayout(Context context) {
        this(context, null, 0);
    }

    public TestLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int actionMasked = ev.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            long nowDown = System.currentTimeMillis();
            if (nowDown - lastDown < DOUBLE_TIME) {
                isIntercept = true;
                return true;
            } else {
                isIntercept = false;
                lastDown = nowDown;
            }
        }
        isIntercept = false;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_DOWN && isIntercept) {
            ((TpoListenLayout) getParent()).smoothSlideTo(1);
            return true;
        }
        return false;
    }
}
