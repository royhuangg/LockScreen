package com.roy.lockscreen.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.roy.lockscreen.R;
import com.roy.lockscreen.util.BitmapUtil;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Roy on 2017/2/16.
 */

public class ShortcutActivity extends BaseActivity {
    private static final int SELECT_PHOTO = 100;
    private static final int TAKE_PHOTO = 200;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.et_shortcut)
    EditText etShortcut;
    @BindView(R.id.tv_icon)
    TextView tvIcon;
    @BindView(R.id.layout_preview)
    LinearLayout layoutPreview;
    @BindView(R.id.rg_icon)
    RadioGroup rgIcon;

    private String tempFileName;
    private Uri outputFileUri;
    private Bitmap currentSelectedPic;
    private int currentSelectedDrawableRes;
    private TypedArray typedArray;
    private ArrayList<Integer> iconsResArray;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcut);
        ButterKnife.bind(this);
        iconsResArray = new ArrayList<>();
        typedArray = getResources().obtainTypedArray(R.array.icons);
        for (int i = 0; i < typedArray.length(); i++) {
            iconsResArray.add(typedArray.getResourceId(i, 0));
        }
        currentSelectedDrawableRes = R.mipmap.ic_launcher;
        ivIcon.setImageResource(currentSelectedDrawableRes);
        etShortcut.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvIcon.setText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        rgIcon.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                currentSelectedDrawableRes = iconsResArray.get(getRadioGroupCheckedIdx(radioGroup));
                ivIcon.setImageResource(currentSelectedDrawableRes);

            }
        });

    }

    private int getRadioGroupCheckedIdx(RadioGroup radioGroup) {
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        int idx = radioGroup.indexOfChild(radioButton);
        return idx;
    }

    public void pickImg(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    public void addShortCut(View view) {
        Intent shortcutIntent = new Intent(getApplicationContext(),
                LockScreenActivity.class); // 啟動捷徑入口，一般用MainActivity，有使用其他入口則填入相對名稱，ex:有使用SplashScreen
        shortcutIntent.setAction(Intent.ACTION_MAIN);
        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent); // shortcutIntent送入
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
                tvIcon.getText().toString()); // 捷徑app名稱
        if (currentSelectedPic != null && !currentSelectedPic.isRecycled()) {
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON,
                    BitmapUtil.getResizedBitmapWithRatio(currentSelectedPic, 400));
//            addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON,
//                    BitmapUtil.getResizedBitmap(currentSelectedPic, 128, 128));
        } else {
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                    Intent.ShortcutIconResource.fromContext(
                            getApplicationContext(),// 捷徑app圖
                            currentSelectedDrawableRes));
        }

        addIntent.putExtra("duplicate", true); // 只創建一次
        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT"); // 安裝
        try {
            getApplicationContext().sendBroadcast(addIntent); // 送出廣播
        } catch (Exception ex) {
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON,
                    BitmapUtil.getResizedBitmap(currentSelectedPic, 128, 128));
            getApplicationContext().sendBroadcast(addIntent); // 送出廣播
        }
        toast(getString(R.string.alert_create_shortcut));
        releaseMemory(currentSelectedPic);
        currentSelectedPic = null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (resultCode == RESULT_OK) {
            releaseMemory(currentSelectedPic);

            switch (requestCode) {
                case SELECT_PHOTO:
                    Uri selectedImage = imageReturnedIntent.getData();
                    currentSelectedPic = BitmapUtil.getBitmapFromUri(this, selectedImage);
                    ivIcon.setImageURI(selectedImage);
                    break;
                case TAKE_PHOTO:
//                    String filePath = outputFileUri.getPath();
                    currentSelectedPic = BitmapUtil.getBitmapFromUri(this, outputFileUri);
                    ivIcon.setImageURI(outputFileUri);
                    break;
            }
        }
    }

    public void takePhoto(View view) {
        tempFileName = "tmp" + System.currentTimeMillis();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(getExternalCacheDir(), tempFileName);
        outputFileUri = Uri.fromFile(file);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(takePictureIntent, TAKE_PHOTO);
    }


}
