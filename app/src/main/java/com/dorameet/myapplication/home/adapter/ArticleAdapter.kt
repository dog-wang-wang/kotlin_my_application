package com.dorameet.myapplication.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dorameet.myapplication.R
import com.dorameet.myapplication.home.data.ArticleData
import java.util.Arrays

class ArticleAdapter(
    private val mList: List<ArticleData>,
    private val mContext: Context,
    private val layoutId: Int
) :
    RecyclerView.Adapter<ArticleViewHolder>() {
    private var viewHolder: ArticleViewHolder? = null
    private val colorArray: List<Int> = Arrays.asList(
        R.color.color_FF44D7B6,
        R.color.color_FF01AAA2,
        R.color.color_FF7C7EE4,
        R.color.color_FF32C5FF,
        R.color.color_FF6DD400,
        R.color.color_FFFF9500,
        R.color.color_FFD4B07E,
        R.color.color_FFF56178,
        R.color.color_FF7BD2D8,
        R.color.color_FFCC95D9,
        R.color.color_FF3DBC80,
        R.color.color_FFDD4ABC,
        R.color.color_FFA38C42,
        R.color.color_FF077547
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = View.inflate(mContext, layoutId, null)
        viewHolder = ArticleViewHolder(view)
        return viewHolder as ArticleViewHolder
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val articleData = mList[position]
        //设置内容
        holder.tvArticleTitle.text = articleData.title
        holder.tvWordNum.text = articleData.wordNum.toString() + "词"
        holder.tvDifficulty.text = articleData.lexile.toString()
        holder.tvArticleType.text = articleData.type
        // 根据 articleData.getTypeId() 设置背景颜色
        // 创建一个 GradientDrawable 对象
        val gradientDrawable = GradientDrawable()
        // 设置渐变颜色（只设置一个颜色）
        gradientDrawable.setColor(
            ContextCompat.getColor(
                mContext,
                colorArray[articleData.typeId - 1]
            )
        )
        // 设置形状为矩形
        gradientDrawable.shape = GradientDrawable.RECTANGLE
        // 为每个角设置半径数组
        // 数组中的值依次对应：左上, 右上, 右下, 左下
        // 但是我们只想要左上角和右下角是圆角，所以应该这样设置：
        // 左上(20,20), 右上(0,0), 右下(20,20), 左下(0,0)
        val cornerRadii = FloatArray(8)
        // 左上 x 半径
        // 左上 y 半径
        // 右下 x 半径
        // 右下 y 半径
        cornerRadii[0] = 40f
        cornerRadii[1] = 40f
        cornerRadii[4] = 40f
        cornerRadii[5] = 40f
        // 右上和左下的半径设置为0
        //setCornerRadii 方法接受的是一个 float 数组，数组长度为8，
        // 分别对应左上、右上、右下、左下的 x 和 y 半径。
        gradientDrawable.cornerRadii = cornerRadii
        // 将 GradientDrawable 设置为背景
        holder.llArticleType.background = gradientDrawable
        Glide.with(mContext).load(articleData.cover).into(holder.ivCoverImage)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notifyChange() {
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}