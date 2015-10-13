package com.cc.myviews.yahooflash;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by silvercc on 15/10/13.
 */
public class BookView extends LinearLayout {

    private final Paint mPaint;
    private final static int OFFSETMARGIN = 10;

    public BookView(Context context) {
        this(context, null, 0);
    }

    public BookView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BookView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2f);
        mPaint.setColor(Color.BLACK);
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //后面的书
        canvas.drawLine(OFFSETMARGIN, 0, this.getWidth(), 0, mPaint);
        canvas.drawLine(OFFSETMARGIN, 0, OFFSETMARGIN, OFFSETMARGIN, mPaint);
        canvas.drawLine(this.getWidth(), 0, this.getWidth(), this.getHeight() - OFFSETMARGIN, mPaint);
        canvas.drawLine(this.getWidth() - OFFSETMARGIN, this.getHeight() - OFFSETMARGIN, this.getWidth(), this.getHeight() - OFFSETMARGIN, mPaint);
        //前面的书
        canvas.drawLine(0, OFFSETMARGIN, this.getWidth() - OFFSETMARGIN, OFFSETMARGIN, mPaint);
        canvas.drawLine(0, OFFSETMARGIN, 0, this.getHeight(), mPaint);
        canvas.drawLine(this.getWidth() - OFFSETMARGIN, OFFSETMARGIN, this.getWidth() -
                OFFSETMARGIN, this.getHeight(), mPaint);
        canvas.drawLine(0, this.getHeight(), this.getWidth() - OFFSETMARGIN, this.getHeight(), mPaint);
    }
}
