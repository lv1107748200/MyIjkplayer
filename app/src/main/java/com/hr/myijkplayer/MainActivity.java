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

public class MainActivity extends AppCompatActivity implements IMediaPlayer.OnPreparedListener {

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
            ijkMediaPlayer.setDataSource("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //给mediaPlayer设置视图
        ijkMediaPlayer.setDisplay(surfaceView.getHolder());

        ijkMediaPlayer.prepareAsync();
    }


    public void onCLick(View view){
        Logger.d("onCLick--->");
        if (ijkMediaPlayer != null) {
            Logger.d("start--->");
            ijkMediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {

        Logger.d("onDestroy--->");

        if (ijkMediaPlayer != null) {
            ijkMediaPlayer.stop();
            ijkMediaPlayer.release();
            ijkMediaPlayer = null;

            IjkMediaPlayer.native_profileEnd();
        }

        super.onDestroy();
    }

    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        System.out.println("--->onPrepared");
    }
}
