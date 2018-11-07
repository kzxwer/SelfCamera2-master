package com.example.android.camera2basic.image;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.camera2basic.base.PickerFragment;
import com.example.android.camera2basic.picker.MediaPickerOpts;

//import com.softwarejoint.media.enums.CropType;

/**
 * Created by Pankaj Soni <pankajsoni@softwarejoint.com> on 15/03/18.
 * Copyright (c) 2018 Software Joint. All rights reserved.
 */
public class ImageEffectFragment extends PickerFragment implements View.OnClickListener {

    //private static final String IMAGE_PATH = "com.softwarejoint.image";

    public final String TAG = "ImageEffectFragment";

    public static ImageEffectFragment newInstance(MediaPickerOpts opts) {
        Bundle args = new Bundle();
        args.putParcelable(MediaPickerOpts.INTENT_OPTS, opts);
        //args.putString(ImageEffectFragment.IMAGE_PATH, imagePath);

        ImageEffectFragment fragment = new ImageEffectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private MediaPickerOpts opts;
    private TextView tv_reset_original;

    private EffectGLView effectView;
    private ImageView iv_crop, iv_crop_circle, iv_crop_star, iv_crop_flower, iv_crop_path;
    private AppCompatImageView iv_none, iv_duo_py, iv_cross, iv_negative, iv_duo_bw, iv_lomo, iv_fillight, iv_bw, iv_sepia;
    private ImageView iv_crop_mask;
    private PathCropView pathCropView;
    private Boolean isEffectApplied;

    private AppCompatImageView iv_filter;

    private View iv_done;

    private Handler uiThreadHandler;

    //private @CropType
    //int cropType = CropType.NONE;
    private String originalImagePath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (opts == null) {
            Bundle args = getArguments();
            //noinspection ConstantConditions
            opts = args.getParcelable(MediaPickerOpts.INTENT_OPTS);
        }

        uiThreadHandler = new Handler();
    }
/*
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_effect, container, false);


        isEffectApplied = false;


        iv_crop_mask = rootView.findViewById(R.id.iv_crop_mask);
        pathCropView = rootView.findViewById(R.id.pathCropView);

        iv_filter = rootView.findViewById(R.id.iv_filter);

        //if (opts.cropEnabled) {  control not working
            tv_reset_original.setOnClickListener(this);
            iv_crop.setOnClickListener(this);
            iv_crop_circle.setOnClickListener(this);
            iv_crop_star.setOnClickListener(this);
            iv_crop_flower.setOnClickListener(this);
            iv_crop_path.setOnClickListener(this);
        } else {
            iv_crop.setVisibility(View.GONE);
        }

        if (opts.filtersEnabled) {
            iv_filter.setOnClickListener(this);

            iv_none.setOnClickListener(this);
            iv_duo_py.setOnClickListener(this);
            iv_cross.setOnClickListener(this);
            iv_negative.setOnClickListener(this);
            iv_duo_bw.setOnClickListener(this);
            iv_lomo.setOnClickListener(this);
            iv_fillight.setOnClickListener(this);
            iv_bw.setOnClickListener(this);
            iv_sepia.setOnClickListener(this);
        } else {
            iv_filter.setVisibility(View.GONE);
        }

        effectView.init(opts);

        iv_done.setOnClickListener(this);

        onCropSelected(cropType);

        return rootView;
    }
*/
    public void onImageLoaded(String imagePath, Bitmap bitmap) {
        effectView.onImageLoaded(imagePath, bitmap);
    }

    @Override
    public boolean onBackPressed() {
        //noinspection ConstantConditions
        getActivity().setResult(Activity.RESULT_CANCELED);
        getActivity().supportFinishAfterTransition();
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        effectView.onResume();
    }
/*
    private void onClickDone() {
        Log.d(TAG, "onClickDone");
        iv_done.setOnClickListener(null);
        effectView.queueEvent(this::applyEffects);
    }
*/
/*
    private void shouldResetBeVisible(){
        if(isEffectApplied || cropType != CropType.NONE){
            tv_reset_original.setVisibility(View.VISIBLE);
        }else {
            tv_reset_original.setVisibility(View.GONE);
        }
    }*/

