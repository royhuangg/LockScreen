package com.roy.lockscreen;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Roy on 2017/2/16.
 */

public class BaseActivity extends AppCompatActivity {

    protected void toast(String msg) {
        Toast.makeText(this, msg, 1000).show();
    }

}
