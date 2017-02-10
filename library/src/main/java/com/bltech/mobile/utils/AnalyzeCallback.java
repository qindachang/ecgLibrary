package com.bltech.mobile.utils;

/**
 * Created by qindachang on 2017/2/9.
 */

public interface AnalyzeCallback extends Callback {
    void onSuccess(AnalyzeResult result);

    /**
     * @param status If status equals 0, this error come from NDK .c file. else if status equals 1,
     *               the error cause receive abnormally data.
     */
    void onFailed(AnalyzeException e, int status);
}
