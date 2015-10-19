package com.cc.myviews.yahooflash;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.RelativeLayout;

import com.cc.myviews.BaseFragment;
import com.cc.myviews.R;

/**
 * Created by silvercc on 15/10/12.
 */
public class YahooFirstFragment extends BaseFragment {

    private static final String TAG = "YahooFirstFragment";
    private RelativeLayout rl_container;
    private LayoutAnimationController mController;
    private int mChildCount;
    private View parentView;

    @Override
    protected View loadView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.layout_yahoo_first_page, null);
    }

    @Override
    protected void findView(View parentView) {
        Log.i(TAG, "parentView=" + parentView);
        this.parentView = parentView;
        rl_container = (RelativeLayout) parentView.findViewById(R.id.rl_container);
    }

    @Override
    protected void init() {
        mChildCount = rl_container.getChildCount();
        mController = AnimationUtils.loadLayoutAnimation(mParentContext, R.anim.layout_animation);
        rl_container.setLayoutAnimation(mController);
        rl_container.startLayoutAnimation();
        for (int i = 0; i < mChildCount; i++) {
            View childAt = rl_container.getChildAt(i);
            childAt.setVisibility(View.VISIBLE);
        }
        parentView.post(new Runnable() {
            @Override
            public void run() {
                ((YahooFlashActivity) mParentContext).setTransformer();
            }
        });
    }

    @Override
    protected void lazyLayout(boolean isVisibleToUser) {
        super.lazyLayout(isVisibleToUser);
        if (rl_container != null) {
            if (isVisibleToUser) {
                rl_container.setLayoutAnimation(mController);
                rl_container.startLayoutAnimation();
                for (int i = 0; i < mChildCount; i++) {
                    rl_container.getChildAt(i).setVisibility(View.VISIBLE);
                }
            } else {
                for (int i = 0; i < mChildCount; i++) {
                    rl_container.getChildAt(i).setVisibility(View.INVISIBLE);
                }
            }
        }
    }
}
