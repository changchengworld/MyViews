package com.cc.myviews.galleryview;

import android.support.v4.app.FragmentTransaction;

import com.cc.myviews.BaseActivity;
import com.cc.myviews.R;

/**
 * Created by silvercc on 15/9/24.
 */
public class galleryActivity extends BaseActivity{

    @Override
    protected void loadView() {
        setContentView(R.layout.activity_gallery);
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
        galleryFragment galleryFragment = new galleryFragment();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.rl_container, galleryFragment);
        fragmentTransaction.commit();
    }
}
