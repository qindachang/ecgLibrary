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
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private CompositeSubscription mSubscriptions;

    private EcgManager mEcgManager;
    private BluetoothLe mBluetoothLe;
    private EcgFileUtils mEcgFileUtils;

    DecodeDataCallback mDecodeDataCallback = new DecodeDataCallback() {
        @Override
        public void onDecodeData(short[] data, int heartRate) {
            //解密、类型转换、滤波 后的数据，这个数据可以用来绘制波形
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBluetoothLe = BluetoothLe.getDefault();
        mEcgManager = EcgManager.getInstance();
        mEcgFileUtils = new EcgFileUtils();
        mSubscriptions = new CompositeSubscription();

        mEcgManager.setDecodeDataCallback(mDecodeDataCallback);

        File file = mEcgFileUtils.initFilePath(FilePathMode.SD, "Android/", "haha.ecg");
        mEcgFileUtils.readyWriteData(file.getPath());

        mBluetoothLe.setOnNotificationListener(TAG, new OnLeNotificationListener() {
            @Override
            public void onSuccess(BluetoothGattCharacteristic characteristic) {
                mEcgManager.decodeData(characteristic.getValue());//解密、类型转换、滤波数据
                mEcgFileUtils.writeDataAsFile(characteristic.getValue());//将接收到的数据写入到文件中
            }

            @Override
            public void onFailed(BleException e) {

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //避免内存泄露
        mEcgManager.removeCallback(mDecodeDataCallback);
        mBluetoothLe.destroy(TAG);
        mSubscriptions.clear();
    }

    /**
     * 检测倒计时
     */
    private void countDown(final int seconds) {
        Subscription subscription = Observable.interval(1, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .take(seconds + 1)
                .map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long aLong) {
                        return seconds - aLong.intValue();
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        //倒计时完毕，也就是检测完毕
                        mEcgFileUtils.finishWriteData();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }
                });
        mSubscriptions.add(subscription);
    }
}
