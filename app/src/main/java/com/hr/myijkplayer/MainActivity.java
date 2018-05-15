package com.hr.myijkplayer;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.orhanobut.logger.Logger;

import static com.hr.mylibrary.utils.GSYVideoType.SCREEN_MATCH_FULL;


public class MainActivity extends Activity {

    private ControlIjkPlayer controlPlayer;

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
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //    NLog.d(NLog.TAGOther,"keyCode--->" + keyCode);

        switch (keyCode) {

            case KeyEvent.KEYCODE_ENTER:     //确定键enter
            case KeyEvent.KEYCODE_DPAD_CENTER:
                Logger.d("enter--->");

                controlPlayer.playerOrPause();//遥控确定键


                break;

            case KeyEvent.KEYCODE_BACK:    //返回键
                Logger.d("back--->");


                break;
            case KeyEvent.KEYCODE_MENU:
                Logger.d("MENU--->");



                break;

            case KeyEvent.KEYCODE_DPAD_DOWN:   //向下键

                /*    实际开发中有时候会触发两次，所以要判断一下按下时触发 ，松开按键时不触发
                 *    exp:KeyEvent.ACTION_UP
                 */
                Logger.d("KEYCODE_DPAD_DOWN--->");
                if (event.getAction() == KeyEvent.ACTION_DOWN){

                }

                break;

            case KeyEvent.KEYCODE_DPAD_UP:   //向上键
                Logger.d("KEYCODE_DPAD_UP--->");

                break;

            case     KeyEvent.KEYCODE_0:   //数字键0
                Logger.d("KEYCODE_0--->");
                finish();

                break;

            case KeyEvent.KEYCODE_DPAD_LEFT: //向左键

                //  NLog.e(NLog.TAGOther,"lonKeyDown eft--->" +  event.getRepeatCount());





                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:  //向右键
                //   NLog.e(NLog.TAGOther,"onKeyDown right---> " + event.getRepeatCount());



                break;
            default:
                break;
        }

        return super.onKeyDown(keyCode, event);

    }


    @Override
    protected void onDestroy() {

        controlPlayer.release();

        super.onDestroy();
    }
}
