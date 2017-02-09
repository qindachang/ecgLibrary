package com.bltech.mobile.utils;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by qindachang on 2017/2/8.
 */

public class EcgManager {
    private static EcgManager ourInstance = new EcgManager();

    public static EcgManager getInstance() {
        return ourInstance;
    }

    private EcgManager() {
    }

    private byte[] txValue = new byte[20];
    private short[] converter = new short[20];
    private short[] heartBeat = new short[2];
    private short[] ecgData = new short[500];

    private Set<Callback> mCallbacks = new LinkedHashSet<>();

    public void decodeData(byte[] data) {
        int count = data.length;
        for (int i = 0; i < count; i++) {
            txValue[i] = EcgNative.decode_raw(data[i]);
            converter[i] = (short) ((txValue[i] & 0xff));
            EcgNative.EcgInserData(converter[i]);
            int ret = EcgNative.EcgProcessData(ecgData, heartBeat);
            if (ret == 1) {
                for (Callback callback : mCallbacks) {
                    if (callback instanceof DecodeDataCallback) {
                        ((DecodeDataCallback) callback).onDecodeData(ecgData, heartBeat[0]);
                    }
                }
            }
        }
    }

    public void setDecodeDataCallback(DecodeDataCallback decodeDataCallback) {
        mCallbacks.add(decodeDataCallback);
    }

    public void removeCallback(Callback callback) {
        mCallbacks.remove(callback);
    }

}
