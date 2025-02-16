package com.dorameet.myapplication.third;

import android.media.MediaPlayer;

/**
 * @author 11230
 */
public class SoundManagerForThird {
    private MediaPlayer backgroundSoundMediaPlayer;
    private MediaPlayer contentSoundMediaPlayer;
    private String currentContentSoundUrl;
    private MediaPlayer.OnCompletionListener onCompletionListener;

    public MediaPlayer getBackgroundSoundMediaPlayer() {
        return backgroundSoundMediaPlayer;
    }

    public MediaPlayer getContentSoundMediaPlayer() {
        return contentSoundMediaPlayer;
    }

    //然后分别写两个函数用于控制两个播放器
    public void playBackgroundSound(String url) {
        if (backgroundSoundMediaPlayer == null) {
            backgroundSoundMediaPlayer = new MediaPlayer();
        }
        try {
            backgroundSoundMediaPlayer.reset();
            backgroundSoundMediaPlayer.setDataSource(url);
            backgroundSoundMediaPlayer.prepare();
            backgroundSoundMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void playContentSound(String url, MediaPlayer.OnCompletionListener onCompletionListener) {
        if (contentSoundMediaPlayer == null) {
            contentSoundMediaPlayer = new MediaPlayer();
        }else{
            contentSoundMediaPlayer.stop();
            contentSoundMediaPlayer.release();
            contentSoundMediaPlayer = new MediaPlayer();
        }
        currentContentSoundUrl = url;
        contentSoundMediaPlayer.setOnCompletionListener(onCompletionListener);
        try {
            contentSoundMediaPlayer.reset();
            contentSoundMediaPlayer.setDataSource(url);
            contentSoundMediaPlayer.prepare();
            contentSoundMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void pauseContentSound(){
        if (contentSoundMediaPlayer != null) {
            contentSoundMediaPlayer.pause();
        }
    }
    public void stopContentSound(){
        if (contentSoundMediaPlayer != null) {
            contentSoundMediaPlayer.stop();
        }
    }
    public void restartContentSound(){
        if (contentSoundMediaPlayer != null) {
            contentSoundMediaPlayer.start();
        }
    }
    public void repeatContentSound(){
        playContentSound(currentContentSoundUrl,onCompletionListener);
    }
    public void releaseBackgroundSound(){
        if (backgroundSoundMediaPlayer != null) {
            if (backgroundSoundMediaPlayer.isPlaying()) {
                backgroundSoundMediaPlayer.stop();
            }
            backgroundSoundMediaPlayer.release();
            backgroundSoundMediaPlayer = null;
        }
    }
    public void releaseContentSound(){
        if (contentSoundMediaPlayer != null) {
            if (contentSoundMediaPlayer.isPlaying()) {
                contentSoundMediaPlayer.stop();
            }
            contentSoundMediaPlayer.release();
            contentSoundMediaPlayer = null;
        }
    }
    public void release(){
        releaseBackgroundSound();
        releaseContentSound();
    }
}
