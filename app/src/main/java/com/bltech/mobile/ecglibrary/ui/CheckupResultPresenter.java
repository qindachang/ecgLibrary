package com.bltech.mobile.ecglibrary.ui;

import android.content.Context;

/**
 * Created by qindachang on 2017/2/9.
 */

public class CheckupResultPresenter implements CheckupResultContract.Presenter {
    private Context mContext;
    private CheckupResultContract.View mView;

    public CheckupResultPresenter(Context context, CheckupResultContract.View view) {
        mContext = context;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {
        mContext = null;
    }
}