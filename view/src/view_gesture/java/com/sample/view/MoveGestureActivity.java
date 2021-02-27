package com.sample.view;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sample.view.multitouch.MoveGestureDetector;

/**
 * <b>Project:</b> YuantaiApplication<br>
 * <b>Create Date:</b> 2017/4/27<br>
 * <b>Author:</b> qiwenming<br>
 * <b>Description:</b> <br>
 *     移动
 */
public class MoveGestureActivity extends AppCompatActivity {
    private View mView;
    private MoveGestureDetector mMoveGestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_gesture);
        mView = findViewById(R.id.move_view);
        mMoveGestureDetector = new MoveGestureDetector(this,mSimpleOnMoveGestureListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mMoveGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private float mOffsetX = 0;
    private float mOffsetY = 0;
    private float mLastX = 0;
    private float mLastY = 0;
    private MoveGestureDetector.SimpleOnMoveGestureListener mSimpleOnMoveGestureListener = new MoveGestureDetector.SimpleOnMoveGestureListener(){
        @Override
        public boolean onMove(MoveGestureDetector detector) {
            mOffsetX = detector.getFocusDelta().x+mLastX;
            mOffsetY = detector.getFocusDelta().y+mLastY;
            mView.setTranslationX(mOffsetX);
            mView.setTranslationY(mOffsetY);
            return super.onMove(detector);
        }

        @Override
        public void onMoveEnd(MoveGestureDetector detector) {
            mLastX = mOffsetX;
            mLastY = mOffsetY;
            super.onMoveEnd(detector);
        }
    };
}
