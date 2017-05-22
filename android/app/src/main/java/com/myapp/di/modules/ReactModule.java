package com.myapp.di.modules;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.shell.MainReactPackage;
import com.imagepicker.ImagePickerPackage;
import com.microsoft.codepush.react.CodePush;
import com.myapp.MainApplication;
import com.myapp.rnmodules.CustomReactPackage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by heruijun on 2017/4/21.
 */
@Module
public class ReactModule {

    private final MainApplication application;

    private boolean debugMode;

    public ReactModule(MainApplication application, boolean debugMode) {
        this.application = application;
        this.debugMode = debugMode;
    }

    @Provides
    @Singleton
    ReactInstanceManager provideReactInstanceManager() {
        return ReactInstanceManager.builder()
                .setApplication(application)
                .setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("index.android")
                .addPackage(new MainReactPackage())
                .addPackage(new ImagePickerPackage())
                .addPackage(new CustomReactPackage())
                .addPackage(new CodePush("NFBvGszkFiqlbXrW3rlLi9aRc7x44ksvOXqog", application.getApplicationContext(), com.facebook.react.BuildConfig.DEBUG, "http://192.168.77.120:3000/"))
                .setJSBundleFile(CodePush.getJSBundleFile("index.android.bundle"))
                .setUseDeveloperSupport(debugMode)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();
    }

}
