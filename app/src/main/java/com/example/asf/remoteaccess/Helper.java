package com.example.asf.remoteaccess;

import static android.content.Context.ACTIVITY_SERVICE;

import android.app.ActivityManager;
import android.content.Context;

public class Helper {

    public boolean isServiceRunning(Context pContext, String pPackageName) {

        ActivityManager manager = (ActivityManager) pContext.getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (pPackageName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
