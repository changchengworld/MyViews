package com.cc.myviews.yahooflash;

import android.support.v4.view.ViewPager;
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
    private ImageView imageView, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7;

    public void setmIsLeft(boolean isLeft) {
        mIsLeft = isLeft;
    }

    public void setmSunMoonView(SunMoonView smv) {
        this.smv = smv;
    }

    @Override
    public void transformPage(View page, float position) {
        ImageView center_box = (ImageView) page.findViewById(R.id.center_box);
        BookView book_view = (BookView) page.findViewById(R.id.book_view);
        SixBallsView sbv = (SixBallsView) page.findViewById(R.id.sbv);
        if (smv != null) {
            if (mIsLeft && center_box != null) {
                animateSecondScreen(smv, position, 0);
            }
            if (!mIsLeft && book_view != null) {
                animateSecondScreen(smv, position, 1);
            }
        }
        if (position < -1) {

        } else if (position < 0 && center_box != null) {//当左划时
            imageView = (ImageView) page.findViewById(R.id.imageView);
            imageView2 = (ImageView) page.findViewById(R.id.imageView2);
            imageView3 = (ImageView) page.findViewById(R.id.imageView3);
            imageView4 = (ImageView) page.findViewById(R.id.imageView4);
            imageView5 = (ImageView) page.findViewById(R.id.imageView5);
            imageView6 = (ImageView) page.findViewById(R.id.imageView6);
            imageView7 = (ImageView) page.findViewById(R.id.imageView7);
            animateFirstScreen(position, imageView, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7);
        } else if (position <= 1 && sbv != null) {
            sbv.moveToRight(position);
        } else if (position > 1) {

        } else {
        }
    }

    /**
     * 控制第一个页面imageview平移
     * @param position
     * @param imageView
     */
    private void animateFirstScreen(float position, ImageView... imageView) {
        float camcordPos = (float) (-position * imageView[0].getLeft());
        imageView[0].setTranslationX(camcordPos);

        float clockPos = (float) (-position * imageView[5].getLeft());
        imageView[5].setTranslationX(clockPos);

        float graphPos = (float) (-position * imageView[2].getLeft());
        imageView[2].setTranslationX(graphPos);

        float audioPos = (float) (-position * imageView[3].getLeft());
        imageView[3].setTranslationX(audioPos);

        float quotePos = (float) (position * imageView[4].getLeft());
        imageView[4].setTranslationX(quotePos);

        float mapPos = (float) (position * imageView[1].getLeft());
        imageView[1].setTranslationX(mapPos);

        float wordpressPos = (float) (position * imageView[6].getLeft());
        imageView[6].setTranslationX(wordpressPos);
    }

    /**
     * 操作第二个界面的SunMoonView旋转
     * @param smv
     * @param position
     * @param direction 0:CW || 1:CCW
     */
    private void animateSecondScreen(SunMoonView smv, float position, int direction) {
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
