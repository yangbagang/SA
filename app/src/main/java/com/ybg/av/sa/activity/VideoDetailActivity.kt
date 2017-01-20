package com.ybg.av.sa.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.ybg.av.sa.R

class VideoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
        val toolbar = findViewById(R.id.detail_toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

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
