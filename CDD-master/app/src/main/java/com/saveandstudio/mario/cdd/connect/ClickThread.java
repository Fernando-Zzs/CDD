package com.saveandstudio.mario.cdd.connect;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.saveandstudio.mario.cdd.GameActivity;
import com.saveandstudio.mario.cdd.Prefabs.Game;

import static android.content.ContentValues.TAG;

public class ClickThread extends Thread {
    private final android.os.Handler mHandler;

    public ClickThread(Handler mHandler) {
        this.mHandler = mHandler;
    }

    public void run() {
        while (true) {
            Message msg = mHandler.obtainMessage();
            if (GameActivity.state == 1) {
                GameActivity.state = 0;

                msg.arg1 = 0;
                msg.what = 1;
                msg.obj = "";
                mHandler.sendMessage(msg);
            } else if (GameActivity.state == 2) {
                GameActivity.state = 0;

                msg.arg1 = 0;
                msg.what = 2;
                msg.obj = "";
                mHandler.sendMessage(msg);
            }
        }
    }
}
