package com.roy.lockscreen.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;

import com.roy.lockscreen.Config;
import com.roy.lockscreen.util.DevicePolicyUtil;

/**
 * Created by Roy on 2017/2/16.
 */

public class AdminReceiver extends DeviceAdminReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        DevicePolicyUtil.getInstance(context).onActiveAdmin();
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        DevicePolicyUtil.getInstance(context).onRemoveActivate();
    }
}
