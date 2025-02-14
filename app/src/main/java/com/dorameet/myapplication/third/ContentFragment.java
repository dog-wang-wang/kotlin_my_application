package com.dorameet.myapplication.third;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.dorameet.myapplication.R;
import com.dorameet.myapplication.third.data.SentenceData;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * @author 11230
 */
public class ContentFragment extends Fragment {
    private TextView tvArticleContent;
    private ImageView ivCover;
    private MaterialButton btnSound;
    private MaterialButton btnBegin;
    private MaterialButton btnRepeat;
    private String content;
    private String imgUrl;
    private List<SentenceData> sentenceSplit;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            content = getArguments().getString(context.getString(R.string.fragment_param_content));
            imgUrl = getArguments().getString(context.getString(R.string.fragment_param_imgUrl));
            String sentenceMessage = getArguments().getString(context.getString(R.string.fragment_param_sentence_split));
            sentenceSplit = new GsonBuilder().setLenient().create().fromJson(sentenceMessage, new TypeToken<List<SentenceData>>() {}.getType());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        btnSound = view.findViewById(R.id.third_btn_sound);
        btnBegin = view.findViewById(R.id.third_btn_begin);
        btnRepeat = view.findViewById(R.id.third_btn_repeat);
        ivCover = view.findViewById(R.id.iv_content_background);
        tvArticleContent = view.findViewById(R.id.tv_article_content);
        tvArticleContent.setText(content);
        Glide.with(this).load(imgUrl).error(R.drawable.image_dialog).into(ivCover);
        //这里还要设置句子的播放事件
        return view;
    }
}
