package com.sample.view.weights

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    companion object {
        const val MIN_SELECT_DURATION = 5 * 1000f
        const val MAX_SELECT_DURATION = 60 * 1000f
        const val MIN_THUMB_TOTAL_COUNT = 10
    }

    /**
     * 缩略图显示
     */
    private var thumbTotalCount = MIN_THUMB_TOTAL_COUNT

    /**
     * 控件宽度
     */
    private var viewWidth = 0

    /**
     * 滑块控件宽度 (可选中最大范围)
     */
    private var seekBarMaxLength = 0f

    /**
     * 视频缩略图距离左右两边最长距离
     */
    private var marginSpace: Int

    /**
     * 视频地址
     */
    private lateinit var videoUri: Uri

    /**
     * 视频时长
     */
    private var videoDuration = 0

    /**
     * 最小时间段像素长度
     */
    private var minLength = 0f

    /**
     * 单位 PX  = 多少时长
     */
    private var unitLengthTime = 0f

    /**
     * 选中时间线(秒)
     */
    private var selectStartTime = 0f
    private var selectEndTime = 0f

    private var mVideoThumbAdapter: VideoTrimmerAdapter = VideoTrimmerAdapter(context)
    private var layoutManager: LinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_video_clip, this, true)

        marginSpace = resources.getDimension(R.dimen.seek_margin_start_end).toInt() + resources.getDimension(R.dimen.slider_width).toInt()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        viewWidth = w

        seekBarMaxLength = viewWidth - resources.getDimension(R.dimen.seek_margin_start_end) * 2 - resources.getDimension(R.dimen.slider_width) * 2

        //缩略图数量
        val maxThumbCount = videoDuration / 1000 / MAX_SELECT_DURATION * 10
        if (maxThumbCount > MIN_THUMB_TOTAL_COUNT) {
            thumbTotalCount = maxThumbCount.toInt()
        }

        //选中最大时长
        selectEndTime = videoDuration.toFloat()
        if (selectEndTime > MAX_SELECT_DURATION) {
            selectEndTime = MAX_SELECT_DURATION
        }

        startShootVideoThumbs(context, videoUri, thumbTotalCount, 0, videoDuration.toLong())

        //缩略图宽度
        val thumbItemWidth = seekBarMaxLength / MIN_THUMB_TOTAL_COUNT
        //缩略图总长
        val thumbTotalWidth = thumbItemWidth * thumbTotalCount
        //计算值 ms / px, 毫秒每像素
        unitLengthTime = videoDuration / thumbTotalWidth
        //最小可选中长度
        minLength = MIN_SELECT_DURATION / unitLengthTime

        mVideoThumbAdapter.setItemWidth(thumbItemWidth)

        bitmapRecyclerView.layoutManager = layoutManager
        bitmapRecyclerView.addItemDecoration(SpacesItemDecoration2(marginSpace, thumbTotalCount))
        bitmapRecyclerView.adapter = mVideoThumbAdapter

        seekBarView.initBottomShadow(thumbTotalWidth)
        seekBarView.initMinLength(minLength)
    }

    public fun initVideoPath(videoUri: Uri) {
        this.videoUri = videoUri
        videoDuration = getVideoDuration(videoUri)

        addListener()

        //视频播放
        videoView.setVideoURI(videoUri)
        videoView.start()
    }

    /**
     * 注册各个监听
     */
    private fun addListener() {

        seekBarView.addRangeChangeListener(rangeChangeListener)
        bitmapRecyclerView.addOnScrollListener(scrollListener)

        //循环播放
        videoView.setOnPreparedListener { mp ->
            mp.isLooping = true
        }
    }

    /**
     * RangeSeekBarView 选中范围监听
     */
    private val rangeChangeListener = object : NewRangeSeekBarView.OnRangeChangeListener {
        override fun onPlayStart() {
        }

        override fun onRangeValuesChanged(dx: Float, isLeft: Boolean): Float {
            if (isLeft) {
                selectStartTime -= dx * unitLengthTime
            } else {
                selectEndTime -= dx * unitLengthTime
            }
            videoView.seekTo(selectStartTime.toInt())
            updateVideoSelectTime()

            return selectEndTime - selectStartTime
        }

        override fun onPlayEnd() {
            videoView.seekTo(selectStartTime.toInt())
        }
    }

    /**
     * RecyclerView 滑动监听
     */
    private val scrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            selectStartTime += dx * unitLengthTime
            selectEndTime += dx * unitLengthTime

            videoView.seekTo(selectStartTime.toInt())
            updateVideoSelectTime()
            seekBarView.updateUnSelectRect(dx)
        }
    }

    /**
     * 更新显示选中时间
     */
    private fun updateVideoSelectTime() {
        videoSelectTime.text = String.format(resources.getString(R.string.video_shoot_tip), selectEndTime / 1000f - selectStartTime / 1000f)
    }

    /**
     * 获取视频时长
     */
    private fun getVideoDuration(videoUri: Uri): Int {

        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, videoUri)
        return retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toInt()
    }

    private fun startShootVideoThumbs(context: Context, videoUri: Uri, totalThumbsCount: Int, startPosition: Long, endPosition: Long) {
        mVideoThumbAdapter.clearBitmaps()
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(context, videoUri)
        for (pos in 1..thumbTotalCount) {
            mVideoThumbAdapter.addBitmaps(mediaMetadataRetriever.getFrameAtTime(pos * 1000000.toLong()))
        }
    }
}