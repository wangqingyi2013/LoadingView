package com.wsofts.loadingview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wangqingyi on 4/25/16.
 */
public class WLoadingView extends View {

    Paint p = new Paint();//画笔
    Path path = new Path();
    RectF rectF;
    int mCirclePercent;//画圆的百分比
    int mLinePercent;//画线的百分比
    boolean isRevert = false;//两种画圆切换的标示
    private Context mContext;
    private int mWidth;
    private int mHeight;
    private int mCenterHeight;
    private int mCenterWidth;
    private boolean isSuccess = false;
    private boolean isError = false;

    private int lineBgColor = 0x66ffffff;
    private int lineColor = 0xffffffff;
    private int lineWidth = 6;


    public WLoadingView(Context context) {
        this(context, null);
    }

    public WLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(attrs, 0);
    }

    public WLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs, defStyleAttr);
    }

    void init(AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray a = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.WLoadingView, defStyleAttr, 0);
            lineColor = a.getColor(R.styleable.WLoadingView_lineColor, 0xffffffff);
            lineWidth = a.getInt(R.styleable.WLoadingView_lineWidth, 6);
            lineBgColor = a.getColor(R.styleable.WLoadingView_lineBgColor, 0x66ffffff);
        }

        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(lineWidth);
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
        mCenterHeight = mHeight / 2;
        mCenterWidth = mWidth / 2;

        setMeasuredDimension(mWidth, mHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        p.setColor(lineBgColor);
        //绘制圆
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2 - 5, p);

        p.setColor(lineColor);
        //百分比弧的矩形
        rectF = new RectF(5, 5, mWidth - 5, mHeight - 5);
        if (!isSuccess && !isError) {
            if (mCirclePercent == 100) {
                mCirclePercent = 0;
                isRevert = !isRevert;
            }
            if (isRevert) {
                canvas.drawArc(rectF, 270 - mCirclePercent / 100.0f * 360, 360 - mCirclePercent / 100.0f * 360, false, p);
                mCirclePercent += 2;
            } else {
                canvas.drawArc(rectF, 270, -mCirclePercent / 100.0f * 360, false, p);
                mCirclePercent += 2;
            }
        } else if (isError) {
            path.reset();
            if (mLinePercent < 100) {
                float percent = mLinePercent / 100f;
                path.moveTo(mCenterWidth, mCenterHeight);
                path.lineTo(mCenterWidth - mWidth / 4 * percent, mCenterHeight - mHeight * 0.25f * percent);
                path.moveTo(mCenterWidth, mCenterHeight);
                path.lineTo(mCenterWidth + mWidth / 4 * percent, mCenterHeight - mHeight * 0.25f * percent);
                path.moveTo(mCenterWidth, mCenterHeight);
                path.lineTo(mCenterWidth - mWidth / 4 * percent, mCenterHeight + mHeight * 0.25f * percent);
                path.moveTo(mCenterWidth, mCenterHeight);
                path.lineTo(mCenterWidth + mWidth / 4 * percent, mCenterHeight + mHeight * 0.25f * percent);
                canvas.drawPath(path, p);
                canvas.drawArc(rectF, 270, -percent * 360, false, p);
                mLinePercent += 5;
            } else {
                path.moveTo(mCenterWidth, mCenterHeight);
                path.lineTo(mCenterWidth - mWidth / 4, mCenterHeight - mHeight * 0.25f);
                path.moveTo(mCenterWidth, mCenterHeight);
                path.lineTo(mCenterWidth + mWidth / 4, mCenterHeight - mHeight * 0.25f);
                path.moveTo(mCenterWidth, mCenterHeight);
                path.lineTo(mCenterWidth - mWidth / 4, mCenterHeight + mHeight * 0.25f);
                path.moveTo(mCenterWidth, mCenterHeight);
                path.lineTo(mCenterWidth + mWidth / 4, mCenterHeight + mHeight * 0.25f);
                canvas.drawPath(path, p);
                canvas.drawArc(rectF, 270, -360, false, p);
            }

        } else {
            path.reset();
            if (mLinePercent < 100) {
                path.moveTo(mWidth / 4, mCenterHeight);
                path.lineTo(mCenterWidth - 10, mCenterHeight + mLinePercent / 100f * mHeight * 0.15f);
                path.lineTo(mWidth * 0.75f, mCenterHeight - mLinePercent / 100f * mHeight * 0.2f);
                canvas.drawPath(path, p);
                canvas.drawArc(rectF, 270, -mLinePercent / 100.0f * 360, false, p);
                mLinePercent += 5;
            } else {
                path.moveTo(mWidth / 4, mCenterHeight);
                path.lineTo(mCenterWidth - 10, mHeight * 0.65f);
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
