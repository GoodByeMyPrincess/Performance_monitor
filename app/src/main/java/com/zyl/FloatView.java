package com.zyl;


import android.content.Context;

import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;


public class FloatView extends FrameLayout {
    private Context mContext;
    private WindowManager mWindowManager;
    private int x;
    private int y;
    public TextView mTemperatureText;
    public TextView mBatteryTempText;
    public TextView mCPUHzText;
    public TextView mMemoryText;


    public FloatView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        inflate(mContext, R.layout.float_view, this);
        mTemperatureText = (TextView) findViewById(R.id.tv_cpu_temp);
        mBatteryTempText = (TextView) findViewById(R.id.tv_battery_temp);
        mCPUHzText = (TextView) findViewById(R.id.tv_cpu_hz);
        mMemoryText = (TextView) findViewById(R.id.tv_mem_usage);
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = (int) motionEvent.getRawX();
                y = (int) motionEvent.getRawY();
                return true;

            case MotionEvent.ACTION_MOVE:
                int nowX = (int) motionEvent.getRawX();
                int nowY = (int) motionEvent.getRawY();

                int movedX = nowX - x;
                int movedY = nowY - y;

                x = nowX;
                y = nowY;
                WindowManager.LayoutParams mLayoutParams = (WindowManager.LayoutParams) getLayoutParams();
                mLayoutParams.x = mLayoutParams.x + movedX;
                mLayoutParams.y = mLayoutParams.y + movedY;
                mWindowManager.updateViewLayout(this, mLayoutParams);
                return true;
            default:
                break;
        }
        return super.onTouchEvent(motionEvent);

    }
}
