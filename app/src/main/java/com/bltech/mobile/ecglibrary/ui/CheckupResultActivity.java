package com.bltech.mobile.ecglibrary.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bltech.mobile.ecglibrary.R;

public class CheckupResultActivity extends AppCompatActivity implements CheckupResultContract.View{

    private CheckupResultContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkup_result);
        new CheckupResultPresenter(this, this);
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
    public void setPresenter(CheckupResultContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }
}
