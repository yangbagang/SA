package com.ybg.av.sa.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
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
    private var catalog = "at"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_list)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.title = title

        val fabPre = findViewById(R.id.fabPre) as FloatingActionButton
        fabPre.setOnClickListener { view ->
            if (pageNum > 2) {
                pageNum +- 1
                loadVideoList()
            } else {
                Toast.makeText(this, "己经是第一页了。", Toast.LENGTH_SHORT).show()
            }
        }

        val fabNext = findViewById(R.id.fabNext) as FloatingActionButton
        fabNext.setOnClickListener { view ->
            if (hasMore) {
                pageNum += 1
                loadVideoList()
            } else {
                Toast.makeText(this, "己经是最后一页了。", Toast.LENGTH_SHORT).show()
            }
        }

        val recyclerView = findViewById(R.id.video_list)!! as RecyclerView
        adapter = VideoItemAdapter()
        adapter.setListener(listener)
        adapter.setData(videoList)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(SpaceItemDecoration(24))

        if (findViewById(R.id.video_detail_container) != null) {
            mTwoPane = true
        }

        loadVideoList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.video_catalog, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when(id) {
            R.id.catalog_at -> {
                catalog = "at"
                pageNum = 1
                loadVideoList()
            }
            R.id.catalog_qiju -> {
                catalog = "qiju"
                pageNum = 1
                loadVideoList()
            }
            R.id.catalog_guochan -> {
                catalog = "guochan"
                pageNum = 1
                loadVideoList()
            }
            R.id.catalog_luanlun -> {
                catalog = "luanlun"
                pageNum = 1
                loadVideoList()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadVideoList() {
        SendRequest.getVideoList(this@VideoListActivity, catalog, pageNum, pageSize, object :
                OkCallback<String>(OkStringParser()){
            override fun onSuccess(code: Int, response: String) {
                val jsonBean = JSonResultBean.fromJSON(response)
                if (jsonBean != null && jsonBean.isSuccess) {
                    val gson = Gson()
                    val data = gson.fromJson<List<VideoInfo>>(jsonBean.data,
                            object : TypeToken<List<VideoInfo>>(){}.type)
                    if (data.size < pageSize) {
                        hasMore = false
                    }
                    //清除旧数据
                    videoList.clear()
                    //更新
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
