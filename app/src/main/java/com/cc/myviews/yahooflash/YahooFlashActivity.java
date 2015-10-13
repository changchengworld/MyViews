package com.cc.myviews.yahooflash;

import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cc.myviews.BaseActivity;
import com.cc.myviews.BaseFragment;
import com.cc.myviews.R;

import java.util.ArrayList;

/**
 * Created by silvercc on 15/10/12.
 */
public class YahooFlashActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private final static int PAGE_NUM = 3;
    private ViewPager vp_yahoo;
    private LinearLayout ll_yahoo_point_container;
    private YahooVpAdapter adapter;
    private ArrayList<BaseFragment> mList;
    private int mCurrentPosition;

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
        mList = new ArrayList<BaseFragment>();
        mList.add(new YahooFirstFragment());
        mList.add(new YahooSecondFragment());
        mList.add(new YahooFirstFragment());
        adapter = new YahooVpAdapter(mFragmentManager, mList);
        vp_yahoo.setAdapter(adapter);
        vp_yahoo.setOffscreenPageLimit(1);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPosition = position;

    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        if (state == ViewPager.SCROLL_STATE_IDLE) {
//            if (mCurrentPosition == 1) {
//                BookView rl_container = (BookView) vp_yahoo.getChildAt(mCurrentPosition).findViewById(R.id.book_view);
//                rl_container.showLayoutAnimation();
//                adapter.getItem(mCurrentPosition);
//                adapter.notifyDataSetChanged();
//            }
//            if (mCurrentPosition == 0) {
//                RelativeLayout rl_container = (RelativeLayout) mList.get(mCurrentPosition).getView().findViewById(R.id.rl_container);
//                rl_container.getLayoutAnimation().getAnimation().start();
//            } else if (mCurrentPosition == 1) {
//                BookView rl_container = (BookView) mList.get(mCurrentPosition).getView().findViewById(R.id.book_view);
//                rl_container.showLayoutAnimation();
//            }
//        }
    }
}
