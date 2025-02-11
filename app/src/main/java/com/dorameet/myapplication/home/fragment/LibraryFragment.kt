package com.dorameet.myapplication.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.dorameet.myapplication.R
import com.dorameet.myapplication.home.adapter.SortAdapter
import com.dorameet.myapplication.home.data.SortData

class LibraryFragment : Fragment(){
    var sortRecyclerView: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_library, container, false)
        //在这里还要通过协程去发送网络请求获取数据
        sortRecyclerView = view.findViewById<RecyclerView>(R.id.rv_sort)
        sortRecyclerView?.adapter  = SortAdapter(listOf(SortData(0, "精选阅读"),SortData(1, "精选阅读")),context,R.layout.item_sort)
        sortRecyclerView?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        return view
    }
}