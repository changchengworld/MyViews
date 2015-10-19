package com.cc.myviews.yahooflash;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.cc.myviews.BaseFragment;
import com.cc.myviews.R;

/**
 * Created by silvercc on 15/10/13.
 */
public class YahooThirdFragment extends BaseFragment {
    private static final String TAG = "YahooThirdFragment";
    private SixBallsView sbv;
    private Button letsgo;

    @Override
    protected View loadView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.layout_yahoo_third_page, null);
    }

    @Override
    protected void findView(View parentView) {
        Log.i(TAG, "parentView=" + parentView);
        sbv = (SixBallsView) parentView.findViewById(R.id.sbv);
        letsgo = (Button) parentView.findViewById(R.id.letsgo);
        ((YahooFlashActivity) mParentContext).setSbv(sbv);
    }

    @Override
    protected void init() {
        letsgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sbv.setIsGoNext(true);
            }
        });
    }
}
