package com.bltech.mobile.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by qindachang on 2017/2/9.
 */

public class AnalyzeResult implements Parcelable {

    private float degree;

    private int[] HRV_des;


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
}
