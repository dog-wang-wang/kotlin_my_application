package com.dorameet.myapplication.third

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dorameet.myapplication.R
import com.dorameet.myapplication.third.data.SentenceData
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.RangeSlider
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * @author 11230
 */
class ContentFragment(private val soundManagerForThird: SoundManagerForThird) : Fragment() {
    private var tvArticleContent: TextView? = null
    private var ivCover: ImageView? = null
    private var btnSound: MaterialButton? = null
    private var btnBegin: MaterialButton? = null
    private var btnRepeat: MaterialButton? = null
    private var content: String? = null
    private var imgUrl: String? = null
    private var sentenceSplit: List<SentenceData>? = null
    private var audioUrl: String? = null
    private var spannableStringBuilder: SpannableStringBuilder? = null
    private var foregroundColorSpan: ForegroundColorSpan? = null
    private var originColorSpan: ForegroundColorSpan? = null
    private var hasRead = 0
    private var thread: Thread? = null
    private val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            //在这里进行更新
            activity?.runOnUiThread {
                spannableStringBuilder!!.setSpan(
                    foregroundColorSpan,
                    0,
                    hasRead,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                tvArticleContent!!.text = spannableStringBuilder
            }
        }
    }
    private var llSound: LinearLayout? = null
    private var rsSound: RangeSlider? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (arguments != null) {
            content = requireArguments().getString(context.getString(R.string.fragment_param_content))
            imgUrl = requireArguments().getString(context.getString(R.string.fragment_param_imgUrl))
            audioUrl = requireArguments().getString(context.getString(R.string.fragment_param_audioUrl))
            val sentenceMessage =
                requireArguments().getString(context.getString(R.string.fragment_param_sentence_split))
            sentenceSplit = GsonBuilder().setLenient().create()
                .fromJson(sentenceMessage, object : TypeToken<List<SentenceData?>?>() {}.type)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_content, container, false)
        initViews(view)
        initEvents()
        tvArticleContent!!.text = content
        Glide.with(this).load(imgUrl).error(R.drawable.image_dialog).into(ivCover!!)
        spannableStringBuilder = SpannableStringBuilder()
        //把文本加入到这个控制器当中
        spannableStringBuilder!!.append(content)
        foregroundColorSpan = ForegroundColorSpan(requireContext().getColor(R.color.color_purple))
        originColorSpan = ForegroundColorSpan(requireContext().getColor(R.color.color_black))
        //这里还要设置句子的播放事件
        return view
    }

    private fun initEvents() {
        btnBegin!!.setOnClickListener { v: View? ->
            if (soundManagerForThird.contentSoundMediaPlayer?.isPlaying == true) {
                soundManagerForThird.pauseContentSound()
            } else {
                soundManagerForThird.restartContentSound()
            }
        }
        btnRepeat!!.setOnClickListener { v: View? ->
            //在这里停止原来的检测线程
            spannableStringBuilder = SpannableStringBuilder()
            //把文本加入到这个控制器当中
            spannableStringBuilder!!.append(content)
            resetTvArticleContentColor()
            if (thread != null) {
                thread!!.interrupt()
            }
            thread = Thread {
                soundManagerForThird.repeatContentSound()
                //然后开启一个新的线程
                checkTime()
            }
            thread!!.start()
        }
        btnSound!!.setOnClickListener { v: View? ->
            //用于显示出来音量控制条
            if (llSound!!.visibility == View.VISIBLE) {
                llSound!!.visibility = View.INVISIBLE
            } else {
                llSound!!.visibility = View.VISIBLE
            }
        }
        rsSound!!.setValues(100f)
        rsSound!!.addOnChangeListener(RangeSlider.OnChangeListener { slider: RangeSlider?, value: Float, fromUser: Boolean ->
            soundManagerForThird.backgroundSoundMediaPlayer?.setVolume(value / 100, value / 100)
        })
    }

    private fun initViews(view: View) {
        btnSound = view.findViewById(R.id.third_btn_sound)
        btnBegin = view.findViewById(R.id.third_btn_begin)
        btnRepeat = view.findViewById(R.id.third_btn_repeat)
        ivCover = view.findViewById(R.id.iv_content_background)
        tvArticleContent = view.findViewById(R.id.tv_article_content)
        llSound = view.findViewById(R.id.ll_sound)
        rsSound = view.findViewById(R.id.range_slider)
    }

    override fun onStart() {
        super.onStart()
        //在这里开始阅读
        //然后要加上一个监听器，监听播放时间，然后根据播放时间设置文本的显示颜色
        thread = Thread {
            soundManagerForThird.playContentSound(
                audioUrl
            ) { mp: MediaPlayer? ->
                //当播放完成之后要重置颜色
                requireActivity().runOnUiThread {
                    resetTvArticleContentColor()
                }
            }
            checkTime()
        }
        thread!!.start()
    }

    private fun checkTime() {
        for (sentenceData in sentenceSplit!!) {
            //一句一句的执行
            while (true) {
                try {
                    val currentTime = soundManagerForThird.contentSoundMediaPlayer?.currentPosition
                    if (currentTime != null) {
                        if (currentTime >= sentenceData.wb!!) {
                            val length = sentenceData.word!!.length
                            hasRead += length
                            if (hasRead >= content!!.length) {
                                hasRead = content!!.length
                            }
                            handler.sendMessage(Message.obtain())
                            break
                        }
                    }
                } catch (e: Exception) {
                    //这段音频已经播放完成了
                    Log.e("aaaa", "这个音频已经播放完成了")
                    break
                }
            }
        }
    }

    private fun resetTvArticleContentColor() {
        spannableStringBuilder!!.setSpan(
            originColorSpan,
            0,
            hasRead,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvArticleContent!!.text = spannableStringBuilder
        hasRead = 0
    }
}