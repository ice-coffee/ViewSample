package com.sample.view.weights;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.sample.view.multitouch.RotateGestureDetector;


/**
 * @author mzp
 * date : 2020/10/30
 * desc :
 */
public class MyTextView extends View {

    private float mScaleFactor = 1.0f;
    private ScaleGestureDetector mScaleGestureDetector;

    private float mRotationDegrees = 0.f;
    private RotateGestureDetector mRotateGestureDetector;

    private Paint mPaint = new Paint();
    private int viewHeight;
    private int viewWidth;
    private int viewCenterX;
    private int viewCenterY;

    private String textContent = "星期五";
    private float textCenterX = 0;
    private float textCenterY = 0;

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint.setTextSize(64);
        mPaint.setStrokeWidth(20);
        mScaleGestureDetector = new ScaleGestureDetector(context, mScaleGestureListener);
        mRotateGestureDetector = new RotateGestureDetector(context, mSimpleOnRotateGestureListener);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
        viewWidth = w;

        viewCenterX = viewWidth / 2;
        viewCenterY = viewHeight / 2;

        Rect textBounds = new Rect();
        mPaint.getTextBounds(textContent, 0, textContent.length(), textBounds);
        textCenterY = (viewHeight + textBounds.height()) / 2;
        textCenterX = (viewWidth - textBounds.width()) / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        mRotateGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);

        canvas.scale(mScaleFactor, mScaleFactor, viewCenterX, viewCenterY);
        canvas.rotate(mRotationDegrees, viewCenterX, viewCenterY);

        mPaint.setColor(Color.WHITE);
        canvas.drawText(textContent, textCenterX, textCenterY, mPaint);
    }

    private ScaleGestureDetector.OnScaleGestureListener mScaleGestureListener = new ScaleGestureDetector.OnScaleGestureListener() {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            invalidate();
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

        }
    };
    private RotateGestureDetector.SimpleOnRotateGestureListener mSimpleOnRotateGestureListener = new RotateGestureDetector.SimpleOnRotateGestureListener() {

        @Override
        public boolean onRotate(RotateGestureDetector detector) {
            mRotationDegrees = -detector.getRotationDegreesDelta() + mRotationDegrees;
            mRotationDegrees = mRotationDegrees % 360;
            Log.e("RotationDegrees", mRotationDegrees + "");
            invalidate();
            return true;
        }
    };
}
