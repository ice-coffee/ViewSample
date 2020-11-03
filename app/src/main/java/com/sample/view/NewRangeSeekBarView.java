package com.sample.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author mzp
 * date : 2020/11/2
 * desc :
 */
public class NewRangeSeekBarView extends View {

    /**
     * 控件宽高
     */
    private int viewHeight;
    private int viewWidth;
    /**
     * 控件可滑动长度
     */
    private float viewMoveWidth;
    /**
     * 控件 marginTop \ marginBottom
     */
    private float viewMarginTb = getResources().getDimension(R.dimen.seek_margin_top_bottom);
    /**
     * 控件 marginStart \ marginEnd
     */
    private float viewMarginSe = getResources().getDimension(R.dimen.seek_margin_start_end);
    /**
     * 边框宽度
     */
    private float viewStrokeWidth = getResources().getDimension(R.dimen.seek_stroke_width);

    private Paint mPaint = new Paint();
    /**
     * 上下两个线段的坐标
     */
    private float[] pts = new float[8];
    /**
     * 进度条线段坐标
     */
    private float[] progressPts = new float[4];
    /**
     * 左滑块
     */
    private Bitmap mLeftBitmap;
    private Rect mLeftSrcRect;
    private float leftDstWidth;
    private Rect mLeftDstRectF;
    /**
     * 右滑块
     */
    private Bitmap mRightBitmap;
    private Rect mRightSrcRect;
    private float rightDstWidth;
    private Rect mRightDstRectF;
    /**
     * 左右滑块滑动的距离
     */
    private float moveLeftRange;
    private float moveRightRange;
    /**
     * 最小范围
     */
    private int minRange;
    /**
     * 动画执行时间
     */
    private long animDuration;
    /**
     * 进度条执行进度
     */
    private float progressValue;
    /**
     * 进度条执行动画
     */
    private ValueAnimator progressAnima;

    public NewRangeSeekBarView(Context context) {
        super(context);
        initView(context, null);
    }

    public NewRangeSeekBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public NewRangeSeekBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(viewStrokeWidth);

        mLeftBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.upload_btn_cut_left);
        mRightBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.upload_btn_cut_right);

        mLeftSrcRect = new Rect(0, 0, mLeftBitmap.getWidth(), mLeftBitmap.getHeight());
        mRightSrcRect = new Rect(0, 0, mRightBitmap.getWidth(), mRightBitmap.getHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        viewHeight = h;
        viewWidth = w;

        int bitmapHeight = viewHeight - (int) viewMarginTb * 2;

        leftDstWidth = mLeftSrcRect.width() / (float) mLeftSrcRect.height() * bitmapHeight;
        rightDstWidth = mRightSrcRect.width() / (float) mRightSrcRect.height() * bitmapHeight;
        mLeftDstRectF = new Rect(0, 0, (int) leftDstWidth, bitmapHeight);
        mRightDstRectF = new Rect(0, 0, (int) rightDstWidth, bitmapHeight);

        viewMoveWidth = viewWidth - viewMarginSe * 2 -leftDstWidth - rightDstWidth;

        pts[0] = viewMarginSe + leftDstWidth;
        pts[1] = viewMarginTb + viewStrokeWidth / 2;
        pts[2] = viewWidth - viewMarginSe -rightDstWidth;
        pts[3] = viewMarginTb + viewStrokeWidth / 2;

        pts[4] = viewMarginSe + leftDstWidth;
        pts[5] = viewHeight - viewMarginTb - viewStrokeWidth / 2;
        pts[6] = viewWidth - viewMarginSe -rightDstWidth;
        pts[7] = viewHeight - viewMarginTb - viewStrokeWidth / 2;

        updateProgressAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);

        canvas.drawLines(pts, mPaint);

        canvas.drawLines(progressPts, mPaint);

        canvas.translate(viewMarginSe, viewMarginTb);
        canvas.drawBitmap(mLeftBitmap, mLeftSrcRect, mLeftDstRectF, mPaint);

        canvas.translate(viewWidth - viewMarginSe * 2 - mRightDstRectF.width(), 0);
        canvas.drawBitmap(mRightBitmap, mRightSrcRect, mRightDstRectF, mPaint);
    }

    float downX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                moveLeftRange += (event.getX() - downX);
                downX = event.getX();

                updateProgressAnimation();

                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
        }
        return true;
    }

    /**
     * 更新进度条动画
     */
    private void updateProgressAnimation() {
        if (null != progressAnima) {
            progressAnima.cancel();
        }
        progressAnima = ValueAnimator.ofFloat(moveLeftRange, viewMoveWidth - moveRightRange);
        progressAnima.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progressValue = (float) animation.getAnimatedValue();

                float x = viewMarginSe + leftDstWidth + progressValue;
                progressPts[0] = x;
                progressPts[1] = 0;
                progressPts[2] = x;
                progressPts[3] = viewHeight;

                invalidate();
            }
        });
        progressAnima.setDuration(animDuration);
        progressAnima.start();
    }
}
