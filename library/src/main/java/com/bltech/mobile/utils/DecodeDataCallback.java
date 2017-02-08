package com.bltech.mobile.utils;

/**
 * Created by qindachang on 2017/2/8.
 */

public interface DecodeDataCallback extends Callback{
    void onDecodeData(short[] data,int heartRate);
}
