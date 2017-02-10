package com.bltech.mobile.ecglibrary.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bltech.mobile.ecglibrary.R;
import com.github.mikephil.charting.charts.LineChart;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainContract.Presenter mPresenter;

    private LineChart mLineChart;
    private Button btnCheckup;
    private TextView tvEcg, tvHeartRate,tvBluetoothStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLineChart = (LineChart) findViewById(R.id.lineChart_ecg);
        btnCheckup = (Button) findViewById(R.id.btn_start_checkup);
        tvEcg = (TextView) findViewById(R.id.tv_ecg_data);
        tvHeartRate = (TextView) findViewById(R.id.tv_heart_rate);
        tvBluetoothStatus = (TextView) findViewById(R.id.tv_bluetooth_status);
        new MainPresenter(this, this);

        btnCheckup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onEcgCheckupClick();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 666 && resultCode == RESULT_OK) {
            mPresenter.scanAndConnect();
        }
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }


    @Override
    public void onBluetoothStatus(String text) {
        tvBluetoothStatus.setText(text);
    }

    @Override
    public void onEcgChartDataChange(short[] data) {
        tvEcg.setText("心电数据：" + Arrays.toString(data));
    }

    @Override
    public void onHeartRateChange(int heartRate) {
        tvHeartRate.setText("心率：" + String.valueOf(heartRate));
    }

    @Override
    public void onCheckupCountDown(String time) {
        btnCheckup.setText(time);
    }

    @Override
    public void onCheckupComplete() {
        btnCheckup.setText("开始检测");
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    /**
     * 初始化图表配置
     */
    private void initLineChart() {

    }

    /**
     * 设置图表数据
     */
    private void setChartData() {

    }
}
