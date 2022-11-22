package com.example.musiccircle.Fragments.Music_Player;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class ExoPlayerLooperThread extends Thread{
    public Handler mHandler;

    public ExoPlayerLooperThread(){

    }
    @Override
    public void run() {
        Looper.prepare();

        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                // process incoming messages here
            }
        };

        Looper.loop();
    }
}