package com.bltech.mobile.utils;

/**
 * Created by qindachang on 2017/2/9.
 */

class ExceptionBuilder {
    private int type;

    private ExceptionBuilder(Builder builder) {
        type = builder.type;
    }

    int getType() {
        return type;
    }

    static class Builder {
        private int type;

        Builder setType(int type) {
            this.type = type;
            return this;
        }

        ExceptionBuilder build() {
            return new ExceptionBuilder(this);
        }
    }
}
