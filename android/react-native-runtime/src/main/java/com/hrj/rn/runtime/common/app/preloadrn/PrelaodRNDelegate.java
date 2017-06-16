package com.hrj.rn.runtime.common.app.preloadrn;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.KeyEvent;
import android.widget.Toast;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.hrj.rn.runtime.common.app.App;
import com.hrj.rn.runtime.modules.CustomReactPackage;
import com.imagepicker.ImagePickerPackage;
import com.microsoft.codepush.react.CodePush;

/**
 * Created by heruijun on 2017/6/16.
 */
public class PrelaodRNDelegate {

    public ReactRootView mReactRootView;
    public ReactInstanceManager mReactInstanceManager;
    private final Activity mActivity;
    private final String mMainComponentName;
    private Application mApplication;
    private final int OVERLAY_PERMISSION_REQ_CODE = 1111;
    private boolean debugMode = false;

    public PrelaodRNDelegate(Activity activity, String mainComponentName) {
        this.mActivity = activity;
        this.mMainComponentName = mainComponentName;
    }

    public void onCreate() {

        mReactRootView = new ReactRootView(mActivity);

        mApplication = App.getInstance();

        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(mApplication)
                .setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("index.android")
                .addPackage(new MainReactPackage())
                .addPackage(new ImagePickerPackage())
                .addPackage(new CustomReactPackage())
                .addPackage(new CodePush("JJeFB7TGAdV92D7cPbEDynBYQwTO4ksvOXqog", mApplication.getApplicationContext(), com.facebook.react.BuildConfig.DEBUG, "http://192.168.60.118:3000/"))
//                .addPackage(new CodePush("NFBvGszkFiqlbXrW3rlLi9aRc7x44ksvOXqog", mApplication.getApplicationContext(), com.facebook.react.BuildConfig.DEBUG, "http://192.168.77.120:3000/"))
                .setJSBundleFile(CodePush.getJSBundleFile("index.android.bundle"))
                .setUseDeveloperSupport(debugMode)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();

        if (debugMode == true) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(mActivity)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + mActivity.getPackageName()));
                    mActivity.startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
                }
            }
        }

        mReactRootView.startReactApplication(mReactInstanceManager, "operatingStatement", null);
        mActivity.setContentView(mReactRootView);
    }

    public void onResume() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostResume(mActivity, (DefaultHardwareBackBtnHandler) mActivity);
        }
    }

    public void onPause() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostPause(mActivity);
        }
    }

    public void onDestroy() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostDestroy();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mReactInstanceManager.onActivityResult(mActivity, requestCode, resultCode, data);

        if (debugMode == true) {
            if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(mActivity)) {
                        Toast.makeText(mActivity, "not granted", Toast.LENGTH_SHORT);
                    }
                }
            }
        }
    }

    public boolean onBackPressed() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onBackPressed();
            return true;
        }
        return false;
    }

    public ReactInstanceManager getReactInstanceManager() {
        return mReactInstanceManager;
    }

    public boolean onRNKeyUp(int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager.showDevOptionsDialog();
            return true;
        }
        return false;
    }

}