package com.ybg.av.sa.activity

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.pili.pldroid.player.AVOptions
import com.pili.pldroid.player.widget.PLVideoView
import com.ybg.av.sa.R
import com.ybg.av.sa.utils.Base64Util
import com.ybg.av.sa.view.MediaController

class VideoDetailFragment : Fragment() {

    private var videoTitle = ""
    private var videoUrl = ""

    //视频播放相关
    private val mIsLiveStreaming = 0
    private var hasVideo = false

    private var mPlayer: PLVideoView? = null
    private var mCover: ImageView? = null
    private var mLoadingView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments.containsKey(VIDEO_URL)) {
            videoTitle = arguments.getString(VIDEO_TITLE)
            videoUrl = arguments.getString(VIDEO_URL)

            val activity = this.activity
            val appBarLayout = activity.findViewById(R.id.toolbar_layout) as CollapsingToolbarLayout
            if (appBarLayout != null) {
                appBarLayout.title = Base64Util.getDecodeString(videoTitle)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.video_detail, container, false)
        initVideoView(rootView)

        if (videoUrl != "") {
            loadingVideo(videoUrl)
        }

        return rootView
    }

    override fun onResume() {
        super.onResume()
        if (hasVideo) {
            mPlayer?.start()
        }
    }

    override fun onPause() {
        super.onPause()
        if (hasVideo) {
            mPlayer?.pause()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (hasVideo) {
            mPlayer?.stopPlayback()
        }
    }

    private fun loadingVideo(videoUrl: String) {
        val id = "c2hvdyUyRjIwMTcwMTA1JTJGMTc0NTNjMTUwM2VjNGJkMDg0ZDQ2NGZhNjgwMjhjMTFfMjIubXA0"
        val url = "http://120.76.74.2/file/file/download/$id/"
        mPlayer?.setVideoPath(url)
        hasVideo = true
        mPlayer?.start()
    }

    private fun initVideoView(rootView: View) {
        mPlayer = rootView.findViewById(R.id.v_player) as PLVideoView
        mCover = rootView.findViewById(R.id.iv_video_cover) as ImageView
        mLoadingView = rootView.findViewById(R.id.loadingView)

        mPlayer?.setCoverView(mCover)
        mPlayer?.setBufferingIndicator(mLoadingView)
        mLoadingView?.visibility = View.VISIBLE

        setOptions(0)

        val mMediaController = MediaController(rootView.context)
        mPlayer?.setMediaController(mMediaController)
        mPlayer?.displayAspectRatio = PLVideoView.ASPECT_RATIO_FIT_PARENT
    }

    private fun setOptions(codecType: Int) {
        val options = AVOptions()

        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000)
        options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000)
        options.setInteger(AVOptions.KEY_PROBESIZE, 128 * 1024)
        // Some optimization with buffering mechanism when be set to 1
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, mIsLiveStreaming)
        if (mIsLiveStreaming === 1) {
            options.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1)
        }

        // 1 -> hw codec enable, 0 -> disable [recommended]
        options.setInteger(AVOptions.KEY_MEDIACODEC, codecType)

        // whether start play automatically after prepared, default value is 1
        options.setInteger(AVOptions.KEY_START_ON_PREPARED, 0)

        mPlayer?.setAVOptions(options)
    }

    companion object {
        val VIDEO_TITLE = "item_title"
        val VIDEO_URL = "item_url"
    }
}
