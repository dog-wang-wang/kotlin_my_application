package com.dorameet.myapplication.home.adapter

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.dorameet.myapplication.R

/**
 * @author 11230
 */
class SortViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvType: TextView = itemView.findViewById(R.id.tv_sort)
    var colorView: LinearLayout = itemView.findViewById(R.id.ll_tag)
    var imageView: ImageView = itemView.findViewById(R.id.iv_sort)

    var rootView: ConstraintLayout = itemView.findViewById(R.id.root_library)
}