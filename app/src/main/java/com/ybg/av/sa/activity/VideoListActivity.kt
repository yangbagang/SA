package com.ybg.av.sa.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ybg.av.sa.R
import com.ybg.av.sa.adapter.VideoItemAdapter
import com.ybg.av.sa.bean.JSonResultBean
import com.ybg.av.sa.bean.VideoInfo
import com.ybg.av.sa.decoration.SpaceItemDecoration
import com.ybg.av.sa.http.SendRequest
import com.ybg.av.sa.http.callback.OkCallback
import com.ybg.av.sa.http.parser.OkStringParser
import java.util.*

class VideoListActivity : AppCompatActivity() {

    private var mTwoPane: Boolean = false
    private lateinit var adapter: VideoItemAdapter
    private var videoList: MutableList<VideoInfo> = ArrayList<VideoInfo>()

    private val pageSize = 10
    private var pageNum = 1
    private var hasMore = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_list)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.title = title

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            if (hasMore) {
                pageNum += 1
                loadVideoList()
            }
        }

        val recyclerView = findViewById(R.id.video_list)!! as RecyclerView
        adapter = VideoItemAdapter()
        adapter.setListener(listener)
        adapter.setData(videoList)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(SpaceItemDecoration(8))

        if (findViewById(R.id.video_detail_container) != null) {
            mTwoPane = true
        }

        loadVideoList()
    }

    private fun loadVideoList() {
        SendRequest.getVideoList(this@VideoListActivity, pageNum, pageSize, object : OkCallback<String>
        (OkStringParser()){
            override fun onSuccess(code: Int, response: String) {
                val jsonBean = JSonResultBean.fromJSON(response)
                if (jsonBean != null && jsonBean.isSuccess) {
                    val gson = Gson()
                    val data = gson.fromJson<List<VideoInfo>>(jsonBean.data,
                            object : TypeToken<List<VideoInfo>>(){}.type)
                    if (data.size < pageSize) {
                        hasMore = false
                    }
                    videoList.addAll(data)
                    adapter.setData(videoList)
                    adapter.notifyDataSetChanged()
                } else {
                    jsonBean?.let {
                        Toast.makeText(this@VideoListActivity, jsonBean.message, Toast
                                .LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(e: Throwable) {
                Toast.makeText(this@VideoListActivity, e.message, Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        })
    }

    private val listener = object : VideoItemAdapter.VideoItemClickListener {

        override fun itemClick(videoInfo: VideoInfo) {
            if (mTwoPane) {
                val arguments = Bundle()
                arguments.putString(VideoDetailFragment.VIDEO_TITLE, videoInfo.titleInfo)
                arguments.putString(VideoDetailFragment.VIDEO_URL, videoInfo.urlInfo)
                val fragment = VideoDetailFragment()
                fragment.arguments = arguments
                supportFragmentManager.beginTransaction()
                        .replace(R.id.video_detail_container, fragment)
                        .commit()
            } else {
                val intent = Intent(this@VideoListActivity, VideoDetailActivity::class.java)
                intent.putExtra(VideoDetailFragment.VIDEO_TITLE, videoInfo.titleInfo)
                intent.putExtra(VideoDetailFragment.VIDEO_URL, videoInfo.urlInfo)

                this@VideoListActivity.startActivity(intent)
            }
        }

    }


}
