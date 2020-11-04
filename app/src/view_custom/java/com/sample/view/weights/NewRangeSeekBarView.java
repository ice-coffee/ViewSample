package com.sample.view.weights;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.sample.view.R;

/**
 * @author mzp
 * date : 2020/11/2
 * desc :
 */
public class NewRangeSeekBarView extends View {

    private final int MIN_SELECT_DURATION = 3 * 1000;
    private final int MAX_SELECT_DURATION = 60 * 1000;

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
    private RectF mLeftDstRectF;
    /**
     * 右滑块
     */
    private Bitmap mRightBitmap;
    private Rect mRightSrcRect;
    private float rightDstWidth;
    private RectF mRightDstRectF;
    /**
     * 左右滑块滑动的距离
     */
    private float moveLeftRange;
    private float moveRightRange;
    /**
     * 最小(大)选择时间段(秒)
     */
    private long minSelectTime = MIN_SELECT_DURATION;
    private long maxSelectTime = MAX_SELECT_DURATION;
    /**
     * 最小时间段像素长度
     */
    private float minRange;
    /**
     * 单位 PX  = 多少时长
     */
    private float unitTimeTange;
    /**
     * 选中时间线(秒)
     */
    private long selectStartTime;
    private long selectEndTime;
    /**
     * 进度条执行动画
     */
    private ValueAnimator progressAnima;
    /**
     * 当前拖动的是否为左滑块
     */
    private boolean isDragLeft;
    /**
     * 当前拖动的是否为右滑块
     */
    private boolean isDragRight;
    /**
     * 选中监听
     */
    private OnRangeChangeListener rangeChangeListener;

    private GestureDetector gestureDetector;

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

        gestureDetector = new GestureDetector(context, gestureListener);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        viewHeight = h;
        viewWidth = w;

        viewMoveWidth = viewWidth - viewMarginSe * 2 - leftDstWidth - rightDstWidth;

        //单位像素代表的时长
        unitTimeTange = maxSelectTime / viewMoveWidth;
        minRange = MIN_SELECT_DURATION / unitTimeTange;

        updateDstBitmapRect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);

        canvas.drawLines(pts, mPaint);

        canvas.drawLines(progressPts, mPaint);

        canvas.drawBitmap(mLeftBitmap, mLeftSrcRect, mLeftDstRectF, mPaint);
        canvas.drawBitmap(mRightBitmap, mRightSrcRect, mRightDstRectF, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isHandle = gestureDetector.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            updateProgressAnimation();
        }
        return isHandle;
    }

    private GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onDown(MotionEvent event) {
            isDragLeft = false;
            isDragRight = false;
            if (mLeftDstRectF.contains(event.getRawX(), event.getY())) {
                isDragLeft = true;
            }
            if (mRightDstRectF.contains(event.getRawX(), event.getY())) {
                isDragRight = true;
            }
            return isDragLeft || isDragRight;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (isDragLeft) {
                moveLeftRange -= distanceX;
            }
            if (isDragRight) {
                moveRightRange += distanceX;
            }
            updateDstBitmapRect();

            invalidate();
            return true;
        }
    };

    /**
     * 绘制更新
     */
    private void updateDstBitmapRect() {

        //取消动画
        if (null != progressAnima) {
            progressAnima.cancel();
        }

        float bitmapHeight = viewHeight - viewMarginTb * 2;
        leftDstWidth = mLeftSrcRect.width() / (float) mLeftSrcRect.height() * bitmapHeight;
        rightDstWidth = mRightSrcRect.width() / (float) mRightSrcRect.height() * bitmapHeight;

        //左右边界滑动限制
        if (moveLeftRange < 0) {
            moveLeftRange = 0;
        }

        if (moveRightRange < 0) {
            moveRightRange = 0;
        }

        float leftRectLeft = viewMarginSe + moveLeftRange;
        float leftRectRight = leftRectLeft + leftDstWidth;
        float rightRectLeft = viewWidth - viewMarginSe - rightDstWidth - moveRightRange;
        float rightRectRgith = rightRectLeft + rightDstWidth;

        //滑动选中最小范围限制
        if (minRange > viewWidth - (viewMarginSe * 2 + leftDstWidth + moveLeftRange + moveRightRange + rightDstWidth)) {
            return;
        }

        //左右滑块绘制Rect
        mLeftDstRectF = new RectF(leftRectLeft, viewMarginTb, leftRectRight, bitmapHeight + viewMarginTb);
        mRightDstRectF = new RectF(rightRectLeft, viewMarginTb, rightRectRgith, bitmapHeight + viewMarginTb);

        //上下两条边线绘制坐标
        pts[0] = leftRectRight;
        pts[1] = viewMarginTb + viewStrokeWidth / 2;
        pts[2] = rightRectLeft;
        pts[3] = viewMarginTb + viewStrokeWidth / 2;

        pts[4] = leftRectRight;
        pts[5] = viewHeight - viewMarginTb - viewStrokeWidth / 2;
        pts[6] = rightRectLeft;
        pts[7] = viewHeight - viewMarginTb - viewStrokeWidth / 2;

        float x = viewMarginSe + leftDstWidth + moveLeftRange;
        progressPts[0] = x;
        progressPts[1] = 0;
        progressPts[2] = x;
        progressPts[3] = viewHeight;

        if (null != rangeChangeListener) {
            selectStartTime = (long) Math.floor(unitTimeTange * moveLeftRange / 1000);
            selectEndTime = (long) Math.ceil(unitTimeTange * (viewMoveWidth - moveRightRange) / 1000);
            rangeChangeListener.onRangeValuesChanged(selectStartTime, selectEndTime);
        }
    }

    /**
     * 更新进度条动画
     */
    private void updateProgressAnimation() {
        progressAnima = ValueAnimator.ofFloat(mLeftDstRectF.right, mRightDstRectF.left);
        progressAnima.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progressValue = (float) animation.getAnimatedValue();
                updateProgressLine(progressValue);

                invalidate();
            }
        });
        progressAnima.setDuration((selectEndTime - selectStartTime) * 1000);
        progressAnima.start();
    }

    /**
     * 进度条位置更新
     */
    private void updateProgressLine(float progressValue) {

        progressPts[0] = progressValue;
        progressPts[1] = 0;
        progressPts[2] = progressValue;
        progressPts[3] = viewHeight;
    }

    /**
     * 根据视频时长初始化选择框可选区间
     * @param duration 视频时长,单位毫秒
     */
    public void setVideoDuration(long duration) {
        //时长区间
        this.minSelectTime = 0;
        if (duration < MAX_SELECT_DURATION) {
            this.maxSelectTime = duration;
        }

        //初始选中开始播放时间和播放结束时间
        this.selectStartTime = minSelectTime;
        this.selectEndTime = maxSelectTime;
    }

    public void addRangeChangeListener(OnRangeChangeListener listener) {
        this.rangeChangeListener = listener;
    }

    public interface OnRangeChangeListener {
        void onRangeValuesChanged(long startTime, long maxValue);
    }
}
