package com.bltech.mobile.ecglibrary.ui;

import android.content.Context;

import com.bltech.mobile.utils.AnalyzeResult;
import com.bltech.mobile.utils.EcgManager;
import com.bltech.mobile.utils.AnalyzeCallback;
import com.bltech.mobile.utils.AnalyzeException;

/**
 * Created by qindachang on 2017/2/9.
 */

public class CheckupResultPresenter implements CheckupResultContract.Presenter {

    private EcgManager mEcgManager;

    private Context mContext;
    private CheckupResultContract.View mView;

    private AnalyzeCallback mAnalyzeCallback = new AnalyzeCallback() {

        @Override
        public void onSuccess(AnalyzeResult result) {
            String[] array = mEcgManager.getAnalyzeComment(mContext, result);
            String comment = array[0];
            String suggest = array[1];
            mView.onCommentSuggestFeedback(result, comment, suggest);
        }

        @Override
        public void onFailed(AnalyzeException e, int status) {
            mView.onCommentSuggestError(e);
        }

    };

    public CheckupResultPresenter(Context context, CheckupResultContract.View view) {
        mContext = context;
        mView = view;
        mView.setPresenter(this);
        mEcgManager = new EcgManager();
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {
        mContext = null;
        mEcgManager.removeCallback(mAnalyzeCallback);
    }

    @Override
    public void startAnalyzeFile(String filePath) {
        mEcgManager.analyzeEcgFile(filePath, mAnalyzeCallback);
    }
}