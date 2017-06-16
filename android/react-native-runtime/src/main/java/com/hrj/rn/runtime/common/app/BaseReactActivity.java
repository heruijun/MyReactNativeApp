package com.hrj.rn.runtime.common.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.hrj.rn.runtime.common.app.preloadrn.PrelaodRNDelegate;

import javax.annotation.Nullable;

public class BaseReactActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {

    private PrelaodRNDelegate mPrelaodRNDelegate;

    public BaseReactActivity() {
        mPrelaodRNDelegate = createPreloadRNDelegate();
    }

    private PrelaodRNDelegate createPreloadRNDelegate() {
        return new PrelaodRNDelegate(this, getMainComponentName());
    }

    @Nullable
    protected String getMainComponentName() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrelaodRNDelegate.onCreate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPrelaodRNDelegate.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPrelaodRNDelegate.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPrelaodRNDelegate.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (!mPrelaodRNDelegate.onBackPressed()) {
            super.onBackPressed();
        }
    }

    public ReactInstanceManager getReactInstanceManager() {
        return mPrelaodRNDelegate.getReactInstanceManager();
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mPrelaodRNDelegate.onRNKeyUp(keyCode) || super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPrelaodRNDelegate.onActivityResult(requestCode, resultCode, data);
    }
}
