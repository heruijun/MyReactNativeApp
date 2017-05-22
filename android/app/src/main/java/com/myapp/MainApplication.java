package com.myapp;

import android.app.Application;
import android.content.Context;

import com.facebook.react.ReactApplication;
import com.oblador.vectoricons.VectorIconsPackage;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.imagepicker.ImagePickerPackage;
import com.microsoft.codepush.react.CodePush;
import com.squareup.leakcanary.LeakCanary;

import java.util.Arrays;
import java.util.List;

/**
 * Created by heruijun on 2017/2/24.
 */
public class MainApplication extends Application implements ReactApplication {

    private static MainApplication instance;

    public static MainApplication get(Context context) {
        return (MainApplication) context.getApplicationContext();
    }

    public static MainApplication getInstance() {
        return instance;
    }

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {

        @Override
        protected String getJSBundleFile() {
            return CodePush.getJSBundleFile();
        }

        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
            new VectorIconsPackage(),
            new ImagePickerPackage(),
            new CodePush(null, getApplicationContext(), BuildConfig.DEBUG),
                    new ImagePickerPackage()
            );
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SoLoader.init(this, /* native exopackage */ false);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
    }
}
