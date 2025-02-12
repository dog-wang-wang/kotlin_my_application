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
class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvArticleType: TextView = itemView.findViewById(R.id.tv_article_type)
    var tvArticleTitle: TextView = itemView.findViewById(R.id.tv_article_title)
    var ivCoverImage: ImageView = itemView.findViewById(R.id.iv_article)
    var tvWordNum: TextView = itemView.findViewById(R.id.tv_article_word_num)
    var tvDifficulty: TextView = itemView.findViewById(R.id.tv_article_difficulty)
    var llArticleType: LinearLayout = itemView.findViewById(R.id.ll_conner)
}