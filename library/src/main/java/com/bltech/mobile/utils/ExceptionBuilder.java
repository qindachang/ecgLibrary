package com.bltech.mobile.utils;

import com.bltech.mobile.utils.annotation.ExceptionMode;

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

        public Builder setType(@ExceptionMode int type) {
            this.type = type;
            return this;
        }
        public ExceptionBuilder build() {
            return new ExceptionBuilder(this);
        }
    }
}
