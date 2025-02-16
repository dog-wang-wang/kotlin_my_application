package com.dorameet.myapplication.third

import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener

/**
 * @author 11230
 */
class SoundManagerForThird {
    var backgroundSoundMediaPlayer: MediaPlayer? = null
        private set
    var contentSoundMediaPlayer: MediaPlayer? = null
        private set
    private var currentContentSoundUrl: String? = null
    private val onCompletionListener: OnCompletionListener? = null

    //然后分别写两个函数用于控制两个播放器
    fun playBackgroundSound(url: String?) {
        if (backgroundSoundMediaPlayer == null) {
            backgroundSoundMediaPlayer = MediaPlayer()
        }
        try {
            backgroundSoundMediaPlayer!!.reset()
            backgroundSoundMediaPlayer!!.setDataSource(url)
            backgroundSoundMediaPlayer!!.prepare()
            backgroundSoundMediaPlayer!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun playContentSound(url: String?, onCompletionListener: OnCompletionListener?) {
        if (contentSoundMediaPlayer == null) {
            contentSoundMediaPlayer = MediaPlayer()
        } else {
            contentSoundMediaPlayer!!.stop()
            contentSoundMediaPlayer!!.release()
            contentSoundMediaPlayer = MediaPlayer()
        }
        currentContentSoundUrl = url
        contentSoundMediaPlayer!!.setOnCompletionListener(onCompletionListener)
        try {
            contentSoundMediaPlayer!!.reset()
            contentSoundMediaPlayer!!.setDataSource(url)
            contentSoundMediaPlayer!!.prepare()
            contentSoundMediaPlayer!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun pauseContentSound() {
        if (contentSoundMediaPlayer != null) {
            contentSoundMediaPlayer!!.pause()
        }
    }

    fun stopContentSound() {
        if (contentSoundMediaPlayer != null) {
            contentSoundMediaPlayer!!.stop()
        }
    }

    fun restartContentSound() {
        if (contentSoundMediaPlayer != null) {
            contentSoundMediaPlayer!!.start()
        }
    }

    fun repeatContentSound() {
        playContentSound(currentContentSoundUrl, onCompletionListener)
    }

    fun releaseBackgroundSound() {
        if (backgroundSoundMediaPlayer != null) {
            if (backgroundSoundMediaPlayer!!.isPlaying) {
                backgroundSoundMediaPlayer!!.stop()
            }
            backgroundSoundMediaPlayer!!.release()
            backgroundSoundMediaPlayer = null
        }
    }

    fun releaseContentSound() {
        if (contentSoundMediaPlayer != null) {
            if (contentSoundMediaPlayer!!.isPlaying) {
                contentSoundMediaPlayer!!.stop()
            }
            contentSoundMediaPlayer!!.release()
            contentSoundMediaPlayer = null
        }
    }

    fun release() {
        releaseBackgroundSound()
        releaseContentSound()
    }
}