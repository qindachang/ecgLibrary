package com.bltech.mobile.ecglibrary.ui;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.widget.Toast;

import com.bltech.mobile.ecglibrary.model.UUIDAttributes;
import com.bltech.mobile.utils.DecodeDataCallback;
import com.bltech.mobile.utils.EcgFileUtils;
import com.bltech.mobile.utils.EcgManager;
import com.bltech.mobile.utils.annotation.FilePathMode;
import com.qindachang.bluetoothle.BluetoothLe;
import com.qindachang.bluetoothle.OnLeConnectListener;
import com.qindachang.bluetoothle.OnLeNotificationListener;
import com.qindachang.bluetoothle.OnLeScanListener;
import com.qindachang.bluetoothle.exception.BleException;
import com.qindachang.bluetoothle.exception.ConnBleException;
import com.qindachang.bluetoothle.exception.ScanBleException;
import com.qindachang.bluetoothle.scanner.ScanRecord;
import com.qindachang.bluetoothle.scanner.ScanResult;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by qindachang on 2017/2/9.
 */

public class MainPresenter implements MainContract.Presenter {
    private static final String TAG = MainPresenter.class.getSimpleName();

    private Context mContext;
    private MainContract.View mView;

    private CompositeSubscription mSubscriptions;
    private EcgManager mEcgManager;
    private BluetoothLe mBluetoothLe;
    private EcgFileUtils mFileUtils;

    private DecodeDataCallback mDecodeDataCallback = new DecodeDataCallback() {

        @Override
        public void onDecodeData(short[] data, int heartRate) {
            //解密、类型转换、滤波 后的数据，这个数据可以用来绘制波形
            mView.onEcgChartDataChange(data);
            mView.onHeartRateChange(heartRate);
        }
    };

    public MainPresenter(Context context, MainContract.View view) {
        mContext = context;
        mView = view;
        mView.setPresenter(this);

        mBluetoothLe = BluetoothLe.getDefault();
        mEcgManager = EcgManager.getInstance();
        mFileUtils = new EcgFileUtils();
        mSubscriptions = new CompositeSubscription();

        mEcgManager.setDecodeDataCallback(mDecodeDataCallback);

        final File file = mFileUtils.initFilePath(FilePathMode.SD, "Android/", "haha.ecg");
        mFileUtils.readyWriteData(file.getPath());

        mBluetoothLe.setOnConnectListener(TAG, new OnLeConnectListener() {
            @Override
            public void onDeviceConnecting() {

            }

            @Override
            public void onDeviceConnected() {
                mView.onBluetoothConnectChange(true);
            }

            @Override
            public void onDeviceDisconnected() {
                mView.onBluetoothConnectChange(false);
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt) {

            }

            @Override
            public void onDeviceConnectFail(ConnBleException e) {

            }
        });

        mBluetoothLe.setOnNotificationListener(TAG, new OnLeNotificationListener() {
            @Override
            public void onSuccess(BluetoothGattCharacteristic characteristic) {
                mEcgManager.decodeData(characteristic.getValue());//解密、类型转换、滤波数据
                mFileUtils.writeDataAsFile(characteristic.getValue());//将接收到的数据写入到文件中
            }

            @Override
            public void onFailed(BleException e) {

            }
        });

    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {
        mContext = null;
        //避免内存泄露
        mEcgManager.removeCallback(mDecodeDataCallback);
        mBluetoothLe.destroy(TAG);
        mBluetoothLe.destroy();
        mSubscriptions.clear();
    }

    @Override
    public void onEcgCheckupClick() {
        if (!mBluetoothLe.isBluetoothOpen()) {
            mBluetoothLe.enableBluetooth(mView.getActivity(), 666);
            return;
        }
        if (!mBluetoothLe.getServicesDiscovered()) {
            scanAndConnect();
            return;
        }

    }

    /**
     * 扫描并连接
     */
    @Override
    public void scanAndConnect() {
        if (mBluetoothLe.getScanning()) {
            Toast.makeText(mContext, "正在扫描中", Toast.LENGTH_SHORT).show();
            return;
        }
        mView.onBluetoothScanning(true);
        mBluetoothLe.setScanPeriod(15000)
                .setScanWithServiceUUID(UUIDAttributes.ecg.SERVICE_ECG)
                .startScan(mView.getActivity(), new OnLeScanListener() {
                    @Override
                    public void onScanResult(BluetoothDevice bluetoothDevice, int rssi, ScanRecord scanRecord) {
                        mBluetoothLe.stopScan();
                        mBluetoothLe.startConnect(bluetoothDevice);
                    }

                    @Override
                    public void onBatchScanResults(List<ScanResult> results) {

                    }

                    @Override
                    public void onScanCompleted() {
                        mView.onBluetoothScanning(false);
                    }

                    @Override
                    public void onScanFailed(ScanBleException e) {

                    }
                });
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
                        mFileUtils.finishWriteData();
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