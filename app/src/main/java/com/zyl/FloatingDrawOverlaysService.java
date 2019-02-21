package com.zyl;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class FloatingDrawOverlaysService extends Service {

    private WindowUtil mWindowUtil;
    private IntentFilter mIntentFilter;
    private BroadcastReceiver mReceiver;


    @Override
    public void onCreate() {
        super.onCreate();
        mWindowUtil = new WindowUtil(this);
        showBatteryTemp();
    }

    public void showBatteryTemp() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                    int temperature = intent.getIntExtra("temperature", 0);
                    String temp = "" + temperature;
                    mWindowUtil.mFloatView.mBatteryTempText.setText(temp.substring(0, 2) + "."
                            + temp.substring(2, 3) + "°C");
                }
            }
        };

        registerReceiver(mReceiver, mIntentFilter);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!mWindowUtil.isStarted) {
            mWindowUtil.showFloatView();
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
