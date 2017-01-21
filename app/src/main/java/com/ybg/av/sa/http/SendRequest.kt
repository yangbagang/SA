package com.ybg.av.sa.http

import android.content.Context
import com.ybg.av.sa.http.callback.OkCallback

object SendRequest {

    fun getVideoList(tag: Context, catalog: String, pageNum: Int, pageSize: Int, callback:
    OkCallback<*>) {
        val params = mapOf<String, String>("keyInfo" to catalog, "pageNum" to "$pageNum", "pageSize"
                to "$pageSize")
        OkHttpProxy.post(HttpUrl.listAtURL, tag, params, callback)
    }

}
