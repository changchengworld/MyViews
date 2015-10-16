package com.cc.myviews.yahooflash;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.cc.myviews.R;

/**
 * Created by silvercc on 15/10/14.
 */
public class CustomTransformer implements ViewPager.PageTransformer {

    private static final String TAG = "CustomTransformer";

    private boolean mIsLeft;
    private SunMoonView smv;

    public void setmIsLeft(boolean isLeft) {
        mIsLeft = isLeft;
    }

    public void setmSunMoonView(SunMoonView smv) {
        this.smv = smv;
    }

    @Override
    public void transformPage(View page, float position) {
        Log.i(TAG, "mIsLeft=" + mIsLeft);
        Log.i(TAG, "smv=" + smv);
        if (smv != null) {
            if (mIsLeft && page.findViewById(R.id.center_box) != null) {
                animateSecondScreen(smv, position, 0);
            }
            if (!mIsLeft && page.findViewById(R.id.book_view) != null) {
                animateSecondScreen(smv, position, 1);
            }
        }
    }

    private void animateSecondScreen(SunMoonView smv, float position, int direction) {
        Log.i(TAG, "direction=" + direction);
        switch (direction) {
            case 0:
                smv.doClockWiseAnimation(position);
                break;
            case 1:
                smv.doAnitClockWiseAnimation(position);
                break;
            default:
                break;
        }
    }
}
