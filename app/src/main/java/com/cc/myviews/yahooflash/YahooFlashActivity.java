package com.cc.myviews.yahooflash;

import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cc.myviews.BaseActivity;
import com.cc.myviews.R;

/**
 * Created by silvercc on 15/10/12.
 */
public class YahooFlashActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ViewPager vp_yahoo;
    private LinearLayout ll_yahoo_point_container;

    @Override
    protected void loadView() {
        setContentView(R.layout.activity_yahoo_flash);
    }

    @Override
    protected void findView() {
        vp_yahoo = (ViewPager) findViewById(R.id.vp_yahoo);
        ll_yahoo_point_container = (LinearLayout) findViewById(R.id.ll_yahoo_point_container);
    }

    @Override
    protected void setListener() {
        vp_yahoo.addOnPageChangeListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        YahooVpAdapter adapter = new YahooVpAdapter(mFragmentManager);
        vp_yahoo.setAdapter(adapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0){
            RelativeLayout rl_container = (RelativeLayout) vp_yahoo.getChildAt(0).findViewById(R.id.rl_container);
            rl_container.getLayoutAnimation().getAnimation().start();
        } else {}
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
