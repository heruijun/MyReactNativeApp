package com.myapp.activity;

import android.os.Bundle;

import com.hrj.rn.runtime.common.app.BaseReactActivity;

/**
 * Created by heruijun on 2017/2/23.
 */
public class RNActivity extends BaseReactActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReactRootView.startReactApplication(mReactInstanceManager, "operatingStatement", null);
        setContentView(mReactRootView);
    }
}