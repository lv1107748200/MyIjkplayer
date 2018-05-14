package com.hr.myijkplayer;

import android.content.Context;
import android.media.AudioManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Surface;
import android.widget.FrameLayout;

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

public class ControlPlayer extends FrameLayout implements
        IGSYSurfaceListener,
        MeasureHelper.MeasureFormVideoParamsListener,
        IMediaPlayer.OnPreparedListener ,
        IMediaPlayer.OnVideoSizeChangedListener ,
        IMediaPlayer.OnSeekCompleteListener,
        IMediaPlayer.OnCompletionListener ,
        IMediaPlayer.OnErrorListener,
        IMediaPlayer.OnBufferingUpdateListener ,
        IMediaPlayer.OnInfoListener,
        IMediaPlayer.OnTimedTextListener{

    private final static  String URLVOIDE = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
    private final static  String URLLIVE = "rtmp://42.159.206.249:1935/1001/31117091";

    IjkMediaPlayer ijkMediaPlayer;
    CusTomSurfaceView cusTomSurfaceView;

    public ControlPlayer(@NonNull Context context) {
        this(context,null);
    }

    public ControlPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ControlPlayer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    public void onSurfaceAvailable(Surface surface) {

    }

    @Override
    public void onSurfaceSizeChanged(Surface surface, int width, int height) {
        Logger.d("onSurfaceSizeChanged--->");

        if(null != ijkMediaPlayer){
            return;
        }

        onCreatPlayerView(URLVOIDE);

    }

    @Override
    public boolean onSurfaceDestroyed(Surface surface) {
        return false;
    }

    @Override
    public void onSurfaceUpdated(Surface surface) {

    }

    @Override
    public int getCurrentVideoWidth() {
        if(null != ijkMediaPlayer){
            return ijkMediaPlayer.getVideoWidth();
        }
        return 0;
    }

    @Override
    public int getCurrentVideoHeight() {
        if(null != ijkMediaPlayer){
            return ijkMediaPlayer.getVideoHeight();
        }
        return 0;
    }

    @Override
    public int getVideoSarNum() {
        if(null != ijkMediaPlayer){
            return ijkMediaPlayer.getVideoSarNum();
        }
        return 0;
    }

    @Override
    public int getVideoSarDen() {
        if(null != ijkMediaPlayer){
            return ijkMediaPlayer.getVideoSarDen();
        }
        return 0;
    }


    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        Logger.d("onPrepared--->");

        cusTomSurfaceView.getRenderView().requestLayout();
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
        cusTomSurfaceView =  CusTomSurfaceView.addSurfaceView(getContext(),this,0,this,this);
    }

    private void onCreatPlayerView(String url){
        if(null == ijkMediaPlayer){
            ijkMediaPlayer = new IjkMediaPlayer();
        }

        ijkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);
        ijkMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            ijkMediaPlayer.setDataSource(url);
             // ijkMediaPlayer.setDataSource("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4");
            //ijkMediaPlayer.setDataSource("rtmp://42.159.206.249:1935/1001/31117091");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //给mediaPlayer设置视图
        ijkMediaPlayer.setDisplay(cusTomSurfaceView.getHolder());

        ijkMediaPlayer.setOnPreparedListener(this);
        ijkMediaPlayer.setOnCompletionListener(this);
        ijkMediaPlayer.setOnBufferingUpdateListener(this);
        ijkMediaPlayer.setOnSeekCompleteListener(this);
        ijkMediaPlayer.setOnVideoSizeChangedListener(this);
        ijkMediaPlayer.setOnErrorListener(this);
        ijkMediaPlayer.setOnInfoListener(this);
        ijkMediaPlayer.setOnTimedTextListener(this);

        ijkMediaPlayer.prepareAsync();
    }

    /**
     * 换集
     */
    public void exchangeCollect(){
        stop();
        release();
        onCreatPlayerView(URLLIVE);
    }

    /**
     * 选择视频显示比例
     */
    public void switchProportion(int type){

        GSYVideoType.setShowType(type);

        if(null != cusTomSurfaceView){
            cusTomSurfaceView.getRenderView().requestLayout();
        }

    }


    /**
     * 播放或暂停
     */
    public void playerOrPause(){
        if(null != ijkMediaPlayer){
            if(ijkMediaPlayer.isPlaying()){
                ijkMediaPlayer.pause();
            }else {
                ijkMediaPlayer.start();
            }
        }
    }

    /**
     * 停止播放
     */
    public void stop(){
        if(null != ijkMediaPlayer){
            ijkMediaPlayer.stop();
        }
    }

    //释放
    public void release(){
        Logger.d("onDestroy--->");

        if (ijkMediaPlayer != null) {

            ijkMediaPlayer.stop();
            ijkMediaPlayer.reset();
            ijkMediaPlayer.release();

            ijkMediaPlayer = null;

            IjkMediaPlayer.native_profileEnd();

        }
    }


}
