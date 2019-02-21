package com.zyl;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zyl.Utils.SystemUtil;


public class MainActivity extends AppCompatActivity {
    private static int REQUEST_CODE_DRAW_OVERLAYS = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SystemUtil.getSystemVersion() >= 6) {

            if (checkDrawOverlaysPermission()) {
                startService(new Intent(MainActivity.this, FloatingDrawOverlaysService.class));
            }
        } else {
            Toast.makeText(this,"若悬浮窗不显示，请手动打开设置给予权限", Toast.LENGTH_LONG).show();
            startService(new Intent(MainActivity.this, FloatingDrawOverlaysService.class));
        }


    }

    private void init() {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkDrawOverlaysPermission() {
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_CODE_DRAW_OVERLAYS);
            return false;
        }
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_DRAW_OVERLAYS) {
            if (Settings.canDrawOverlays(this)) {
                init();
            } else {
                Toast.makeText(this, "没有悬浮窗权限,请打开设置给予权限！", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}
