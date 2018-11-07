package com.example.android.camera2basic.tasks;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.android.camera2basic.image.EffectGLView;
import com.example.android.camera2basic.image.ImageEffectFragment;
import com.example.android.camera2basic.utils.BitmapUtils;

import java.lang.ref.WeakReference;

/**
 * Created by Pankaj Soni <pankajsoni@softwarejoint.com> on 17/03/18.
 * Copyright (c) 2018 Software Joint. All rights reserved.
 */
public class LoadImageTask extends AsyncTask<Void, Void, Bitmap> {

    private final String filePath;
    private WeakReference<EffectGLView> glViewRef;
    private ImageEffectFragment fragment;

    public LoadImageTask(String filePath, ImageEffectFragment fragment) {
        this.filePath = filePath;
        this.fragment = fragment;
    }

    public LoadImageTask(String filePath, EffectGLView effectGLView) {
        this.filePath = filePath;
        this.glViewRef = new WeakReference<>(effectGLView);
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;

        final int imgSize = Math.min(width, height);
        return BitmapUtils.decodeBitmapFromFile(filePath, imgSize);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (glViewRef != null) {
            EffectGLView glView = glViewRef.get();
            if (glView != null && glView.getWindowToken() != null) {
                glView.onImageLoaded(filePath, bitmap);
            }
        } else if (fragment != null && !fragment.isRemoving()) {
            fragment.onImageLoaded(filePath, bitmap);
        }
    }
}
