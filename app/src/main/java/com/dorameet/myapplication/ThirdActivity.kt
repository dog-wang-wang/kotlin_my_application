package com.dorameet.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.dorameet.myapplication.data.Result
import com.dorameet.myapplication.home.data.SortData
import com.dorameet.myapplication.third.ContentFragment
import com.dorameet.myapplication.third.ContentFragmentAdapter
import com.dorameet.myapplication.third.data.Content
import com.dorameet.myapplication.utils.OkHttpUtils
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class ThirdActivity : AppCompatActivity() {
    var tvBarTitle: TextView? = null
    var viewPager: ViewPager2? = null
    var progressBar:LinearProgressIndicator? = null
    var tvNumerator: TextView? = null
    var tvDenominator: TextView? = null
    var dataList : ArrayList<Fragment> = ArrayList()
    var page:Int =1;
    var sumPage:Int = 1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_third)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.third)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initViews()
        //获取文本信息
        initData()
        viewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                page = position+1
                if (sumPage!=1) {
//                    Toast.makeText(this@ThirdActivity, "已加载完所有数据$sumPage", Toast.LENGTH_SHORT).show()
                    progressBar?.setProgress(100*page/sumPage)
                    tvNumerator?.setText(page.toString())
                }
            }
        })
    }

    private fun initData() {
        //第一个请求用来请求文章类型
        val okHttpUtils = OkHttpUtils()
        val hash: HashMap<String,Int> = HashMap()
        hash["aid"] = 6
        val request = okHttpUtils.createBuilder("http://test.shiqu.zhilehuo.com", "/knowledge/article/getArticleDetail", hash).addHeader("Cookie", "sid=i5VMMK2c7EEm5qK597kJeDqrel7NKCRqSQRGQn8mJBs=").get().build()
        okHttpUtils.sendRequest(request, object : Callback {
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
                        val data: Result<Content> = gson.fromJson(responseData, (object: TypeToken<Result<Content>>(){}).type)
                        //然后展示数据
                        runOnUiThread{
                            sumPage = data.getData()?.readCount!!
                            tvBarTitle?.setText(data.getData()?.title)
                            tvNumerator?.setText(page.toString())
                            tvDenominator?.setText(data.getData()?.readCount.toString())
                            progressBar?.setProgress(100/sumPage)
                            //然后还要创建出来这五个文章
                            var num: Int = 0
                            data.getData()?.contentList?.forEach {
                                val fragment = ContentFragment()
                                fragment.setArguments(Bundle().apply {
                                    // 这里要传两个参数，一个是文章内容，一个是图片地址
                                    putString(getString(R.string.fragment_param_content), it.sentence)
                                    putString(getString(R.string.fragment_param_imgUrl), data.getData()?.imgList?.get(num))
                                    //还需要传递时间切片信息
                                    putString(getString(R.string.fragment_param_sentence_split), it.sentenceByXFList.toString())
                                })
                                dataList.add(fragment)
                                num++
                            }
                            viewPager?.adapter = ContentFragmentAdapter(this@ThirdActivity, dataList)
                        }
                    }else{
                        showToast("网络请求失败")
                    }
                }else{
                    showToast(response.message)
                }
            }
            fun showToast(text:String){
                runOnUiThread{
                    Toast.makeText(this@ThirdActivity, text, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initViews() {
        tvBarTitle = findViewById<TextView>(R.id.third_message_textView)
        viewPager = findViewById<ViewPager2>(R.id.viewPager)
        progressBar = findViewById<LinearProgressIndicator>(R.id.third_linear_progress)
        tvNumerator = findViewById<TextView>(R.id.tv_numerator)
        tvDenominator = findViewById<TextView>(R.id.tv_denominator)
    }
}