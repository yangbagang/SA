package com.ybg.av.sa.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.nostra13.universalimageloader.core.ImageLoader
import com.ybg.av.sa.R
import com.ybg.av.sa.bean.VideoInfo
import com.ybg.av.sa.utils.Base64Util

/**
 * Created by yangbagang on 2017/1/20.
 */
class VideoItemAdapter : RecyclerView.Adapter<VideoItemAdapter.ViewHolder>() {

    private var videoList: MutableList<VideoInfo>? = null
    private var videoItemListener: VideoItemClickListener? = null

    fun setData(list: MutableList<VideoInfo>) {
        videoList = list
    }

    fun setListener(listener: VideoItemClickListener) {
        videoItemListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.video_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (videoList == null) {
            return
        }
        val videoInfo = videoList!![position]
        ImageLoader.getInstance().displayImage(videoInfo.imgInfo, holder.mImgView)
        holder.mTitleView.text = Base64Util.getDecodeString(videoInfo.titleInfo)

        holder.mView.setOnClickListener {
            videoItemListener?.itemClick(videoInfo)
        }
    }

    override fun getItemCount(): Int {
        return videoList?.size ?: 0
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mImgView: ImageView
        val mTitleView: TextView

        init {
            mImgView = mView.findViewById(R.id.iv_video_img) as ImageView
            mTitleView = mView.findViewById(R.id.tv_video_title) as TextView
        }

        override fun toString(): String {
            return super.toString() + " '" + mTitleView.text + "'"
        }
    }

    interface VideoItemClickListener {
        fun itemClick(videoInfo: VideoInfo)
    }
}