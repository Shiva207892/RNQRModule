package com.awesomeproject;

import android.content.pm.PackageManager;
import android.util.Log;

import com.app.codescanner.utils.PermissionChanged;
import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactPackage;
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint;
import com.facebook.react.defaults.DefaultReactActivityDelegate;

import java.util.List;

public class MainActivity extends ReactActivity {

  int PERMISSION_REQUEST_CAMERA=101;
  PermissionChanged onpermissionchanged;

  public void setpermissionlistener(PermissionChanged onpermissionchanged)
  {
    this.onpermissionchanged=onpermissionchanged;
  }


  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "QRModuleApp";
  }

  /**
   * Returns the instance of the {@link ReactActivityDelegate}. Here we use a util class {@link
   * DefaultReactActivityDelegate} which allows you to easily enable Fabric and Concurrent React
   * (aka React 18) with two boolean flags.
   */
  @Override
  protected ReactActivityDelegate createReactActivityDelegate() {
    return new DefaultReactActivityDelegate(
        this,
        getMainComponentName(),
        // If you opted-in for the New Architecture, we enable the Fabric Renderer.
        DefaultNewArchitectureEntryPoint.getFabricEnabled(), // fabricEnabled
        // If you opted-in for the New Architecture, we enable Concurrent React (i.e. React 18).
        DefaultNewArchitectureEntryPoint.getConcurrentReactEnabled() // concurrentRootEnabled
        );
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                         int[] grantResults) {
    Log.d("onReqRES: ", "ON REQUEST RESULT CALLED");
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == PERMISSION_REQUEST_CAMERA)
    {
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
      {
        if(onpermissionchanged != null)
        {
          if (onpermissionchanged != null)
            onpermissionchanged.onPermissionChanged("granted");
        } else {
          if (onpermissionchanged != null)
            onpermissionchanged.onPermissionChanged(null);
        }
        }
    }
  }
}
