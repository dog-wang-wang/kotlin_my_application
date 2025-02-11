package com.dorameet.myapplication.home.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.dorameet.myapplication.R
import com.dorameet.myapplication.home.adapter.SortAdapter
import com.dorameet.myapplication.home.data.SortData
import com.dorameet.myapplication.utils.OkHttpUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException


class LibraryFragment : Fragment(){
    var sortRecyclerView: RecyclerView? = null
    var typeList:MutableList<SortData> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_library, container, false)
        sortRecyclerView = view.findViewById<RecyclerView>(R.id.rv_sort)
        typeList.add(SortData(0,"精选阅读"))
        //在这里还要发送网络请求
        initData()
        sortRecyclerView?.adapter  = SortAdapter(typeList,context,R.layout.item_sort)
        sortRecyclerView?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        return view
    }

    private fun initData() {
        //使用OkHttp发送网络请求
        val okHttpUtils = OkHttpUtils()
        //第一个请求用来请求文章类型
        val request = okHttpUtils.createBuilder("http://test.shiqu.zhilehuo.com", "/englishgpt/library/articleTypeList").addHeader("Cookie", "sid=OFvEbpyl4PyKkc/cSjl2tW3g5Ga/z5DPSQRGQn8mJBs=").get().build()
        okHttpUtils.sendRequest(request, object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                showToast(e.message.toString())
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call, response: Response) {
                //这里接收网络数据
                if (response.code==200) {
                    val responseData = response.body?.string()
                    if (responseData != null) {
                        //在这里处理数据
                        val gson = Gson()
                        val data:com.dorameet.myapplication.Result<MutableList<SortData>> = gson.fromJson(responseData, (object:TypeToken<com.dorameet.myapplication.Result<MutableList<SortData>>>(){}).type)
                        //然后展示数据
                        activity?.runOnUiThread{
                            val a = data.getData()
                            a?.let { typeList.addAll(it) }
                            sortRecyclerView?.adapter?.notifyDataSetChanged()
                        }
                    }else{
                        showToast("网络请求失败")
                    }
                }else{
                    showToast(response.message)
                }
            }
            fun showToast(text:String){
                activity?.runOnUiThread{
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                }
            }
        })
        //这个请求用来请求分数值

    }
}