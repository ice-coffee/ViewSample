package com.sample.view.weights

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.common.utils.dp2px
import com.sample.view.R
import com.sample.view.SpacesItemDecoration2
import com.sample.view.VideoTrimmerAdapter
import kotlinx.android.synthetic.main.view_video_clip.view.*

/**
 *  @author mzp
 *  date : 2020/11/5
 *  desc :
 */
public class VideoClipView(context: Context, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    private val thumbItemWidth = resources.getDimension(R.dimen.thumb_item_width)

    private var videoDuration = 0

    /**
     * 选中时间线(秒)
     */
    private var selectStartTime = 0
    private var selectEndTime = 0

    private var mVideoThumbAdapter: VideoTrimmerAdapter = VideoTrimmerAdapter(context)
    private var layoutManager: LinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_video_clip, this, true)

        val marginSpace = resources.getDimension(R.dimen.seek_margin_start_end).toInt() + resources.getDimension(R.dimen.slider_width).toInt()
        bitmapRecyclerView.layoutManager = layoutManager
        bitmapRecyclerView.addItemDecoration(SpacesItemDecoration2(marginSpace, 20))
        bitmapRecyclerView.adapter = mVideoThumbAdapter

        seekBarView.initBottomShadow( dp2px(thumbItemWidth.toInt()) * 20f + marginSpace * 2f)
    }

    public fun initVideoPath(videoUri: Uri) {
        addListener()

        videoView.setVideoURI(videoUri)
        videoView.start()
    }

    private fun addListener() {

        seekBarView.addRangeChangeListener(rangeChangeListener)
        bitmapRecyclerView.addOnScrollListener(scrollListener)

        videoView.setOnPreparedListener { mp ->
            videoDuration = mp.duration
            //循环播放
            mp.isLooping = true
            //
            seekBarView.setVideoDuration(videoDuration)
        }
    }

    private val rangeChangeListener = object : NewRangeSeekBarView.OnRangeChangeListener {
        override fun onPlayStart() {
        }

        override fun onRangeValuesChanged(startTime: Int, endTime: Int) {
            selectStartTime = startTime
            selectEndTime = endTime

            updateVideoSelectTime()
            videoView.seekTo(selectStartTime)
        }

        override fun onPlayEnd() {
            videoView.seekTo(selectStartTime)
        }
    }

    private val scrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            selectStartTime += dx * seekBarView.getUnitLengthTime()
            selectEndTime -= dx * seekBarView.getUnitLengthTime()

            videoView.seekTo(selectStartTime)
            updateVideoSelectTime()
            seekBarView.updateUnSelectRect(dx)
        }
    }

    private fun updateVideoSelectTime() {
        videoSelectTime.text = String.format(resources.getString(R.string.video_shoot_tip), selectEndTime / 1000f - selectStartTime / 1000f)
    }
}