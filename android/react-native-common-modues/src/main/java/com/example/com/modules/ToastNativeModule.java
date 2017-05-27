package com.example.com.modules;

import android.content.Context;
import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * Created by heruijun on 2017/3/6.
 */
public class ToastNativeModule extends ReactContextBaseJavaModule {

    public static final String REACTCLASSNAME = "ToastNativeModule";
    private Context mContext;

    public ToastNativeModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
    }

    @Override
    public String getName() {
        return REACTCLASSNAME;
    }

    /**
     * 必须添加反射注解不然会报错
     * 这个方法就是ReactNative将要调用的方法，会通过此类名字调用
     *
     * @param msg
     */
    @ReactMethod
    public void callNativeMethod(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}