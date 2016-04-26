package com.wingsofts.loadingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wangqingyi on 4/25/16.
 */
public class MyLoadingView extends View {

    private Context mContext;
    private int mWidth;
    private int mHeight;
    private boolean isSuccess = false;
    private boolean isError = false;

    public MyLoadingView(Context context) {
        this(context, null);
    }

    public MyLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(8);
        p.setAntiAlias(true);
    }

    public MyLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(8);
        p.setAntiAlias(true);
    }

    public void success() {
        isSuccess = true;
    }

    public void error() {
        isError = true;
    }

    public void reset() {
        isSuccess = false;
        isError = false;
        mLinePercent = 0;
        mCirclePercent = 0;
        isRevert = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            mWidth = 200;
        }


        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            mHeight = 200;
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    Paint p = new Paint();

    int mCirclePercent;
    int mLinePercent;
    boolean isRevert = false;

    @Override
    protected void onDraw(Canvas canvas) {

        p.setColor(Color.parseColor("#2EA4F2"));
        //百分比弧的矩形
        RectF rectF = new RectF(5, 5, mWidth - 5, mHeight - 5);
        //绘制圆
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2 - 5, p);
        p.setColor(Color.WHITE);

        if (!isSuccess && !isError) {
            if (mCirclePercent == 100) {
                mCirclePercent = 0;
                isRevert = !isRevert;
            }
            if (isRevert) {
                canvas.drawArc(rectF, 225 - mCirclePercent / 100.0f * 360, 360 - mCirclePercent / 100.0f * 360, false, p);
                mCirclePercent += 2;
            } else {
                canvas.drawArc(rectF, 225, -mCirclePercent / 100.0f * 360, false, p);
                mCirclePercent += 2;
            }
        } else if (isError) {
            Path path = new Path();
            if (mLinePercent < 100) {
                path.moveTo(mWidth / 2, mHeight * 0.5f);
                path.lineTo(mWidth / 2 - mWidth / 4 * (mLinePercent / 100f), mHeight * 0.5f - mHeight * 0.25f * (mLinePercent / 100f));
                path.moveTo(mWidth / 2, mHeight * 0.5f);
                path.lineTo(mWidth / 2 + mWidth / 4 * (mLinePercent / 100f), mHeight * 0.5f - mHeight * 0.25f * (mLinePercent / 100f));
                path.moveTo(mWidth / 2, mHeight * 0.5f);
                path.lineTo(mWidth / 2 - mWidth / 4 * (mLinePercent / 100f), mHeight * 0.5f + mHeight * 0.25f * (mLinePercent / 100f));
                path.moveTo(mWidth / 2, mHeight * 0.5f);
                path.lineTo(mWidth / 2 + mWidth / 4 * (mLinePercent / 100f), mHeight * 0.5f + mHeight * 0.25f * (mLinePercent / 100f));
                canvas.drawPath(path, p);
                canvas.drawArc(rectF, 270, -mLinePercent / 100.0f * 360, false, p);
                mLinePercent += 2;
            } else {
                path.moveTo(mWidth / 2, mHeight * 0.5f);
                path.lineTo(mWidth / 2 - mWidth / 4, mHeight * 0.5f - mHeight * 0.25f);
                path.moveTo(mWidth / 2, mHeight * 0.5f);
                path.lineTo(mWidth / 2 + mWidth / 4, mHeight * 0.5f - mHeight * 0.25f);
                path.moveTo(mWidth / 2, mHeight * 0.5f);
                path.lineTo(mWidth / 2 - mWidth / 4, mHeight * 0.5f + mHeight * 0.25f);
                path.moveTo(mWidth / 2, mHeight * 0.5f);
                path.lineTo(mWidth / 2 + mWidth / 4, mHeight * 0.5f + mHeight * 0.25f);
                canvas.drawPath(path, p);
                canvas.drawArc(rectF, 270, -360, false, p);
            }

        } else {
            Path path = new Path();
            if (mLinePercent < 100) {
                path.moveTo(mWidth / 4, mHeight * 0.5f);
                path.lineTo(mWidth / 2 - 10, mHeight * 0.5f + mLinePercent / 100f * mHeight * 0.15f);
                path.lineTo(mWidth * 0.75f, mHeight * 0.5f - mLinePercent / 100f * mHeight * 0.2f);
                canvas.drawPath(path, p);
                canvas.drawArc(rectF, 270, -mLinePercent / 100.0f * 360, false, p);
                mLinePercent += 5;
            } else {
                path.moveTo(mWidth / 4, mHeight * 0.5f);
                path.lineTo(mWidth / 2 - 10, mHeight * 0.65f);
                path.lineTo(mWidth * 0.75f, mHeight * 0.3f);
                canvas.drawPath(path, p);
//                            绘制最终的圆
                canvas.drawArc(rectF, 270, -360, false, p);
            }
        }

//        postInvalidateDelayed(10);

        invalidate();
    }
}
