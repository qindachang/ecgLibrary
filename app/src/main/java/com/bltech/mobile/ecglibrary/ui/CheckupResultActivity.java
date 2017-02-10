package com.bltech.mobile.ecglibrary.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.bltech.mobile.ecglibrary.R;
import com.bltech.mobile.utils.AnalyzeException;
import com.bltech.mobile.utils.AnalyzeResult;

public class CheckupResultActivity extends AppCompatActivity implements CheckupResultContract.View{

    private TextView tvResult;

    private CheckupResultContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkup_result);
        tvResult = (TextView) findViewById(R.id.tv_checkup_result);
        setTitle("检测详情");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        String filePath = intent.getStringExtra("filePath");

        new CheckupResultPresenter(this, this);
        mPresenter.startAnalyzeFile(filePath);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onCommentSuggestFeedback(AnalyzeResult analyzeResult, String comment, String suggestion) {
        tvResult.setText(analyzeResult.toString() + "\n" + comment + "\n" + suggestion);
    }

    @Override
    public void onCommentSuggestError(AnalyzeException e) {
        tvResult.setText(e.toString());
    }
}
