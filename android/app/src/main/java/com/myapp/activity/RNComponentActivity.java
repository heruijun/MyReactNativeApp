package com.myapp.activity;

import com.hrj.rn.runtime.common.app.BaseReactActivity;

import javax.annotation.Nullable;

/**
 * Created by heruijun on 2017/2/23.
 */
public class RNComponentActivity extends BaseReactActivity {
    
    @Nullable
    @Override
    protected String getMainComponentName() {
        return "rnComponent";
    }
}