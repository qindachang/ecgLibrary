package com.bltech.mobile.ecglibrary;

import android.app.Application;

import com.bltech.mobile.utils.EcgConfig;
import com.bltech.mobile.utils.EcgSDK;
import com.qindachang.bluetoothle.BluetoothConfig;
import com.qindachang.bluetoothle.BluetoothLe;

/**
 * Created by admin on 2017/1/9.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        EcgSDK.initSDK(new EcgConfig.Builder().setFilterFrequency(50).build());

        BluetoothConfig config = new BluetoothConfig.Builder()
                .enableQueueInterval(true)
                .setQueueIntervalTime(BluetoothConfig.AUTO)
                .build();
        BluetoothLe.getDefault().init(this,config);
    }
}
