package com.ybg.av.sa.http.parser

import java.io.IOException

import okhttp3.Response

class OkStringParser : OkBaseParser<String>() {

    @Throws(IOException::class)
    public override fun parse(response: Response): String {

        if (response.isSuccessful) {
            return response.body().string()
        }

        return ""
    }
}