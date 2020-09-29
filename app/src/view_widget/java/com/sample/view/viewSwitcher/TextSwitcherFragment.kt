package com.sample.view.viewSwitcher

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.sample.view.R
import kotlinx.android.synthetic.main.fragment_switcher_text.*

/**
 * date: 2020/9/20
 * author: ice_coffee
 * remark:
 */
class TextSwitcherFragment: Fragment() {

    private val textArray = arrayOf("First", "Second", "Third")

    /* 即将显示的文字下标 */
    private var mTextsPosition = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_switcher_text, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {

        /* 指定ViewFactory */
        mTextSwitcher.setFactory {
            val t = TextView(context)
            t.gravity = Gravity.CENTER
            t
        }

        btSwitchText.setOnClickListener { setNextPosition() }

        /* 加载显示动画 */
        mTextSwitcher.setInAnimation(context, R.anim.fade_in)
        /* 加载隐藏动画 */
        mTextSwitcher.setOutAnimation(context, R.anim.fade_out)

        /* 切换文字内容 */
        mTextSwitcher.setText(textArray[mTextsPosition])
        setNextPosition()
    }

    /**
     * 循环获取TEXTS数组的下标
     */
    private fun setNextPosition() {
        /* 切换文字内容 */
        mTextSwitcher.setText(textArray[mTextsPosition])
        mTextsPosition = (mTextsPosition + 1) % textArray.size
    }
}