package com.bltech.mobile.utils;

import com.bltech.mobile.utils.annotation.ExceptionMode;

/**
 * Created by qindachang on 2017/2/8.
 */

public class AnalyzeException  {

    private int type;

    void setType(ExceptionBuilder builder) {
        this.type = builder.getType();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (type == ExceptionMode.ABNORMAL) {
            builder.append("Data receive error!");
        } else if (type == ExceptionMode.NDK) {
            builder.append("The ecg analyze error!");
        }
        return builder.toString();
    }
}
