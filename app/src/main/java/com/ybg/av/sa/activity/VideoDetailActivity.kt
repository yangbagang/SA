package com.ybg.av.sa.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import com.ybg.av.sa.R

class VideoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置无标题窗口
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_video_detail)

        if (savedInstanceState == null) {
            val arguments = Bundle()
            arguments.putString(VideoDetailFragment.VIDEO_TITLE,
                    intent.getStringExtra(VideoDetailFragment.VIDEO_TITLE))
            arguments.putString(VideoDetailFragment.VIDEO_URL,
                    intent.getStringExtra(VideoDetailFragment.VIDEO_URL))
            val fragment = VideoDetailFragment()
            fragment.arguments = arguments
            supportFragmentManager.beginTransaction()
                    .add(R.id.video_detail_container, fragment)
                    .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, VideoListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
