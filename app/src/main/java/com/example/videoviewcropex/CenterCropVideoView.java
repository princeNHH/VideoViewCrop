package com.example.videoviewcropex;


import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.TextureView;

public class CenterCropVideoView extends TextureView {

    private int videoWidth = 0;
    private int videoHeight = 0;

    // Constructor 1: Context
    public CenterCropVideoView(Context context) {
        super(context);
        init();
    }

    // Constructor 2: Context + AttributeSet
    public CenterCropVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    // Constructor 3: Context + AttributeSet + defStyleAttr
    public CenterCropVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        videoWidth = 100;
        videoHeight = 100;
    }

    public void updateVideoSize(int videoWidth, int videoHeight) {
        this.videoWidth = videoWidth;
        this.videoHeight = videoHeight;
        adjustAspectRatio();
    }

    private void adjustAspectRatio() {
        if (videoWidth == 0 || videoHeight == 0) return;

        float viewWidth = getWidth();
        float viewHeight = getHeight();
        float videoAspectRatio = (float) videoWidth / videoHeight;
        float viewAspectRatio = viewWidth / viewHeight;

        float scaleX;
        float scaleY;
        if (videoAspectRatio > viewAspectRatio) {
            scaleX = videoAspectRatio / viewAspectRatio;
            scaleY = 1f;
        } else {
            scaleX = 1f;
            scaleY = viewAspectRatio / videoAspectRatio;
        }

        Matrix matrix = new Matrix();
        matrix.setScale(scaleX, scaleY, viewWidth / 2, viewHeight / 2);
        setTransform(matrix);
        invalidate();
    }
}