package com.roy.lockscreen;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    //<div>Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
    private static final int REQUEST_ACTIVE = 0001;
    DevicePolicyManager devicePolicyManager;
    ComponentName componentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(MainActivity.this, AdminReceiver.class);

        lockScreen();

    }

    private void lockScreen() {
        if (devicePolicyManager.isAdminActive(componentName)) {
            if (isScreenOn()) {
                devicePolicyManager.lockNow();
            }
            finish();
        } else {
            activtAdmin();
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

    private void activtAdmin() {
        Intent intent = new Intent(
                DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "LockScreen");
        startActivityForResult(intent, REQUEST_ACTIVE);
    }

    public void activtAdmin(View view) {
        activtAdmin();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_ACTIVE) {

            }
        } else if (resultCode == RESULT_CANCELED) {
            Config.loge("User cancel add app to admin.");
        }
    }

    @Override
    protected void onDestroy() {
        devicePolicyManager = null;
        componentName = null;
        System.gc();
        super.onDestroy();
    }
}
