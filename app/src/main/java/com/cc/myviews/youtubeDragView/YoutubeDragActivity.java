package com.cc.myviews.youtubeDragView;

import android.support.v4.app.FragmentTransaction;

import com.cc.myviews.BaseActivity;
import com.cc.myviews.R;

/**
 * Created by silvercc on 15/10/9.
 */
public class YoutubeDragActivity extends BaseActivity{
    @Override
    protected void loadView() {
        setContentView(R.layout.activity_gallery);//有个blank的就行
    }

    @Override
    protected void findView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        YoutubeDragFragment youtubeDragFragment = new YoutubeDragFragment();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.rl_container, youtubeDragFragment);
        fragmentTransaction.commit();
    }
}
