package com.sample.view.ViewSwitcher;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.sample.view.R;

/**
 * Created by ice_coffee on 2015/11/25.
 */
public class TextSwitcherActivity extends Activity
{
    private static final String[] TEXTS = { "First", "Second", "Third" };
    /* 即将显示的文字下标 */
    private int mTextsPosition = 0;
    private TextSwitcher mTextSwitcher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switcher_text);

        /* TextSwitcher控件初始化 */
        mTextSwitcher = (TextSwitcher) findViewById(R.id.your_textview);

        /* 指定ViewFactory */
        mTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView t = new TextView(TextSwitcherActivity.this);
                t.setGravity(Gravity.CENTER);
                return t;
            }
        });

        /* 加载显示动画 */
        mTextSwitcher.setInAnimation(this, R.anim.fade_in);
        /* 加载隐藏动画 */
        mTextSwitcher.setOutAnimation(this, R.anim.fade_out);

        onSwitchText(null);
    }

    /**
     * Button 按钮
     * @param v
     */
    public void onSwitchText(View v) {
        /* 切换文字内容 */
        mTextSwitcher.setText(TEXTS[mTextsPosition]);
        setNextPosition();
    }

    /**
     * 循环获取TEXTS数组的下标
     */
    private void setNextPosition() {
        mTextsPosition = (mTextsPosition + 1) % TEXTS.length;
    }
}
