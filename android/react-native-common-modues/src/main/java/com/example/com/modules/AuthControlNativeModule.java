package com.example.com.modules;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;

/**
 * Created by heruijun on 2017/3/6.
 */
public class AuthControlNativeModule extends ReactContextBaseJavaModule {

    final ReactApplicationContext reactContext;
    WritableArray codes;

    public AuthControlNativeModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "AuthControlNativeModule";
    }

    @ReactMethod
    public void getAuthCodes(@NonNull String moduleName, @NonNull final Callback callback) {
        Activity currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            callback.invoke("can't find current Activity");
            return;
        }

        if (TextUtils.equals(moduleName, "manager")) {
            codes = Arguments.createArray();
            codes.pushString("A0001");
            codes.pushString("A0002");
            codes.pushString("A0003");
            callback.invoke(codes);
        }
    }
}