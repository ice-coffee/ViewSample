package com.sample.view.ViewSwitcher;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.sample.view.R;

/**
 * Created by ice_coffee on 2015/11/25.
 */
public class ImageSwitcherActivity extends Activity
{
    private ImageSwitcher mImageSwitcher;
    private int[] icons = {R.mipmap.n_guide_1, R.mipmap.n_guide_2, R.mipmap.n_guide_3};
    private int mImagePosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switcher_image);

        /* ImageSwitcher控件初始化 */
        mImageSwitcher = (ImageSwitcher) findViewById(R.id.your_imageview);

        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView()
            {
                ImageView imageView = new ImageView(ImageSwitcherActivity.this);

                return imageView;
            }
        });

        /* 加载显示动画 */
        mImageSwitcher.setInAnimation(this, R.anim.image_fade_in);
        /* 加载隐藏动画 */
        mImageSwitcher.setOutAnimation(this, R.anim.image_fade_out);

        onSwitchImage(null);
    }

    /**
     * Button 按钮
     * @param v
     */
    public void onSwitchImage(View v)
    {
        mImageSwitcher.setImageResource(icons[mImagePosition]);
        setNextPosition();
    }

    /**
     * 循环获取icons数组的下标
     */
    private void setNextPosition() {
        mImagePosition = (mImagePosition + 1) % icons.length;
    }
}
