package com.dorameet.myapplication.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dorameet.myapplication.R;
import com.dorameet.myapplication.Result;
import com.dorameet.myapplication.home.data.SortData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 11230
 */
public class SortAdapter extends RecyclerView.Adapter<SortViewHolder> {
    private List<SortData> mList;
    private Context mContext;
    private int layoutId;
    private SortViewHolder viewHolder;
    public OnTypeItemClickListener onTypeItemClickListener;
    public interface OnTypeItemClickListener{
        void onItemClick(int position);
    }
    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    private int selectedPosition = 0;

    public SortAdapter(List<SortData> mList, Context mContext, int layoutId, OnTypeItemClickListener onTypeItemClickListener) {
        this.mList = mList;
        this.mContext = mContext;
        this.layoutId = layoutId;
        this.onTypeItemClickListener = onTypeItemClickListener;
    }

    @NonNull
    @Override
    public SortViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, layoutId, null);
        viewHolder = new SortViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SortViewHolder holder, int position) {
        SortData sortData = mList.get(position);
        int a = position;
        // 设置条目背景颜色
        if (position == selectedPosition) {
            // 选中状态背景色
            holder.getRootView().setBackgroundResource(R.color.color_white);
            //还要更改字体颜色
            holder.getTvType().setTextColor(mContext.getColor(R.color.color_blue_mid));
            holder.getColorView().setVisibility(View.VISIBLE);
        } else {
            // 未选中状态背景色，需要您定义这个颜色资源
            holder.getRootView().setBackgroundResource(R.color.color_color_no);
            holder.getTvType().setTextColor(mContext.getColor(R.color.color_grey_big));
            holder.getColorView().setVisibility(View.INVISIBLE);
        }
        //设置第一个图标
        if (sortData.getId()==0){
            holder.getImageView().setImageResource(R.mipmap.ic_recommand);
        }
        //设置内容
        holder.getTvType().setText(sortData.getType());
        //设定点击事件
        holder.getRootView().setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                //首先在这里更改selectedPosition
                selectedPosition = a;
                //同时还要更改fragment中的typeId
                onTypeItemClickListener.onItemClick(sortData.getId());
                //然后通知数据源更新
                notifyDataSetChanged();
            }
        });

    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
}
