package com.hr.myijkplayer;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static com.hr.mylibrary.utils.GSYVideoType.SCREEN_MATCH_FULL;


public class MainActivity extends Activity {

    private ControlPlayer controlPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controlPlayer = findViewById(R.id.ControlPlayer);

        controlPlayer.setBackgroundResource(R.color.colorAccent);

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_play_pause:

                controlPlayer.playerOrPause();

                break;
            case R.id.btn_proportion_switch:

                controlPlayer.switchProportion(SCREEN_MATCH_FULL);

                break;
            case R.id.btn_switch_thing:

                controlPlayer.exchangeCollect();

                break;
        }
    }


    @Override
    protected void onDestroy() {

        controlPlayer.release();

        super.onDestroy();
    }
}
