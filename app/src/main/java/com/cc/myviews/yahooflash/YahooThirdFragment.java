package com.cc.myviews.yahooflash;

import android.view.LayoutInflater;
import android.view.View;

import com.cc.myviews.BaseFragment;
import com.cc.myviews.R;

/**
 * Created by silvercc on 15/10/13.
 */
public class YahooThirdFragment extends BaseFragment{
    private SixBallsView sbv;

    @Override
    protected View loadView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.layout_yahoo_third_page, null);
    }

    @Override
    protected void findView(View parentView) {
        sbv = (SixBallsView)parentView.findViewById(R.id.sbv);
        ((YahooFlashActivity)mParentContext).setSbv(sbv);
    }

    @Override
    protected void init() {

    }
}
