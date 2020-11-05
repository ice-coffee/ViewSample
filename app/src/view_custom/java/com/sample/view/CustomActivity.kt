package com.sample.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.sample.common.utils.LogUtils
import com.sample.common.utils.dp2px
import kotlinx.android.synthetic.main.activity_custom.*

/**
 *  @author mzp
 *  date : 2020/11/4
 *  desc :
 */
public class CustomActivity: AppCompatActivity() {

    private lateinit var mVideoThumbAdapter: VideoTrimmerAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_custom)

        layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        bitmapRecyclerView.layoutManager = layoutManager
        mVideoThumbAdapter = VideoTrimmerAdapter(this)
        bitmapRecyclerView.adapter = mVideoThumbAdapter
        bitmapRecyclerView.addOnScrollListener(listener)
        seekBarView.initBottomShadow( dp2px(100) * 20 + resources.getDimension(R.dimen.seek_margin_start_end) * 2)

        seekBarView.setVideoDuration(120 * 1000)
    }

    private val listener: OnScrollListener = object : OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (layoutManager.findFirstVisibleItemPosition() < 1) {
                seekBarView.updateUnSelectLeftRect(dx)
            }

            if (layoutManager.findLastVisibleItemPosition() > 18) {
                seekBarView.updateUnSelectRightRect(dx)
            }

            Log.e("onScrolled", "${layoutManager.findFirstVisibleItemPosition()}, ${layoutManager.findFirstCompletelyVisibleItemPosition()}, ${layoutManager.findLastVisibleItemPosition()}, ${layoutManager.findLastCompletelyVisibleItemPosition()}")
        }
    }
}