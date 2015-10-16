package com.cc.myviews.yahooflash;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.cc.myviews.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silvercc on 15/10/12.
 */
public class YahooVpAdapter extends FragmentPagerAdapter{

    private List<BaseFragment> mList;

    public YahooVpAdapter(FragmentManager fm, List<BaseFragment> list) {
        super(fm);
        mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment fm = mList.get(position);
        return fm;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    private BaseFragment getFragmentByPosition(int position){
        BaseFragment fm = null;
        switch (position){
            case 0:
                fm = new YahooFirstFragment();
                break;
            case 1:
                fm = new YahooSecondFragment();
                break;
            case 2:
                fm = new YahooThirdFragment();
                break;
        }
        return fm;
    }

}
