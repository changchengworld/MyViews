package com.cc.myviews.galleryview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.cc.myviews.R;

/**
 * Created by silvercc on 15/9/24.
 */
public class AntiAliasImageView extends ImageView{
    private String mText;
    private Paint mPaint;
    private Paint mTextPaint;
    public AntiAliasImageView(Context context) {
        this(context, null, 0);
    }

    public AntiAliasImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AntiAliasImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AntiAliasImageView);
        mText = ta.getString(R.styleable.AntiAliasImageView_text);
        ta.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(7f);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setFlags(Paint.FILTER_BITMAP_FLAG);
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStrokeWidth(1);
        mTextPaint.setColor(Color.GREEN);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(40);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AntiAliasImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*第一个参数代表要清除掉的画笔属性，第二个参数代表需要添加给画笔的属性*/
//        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG));
        super.onDraw(canvas);
        int width = this.getWidth();
        int height = this.getHeight();
        canvas.drawLine(0,0,width,0,mPaint);
        canvas.drawLine(width,0,width,height,mPaint);
        canvas.drawLine(0,height,width,height,mPaint);
        canvas.drawLine(0,0,0,height,mPaint);
        canvas.drawText(mText, this.getWidth()/2, this.getHeight()/2, mTextPaint);
    }
}
