package com.bltech.mobile.utils;

/**
 * Created by admin on 2017/1/9.
 */

public class EcgSDK {

    public static void initSDK(EcgConfig config) {
        EcgNative.initialize();
        int filterFrequency = config.getFilterFrequency();
        EcgNative.EcgIni(filterFrequency);
    }


}
