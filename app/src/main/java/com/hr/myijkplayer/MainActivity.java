package com.hr.myijkplayer;

import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;

public class MainActivity extends AppCompatActivity implements
        IMediaPlayer.OnPreparedListener ,
        IMediaPlayer.OnVideoSizeChangedListener ,
        IMediaPlayer.OnSeekCompleteListener,
        IMediaPlayer.OnCompletionListener ,
        IMediaPlayer.OnErrorListener,
        IMediaPlayer.OnBufferingUpdateListener ,
        IMediaPlayer.OnInfoListener,
        IMediaPlayer.OnTimedTextListener {

    private SurfaceView surfaceView;
    IjkMediaPlayer ijkMediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surfaceView = findViewById(R.id.surfaceview);

        ijkMediaPlayer = new IjkMediaPlayer();

        surfaceView.getHolder().addCallback(new LmnSurfaceCallback());
    }
    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        Logger.d("onPrepared--->");
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

    private class LmnSurfaceCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            //surfaceview创建成功后，加载视频
            Logger.d("surfaceChanged--->");
            load();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
        }
    }

    private void load(){
        ijkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);
        ijkMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
          //  ijkMediaPlayer.setDataSource("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4");
            ijkMediaPlayer.setDataSource("rtmp://42.159.206.249:1935/1001/31117091");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //给mediaPlayer设置视图
        ijkMediaPlayer.setDisplay(surfaceView.getHolder());

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


    public void onCLick(View view){
        Logger.d("onCLick--->");
        if (ijkMediaPlayer != null) {
            if(ijkMediaPlayer.isPlaying()){
                ijkMediaPlayer.pause();
            }else {
                ijkMediaPlayer.start();
            }
        }
    }

    @Override
    protected void onDestroy() {

        Logger.d("onDestroy--->");

        if (ijkMediaPlayer != null) {

            ijkMediaPlayer.stop();
            ijkMediaPlayer.release();

            ijkMediaPlayer.setOnPreparedListener(null);
            ijkMediaPlayer.setOnCompletionListener(null);
            ijkMediaPlayer.setOnBufferingUpdateListener(null);
            ijkMediaPlayer.setOnSeekCompleteListener(null);
            ijkMediaPlayer.setOnVideoSizeChangedListener(null);
            ijkMediaPlayer.setOnErrorListener(null);
            ijkMediaPlayer.setOnInfoListener(null);
            ijkMediaPlayer.setOnTimedTextListener(null);
            ijkMediaPlayer = null;

            IjkMediaPlayer.native_profileEnd();

        }

        super.onDestroy();
    }
}
