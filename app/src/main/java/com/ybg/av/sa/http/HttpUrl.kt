package com.ybg.av.sa.http

/**
 * 网络请求相关设置,配置请求地址及参数
 */
object HttpUrl {

    private val debug = false

    //开发服务器地址
    val API_HOST_DEBUG = "http://192.168.12.99:8080/ma"
    //生产服务器地址
    val API_HOST_PRODUCT = "https://139.224.186.241:8443/ma"

    val ROOT_URL = if (debug) API_HOST_DEBUG else API_HOST_PRODUCT

    //悦美榜
    val listAtURL: String
        get() = ROOT_URL + "/videoInfo/listAt"

}
