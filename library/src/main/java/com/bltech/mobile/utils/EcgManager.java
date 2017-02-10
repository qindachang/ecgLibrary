package com.bltech.mobile.utils;

import com.bltech.mobile.utils.annotation.ExceptionMode;

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

    /**
     * decode & translate & lvbo bluetooth receive data.
     *
     * @param data original ecg data from bluetooth
     */
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
        if (!mCallbacks.contains(decodeDataCallback)) {
            mCallbacks.add(decodeDataCallback);
        }
    }

    public boolean removeCallback(Callback callback) {
        return mCallbacks.remove(callback);
    }

    /**
     * Analyze ecg file in order to get analyze result.
     *
     * @param filePath ecg file path
     * @param callback AnalyzeCallback
     */
    public void analyzeEcgFile(String filePath, AnalyzeCallback callback) {
        if (!mCallbacks.contains(callback)) {
            mCallbacks.add(callback);
        }
        AnalyzeResult analyzeResult = new AnalyzeResult();
        analyzeResult.setDegree(1.0f);
        analyzeResult.setHRV_des(new int[]{0, 0, 0, 0, 0, 0});
        analyzeResult.setAnalyze(new int[]{0, 0, 0, 0, 0, 0});
        try {
            int[] HRV_des = new int[6];
            int[] analyze = new int[6];
            float degree = EcgNative.HRV_des(filePath, HRV_des, analyze);
            analyzeResult.setDegree(degree);
            analyzeResult.setHRV_des(HRV_des);
            analyzeResult.setAnalyze(analyze);
            if (checkArrayIsAllTheSame(HRV_des)) {
                for (Callback c : mCallbacks) {
                    if (c instanceof AnalyzeCallback) {
                        AnalyzeException exception = new AnalyzeException();
                        ExceptionBuilder builder = new ExceptionBuilder.Builder().setType(ExceptionMode.ABNORMAL).build();
                        exception.setType(builder);
                        ((AnalyzeCallback) c).onFailed(exception, ExceptionMode.ABNORMAL);
                    }
                }
            } else {
                for (Callback c : mCallbacks) {
                    if (c instanceof AnalyzeCallback) {
                        ((AnalyzeCallback) c).onSuccess(analyzeResult);
                    }
                }
            }
        } catch (Exception e) {
            for (Callback c : mCallbacks) {
                if (c instanceof AnalyzeCallback) {
                    AnalyzeException exception = new AnalyzeException();
                    ExceptionBuilder builder = new ExceptionBuilder.Builder().setType(ExceptionMode.NDK).build();
                    exception.setType(builder);
                    ((AnalyzeCallback) c).onFailed(exception, ExceptionMode.NDK);
                }
            }
        }

    }

    private boolean checkArrayIsAllTheSame(int[] arr) {
        int f = arr[0];
        for (int what : arr) {
            if (what != f) {
                return false;
            }
        }
        return true;
    }

    /**
     * get comment and suggest form AnalyzeResult object.
     *
     * @return String[0]comment，String[1]suggest
     */
    public String[] getAnalyzeComment(AnalyzeResult analyzeResult) {
        //TODO I am thinking about how to write this function.
        return null;
    }
}
