package com.SCUT.ZZS_YJX_YMX.cdd.Connect;

import android.os.Handler;
import android.os.Message;

import com.SCUT.ZZS_YJX_YMX.cdd.GameActivity;

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
