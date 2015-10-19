package com.cc.myviews.yahooflash;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cc.myviews.BaseActivity;
import com.cc.myviews.BaseFragment;
import com.cc.myviews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silvercc on 15/10/12.
 */
public class YahooFlashActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private static final String TAG = "YahooFlashActivity";
    private ViewPager vp_yahoo;
    private YahooVpAdapter adapter;
    private float mPreviousOffset;
    private CustomTransformer customTransformer;
    private SixBallsView sbv;
    private List<BaseFragment> mList;
    private boolean isLeft;
    private LinearLayout ll_yahoo_point_container;
    private TextView[] indicators;

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
        indicators = new TextView[3];
        mList = new ArrayList<BaseFragment>();
        YahooFirstFragment yahooFirstFragment = new YahooFirstFragment();
        YahooSecondFragment yahooSecondFragment = new YahooSecondFragment();
        YahooThirdFragment yahooThirdFragment = new YahooThirdFragment();
        mList.add(yahooFirstFragment);
        mList.add(yahooSecondFragment);
        mList.add(yahooThirdFragment);
    }

    @Override
    protected void initView() {
        adapter = new YahooVpAdapter(mFragmentManager, mList);
        vp_yahoo.setAdapter(adapter);
        customTransformer = new CustomTransformer();
        initIndicator();
    }

    private void initIndicator() {
        for (int i = 0; i < 3; i++) {
            indicators[i] = new TextView(this);
            indicators[i].setWidth((int) getResources().getDimension(R.dimen.dimen_12));
            indicators[i].setHeight((int) getResources().getDimension(R.dimen.dimen_12));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, (int) getResources().getDimension(R.dimen.dimen_15), 0);
            indicators[i].setLayoutParams(params);
            indicators[i].setBackgroundResource(R.drawable.shape_gray_point);
            ll_yahoo_point_container.addView(indicators[i]);
        }
        indicators[0].setBackgroundResource(R.drawable.shape_red_point);
    }

    public void setTransformer() {
        vp_yahoo.setPageTransformer(false, customTransformer);
    }

    public CustomTransformer getCustomTransformer() {
        return this.customTransformer;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //positionOffset:当前拖动的页面平移的百分比[0,1）
        if (positionOffset > mPreviousOffset) {
            isLeft = true;
        } else if (positionOffset < mPreviousOffset) {
            isLeft = false;
        }
        customTransformer.setmIsLeft(isLeft);
        mPreviousOffset = positionOffset;
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 2) {
            ll_yahoo_point_container.setVisibility(View.GONE);
        } else {
            ll_yahoo_point_container.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(R.drawable.shape_gray_point);
        }
        indicators[position].setBackgroundResource(R.drawable.shape_red_point);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        boolean isBallRotate = false;
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            isBallRotate = true;
        } else {
            isBallRotate = false;
        }
        if (sbv != null) {
            sbv.setBallRotate(isBallRotate);
        }
    }

    public void setSbv(SixBallsView sbv) {
        this.sbv = sbv;
    }
}
