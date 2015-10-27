package com.cc.myviews.galleryview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cc.myviews.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by silvercc on 15/9/24.
 */
public class ImageSlidingGallery extends FrameLayout {

    private static final String TAG = "ImageSlidingGallery";
    private static final int XVEL_THRESHOLD = 100;
    private static final int CHILDVIEWROTATEDEGREE = 5;
    private static final int ANIM_INIT = 1;
    private static final int ANIM_ROTATE = 2;
    private static Handler mUiHandler;
    private Context mContext;
    private ViewDragHelper mViewDragHelper;
    private List<AntiAliasImageView> mViewList;
    private AntiAliasImageView mLastChildView;
    private int screenWidth;
    private int mViewLeftX;
    private boolean isRotating;

    public ImageSlidingGallery(Context context) {
        this(context, null, 0);
    }

    public ImageSlidingGallery(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageSlidingGallery(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mViewList = new ArrayList<AntiAliasImageView>();

        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();

        mUiHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int cycleNum = msg.getData().getInt("cyclyNum");
                switch (msg.what) {
                    case ANIM_INIT:
                        processAnimInit(cycleNum);
                        break;
                    case ANIM_ROTATE:
                        processAnimRotate(cycleNum);
                        break;
                    default:
                        break;
                }
            }
        };

        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new MyViewDragCallBack());
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    protected void onFinishInflate() {//xml里面所有的子view加载完成后调用
        super.onFinishInflate();
        initialViewList();
    }

    private void initialViewList() {
        mViewList.clear();
        int childNum = getChildCount();
        for (int i = 0; i < childNum; i++) {
            AntiAliasImageView childView = (AntiAliasImageView) getChildAt(i);
            childView.setRotation((childNum - 1 - i) * CHILDVIEWROTATEDEGREE);
            mViewList.add(childView);
        }
        mLastChildView = mViewList.get(childNum - 1);
    }

    public void initAnim() {
        new Mythread(ANIM_INIT, 100).start();
    }

    class Mythread extends Thread {
        private int mSleepTime;
        private int mType;

        public Mythread(int type, int sleepTime) {
            mType = type;
            mSleepTime = sleepTime;
        }

        @Override
        public void run() {
            super.run();
            for (int i = 0; i < mViewList.size(); i++) {
                Message msg = Message.obtain(mUiHandler);
                msg.what = mType;
                Bundle data = new Bundle();
                data.putInt("cyclyNum", i);
                msg.setData(data);
                msg.sendToTarget();
                SystemClock.sleep(mSleepTime);
            }
        }
    }

    /**
     * 初始化时子view的进入动画
     * @param cycleNum
     */
    private void processAnimInit(int cycleNum) {
        AntiAliasImageView view = mViewList.get(cycleNum);
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_init_in);
        view.setVisibility(View.VISIBLE);
        view.startAnimation(animation);
    }

    /**
     * 子view旋转的动画
     * @param cycleNum
     */
    private void processAnimRotate(int cycleNum) {
        if (cycleNum == mViewList.size() - 1) {
            isRotating = false;
            return;
        }
        AntiAliasImageView view = mViewList.get(mViewList.size() - 1 - cycleNum);
        float rotationDegree = view.getRotation();
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", rotationDegree, rotationDegree - CHILDVIEWROTATEDEGREE);
        animator.setDuration(200);
        animator.start();
    }

    class MyViewDragCallBack extends ViewDragHelper.Callback {

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView.getRight() <= screenWidth / 3 || left >= screenWidth * 2 / 3) {
                if (!isRotating) {
                    isRotating = true;
                    mViewDragHelper.abort();
                    orderViewList();
                }
            } else if (changedView.getRotation() == 0) {
                processGradualAlpha(changedView, left);
            }
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            if (child == mLastChildView) {
                return true;
            }
            return false;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {//横向拖动的范围
            return screenWidth / 2;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int finalLeft = mViewLeftX;
            if (xvel > XVEL_THRESHOLD) {
                finalLeft = screenWidth;
            } else if (xvel < -XVEL_THRESHOLD) {
                finalLeft = -mLastChildView.getWidth();
            } else {
                if (mLastChildView.getLeft() > screenWidth / 2) {
                    finalLeft = screenWidth;
                } else if (mLastChildView.getRight() < screenWidth / 2) {
                    finalLeft = -mLastChildView.getWidth();
                }
            }
            if (mViewDragHelper.smoothSlideViewTo(mLastChildView, finalLeft, mLastChildView.getTop())) {
                ViewCompat.postInvalidateOnAnimation(ImageSlidingGallery.this);
            }
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return child.getTop();
        }
    }

    /**
     * 拖动中改变控件的透明度
     *
     * @param changedView
     * @param left
     */
    private void processGradualAlpha(View changedView, int left) {
        int halfScreenWidth = screenWidth / 2;
        float alpha = 1f;
        if (left > halfScreenWidth) {
            alpha = 1 - ((float) left - halfScreenWidth) / halfScreenWidth;
        } else if (changedView.getRight() < halfScreenWidth) {
            alpha = 1 - ((float) halfScreenWidth - changedView.getRight()) / halfScreenWidth;
        }
        changedView.setAlpha(alpha);
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mViewLeftX = mLastChildView.getLeft();
    }

    /**
     * 对子控件重新排序
     */
    private void orderViewList() {
        int num = mViewList.size();
        //将除了位于最表层的图层外逐一移动到前段
        for (int i = 0; i < num - 1; i++) {
            AntiAliasImageView tempView = mViewList.get(i);
            tempView.bringToFront();
        }
        //初始化最后面的view旋转度和透明度
        mLastChildView.setAlpha(1f);
        mLastChildView.setRotation((num - 1) * CHILDVIEWROTATEDEGREE);
        mViewList.remove(num - 1);
        mViewList.add(0, mLastChildView);
        mLastChildView = mViewList.get(num - 1);
        new Mythread(ANIM_ROTATE, 100).start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {//必须实现
        //将事件传递给draghelper
        mViewDragHelper.processTouchEvent(event);
        //返回true标示消费此事件
        return true;
    }

    //点击左右点击区域切换图片
    public void clickToFade(int type) {
        int finalLeft = mViewLeftX;
        if (type == 0) {
            finalLeft = -mLastChildView.getWidth();
        } else if (type == 1) {
            finalLeft = screenWidth;
        } else {
        }
        if (mViewDragHelper.smoothSlideViewTo(mLastChildView, finalLeft, mLastChildView.getTop())) {
            //刷新此控件，跟invalidate方法作用一样，区别在于会在此控件执行下一步动画之前刷新该控件
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

}
