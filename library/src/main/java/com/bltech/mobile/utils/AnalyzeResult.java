package com.bltech.mobile.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by qindachang on 2017/2/9.
 */

public class AnalyzeResult implements Parcelable {
    /**
     * 交感-副交感神经平衡度
     */
    private float degree;
    /**
     * 6位int数组。【精神压力，疲劳程度，身心负荷，身体素质，心脏功能年龄（1：青少年，2：青壮年，3：中年，4：年轻老人，
     * 5：老年），心率】
     */
    private int[] HRV_des;
    /**
     * 6种异常情况：【心率不齐，心动过速，心动过缓，停搏情况，漏搏情况，房性早搏】
     */
    private int[] analyze;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.degree);
        dest.writeIntArray(this.HRV_des);
        dest.writeIntArray(this.analyze);
    }

    public AnalyzeResult() {
    }

    protected AnalyzeResult(Parcel in) {
        this.degree = in.readFloat();
        this.HRV_des = in.createIntArray();
        this.analyze = in.createIntArray();
    }

    public static final Parcelable.Creator<AnalyzeResult> CREATOR = new Parcelable.Creator<AnalyzeResult>() {
        @Override
        public AnalyzeResult createFromParcel(Parcel source) {
            return new AnalyzeResult(source);
        }

        @Override
        public AnalyzeResult[] newArray(int size) {
            return new AnalyzeResult[size];
        }
    };

    public float getDegree() {
        return degree;
    }

    public void setDegree(float degree) {
        this.degree = degree;
    }

    public int[] getHRV_des() {
        return HRV_des;
    }

    public void setHRV_des(int[] HRV_des) {
        this.HRV_des = HRV_des;
    }

    public int[] getAnalyze() {
        return analyze;
    }

    public void setAnalyze(int[] analyze) {
        this.analyze = analyze;
    }

    @Override
    public String toString() {
        return "AnalyzeResult:{degree=" + degree
                + ", HRV_des[]=" + Arrays.toString(HRV_des)
                + ", analyze[]=" + Arrays.toString(analyze)
                + "}";
    }
}
