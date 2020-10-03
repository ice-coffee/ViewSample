package com.sample.view.appbarLayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.sample.view.R
import kotlinx.android.synthetic.main.fragment_appbar_threee.*


/**
 * date: 2020/10/3
 * author: ice_coffee
 * remark:
 */
class SampleThreeFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_appbar_threee, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //获取BottomSheetBehavior
        //获取BottomSheetBehavior
        val sheetBehavior = BottomSheetBehavior.from(shareView)

        //设置折叠时的高度
        //sheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        //监听BottomSheetBehavior 状态的变化

        //设置折叠时的高度
        //sheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        //监听BottomSheetBehavior 状态的变化
        sheetBehavior.setBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {}
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
        //下滑的时候是否可以隐藏
        //下滑的时候是否可以隐藏
        sheetBehavior.isHideable = true
        btn_show_bottom_sheet.setOnClickListener(View.OnClickListener {
            if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            } else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN)
            }
        })
    }
}