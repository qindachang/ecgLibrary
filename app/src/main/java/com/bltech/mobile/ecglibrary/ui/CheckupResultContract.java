package com.bltech.mobile.ecglibrary.ui;

import com.bltech.mobile.ecglibrary.BasePresenter;
import com.bltech.mobile.ecglibrary.BaseView;
import com.bltech.mobile.utils.AnalyzeException;
import com.bltech.mobile.utils.AnalyzeResult;

/**
 * Created by qindachang on 2017/2/9.
 */

public interface CheckupResultContract {
    interface View extends BaseView<Presenter> {
        void onCommentSuggestFeedback(AnalyzeResult analyzeResult, String comment, String suggestion);
        void onCommentSuggestError(AnalyzeException e);
    }

    interface Presenter extends BasePresenter {
        void startAnalyzeFile(String filePath);
    }
}