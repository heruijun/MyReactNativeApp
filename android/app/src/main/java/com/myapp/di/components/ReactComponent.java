package com.myapp.di.components;

import com.myapp.BaseReactActivity;
import com.myapp.di.modules.ReactModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by heruijun on 2017/4/21.
 */
@Component(modules = ReactModule.class)
@Singleton
public interface ReactComponent {

    void inject(BaseReactActivity baseReactActivity);

}
