package com.awesomeproject;
import androidx.annotation.NonNull;

import com.app.codescanner.QBarScannerView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.Map;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class RNEventEmitter extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "RNEventEmitter";
    private DeviceEventManagerModule.RCTDeviceEventEmitter mEmitter;

    public static ReactApplicationContext reactContext;
    RNEventEmitter(ReactApplicationContext context) {
        super(context);
        reactContext = context;
    }

    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    public void sendEvent(String eventName, String message) {
//        WritableMap params = Arguments.createMap();
//        params.putString("message", message);
        if(mEmitter == null) {
            mEmitter = getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
        }
        if(mEmitter != null) {
            mEmitter.emit(eventName, message);
        }
    }

    @ReactMethod
    public void toggle(Callback callback) {
        Log.d("11111", "Hello RNEventEmitter");
        callback.invoke("Hello welcome to native module");
    }

    private int count = 0;

    @ReactMethod
    public void test() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                count+=1;
                Log.d("11111", "Hello testing");
                sendEvent("onCodeSuccess", String.valueOf(count));
            }
        }, 0, 2000);
    }

    public View getscannerview(int width,int height)
    {
        QBarScannerView scannerView=new QBarScannerView(reactContext);
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        if(width > 0 && height > 0)
        {
            params.width = width;
            params.height = height;
        }
        scannerView.setLayoutParams(params);
        scannerView.startCamera();
        scannerView.setFormats(null);

        return scannerView;
    }


    @ReactMethod
    public void createQRModuleEvent(String name, String location) {
        Log.d("QRModule", "Create event called with name: " + name
                + " and location: " + location);
    }

//    @ReactMethod
//    public void gotoNextScreen() {
//        Intent intent = new Intent(reactContext, ScanActivity.class);
//        getCurrentActivity().startActivity(intent);
//    }
}
