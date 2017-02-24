package com.roy.lockscreen.util;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.roy.lockscreen.receiver.AdminReceiver;

/**
 * Created by Roy on 2017/2/24.
 */
public class DevicePolicyUtil {
    private DevicePolicyListener devicePolicyListener;
    private DevicePolicyManager devicePolicyManager;
    private ComponentName componentName;
    private Context context;

    private static DevicePolicyUtil ourInstance;

    public static DevicePolicyUtil getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new DevicePolicyUtil(context);
        }
        return ourInstance;
    }

    private DevicePolicyUtil(Context context) {
        this.context = context;
        devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(context, AdminReceiver.class);
    }

    public boolean isAdminActive() {
        return devicePolicyManager.isAdminActive(componentName);
    }


    public void activtAdmin(Activity activity, int requestCode) {
        Intent intent = new Intent(
                DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "LockScreen");
//        context.startActivity(intent);
        activity.startActivityForResult(intent, requestCode);
    }

    public void removeActiveAdmin() {
        devicePolicyManager.removeActiveAdmin(componentName);
    }

    public void lockNow(){
        devicePolicyManager.lockNow();
    }

    /**
     * 成功啟動Admin
     * 需要重Receiver手動呼叫此方法
     */
    public void onActiveAdmin() {
        if (devicePolicyListener != null) {
            devicePolicyListener.onActiveAdmin();
        }
    }

    /**
     * 成功移除Admin
     * 需要重Receiver手動呼叫此方法
     */
    public void onRemoveActivate() {
        if (devicePolicyListener != null) {
            devicePolicyListener.onRemovedActiveAdmin();
        }
    }

    public DevicePolicyListener getDevicePolicyListener() {
        return devicePolicyListener;
    }

    public void setDevicePolicyListener(DevicePolicyListener devicePolicyListener) {
        this.devicePolicyListener = devicePolicyListener;
    }

    public interface DevicePolicyListener {
        void onActiveAdmin();

        void onRemovedActiveAdmin();
    }
}
