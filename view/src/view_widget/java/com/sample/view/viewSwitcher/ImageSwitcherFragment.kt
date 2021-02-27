package com.sample.view.viewSwitcher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.sample.view.R
import kotlinx.android.synthetic.main.fragment_switcher_image.*

/**
 * date: 2020/9/20
 * author: ice_coffee
 * remark:
 */
class ImageSwitcherFragment: Fragment() {

    private val icons = intArrayOf(R.mipmap.n_guide_1, R.mipmap.n_guide_2, R.mipmap.n_guide_3)
    private var mImagePosition = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_switcher_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {

        mImageSwitcher.setFactory { ImageView(context) }

        btSwitchImage.setOnClickListener { setNextPosition() }

        /* 加载显示动画 */
        mImageSwitcher.setInAnimation(context, R.anim.image_fade_in)
        /* 加载隐藏动画 */
        mImageSwitcher.setOutAnimation(context, R.anim.image_fade_out)

        mImageSwitcher.setImageResource(icons[mImagePosition])
    }

    /**
     * 循环获取icons数组的下标
     */
    private fun setNextPosition() {
        /* 切换文字内容 */
        mImageSwitcher.setImageResource(icons[mImagePosition])
        mImagePosition = (mImagePosition + 1) % icons.size
    }
}