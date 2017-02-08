package com.bltech.mobile.ecglibrary;

import android.app.Application;

import com.bltech.mobile.utils.EcgConfig;
import com.bltech.mobile.utils.EcgSDK;

/**
 * Created by admin on 2017/1/9.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        EcgSDK.initSDK(new EcgConfig.Builder().setFilterFrequency(50).build());
    }
}
