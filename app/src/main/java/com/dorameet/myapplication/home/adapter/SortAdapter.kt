package com.dorameet.myapplication.home.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dorameet.myapplication.R
import com.dorameet.myapplication.home.data.SortData

/**
 * @author 11230
 */
class SortAdapter(
    private val mList: List<SortData>,
    private val mContext: Context,
    private val layoutId: Int,
    var onTypeItemClickListener: OnTypeItemClickListener
) :
    RecyclerView.Adapter<SortViewHolder>() {
    private var viewHolder: SortViewHolder? = null

    interface OnTypeItemClickListener {
        fun onItemClick(position: Int)
    }

    var selectedPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortViewHolder {
        val view = View.inflate(mContext, layoutId, null)
        viewHolder = SortViewHolder(view)
        return viewHolder as SortViewHolder
    }

    override fun onBindViewHolder(holder: SortViewHolder, position: Int) {
        val sortData = mList[position]
        val a = position
        // 设置条目背景颜色
        if (position == selectedPosition) {
            // 选中状态背景色
            holder.rootView.setBackgroundResource(R.color.color_white)
            //还要更改字体颜色
            holder.tvType.setTextColor(mContext.getColor(R.color.color_blue_mid))
            holder.colorView.visibility = View.VISIBLE
        } else {
            // 未选中状态背景色，需要您定义这个颜色资源
            holder.rootView.setBackgroundResource(R.color.color_color_no)
            holder.tvType.setTextColor(mContext.getColor(R.color.color_grey_big))
            holder.colorView.visibility = View.INVISIBLE
        }
        //设置第一个图标
        if (sortData.id == 0) {
            holder.imageView.setImageResource(R.mipmap.ic_recommand)
        }
        //设置内容
        holder.tvType.text = sortData.type
        //设定点击事件
        holder.rootView.setOnClickListener { //首先在这里更改selectedPosition
            selectedPosition = a
            //同时还要更改fragment中的typeId
            onTypeItemClickListener.onItemClick(sortData.id)
            //然后通知数据源更新
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}