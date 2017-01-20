package com.ybg.av.sa.http.listener

import com.ybg.av.sa.http.Model.Progress

interface ProgressListener {
    fun onProgress(progress: Progress)
}