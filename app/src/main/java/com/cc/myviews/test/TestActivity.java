package com.cc.myviews.test;

import android.view.View;
import android.widget.TextView;

import com.cc.myviews.BaseActivity;
import com.cc.myviews.R;

/**
 * Created by silvercc on 15/10/25.
 */
public class TestActivity extends BaseActivity implements View.OnClickListener, OnPositionChangeListner {
    private TextView tv_click;
    private TpoListenLayout tpo_listen_layout;

    @Override
    protected void loadView() {
        setContentView(R.layout.activity_layout_test);
    }

    @Override
    protected void findView() {
        tpo_listen_layout=(TpoListenLayout)findViewById(R.id.tpo_listen_layout);
        tv_click=(TextView)findViewById(R.id.tv_click);
    }

    @Override
    protected void setListener() {
        tv_click.setOnClickListener(this);
        tpo_listen_layout.setOnPositionChangeListner(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_click:
                tpo_listen_layout.smoothSlideTo(0);
                break;
        }
    }

    @Override
    public void positionChange(float offset) {
        //Do your business when the ViewDragHelper.Callback onViewPositionChanged was called.
    }
}
