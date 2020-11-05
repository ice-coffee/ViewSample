package com.sample.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
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
        seekBarView.initBottomShadow( dp2px(30) * 10f + dp2px(56) * 2f)

        seekBarView.setVideoDuration(120 * 1000)
    }

    private val listener: OnScrollListener = object : OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            seekBarView.updateUnSelectRect(dx)
        }
    }
}