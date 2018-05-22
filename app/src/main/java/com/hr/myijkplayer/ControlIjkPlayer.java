package com.hr.myijkplayer;

import android.content.Context;
import android.media.AudioManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.View;
import android.widget.FrameLayout;

import com.hr.mylibrary.player.IjkPlayerMger;
import com.hr.mylibrary.playerview.CusTomSurfaceView;
import com.hr.mylibrary.playerview.IGSYSurfaceListener;
import com.hr.mylibrary.utils.GSYVideoType;
import com.hr.mylibrary.utils.MeasureHelper;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;

/**
 * Created by 吕 on 2018/5/14.
 */

public class ControlIjkPlayer extends FrameLayout implements
        IMediaPlayer.OnPreparedListener ,
        IMediaPlayer.OnVideoSizeChangedListener ,
        IMediaPlayer.OnSeekCompleteListener,
        IMediaPlayer.OnCompletionListener ,
        IMediaPlayer.OnErrorListener,
        IMediaPlayer.OnBufferingUpdateListener ,
        IMediaPlayer.OnInfoListener,
        IMediaPlayer.OnTimedTextListener{

    private final static  String URLVOIDE = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
    private final static  String URLVOIDE3 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4";
    private final static  String URLLIVE = "rtmp://42.159.206.249:1935/1001/31117091";

    IjkMediaPlayer ijkMediaPlayer;


    public ControlIjkPlayer(@NonNull Context context) {
        this(context,null);
    }

    public ControlIjkPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ControlIjkPlayer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        Logger.d("onPrepared--->");
      //  GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_16_9);

    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {
        //  Logger.d("onVideoSizeChanged--->");
    }

    @Override
    public void onSeekComplete(IMediaPlayer iMediaPlayer) {
        Logger.d("onSeekComplete--->");
    }

    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {
        Logger.d("onCompletion--->");
    }

    @Override
    public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
        Logger.d("onError--->");
        return false;
    }

    @Override
    public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
        // Logger.d("onBufferingUpdate--->");
    }

    @Override
    public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
        // Logger.d("onInfo--->");
        return false;
    }

    @Override
    public void onTimedText(IMediaPlayer iMediaPlayer, IjkTimedText ijkTimedText) {
        Logger.d("onTimedText--->");
    }

    private void init(){

            CusTomSurfaceView.addSurfaceViewSupter(IjkPlayerMger.getInstance().getCusTomSurfaceView(getContext()),this);
             exchangeCollectOther();
        View view =  View.inflate(getContext(), R.layout.video_control_layout_ijk,this);
    }

    /**
     * 换集
     */
    public void exchangeCollectOther(){

        IjkPlayerMger.getInstance().initMediaPlayer(URLLIVE, new IjkPlayerMger.IjkPlayerCallBack() {
            @Override
            public void mangerCallBack(IjkMediaPlayer mediaPlayer) {

                mediaPlayer.setOnPreparedListener(ControlIjkPlayer.this);
                mediaPlayer.setOnCompletionListener(ControlIjkPlayer.this);
                mediaPlayer.setOnBufferingUpdateListener(ControlIjkPlayer.this);
                mediaPlayer.setOnSeekCompleteListener(ControlIjkPlayer.this);
                mediaPlayer.setOnVideoSizeChangedListener(ControlIjkPlayer.this);
                mediaPlayer.setOnErrorListener(ControlIjkPlayer.this);
                mediaPlayer.setOnInfoListener(ControlIjkPlayer.this);
                mediaPlayer.setOnTimedTextListener(ControlIjkPlayer.this);

                mediaPlayer.prepareAsync();
            }
        });


    }
    public void removeView(){
        removeView(IjkPlayerMger.getInstance().getCusTomSurfaceView(getContext()));
    }


}
