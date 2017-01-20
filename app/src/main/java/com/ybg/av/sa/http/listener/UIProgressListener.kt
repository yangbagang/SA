package com.ybg.av.sa.http.listener

import com.ybg.av.sa.http.Model.Progress

interface UIProgressListener {

    fun onUIProgress(progress: Progress)

    fun onUIStart()

    fun onUIFinish()
}
