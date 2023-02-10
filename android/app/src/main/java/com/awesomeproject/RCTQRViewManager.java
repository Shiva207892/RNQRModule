package com.awesomeproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.app.codescanner.QBarScannerView;
import com.app.codescanner.utils.PermissionChanged;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

public class RCTQRViewManager extends SimpleViewManager<QBarScannerView> {
    public static final String REACT_CLASS = "QRModuleAndroid";
    ReactApplicationContext mCallerContext;
    RelativeLayout qrview;
    QBarScannerView scannerView;
    int PERMISSION_REQUEST_CAMERA=101;
    //public static PermissionChanged camerastoragepermissionlistener;
    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    public RCTQRViewManager(ReactApplicationContext reactContext) {
        mCallerContext = reactContext;
        Log.d("abc", "RCTQRViewManager is called");
     //   if(reactContext.getCurrentActivity() instanceof MainActivity)
     //   {
         //   ((MainActivity)(reactContext.getCurrentActivity())).setpermissionlistener(camerastoragepermissionlistener);
            //(((MainActivity) reactContext.getCurrentActivity()).setpermissionlistener(camerastoragepermissionlistener))
      //  }
    }

    QBarScannerView.ResultHandler mScanResult = new QBarScannerView.ResultHandler()
    {
        @Override
        public void handleResult(String result,String type)
        {
            try
            {
                String codevalue = result;
               // Toast.makeText(mCallerContext,type+" >>"+result,Toast.LENGTH_SHORT).show();
                //common.showapplog("BarCodeVALUE 1:>> ",codevalue);
                codevalue = codevalue.replace("&","##AMP##");
                // common.showapplog("BarCodeVALUE 2:>> ",codevalue);
                // common.showapplog("BarCodeFormat ",str);

                Log.e("res is:", result);
                RNEventEmitter rnEventEmitter = new RNEventEmitter(mCallerContext);
                rnEventEmitter.sendEvent("onCodeSuccess", result);
                resumecamera(scannerView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public static boolean iscamerapermissionenabled(final Context context) {
        Log.d("iscameraPermission:", "iscamerapermissionenabled called");
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    public QBarScannerView getscannerview(ReactApplicationContext context,int width, int height)
    {
        QBarScannerView scannerView=new QBarScannerView(context.getBaseContext());
        //View myview = LayoutInflater.from(mCallerContext).inflate(R.layout.activity_main,null);

        //QBarScannerView scannerView= (QBarScannerView)myview.findViewById(R.id.scanner_view);
        //RelativeLayout rootview= (RelativeLayout)myview.findViewById(R.id.rootview);
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        if(width > 0 && height > 0)
        {
            params.width = width;
            params.height = height;
        }
        scannerView.setLayoutParams(params);
        scannerView.setResultHandler(mScanResult);

        if(! iscamerapermissionenabled(mCallerContext))
        {
            checkcamerapermission();
        }
        else
        {
            scannerView.startCamera(0);
            scannerView.setFormats(null);
        }

        if(iscamerapermissionenabled(mCallerContext))
        {

        }

//        try {
//            rootview.removeAllViews();
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }
        return scannerView;
    }

    public PermissionChanged camerastoragepermissionlistener = new PermissionChanged() {
        @Override
        public void onPermissionChanged(Object object)
        {
            if (object != null)
            {
                scannerView.startCamera(0);
                scannerView.setFormats(null);
            }
        }
    };

    public void checkcamerapermission() {
        Log.d("checkcamperm:", "check camera permisssion called");

        if (!iscamerapermissionenabled(mCallerContext)) {
            ActivityCompat.requestPermissions(
                    mCallerContext.getCurrentActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
            return;
        }
        camerastoragepermissionlistener.onPermissionChanged("granted");
    }



    @ReactProp(name="startcamera")
    public void startcamera(QBarScannerView scannerView, int type) {

    }

    public void resumecamera(QBarScannerView scannerView) {
        if(iscamerapermissionenabled(mCallerContext) && scannerView != null && scannerView.isShown())
        {
            scannerView.resumeCameraPreview(mScanResult);
        }
    }

    @NonNull
    @Override
    protected QBarScannerView createViewInstance(@NonNull ThemedReactContext
                                                              context) {
        Log.d("createinstance", "createinstance is called");
        qrview=new RelativeLayout(context);
        scannerView = getscannerview(mCallerContext,0,0);
        //qrview.addView(scannerView);
        qrview.setBackgroundColor(Color.DKGRAY);
        return scannerView;
    }
}
