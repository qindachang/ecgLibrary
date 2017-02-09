package com.bltech.mobile.ecglibrary.ui;

import android.app.Activity;

import com.bltech.mobile.ecglibrary.BasePresenter;
import com.bltech.mobile.ecglibrary.BaseView;

/**
 * Created by qindachang on 2017/2/9.
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {
        void onBluetoothScanning(boolean scanning);
        void onBluetoothConnectChange(boolean connected);
        void onEcgChartDataChange(short[] data);
        void onHeartRateChange(int heartRate);
        void onCheckupCountDown(String time);
        void onCheckupComplete();
        Activity getActivity();
    }

    interface Presenter extends BasePresenter {
        void onEcgCheckupClick();
        void scanAndConnect();
    }
}