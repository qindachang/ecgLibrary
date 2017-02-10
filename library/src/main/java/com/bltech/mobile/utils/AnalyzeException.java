package com.bltech.mobile.utils;

import com.bltech.mobile.utils.annotation.ExceptionMode;

import java.io.Serializable;

/**
 * Created by qindachang on 2017/2/8.
 */

@SuppressWarnings("serial")
public class AnalyzeException implements Serializable {

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
