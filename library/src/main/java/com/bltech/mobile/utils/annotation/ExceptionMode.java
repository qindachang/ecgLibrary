package com.bltech.mobile.utils.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by qindachang on 2017/2/9.
 */
@IntDef(flag = false, value = {ExceptionMode.NDK, ExceptionMode.ABNORMAL})
@Retention(RetentionPolicy.SOURCE)
public @interface ExceptionMode {
    int NDK = 0;
    int ABNORMAL = 1;
}
