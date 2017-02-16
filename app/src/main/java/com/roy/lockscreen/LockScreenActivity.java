package com.roy.lockscreen;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;

/**
 * Created by Roy on 2017/2/16.
 */

public class LockScreenActivity extends Activity {
    private DevicePolicyManager devicePolicyManager;
    private ComponentName componentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(this, AdminReceiver.class);
        lockScreen();
    }

    private void lockScreen() {
        if (devicePolicyManager.isAdminActive(componentName)) {
            if (isScreenOn()) {
                devicePolicyManager.lockNow();
            }
            finish();
        } else {
            Config.loge("需要先取的Admin權限");
        }
    }

    private boolean isScreenOn() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            return pm.isInteractive();
        } else {
            return pm.isScreenOn();
        }
    }
}
