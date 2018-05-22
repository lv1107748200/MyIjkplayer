package com.hr.mylibrary.player;


import android.content.Context;
import android.media.AudioManager;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import com.hr.mylibrary.playerview.CusTomSurfaceView;
import com.hr.mylibrary.playerview.IGSYSurfaceListener;
import com.hr.mylibrary.utils.GSYVideoType;
import com.hr.mylibrary.utils.MeasureHelper;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by 吕 on 2018/3/28.
 */

public class IjkPlayerMger implements
        IGSYSurfaceListener,
        MeasureHelper.MeasureFormVideoParamsListener{

    volatile private static IjkPlayerMger instance = null;

    private IjkMediaPlayer mediaPlayer;
    private CusTomSurfaceView cusTomSurfaceView;
    private IjkPlayerCallBack callBack;

    private boolean isCom;
    private boolean isStart;

    public static IjkPlayerMger getInstance(){
        if(instance == null){
            synchronized (IjkPlayerMger.class) {
                if(instance == null){
                    instance = new IjkPlayerMger();
                }
            }
        }

        return instance;
    }

    public IjkMediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public CusTomSurfaceView getCusTomSurfaceView(Context context){

        if(null == cusTomSurfaceView){
            isCom = false;
            cusTomSurfaceView = new CusTomSurfaceView(context);
            cusTomSurfaceView.setIGSYSurfaceListener(this);
            cusTomSurfaceView.setVideoParamsListener(this);
        }

        return cusTomSurfaceView;
    }



    public void initMediaPlayer(String url,IjkPlayerCallBack callBack){

           this.callBack = callBack;

            if(null == mediaPlayer){
                mediaPlayer = new IjkMediaPlayer();
            }else {
                mediaPlayer.reset();
            }
            mediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setLooping(true);
        //SeekTo的时候，会跳回到拖动前的位置
            mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "enable-accurate-seek", 1);
            try {
                mediaPlayer.setDataSource(url);
            } catch (IOException e) {
                e.printStackTrace();
            }



        if(isCom){
            isStart = false;
            mediaPlayer.setDisplay(cusTomSurfaceView.getHolder());
            callBack.mangerCallBack(mediaPlayer);
        }else {
            isStart = true;
        }
        Logger.d("initMediaPlayer--->" + " isStart: " +isStart+"   isCom:  " + isCom);

    }

    public void setOnDesry(){
        if(null != mediaPlayer){

            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();

            mediaPlayer = null;

            IjkMediaPlayer.native_profileEnd();
        }
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

    public void rePanduan(){
        isStart = true;
        isCom = false;
    }

    @Override
    public void onSurfaceAvailable(Surface surface) {

    }

    @Override
    public void onSurfaceSizeChanged(Surface surface, int width, int height) {
        Logger.d("onSurfaceSizeChanged--->" + " isStart: " +isStart);
        isCom = true;
        if(isStart){
            if(null != this.callBack){
                mediaPlayer.setDisplay(cusTomSurfaceView.getHolder());
                callBack.mangerCallBack(mediaPlayer);
            }
        }
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
        if(null != mediaPlayer){
            return mediaPlayer.getVideoWidth();
        }
        return 0;
    }

    @Override
    public int getCurrentVideoHeight() {
        if(null != mediaPlayer){
            return mediaPlayer.getVideoHeight();
        }
        return 0;
    }

    @Override
    public int getVideoSarNum() {
        if(null != mediaPlayer){
            return mediaPlayer.getVideoSarNum();
        }
        return 0;
    }

    @Override
    public int getVideoSarDen() {
        if(null != mediaPlayer){
            return mediaPlayer.getVideoSarDen();
        }
        return 0;
    }

    public interface IjkPlayerCallBack{
        void  mangerCallBack(IjkMediaPlayer mediaPlayer);
    }
}
