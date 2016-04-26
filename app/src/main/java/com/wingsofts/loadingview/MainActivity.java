package com.wingsofts.loadingview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    private WLoadingView mLoadingView;
    private LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingView = (WLoadingView) findViewById(R.id.myloadingView);
        loadingView = (LoadingView) findViewById(R.id.loadingView);
    }

    public void success(View v) {
        mLoadingView.success();
    }

    public void error(View v) {
        mLoadingView.error();
    }

    public void reset(View v) {
        mLoadingView.reset();
    }

    public void successLoading(View v) {
        loadingView.success();
    }

    public void errorLoading(View v) {
//        loadingView.error();
    }

    public void resetLoading(View v) {
        loadingView.reset();
    }
}
