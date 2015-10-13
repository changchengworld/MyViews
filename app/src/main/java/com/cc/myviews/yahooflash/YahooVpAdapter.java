package com.cc.myviews.yahooflash;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cc.myviews.BaseFragment;

/**
 * Created by silvercc on 15/10/12.
 */
public class YahooVpAdapter extends FragmentPagerAdapter{

    private final static int PAGE_NUM = 3;
    public YahooVpAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment fm = getFragmentByPosition(position);
        return fm;
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }

    private BaseFragment getFragmentByPosition(int position){
        BaseFragment bfm=null;
        switch (position){
            case 0:
                bfm = new YahooFirstFragment();
                break;
            case 1:
                bfm = new YahooFirstFragment();
                break;
            case 2:
                bfm = new YahooFirstFragment();
                break;
            default:
                break;
        }
        return bfm;
    }
}
