package com.ybg.av.sa.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.Window
import android.view.WindowManager

import com.ybg.av.sa.R

class WelcomeActivity : Activity() {

    private var time = 3//倒计时

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置无标题窗口
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_welcome)

        mHandler!!.postDelayed(runnable, 1000)
    }

    private fun enterListActivity() {
        val intent = Intent(this, VideoListActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mHandler != null) {
            mHandler!!.removeCallbacks(null)
            mHandler = null
        }
        time = 0
    }

    private val runnable = object : Runnable {
        override fun run() {
            time--
            if (time == 0) {
                enterListActivity()
            } else {
                if (mHandler != null) {
                    mHandler!!.sendEmptyMessage(158)
                }
            }
            if (mHandler != null) {
                mHandler!!.postDelayed(this, 1000)
            }
        }
    }

    private var mHandler: Handler? = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                158 -> {
                }
            }//tv_second_num.setText(time + "秒后关闭");
            super.handleMessage(msg)
        }
    }

}
