package com.hrj.rn.runtime.modules;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * Created by heruijun on 2017/3/6.
 */
public class IntentNativeModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    final ReactApplicationContext reactContext;
    public static final int REQUEST_RESULT_CODE = 15001;
    Callback mCallback;

    public IntentNativeModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.reactContext.addActivityEventListener(this);
    }

    @Override
    public String getName() {
        return "IntentNativeModule";
    }

    @ReactMethod
    public void intent(@NonNull String action, @NonNull final Callback callback) {
        Activity currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            callback.invoke("can't find current Activity");
            return;
        }

        mCallback = callback;

        Intent intent = new Intent(action);
        int requestCode = REQUEST_RESULT_CODE;
        try {
            currentActivity.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            callback.invoke("Activity not found");
        }
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) {
            mCallback = null;
        }
        // TODO
    }

    @Override
    public void onNewIntent(Intent intent) {

    }
}