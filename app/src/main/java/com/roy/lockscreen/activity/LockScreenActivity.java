package com.roy.lockscreen.activity;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Toast;

import com.roy.lockscreen.R;
import com.roy.lockscreen.receiver.AdminReceiver;
import com.roy.lockscreen.Config;
import com.roy.lockscreen.util.DevicePolicyUtil;

/**
 * Created by Roy on 2017/2/16.
 */

public class LockScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lockScreen();
    }

    private void lockScreen() {
        if (DevicePolicyUtil.getInstance(this).isAdminActive()) {
            if (isScreenOn()) {
                DevicePolicyUtil.getInstance(this).lockNow();
            }
        } else {
            Config.loge("需要先取的Admin權限");
            Toast.makeText(this, getString(R.string.toast_get_admin), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }

    private boolean isScreenOn() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            return pm.isInteractive();
        } else {
            return pm.isScreenOn();
        }
    }

    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}
