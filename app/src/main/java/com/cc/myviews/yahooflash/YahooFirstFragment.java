package com.cc.myviews.yahooflash;

import android.view.LayoutInflater;
import android.view.View;

import com.cc.myviews.BaseFragment;
import com.cc.myviews.R;

/**
 * Created by silvercc on 15/10/12.
 */
public class YahooFirstFragment extends BaseFragment {

    @Override
    protected View loadView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.layout_yahoo_first_page, null);
    }

    @Override
    protected void findView(View parentView) {

    }

    @Override
    protected void init() {

    }
}
