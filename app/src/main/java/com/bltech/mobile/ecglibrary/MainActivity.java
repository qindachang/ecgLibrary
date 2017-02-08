package com.bltech.mobile.ecglibrary;

import android.bluetooth.BluetoothGattCharacteristic;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bltech.mobile.utils.DecodeDataCallback;
import com.bltech.mobile.utils.EcgFileUtils;
import com.bltech.mobile.utils.EcgManager;
import com.bltech.mobile.utils.annotation.FilePathMode;
import com.qindachang.bluetoothle.BluetoothLe;
import com.qindachang.bluetoothle.OnLeNotificationListener;
import com.qindachang.bluetoothle.exception.BleException;

import java.io.File;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private EcgManager mEcgManager;
    private BluetoothLe mBluetoothLe;
    private EcgFileUtils mEcgFileUtils;

    DecodeDataCallback mDecodeDataCallback = new DecodeDataCallback() {
        @Override
        public void onDecodeData(short[] data, int heartRate) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBluetoothLe = BluetoothLe.getDefault();

        mEcgManager = EcgManager.getInstance();
        mEcgManager.setDecodeDataCallback(mDecodeDataCallback);

        mBluetoothLe.setOnNotificationListener(TAG, new OnLeNotificationListener() {
            @Override
            public void onSuccess(BluetoothGattCharacteristic characteristic) {
                mEcgManager.decodeData(characteristic.getValue());
            }

            @Override
            public void onFailed(BleException e) {

            }
        });

        mEcgFileUtils = new EcgFileUtils();
        File file = mEcgFileUtils.initFilePath(FilePathMode.SD, "Android/", "haha.ecg");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //避免内存泄露
        mEcgManager.removeCallback(mDecodeDataCallback);
        mBluetoothLe.destroy(TAG);
    }
}