    @Override
    public void onClick(final View view) {
        final int id = view.getId();

    }
/*
    private void animateOption(ImageView view, boolean animateIn) {
        final float scale = animateIn ? 1.2f : 1.0f;
        @ColorRes int colorRes = animateIn ? R.color.holo_purple : android.R.color.white;

        if (view.getScaleX() == scale) return;

        view.animate().scaleX(scale).scaleY(scale)
                .setDuration(200L)
                .setListener(null)
                .start();
        @ColorInt int colorInt = ContextCompat.getColor(view.getContext(), colorRes);
        ImageViewCompat.setImageTintList(view, ColorStateList.valueOf(colorInt));
    }
*/
/*
    private void onCropSelected(@CropType int type) {
        animateCropOption(false);

        if (cropType == type) {
            cropType = CropType.NONE;
        } else {
            cropType = type;
        }

        iv_crop_mask.setVisibility(View.GONE);
        pathCropView.setVisibility(View.GONE);

        switch (cropType) {
            case CropType.NONE:
                iv_crop_mask.setVisibility(View.GONE);
                break;
            case CropType.CIRCLE:
                iv_crop_mask.setImageResource(R.drawable.circle_mask);
                iv_crop_mask.setVisibility(View.VISIBLE);
                break;
            case CropType.STAR:
                iv_crop_mask.setImageResource(R.drawable.star_mask);
                iv_crop_mask.setVisibility(View.VISIBLE);
                break;
            case CropType.FLOWER:
                iv_crop_mask.setImageResource(R.drawable.flower_mask);
                iv_crop_mask.setVisibility(View.VISIBLE);
                break;
            case CropType.PATH:
                pathCropView.setVisibility(View.VISIBLE);
                break;
        }

        if (cropType != CropType.NONE) {
            animateCropOption(true);
        }
    }

    private boolean isCropVisible() {
        return iv_crop_circle.getVisibility() == View.VISIBLE;
    }

    private void toggleCrop() {
        if (pathCropView.getVisibility() == View.VISIBLE && pathCropView.clear()) return;

        final long duration = AnimationHelper.getShortDuration();

        iv_crop.setOnClickListener(null);

        if (isCropVisible()) {
            iv_crop_mask.setVisibility(View.GONE);
            pathCropView.setVisibility(View.GONE);

            final int translationX = (int) (Resources.getSystem().getDisplayMetrics().density * 48);

            iv_crop_circle.animate().translationX(translationX)
                    .alpha(0).setDuration(duration).start();

            iv_crop_star.animate().translationX(translationX * 2)
                    .alpha(0).setDuration(duration * 2).start();

            iv_crop_flower.animate().translationX(translationX * 3)
                    .alpha(0).setDuration(duration * 3).start();

            iv_crop_path.animate().translationX(translationX * 4)
                    .alpha(0).setDuration(duration * 4)
                    .setListener(new AnimatorListenerAdapter() {

                        @Override
                        public void onAnimationStart(Animator animation) {
                            iv_crop.setImageResource(R.drawable.crop_option_white);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            iv_crop_circle.setVisibility(View.GONE);
                            iv_crop_star.setVisibility(View.GONE);
                            iv_crop_flower.setVisibility(View.GONE);
                            iv_crop_path.setVisibility(View.GONE);
                            iv_crop.setOnClickListener(ImageEffectFragment.this);
                        }
                    }).start();
        } else {
            iv_crop_circle.setVisibility(View.VISIBLE);
            iv_crop_star.setVisibility(View.VISIBLE);
            iv_crop_flower.setVisibility(View.VISIBLE);
            iv_crop_path.setVisibility(View.VISIBLE);

            iv_crop_circle.animate().translationX(0)
                    .alpha(1).setDuration(duration).start();

            iv_crop_star.animate().translationX(0)
                    .alpha(1).setDuration(duration * 2).start();

            iv_crop_flower.animate().translationX(0)
                    .alpha(1).setDuration(duration * 3).start();

            iv_crop_path.animate().translationX(0)
                    .alpha(1).setDuration(duration * 4)
                    .setListener(new AnimatorListenerAdapter() {

                        @Override
                        public void onAnimationStart(Animator animation) {
                            iv_crop.setImageResource(R.drawable.clear_white);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            iv_crop.setOnClickListener(ImageEffectFragment.this);

                            if (cropType == CropType.PATH) {
                                pathCropView.setVisibility(View.VISIBLE);
                            } else if (cropType != CropType.NONE) {
                                iv_crop_mask.setVisibility(View.VISIBLE);
                            }
                        }
                    }).start();
        }
    }

*/
/*
    @SuppressLint("SwitchIntDef")
    @SuppressWarnings("ConstantConditions")
    private void applyEffects() {
        Bitmap original = BitmapUtils.createBitmapFromGLSurface(0, 0, effectView.getWidth(), effectView.getHeight());
        if (original == null) return;

        String mediaPath;

        if (cropType == CropType.NONE) {
            mediaPath = BitmapUtils.saveBitmap(original, opts, true);
            original.recycle();
        } else if (cropType != CropType.PATH) {
            @DrawableRes int resId;

            switch (cropType) {
                case CropType.STAR:
                    resId = R.drawable.star_crop;
                    break;
                case CropType.FLOWER:
                    resId = R.drawable.flower_crop;
                    break;
                case CropType.CIRCLE:
                default:
                    resId = R.drawable.circle_crop;
                    break;
            }

            Bitmap mask = BitmapUtils.decodeResource(getResources(), resId, original);

            Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);

            Canvas mCanvas = new Canvas(result);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            mCanvas.drawBitmap(original, 0, 0, null);
            mCanvas.drawBitmap(mask, 0, 0, paint);
            paint.setXfermode(null);

            mediaPath = BitmapUtils.saveBitmap(result, opts, true);

            original.recycle();
            mask.recycle();
            result.recycle();

        } else if (!pathCropView.pathValidForCrop()) {
            mediaPath = BitmapUtils.saveBitmap(original, opts, true);
            original.recycle();
        } else {
            pathCropView.completePath();
            pathCropView.postDelayed(() -> cropByPath(original), 200L);

            mediaPath = null;
        }

        if (mediaPath == null) return;

        uiThreadHandler.post(() -> scanMedia(mediaPath));
    }

    private void cropByPath(Bitmap original) {
        Bitmap result = Bitmap.createBitmap(pathCropView.getWidth(), pathCropView.getHeight(), Bitmap.Config.ARGB_8888);

        final Path path = pathCropView.getPath();

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0XFF000000);

        canvas.drawPath(path, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(original, 0, 0, paint);
        paint.setXfermode(null);

        String mediaPath = BitmapUtils.saveBitmap(result, opts, true);

        result.recycle();
        original.recycle();

        if (mediaPath != null) {
            scanMedia(mediaPath);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void scanMedia(String mediaPath) {
        Log.d(TAG, "mediaPath: " + mediaPath);

        MediaScannerConnection.scanFile(getContext().getApplicationContext(), new String[]{
                mediaPath
        }, new String[]{
                "image/png"
        }, null);

        ArrayList<String> items = new ArrayList<>();
        items.add(mediaPath);

        Intent resultIntent = new Intent();
        resultIntent.putStringArrayListExtra(MediaPickerOpts.INTENT_RES, items);
        FragmentActivity activity = getActivity();
        activity.setResult(RESULT_OK, resultIntent);
        activity.supportFinishAfterTransition();
    }*/
}