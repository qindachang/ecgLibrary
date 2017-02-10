package com.bltech.mobile.ecglibrary.ui;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.bltech.mobile.ecglibrary.helper.CheckupStateHelper;
import com.bltech.mobile.ecglibrary.model.UUIDAttributes;
import com.bltech.mobile.ecglibrary.utils.TimeUtils;
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

    private String filePath;//心电文件路径

    private Context mContext;
    private MainContract.View mView;

    private CompositeSubscription mSubscriptions;
    private EcgManager mEcgManager;
    private BluetoothLe mBluetoothLe;
    private EcgFileUtils mFileUtils;
    private CheckupStateHelper mCheckupStateHelper;

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
        mCheckupStateHelper = new CheckupStateHelper();

        mEcgManager.setDecodeDataCallback(mDecodeDataCallback);


        mBluetoothLe.setOnConnectListener(TAG, new OnLeConnectListener() {
            @Override
            public void onDeviceConnecting() {

            }

            @Override
            public void onDeviceConnected() {
                mView.onBluetoothStatus("蓝牙已连接");
            }

            @Override
            public void onDeviceDisconnected() {
                mView.onBluetoothStatus("蓝牙已断开！");
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt) {
                mView.onBluetoothStatus("发现蓝牙服务");
            }

            @Override
            public void onDeviceConnectFail(ConnBleException e) {

            }
        });

        mBluetoothLe.setOnNotificationListener(TAG, new OnLeNotificationListener() {
            @Override
            public void onSuccess(BluetoothGattCharacteristic characteristic) {
                if (characteristic.getUuid().equals(UUIDAttributes.ecg.NOTIFICATION_ECG_SIGNAL)) {
                    mEcgManager.decodeData(characteristic.getValue());//解密、类型转换、滤波数据
                    mFileUtils.writeDataAsFile(characteristic.getValue());//将接收到的数据写入到文件中
                }
            }

            @Override
            public void onFailed(BleException e) {

            }
        });


        mView.onBluetoothStatus(mBluetoothLe.getServicesDiscovered() ? "已连接设备" : "未连接设备");

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
        mBluetoothLe.close();
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
        if (mCheckupStateHelper.getCheckuping()) {
            Toast.makeText(mContext, "正在检测中", Toast.LENGTH_SHORT).show();
            return;
        }
        final File file = mFileUtils.initFilePath(FilePathMode.SD, "Android/data", "haha.ecg");
        mFileUtils.readyWriteData(file.getPath());
        filePath = file.getPath();

        mBluetoothLe.writeDataToCharacteristic(new byte[]{8}, UUIDAttributes.command.SERVICE_COMMAND, UUIDAttributes.command.WRITE);//打开总开关
        mBluetoothLe.writeDataToCharacteristic(new byte[]{1, 1}, UUIDAttributes.command.SERVICE_COMMAND, UUIDAttributes.command.WRITE);//打开心率通知
        mBluetoothLe.enableNotification(true, UUIDAttributes.ecg.SERVICE_ECG, UUIDAttributes.ecg.NOTIFICATION_HEART_RATE);//使能心率通知
        mBluetoothLe.enableNotification(true, UUIDAttributes.ecg.SERVICE_ECG, UUIDAttributes.ecg.NOTIFICATION_ECG_SIGNAL);//使能心电通知
        countDown(60);
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
        mCheckupStateHelper.setStart();
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
                        mView.onCheckupComplete();
                        //倒计时完毕，也就是检测完毕
                        mFileUtils.finishWriteData();
                        mCheckupStateHelper.setFinish();
                        mBluetoothLe.enableNotification(false, UUIDAttributes.ecg.SERVICE_ECG, UUIDAttributes.ecg.NOTIFICATION_HEART_RATE);
                        mBluetoothLe.enableNotification(false, UUIDAttributes.ecg.SERVICE_ECG, UUIDAttributes.ecg.NOTIFICATION_ECG_SIGNAL);
                        go2CheckupResult();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        mView.onCheckupCountDown(TimeUtils.seconds2Date(integer));
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 跳转到检测详情
     */
    private void go2CheckupResult() {
        Intent intent = new Intent(mContext, CheckupResultActivity.class);
        intent.putExtra("filePath", filePath);
        mContext.startActivity(intent);
    }

}