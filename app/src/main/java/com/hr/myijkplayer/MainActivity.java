package com.hr.myijkplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    private ControlPlayer controlPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controlPlayer = findViewById(R.id.ControlPlayer);

        controlPlayer.setBackgroundResource(R.color.colorAccent);

    }

    @Override
    protected void onDestroy() {

        controlPlayer.release();

        super.onDestroy();
    }
}
