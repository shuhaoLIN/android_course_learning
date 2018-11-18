package com.example.listviewtest;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class Buy_comfirm extends Activity {

    private String FUU = "Buy_comfirm";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_fruit_layout);
//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null){
//            Log.d(FUU,"消去标题栏");
//            actionBar.hide();
//        }
    }
}
