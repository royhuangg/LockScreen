package com.roy.lockscreen.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Roy on 2017/2/16.
 */

public class BaseActivity extends AppCompatActivity {

    protected void toast(String msg) {
        Toast.makeText(this, msg, 1000).show();
    }

    protected void releaseMemory(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        System.gc();
    }

    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }

}
