package com.ybg.av.sa.http

import android.content.Context
import com.ybg.av.sa.http.callback.OkCallback

object SendRequest {

    fun getVideoList(tag: Context, pageNum: Int, pageSize: Int, callback: OkCallback<*>) {
        val params = mapOf<String, Int>("pageNum" to pageNum, "pageSize" to pageSize)
        OkHttpProxy.post(HttpUrl.listAtURL, tag, params, callback)
    }

}
