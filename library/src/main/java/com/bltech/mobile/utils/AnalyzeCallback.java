package com.bltech.mobile.utils;

/**
 * Created by qindachang on 2017/2/9.
 */

public interface AnalyzeCallback extends Callback {
    void onSuccess(AnalyzeResult result);

    void onFailed(AnalyzeException e, int status);
}
