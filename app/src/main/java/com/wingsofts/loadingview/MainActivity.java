package com.wingsofts.loadingview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
public class MainActivity extends Activity {
    private MyLoadingView mLoadingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingView = (MyLoadingView) findViewById(R.id.myloadingView);

    }

    public void success(View v) {

        mLoadingView.success();
//        new AlertDialog.Builder(this).setView(R.layout.layout).setNegativeButton("OK",null).create().show();
    }

    public void error(View v) {

        mLoadingView.error();
//        new AlertDialog.Builder(this).setView(R.layout.layout).setNegativeButton("OK",null).create().show();
    }

    public void reset(View v) {

        mLoadingView.reset();
//        new AlertDialog.Builder(this).setView(R.layout.layout).setNegativeButton("OK",null).create().show();
    }
}
