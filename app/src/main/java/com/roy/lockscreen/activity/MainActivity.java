package com.roy.lockscreen.activity;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.roy.lockscreen.BuildConfig;
import com.roy.lockscreen.Config;
import com.roy.lockscreen.R;
import com.roy.lockscreen.receiver.AdminReceiver;
import com.roy.lockscreen.util.DevicePolicyUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements DevicePolicyUtil.DevicePolicyListener {
    //<div>Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
    private static final int REQUEST_ACTIVE = 0001;
    @BindView(R.id.btn_shortcut)
    Button btnShortcut;
    @BindView(R.id.layout_main)
    RelativeLayout layoutMain;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.btn_active)
    Button btnActive;
    @BindView(R.id.layout_active)
    RelativeLayout layoutActive;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DevicePolicyUtil.getInstance(this).setDevicePolicyListener(this);

        checkHasAdminActive();
    }

    private void checkHasAdminActive() {
        boolean isAdminActive = DevicePolicyUtil.getInstance(this).isAdminActive();
        layoutActive.setVisibility(isAdminActive ? View.GONE : View.VISIBLE);
        layoutMain.setVisibility(!isAdminActive ? View.GONE : View.VISIBLE);
    }

    public void customShortcut(View view) {
        startActivity(new Intent(this, ShortcutActivity.class));
    }

    public void addShortCut(View view) {
        Intent shortcutIntent = new Intent(getApplicationContext(),
                LockScreenActivity.class); // 啟動捷徑入口，一般用MainActivity，有使用其他入口則填入相對名稱，ex:有使用SplashScreen
        shortcutIntent.setAction(Intent.ACTION_MAIN);
        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent); // shortcutIntent送入
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
                getString(R.string.text_lock)); // 捷徑app名稱
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(
                        getApplicationContext(),// 捷徑app圖
                        R.drawable.ic_lock_outline_pink_a400_48dp));
        addIntent.putExtra("duplicate", false); // 只創建一次
        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT"); // 安裝
        getApplicationContext().sendBroadcast(addIntent); // 送出廣播
        toast(getString(R.string.alert_create_shortcut));

    }

    public void activtAdmin(View view) {
        DevicePolicyUtil.getInstance(this).activtAdmin(this, REQUEST_ACTIVE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_ACTIVE) {
                checkHasAdminActive();
            }
        } else if (resultCode == RESULT_CANCELED) {
            Config.loge("User cancel add app to admin.");
        }
    }


    public void startVoiceRecognition(View view) {
        startActivity(new Intent(this, VoiceRecognitionDemoActivity.class));
    }

    public void uninstall(View view) {
        new AlertDialog.Builder(this).setMessage(getString(R.string.alert_uninstall)+ getString(R.string.app_name) + " ?").setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DevicePolicyUtil.getInstance(MainActivity.this).removeActiveAdmin();

            }
        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }

    @Override
    public void onActiveAdmin() {
        checkHasAdminActive();
    }

    @Override
    public void onRemovedActiveAdmin() {
        layoutActive.setVisibility(View.VISIBLE);
        layoutMain.setVisibility(View.GONE);
        deleteApp();
    }

    private void deleteApp() {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + BuildConfig.APPLICATION_ID));
        startActivity(intent);
        finish();
    }
}
