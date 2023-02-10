package com.app.codescanner.core;


import android.hardware.Camera;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

// This code is mostly based on the top answer here: http://stackoverflow.com/questions/18149964/best-use-of-handlerthread-over-other-similar-classes
public class CameraHandlerThread extends HandlerThread {
    private static final String LOG_TAG = "CameraHandlerThread";

    private BarcodeScannerView mScannerView;

    public CameraHandlerThread(BarcodeScannerView scannerView) {
        super("CameraHandlerThread");
        mScannerView = scannerView;
        start();
    }

    public void startCamera(final int cameraId) {
        Handler localHandler = new Handler(getLooper());
        localHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.e("Qbarscanner:::",">>0");
                final Camera camera = CameraUtils.getCameraInstance(cameraId);
                if(camera!= null)
                    Log.e("Qbarscanner:::",">>1");

                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Qbarscanner:::",">>11111");
                        mScannerView.setupCameraPreview(CameraWrapper.getWrapper(camera, cameraId));
                        Log.e("Qbarscanner:::",">>22");
                    }
                });
            }
        });
    }
}
