package com.cc.myviews.galleryview;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.myviews.BaseFragment;
import com.cc.myviews.R;

/**
 * Created by silvercc on 15/9/24.
 */
public class galleryFragment extends BaseFragment {
    private TextView tv_gallery_first, tv_gallery_second, tv_gallery_third;
    private ImageView iv_gallery_left, iv_gallery_right, iv_gallery_up;
    private static Handler mHandler;
    private ImageSlidingGallery isg;

    @Override
    protected View loadView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_gallery,null);
    }

    @Override
    protected void findView(View parentView) {
        tv_gallery_first = (TextView)parentView.findViewById(R.id.tv_gallery_first);
        tv_gallery_second = (TextView)parentView.findViewById(R.id.tv_gallery_second);
        tv_gallery_third = (TextView)parentView.findViewById(R.id.tv_gallery_third);

        iv_gallery_left = (ImageView)parentView.findViewById(R.id.iv_gallery_left);
        iv_gallery_right = (ImageView)parentView.findViewById(R.id.iv_gallery_right);
        iv_gallery_up = (ImageView)parentView.findViewById(R.id.iv_gallery_up);

        isg = (ImageSlidingGallery)parentView.findViewById(R.id.isg);
    }

    @Override
    protected void init() {
        Animation anim_left = AnimationUtils.loadAnimation(mParentContext, R.anim.anim_left);
        iv_gallery_left.startAnimation(anim_left);
        Animation anim_right = AnimationUtils.loadAnimation(mParentContext, R.anim.anim_right);
        iv_gallery_right.startAnimation(anim_right);
        Keyframe kf0 = Keyframe.ofFloat(0, 0);
        Keyframe kf1 = Keyframe.ofFloat(0.6f, -70);
        Keyframe kf2 = Keyframe.ofFloat(1.0f, -70);
        PropertyValuesHolder pvhTranslateY = PropertyValuesHolder.ofKeyframe(View.TRANSLATION_Y, kf0, kf1, kf2);
        Keyframe kf3 = Keyframe.ofFloat(0, 0);
        Keyframe kf4 = Keyframe.ofFloat(0.4f, 1);
        Keyframe kf5 = Keyframe.ofFloat(0.6f, 0);
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofKeyframe(View.ALPHA, kf3, kf4, kf5);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(iv_gallery_up, pvhTranslateY, pvhAlpha);
        animator.setDuration(1200);
        animator.setRepeatMode(Animation.RESTART);
        animator.setRepeatCount(Animation.INFINITE);
        animator.start();
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                isg.initAnim();
            }
        };
        delayPostAnimation();
    }

    private void delayPostAnimation(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(1);
            }
        }, 1200);
    }
}
