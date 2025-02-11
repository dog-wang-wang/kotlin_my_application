package com.dorameet.myapplication.utils

import android.content.Context
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class OkHttpUtils() {
    /**创建客户端实例 */
    var okHttpClient: OkHttpClient? = null
    var context: Context? = null

    init {
        if (okHttpClient == null) {
            okHttpClient = OkHttpClient.Builder()
                .build()
        }
    }

    @Throws(IOException::class)
    fun sendRequest(request: Request): Response {
        val call = okHttpClient!!.newCall(request)
        return call.execute()
    }

    fun sendRequest(request: Request, callback: Callback): Call {
        val call = okHttpClient!!.newCall(request)
        call.enqueue(callback)
        return call
    }

    fun createBuilder(ip: String, path: String): Request.Builder {
        val builder = Request.Builder()
        return builder.url(ip + path)
    }

    fun createBuilder(ip: String, path: String, params: HashMap<String?, *>): Request.Builder {
        val builder = Request.Builder()
        val url = ip + path
        val sb = StringBuilder()
        sb.append(url)
        sb.append("?")
        for (key in params.keys) {
            sb.append(key)
            sb.append("=")
            sb.append(params[key])
            sb.append("&")
        }
        val index = sb.lastIndexOf("&")
        sb.replace(index, index + 1, "")
        return builder.url(sb.toString())
    }

    fun createBuilderWithHeader(ip: String, path: String): Request.Builder {
        val builder = Request.Builder()
        return builder.url(ip + path)
    }

    fun createBuilderWithHeader(
        ip: String,
        path: String,
        params: HashMap<String?, *>
    ): Request.Builder {
        val builder = Request.Builder()
        val url = ip + path
        val sb = StringBuilder()
        sb.append(url)
        sb.append("?")
        for (key in params.keys) {
            sb.append(key)
            sb.append("=")
            sb.append(params[key])
            sb.append("&")
        }
        val index = sb.lastIndexOf("&")
        sb.replace(index, index + 1, "")
        return builder.url(sb.toString())
    }

    fun createBuilderWithHeader(ip: String, path: String, params: List<String?>): Request.Builder {
        val builder = Request.Builder()
        val url = ip + path
        val sb = StringBuilder()
        sb.append(url)
        sb.append("/")
        for (key in params) {
            sb.append(key)
            sb.append("/")
        }
        val index = sb.lastIndexOf("/")
        sb.replace(index, index + 1, "")
        return builder.url(sb.toString())
    }
}