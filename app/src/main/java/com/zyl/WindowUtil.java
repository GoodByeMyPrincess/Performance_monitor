package com.zyl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.WindowManager;

import com.zyl.Utils.GetUtils;

import static android.content.Context.WINDOW_SERVICE;

public class WindowUtil {
    public static boolean isStarted = false;
    public static int initX = 300;
    public static int initY = 300;
    public FloatView mFloatView;

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private Context mContext;


    @SuppressLint("HandlerLeak")
    private  Handler mHandler1 = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            showCPUTemp();
            sendEmptyMessageDelayed(0, 1500);
        }
    };


    @SuppressLint("HandlerLeak")
    private Handler mHandler2 = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            showCPUHz();
            sendEmptyMessageDelayed(1,1200);
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler mHandler3 = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            showMemoryUsage();
            sendEmptyMessageDelayed(2,5000);
        }
    };

    public WindowUtil(Context context) {
        mContext = context;

        mFloatView = new FloatView(context);

        mWindowManager = (WindowManager) mContext.getSystemService(WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
        }

        mLayoutParams.format = PixelFormat.RGBA_8888;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.x = initX;
        mLayoutParams.y = initY;
    }

    public void showCPUTemp() {
        String cpuTemp = GetUtils.getCPUTemp();
        mFloatView.mTemperatureText.setText(cpuTemp);
    }

    public void showCPUHz() {
        String cpuHz = GetUtils.getGetCpuHz();
        mFloatView.mCPUHzText.setText(cpuHz);
    }

    public void showMemoryUsage() {
        String memoryUsage = GetUtils.getMemoryUsage();
        mFloatView.mMemoryText.setText(memoryUsage);
    }

    public void showFloatView() {
        mWindowManager.addView(mFloatView, mLayoutParams);
        isStarted = true;
        mHandler1.sendEmptyMessage(0);
        mHandler2.sendEmptyMessage(1);
        mHandler3.sendEmptyMessage(2);
    }

    public void closeFloatView() {
        mWindowManager.removeView(mFloatView);
        isStarted = false;
    }


}
