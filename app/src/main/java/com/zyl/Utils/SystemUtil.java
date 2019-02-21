package com.zyl.Utils;

import android.os.Build;

public class SystemUtil {
    public static int getSystemVersion() {
        String version = Build.VERSION.RELEASE.substring(0,1);
        return Integer.parseInt(version);
    }
}
