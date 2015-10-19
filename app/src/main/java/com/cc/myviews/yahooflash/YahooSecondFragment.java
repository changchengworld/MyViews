package com.cc.myviews.yahooflash;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.cc.myviews.BaseFragment;
import com.cc.myviews.R;

/**
 * Created by silvercc on 15/10/12.
 */
public class YahooSecondFragment extends BaseFragment {
    private static final String TAG = "YahooSecondFragment";
    private BookView book_view;
    private LayoutAnimationController controller;
    private int mChildCount;
    private SunMoonView mSmv;

    @Override
    protected View loadView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.layout_yahoo_second_page, null);
    }

    @Override
    protected void findView(View parentView) {
        Log.i(TAG, "parentView=" + parentView);
        book_view = (BookView) parentView.findViewById(R.id.book_view);
        mSmv = (SunMoonView) parentView.findViewById(R.id.smv);
    }

    @Override
    protected void init() {
        ((YahooFlashActivity) mParentContext).getCustomTransformer().setmSunMoonView(mSmv);
        mChildCount = book_view.getChildCount();
        controller = AnimationUtils.loadLayoutAnimation(mParentContext, R.anim.layout_animation);
    }

    @Override
    protected void lazyLayout(boolean isVisibleToUser) {
        super.lazyLayout(isVisibleToUser);
        if (isVisibleToUser) {
            book_view.setLayoutAnimation(controller);
            book_view.startLayoutAnimation();
            for (int i = 0; i < mChildCount; i++) {
                book_view.getChildAt(i).setVisibility(View.VISIBLE);
            }
        } else {
            for (int i = 0; i < mChildCount; i++) {
                book_view.getChildAt(i).setVisibility(View.INVISIBLE);
            }
        }
    }

}
