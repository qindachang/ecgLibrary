package com.bltech.mobile.utils.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by qindachang on 2017/2/8.
 */
@IntDef(flag = false, value = {FilePathMode.SD, FilePathMode.INTERNAL})
@Retention(RetentionPolicy.SOURCE)
public @interface FilePathMode {
    int SD = 1;
    int INTERNAL = 2;
}
