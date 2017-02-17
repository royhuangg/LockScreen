package com.roy.lockscreen.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.roy.lockscreen.Config;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Roy on 2017/2/17.
 */

public class BitmapUtil {

    @Nullable
    public static Bitmap getBitmapFromUri(Context context, Uri selectedImage) {
        InputStream imageStream = null;
        try {
            imageStream = context.getContentResolver().openInputStream(selectedImage);
            return BitmapFactory.decodeStream(imageStream,null,getBitmapOptions(0));
        } catch (FileNotFoundException e) {
            Config.loge(e);
        }
        return null;
    }

    public static Bitmap getResizedBitmapWithRatio(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static Bitmap getResizedBitmap(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    public static BitmapFactory.Options getBitmapOptions(int scale){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inSampleSize = scale;
        return options;
    }
}
