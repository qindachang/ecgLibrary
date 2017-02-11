package com.bltech.mobile.utils;

/**
 * Created by admin on 2017/1/9.
 */

public class EcgSDK {

    private EcgSDK(){}

    public static void initSDK(EcgConfig config) {
        if (config == null) {
            throw new NullPointerException("EcgConfig object can not be null.");
        }
        EcgNative.initialize();
        int filterFrequency = config.getFilterFrequency();
        EcgNative.EcgIni(filterFrequency);
    }

}
