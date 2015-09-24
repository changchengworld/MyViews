package com.cc.myviews;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by silvercc on 15/9/24.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseInit();
        loadView();
        findView();
        setListener();
        initData();
        initView();
    }

    private void baseInit() {
        mFragmentManager = getSupportFragmentManager();
    }

    /**
     * 加载当前activity布局
     */
    protected abstract void loadView();

    /**
     * 查找当前加载的view中的子view
     */
    protected abstract void findView();

    /**
     * 给查找到的view设置监听器
     */
    protected abstract void setListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化控件
     */
    protected abstract void initView();
}
