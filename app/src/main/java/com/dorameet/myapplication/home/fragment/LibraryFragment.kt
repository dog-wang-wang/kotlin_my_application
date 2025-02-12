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
import com.dorameet.myapplication.home.adapter.ArticleAdapter
import com.dorameet.myapplication.home.adapter.SortAdapter
import com.dorameet.myapplication.home.data.ArticleData
import com.dorameet.myapplication.home.data.ArticleResponse
import com.dorameet.myapplication.home.data.SortData
import com.dorameet.myapplication.utils.OkHttpUtils
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException


class LibraryFragment : Fragment(){
    var sortRecyclerView: RecyclerView? = null
    var articleRecyclerView: RecyclerView? = null
    var typeList:MutableList<SortData> = ArrayList()
    var chips:ChipGroup? = null
    var currentTypeId:Int = 0
    var currentLexile:Int = 5
    val articleList:MutableList<ArticleData> = ArrayList()
    var pageNum:Int? = null
    var pageSize :Int? = null
    var total:Int? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_library, container, false)
        sortRecyclerView = view.findViewById<RecyclerView>(R.id.rv_sort)
        articleRecyclerView = view.findViewById<RecyclerView>(R.id.rv_article)
        chips = view.findViewById<ChipGroup>(R.id.grade_chips)
        typeList.add(SortData(0,"精选阅读"))
        //在这里还要发送网络请求
        sortRecyclerView?.adapter  = SortAdapter(typeList,context,R.layout.item_sort)
        initData()
        sortRecyclerView?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        //在这里要请求文章数据
        articleRecyclerView?.adapter  = ArticleAdapter(articleList, context,R.layout.item_article)
        initArticleData()
        articleRecyclerView?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        return view
    }

    private fun initArticleData() {
        getArticleList()
    }

    private val okHttpUtils: OkHttpUtils = OkHttpUtils()

    private fun initData() {
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
                            a?.let {
                                typeList.addAll(it)
                            }
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
        val requestGrade = okHttpUtils.createBuilder("http://test.shiqu.zhilehuo.com", "/englishgpt/appArticle/selectList").addHeader("Cookie", "sid=OFvEbpyl4PyKkc/cSjl2tW3g5Ga/z5DPSQRGQn8mJBs=").get().build()
        okHttpUtils.sendRequest(requestGrade, object : Callback{
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
                        val data:com.dorameet.myapplication.Result<MutableList<Int>> = gson.fromJson(responseData, (object:TypeToken<com.dorameet.myapplication.Result<MutableList<Int>>>(){}).type)
                        //在这里遍历集合
                        val a = data.getData()
                        a?.forEach {
                            val chip = com.google.android.material.chip.Chip(context)
                            chip.text = it.toString()
                            chip.isCheckable = true
                            chip.isCheckedIconVisible = false
                            chip.chipBackgroundColor = context?.getColorStateList(R.color.color_white)
                            chip.chipCornerRadius = 100f
                            chip.chipStrokeColor = context?.getColorStateList(R.color.color_color_no)
                            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                                if (isChecked) {
                                    //选中
                                    chip.setTextColor(context?.getColor(R.color.color_blue)!!)
                                    chip.chipStrokeColor = context?.getColorStateList(R.color.color_blue)
                                } else {
                                    //取消选中
                                    chip.setTextColor(context?.getColor(R.color.color_grey_big)!!)
                                    chip.chipStrokeColor = context?.getColorStateList(R.color.color_color_no)
                                }
                            }
                            //然后展示数据
                            activity?.runOnUiThread{
                                chips?.addView(chip)
                            }
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
    private fun getArticleList(){
        val hashMap:HashMap<String,Int> = HashMap()
        hashMap["lexile"] = currentLexile
        hashMap["typeId"] = currentTypeId
        val requestArticle = okHttpUtils.createBuilder("http://test.shiqu.zhilehuo.com", "/englishgpt/library/articleList",hashMap).addHeader("Cookie", "sid=OFvEbpyl4PyKkc/cSjl2tW3g5Ga/z5DPSQRGQn8mJBs=").get().build()
        okHttpUtils.sendRequest(requestArticle, object : Callback{
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
                        //这里获取到最新的数据
                        val data:com.dorameet.myapplication.Result<ArticleResponse> = gson.fromJson(responseData, (object:TypeToken<com.dorameet.myapplication.Result<ArticleResponse>>(){}).type)
                        //然后把数据添加到list当中
                        //并且更新现在的页号和总页数以及业内数据条数
                        pageNum = data.getData()?.pages
                        pageSize = data.getData()?.pageSize
                        total = data.getData()?.total
                        activity?.runOnUiThread{
                            data.getData()?.list?.let {
                                articleList.addAll(it)
                            }
                            (articleRecyclerView?.adapter as ArticleAdapter).notifyChange()
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