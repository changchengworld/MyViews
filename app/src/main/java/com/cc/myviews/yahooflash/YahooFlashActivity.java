package com.cc.myviews.yahooflash;

import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cc.myviews.BaseActivity;
import com.cc.myviews.BaseFragment;
import com.cc.myviews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silvercc on 15/10/12.
 */
public class YahooFlashActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ViewPager vp_yahoo;
    private YahooVpAdapter adapter;
    private float mPreviousOffset;
    private CustomTransformer customTransformer;
    private SixBallsView sbv;
    private List<BaseFragment> mList;
    private boolean isLeft;

    @Override
    protected void loadView() {
        setContentView(R.layout.activity_yahoo_flash);
    }

    @Override
    protected void findView() {
        vp_yahoo = (ViewPager) findViewById(R.id.vp_yahoo);
    }

    @Override
    protected void setListener() {
        vp_yahoo.addOnPageChangeListener(this);
    }

    @Override
    protected void initData() {
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

    }

    public void setTransformer() {
        vp_yahoo.setPageTransformer(false, customTransformer);
    }

    //    public ViewPager getVp_yahoo(){
//        return this.vp_yahoo;
//    }
//
    public CustomTransformer getCustomTransformer() {
        return this.customTransformer;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
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
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        boolean isBallRotate = false;
        if(state == ViewPager.SCROLL_STATE_IDLE){
            isBallRotate = true;
        } else {
            isBallRotate = false;
        }
        if(sbv!=null){
            sbv.setBallRotate(isBallRotate);
        }
    }

    public void setSbv(SixBallsView sbv){
        this.sbv = sbv;
    }
}
