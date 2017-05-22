package com.myapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.myapp.di.components.DaggerReactComponent;
import com.myapp.di.modules.ReactModule;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class BaseReactActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {

    public ReactRootView mReactRootView;

    @Inject
    public ReactInstanceManager mReactInstanceManager;

    private final static int OVERLAY_PERMISSION_REQ_CODE = 10;
    private boolean debugMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReactRootView = new ReactRootView(this);

        DaggerReactComponent.builder()
                .reactModule(new ReactModule(MainApplication.getInstance(), debugMode))
                .build().inject(this);

        if (debugMode == true) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostPause(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostResume(this, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostDestroy();
        }
    }

    @Override
    public void onBackPressed() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @NotNull
    public ReactInstanceManager getReactInstanceManager() {
        return mReactInstanceManager;
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager.showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mReactInstanceManager.onActivityResult(this, requestCode, resultCode, data);

        if (debugMode == true) {
            if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(this)) {
                        Toast.makeText(BaseReactActivity.this, "not granted", Toast.LENGTH_SHORT);
                    }
                }
            }
        }
    }
}
