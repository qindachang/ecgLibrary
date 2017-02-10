package com.bltech.mobile.utils;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 *
 * Created by qindachang on 2017/2/10.
 */

class Suggestion {
    private float hrv;
    private int[] HRV_des;

    private Context mContext;

    Suggestion(Context context, @NonNull AnalyzeResult analyzeResult) {
        mContext = context;
        hrv = analyzeResult.getDegree();
        HRV_des = analyzeResult.getHRV_des();
    }

    String getComment() {
        StringBuilder builder = new StringBuilder();
        if (hrv <= 0.4) {
            builder.append(mContext.getResources().getString(R.string.Sympathetic1)).append(",");
        } else if (hrv < 0.8 & hrv > 0.4) {
            builder.append(mContext.getResources().getString(R.string.Sympathetic2)).append(",");
        } else if (hrv >= 0.8 && hrv <= 1.5) {
            builder.append(mContext.getResources().getString(R.string.Sympathetic3)).append(",");
        } else if (hrv <= 3.9 & hrv > 1.5) {
            builder.append(mContext.getResources().getString(R.string.Sympathetic4)).append(",");
        } else if (hrv > 3.9) {
            builder.append(mContext.getResources().getString(R.string.Sympathetic5)).append(",");
        }
        if (HRV_des[0] <= 13) {
            builder.append(mContext.getResources().getString(R.string.mental_pressure1)).append(",");
        } else if (HRV_des[0] <= 51 & HRV_des[0] > 13) {
            builder.append(mContext.getResources().getString(R.string.mental_pressure2)).append(",");
        } else if (HRV_des[0] <= 75 & HRV_des[0] > 51) {
            builder.append(mContext.getResources().getString(R.string.mental_pressure3)).append(",");
        } else if (HRV_des[0] > 75) {
            builder.append(mContext.getResources().getString(R.string.mental_pressure4)).append(",");
        }
        if (HRV_des[1] <= 25) {
            builder.append(mContext.getResources().getString(R.string.body_pressure1)).append(",");
        } else if (HRV_des[1] <= 50 & HRV_des[1] > 25) {
            builder.append(mContext.getResources().getString(R.string.body_pressure2)).append(",");
        } else if (HRV_des[1] <= 75 & HRV_des[1] > 50) {
            builder.append(mContext.getResources().getString(R.string.body_pressure3)).append(",");
        } else if (HRV_des[1] > 75) {
            builder.append(mContext.getResources().getString(R.string.body_pressure4)).append(",");
        }
        if (HRV_des[3] <= 20) {
            builder.append(mContext.getResources().getString(R.string.Compression1));
        } else if (HRV_des[3] <= 40 & HRV_des[3] > 20) {
            builder.append(mContext.getResources().getString(R.string.Compression2));
        } else if (HRV_des[3] <= 60 & HRV_des[3] > 40) {
            builder.append(mContext.getResources().getString(R.string.Compression3));
        } else if (HRV_des[3] <= 80 & HRV_des[3] > 60) {
            builder.append(mContext.getResources().getString(R.string.Compression4));
        } else if (HRV_des[3] > 80) {
            builder.append(mContext.getResources().getString(R.string.Compression5));
        }
        return builder.toString();
    }

    String getSuggest() {
        StringBuilder builder = new StringBuilder();
        if (hrv <= 0.4 & HRV_des[3] <= 60) {
            builder.append(mContext.getResources().getString(R.string.Evaluation1));
        } else if (hrv <= 0.4 & HRV_des[3] > 60) {
            builder.append(mContext.getResources().getString(R.string.Evaluation2));
        } else if (HRV_des[0] <= 13 & HRV_des[3] <= 60) {
            builder.append(mContext.getResources().getString(R.string.Evaluation3));
        } else if (HRV_des[0] <= 13 & HRV_des[3] > 60) {
            builder.append(mContext.getResources().getString(R.string.Evaluation4));
        } else if (hrv > 3.9 & HRV_des[3] <= 60) {
            builder.append(mContext.getResources().getString(R.string.Evaluation5));
        } else if (hrv > 3.9 & HRV_des[3] > 60) {
            builder.append(mContext.getResources().getString(R.string.Evaluation6));
        } else if (HRV_des[1] > 50 & HRV_des[3] <= 60) {
            builder.append(mContext.getResources().getString(R.string.Evaluation7));
        } else if (HRV_des[1] > 50 & HRV_des[3] > 60) {
            builder.append(mContext.getResources().getString(R.string.Evaluation8));
        } else if (hrv >= 0.8 & hrv <= 3.9 & HRV_des[0] > 13
                & HRV_des[0] <= 51 & HRV_des[1] <= 50
                & HRV_des[3] > 40 & HRV_des[3] <= 80) {
            builder.append(mContext.getResources().getString(R.string.Evaluation9));
        } else if (hrv >= 0.8 & hrv <= 3.9 & HRV_des[0] > 13
                & HRV_des[0] <= 51 & HRV_des[1] <= 50
                & HRV_des[3] > 80) {
            builder.append(mContext.getResources().getString(R.string.Evaluation10));
        } else {
            builder.append(mContext.getResources().getString(R.string.Evaluation11));
        }
        return builder.toString();
    }
}
