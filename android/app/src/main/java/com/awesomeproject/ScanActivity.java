package com.awesomeproject;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.codescanner.QBarScannerView;
import com.app.codescanner.zbar.BarScannerView;
import com.app.codescanner.zbar.BarcodeFormat;

import java.util.ArrayList;
import java.util.List;

public class ScanActivity extends AppCompatActivity {

    QBarScannerView mScannerView;
    QBarScannerView.ResultHandler mScanResult;
    int PERMISSION_REQUEST_CAMERA=101;
    private ItemChanged camerastoragepermissionlistener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("onCreate:", "on create called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout rootview= (RelativeLayout)findViewById(R.id.rootview);
        //mScannerView = (QBarScannerView)findViewById(R.id.scanner_view);
        mScannerView = new QBarScannerView(this);


        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        mScannerView.setLayoutParams(params);
        rootview.addView(mScannerView);
        Log.d("oncreate:", "on create complete");
    }

    @Override
    protected void onResume() {
        Log.d("onResume:", "ON RESUME CALLED");
        if(! iscamerapermissionenabled(this))
        {
            checkcamerapermission(new ItemChanged() {
                @Override
                public void onItemChanged(Object object)
                {
                    if (object != null)
                    {
                        startQrReader();
                    }
                }
            });
        }
        else
        {
            startQrReader();
        }
        super.onResume();

    }

    @Override
    protected void onPause() {
        Log.d("onPause:", "ON PAUSE CALLED");
        stopqrreader();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void stopqrreader()
    {
        Log.d("stopqrreader: ", "stop qr reader called");
        try {
            if(mScannerView != null && mScannerView.isShown())
                mScannerView.stopCamera();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static boolean iscamerapermissionenabled(final Context context) {
        Log.d("iscameraPermission:", "iscamerapermissionenabled called");
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    public void checkcamerapermission(ItemChanged mPermissionChanged) {
        Log.d("checkcamperm:", "check camera permisssion called");
        this.camerastoragepermissionlistener = mPermissionChanged;

        if (!iscamerapermissionenabled(this)) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
            return;
        }
        camerastoragepermissionlistener.onItemChanged("granted");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        Log.d("onReqRES: ", "ON REQUEST RESULT CALLED");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (camerastoragepermissionlistener != null)
                    camerastoragepermissionlistener.onItemChanged("granted");
            } else {
                if (camerastoragepermissionlistener != null)
                    camerastoragepermissionlistener.onItemChanged(null);
            }
        }
    }

    public void startQrReader()
    {
        Log.d("startQRReader","starting qr reader");
        mScanResult = new QBarScannerView.ResultHandler()
        {
            @Override
            public void handleResult(String result,String type)
            {
                try
                {
                    String codevalue = result;
                    Toast.makeText(ScanActivity.this,type+" >>"+result,Toast.LENGTH_SHORT).show();
                    //common.showapplog("BarCodeVALUE 1:>> ",codevalue);
                    codevalue = codevalue.replace("&","##AMP##");
                    // common.showapplog("BarCodeVALUE 2:>> ",codevalue);
                    // common.showapplog("BarCodeFormat ",str);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mScannerView.resumeCameraPreview(mScanResult);
                    }
                }, 200);
            }
        };

        if(mScannerView != null && (mScanResult != null))
        {
            if(mScannerView != null)
            {
                if(mScannerView.isShown())
                    mScannerView.stopCamera();
            }

            mScannerView.setVisibility(View.VISIBLE);
            mScannerView.setResultHandler(mScanResult);
            // You can optionally set aspect ratio tolerance level
            // that is used in calculating the optimal Camera preview size
            //mScannerView.setAspectTolerance(0.2f);
            mScannerView.startCamera();
            //mScannerView.setFlash(mFlash);
            //mScannerView.setFormats(setSupportedFormat(true,false,false));
            mScannerView.setFormats(null);
            //mScannerView.setFormats(common.setSupportedFormat(true,false,false));
        }
    }
}