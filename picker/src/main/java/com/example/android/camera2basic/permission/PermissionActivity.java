package com.example.android.camera2basic.permission;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.camera2basic.MediaType;
import com.example.android.camera2basic.ScaleType;
import com.example.android.camera2basic.picker.MediaPickerOpts;

/**
 * Created by Pankaj Soni <pankajsoni@softwarejoint.com> on 18/03/18.
 * Copyright (c) 2018 Software Joint. All rights reserved.
 */
public class PermissionActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionManager.requestPermission(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean granted = PermissionManager.onRequestPermissionResult(grantResults);
        setResult(granted ? RESULT_OK : RESULT_CANCELED);

        finish();
    }

}