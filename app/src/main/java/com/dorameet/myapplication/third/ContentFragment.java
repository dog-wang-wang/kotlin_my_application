package com.dorameet.myapplication.third;

import android.content.Context;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.dorameet.myapplication.R;
import com.dorameet.myapplication.third.data.SentenceData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.RangeSlider;
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
    private SoundManagerForThird soundManagerForThird;
    private String audioUrl;
    private SpannableStringBuilder spannableStringBuilder;
    private ForegroundColorSpan foregroundColorSpan;
    private ForegroundColorSpan originColorSpan;
    private int hasRead = 0;
    private Thread thread;
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull android.os.Message msg) {
            super.handleMessage(msg);
            //在这里进行更新
            getActivity().runOnUiThread(()->{
                spannableStringBuilder.setSpan(foregroundColorSpan, 0, hasRead, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvArticleContent.setText(spannableStringBuilder);
            });
        }
    };
    private LinearLayout llSound;
    private RangeSlider rsSound;

    public ContentFragment(SoundManagerForThird soundManagerForThird){
        this.soundManagerForThird = soundManagerForThird;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            content = getArguments().getString(context.getString(R.string.fragment_param_content));
            imgUrl = getArguments().getString(context.getString(R.string.fragment_param_imgUrl));
            audioUrl = getArguments().getString(context.getString(R.string.fragment_param_audioUrl));
            String sentenceMessage = getArguments().getString(context.getString(R.string.fragment_param_sentence_split));
            sentenceSplit = new GsonBuilder().setLenient().create().fromJson(sentenceMessage, new TypeToken<List<SentenceData>>() {}.getType());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        initViews(view);
        initEvents();
        tvArticleContent.setText(content);
        Glide.with(this).load(imgUrl).error(R.drawable.image_dialog).into(ivCover);
        spannableStringBuilder = new SpannableStringBuilder();
        //把文本加入到这个控制器当中
        spannableStringBuilder.append(content);
        foregroundColorSpan = new ForegroundColorSpan(getContext().getColor(R.color.color_purple));
        originColorSpan = new ForegroundColorSpan(getContext().getColor(R.color.color_black));
        //这里还要设置句子的播放事件
        return view;
    }

    private void initEvents() {
        btnBegin.setOnClickListener(v -> {
            if (soundManagerForThird.getContentSoundMediaPlayer().isPlaying()){
                soundManagerForThird.pauseContentSound();
            }else {
                soundManagerForThird.restartContentSound();
            }
        });
        btnRepeat.setOnClickListener(v -> {
            //在这里停止原来的检测线程
            spannableStringBuilder = new SpannableStringBuilder();
            //把文本加入到这个控制器当中
            spannableStringBuilder.append(content);
            resetTvArticleContentColor();
            if (thread != null){
                thread.interrupt();
            }
            thread = new Thread(()->{
            soundManagerForThird.repeatContentSound();
                //然后开启一个新的线程
                checkTime();
            });
            thread.start();
        });
        btnSound.setOnClickListener(v->{
            //用于显示出来音量控制条
            if (llSound.getVisibility() == View.VISIBLE) {
                llSound.setVisibility(View.INVISIBLE);
            }else {
                llSound.setVisibility(View.VISIBLE);
            }
        });
        rsSound.setValues(100f);
        rsSound.addOnChangeListener((slider, value, fromUser) -> {
            soundManagerForThird.getBackgroundSoundMediaPlayer().setVolume(value/100, value/100);
        });
    }

    private void initViews(View view) {
        btnSound = view.findViewById(R.id.third_btn_sound);
        btnBegin = view.findViewById(R.id.third_btn_begin);
        btnRepeat = view.findViewById(R.id.third_btn_repeat);
        ivCover = view.findViewById(R.id.iv_content_background);
        tvArticleContent = view.findViewById(R.id.tv_article_content);
        llSound = view.findViewById(R.id.ll_sound);
        rsSound = view.findViewById(R.id.range_slider);
    }

    @Override
    public void onStart() {
        super.onStart();
        //在这里开始阅读
        //然后要加上一个监听器，监听播放时间，然后根据播放时间设置文本的显示颜色
        thread =  new Thread(()->{
            soundManagerForThird.playContentSound(audioUrl, mp -> {
                //当播放完成之后要重置颜色
                getActivity().runOnUiThread(()->{
                    resetTvArticleContentColor();
                });
            });
            checkTime();
        });
        thread.start();
    }

    private void checkTime() {
        for(SentenceData sentenceData : sentenceSplit){
            //一句一句的执行
            while(true) {
                try {
                    int currentTime = soundManagerForThird.getContentSoundMediaPlayer().getCurrentPosition();
                    if (currentTime >= sentenceData.getWb()){
                        int length = sentenceData.getWord().length();
                        hasRead+= length;
                        if (hasRead >=content.length()){
                            hasRead = content.length();
                        }
                        handler.sendMessage(Message.obtain());
                        break;
                    }
                } catch (Exception e) {
                    //这段音频已经播放完成了
                    Log.e("aaaa", "这个音频已经播放完成了");
                    break;
                }
            }
        }
    }

    private void resetTvArticleContentColor() {
        spannableStringBuilder.setSpan(originColorSpan, 0, hasRead, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvArticleContent.setText(spannableStringBuilder);
        hasRead = 0;
    }
}
