package com.dorameet.myapplication.home.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dorameet.myapplication.R;

/**
 * @author 11230
 */
public class SortViewHolder extends RecyclerView.ViewHolder {
    private TextView tvType;
    private LinearLayout colorView;
    private ImageView imageView;

    public ConstraintLayout getRootView() {
        return rootView;
    }

    public void setRootView(ConstraintLayout rootView) {
        this.rootView = rootView;
    }

    private ConstraintLayout rootView;

    public TextView getTvType() {
        return tvType;
    }

    public void setTvType(TextView tvType) {
        this.tvType = tvType;
    }

    public LinearLayout getColorView() {
        return colorView;
    }

    public void setColorView(LinearLayout colorView) {
        this.colorView = colorView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public SortViewHolder(@NonNull View itemView) {
        super(itemView);
        tvType = itemView.findViewById(R.id.tv_sort);
        colorView = itemView.findViewById(R.id.ll_tag);
        imageView = itemView.findViewById(R.id.iv_sort);
        rootView = itemView.findViewById(R.id.root_library);
    }
}
