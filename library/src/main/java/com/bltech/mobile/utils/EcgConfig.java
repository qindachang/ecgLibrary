package com.bltech.mobile.utils;

/**
 * Created by admin on 2017/1/9.
 */

public class EcgConfig {

    private int filterFrequency;

    public EcgConfig(Builder builder) {
        filterFrequency = builder.filterFrequency;
    }

    int getFilterFrequency() {
        return filterFrequency;
    }

    public static class Builder {

        private int filterFrequency;

        public Builder setFilterFrequency(int filterFrequency) {
            this.filterFrequency = filterFrequency;
            return this;
        }

        public EcgConfig build() {
            return new EcgConfig(this);
        }
    }
}
