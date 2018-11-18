package com.example.chapter10androidthreadtest;

import android.icu.lang.UCharacter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int UPDATE_TEXT = 1;
    private TextView textView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT:
                    //在这里就可以进行UI操作了，因为是在主线程里面
                    textView = (TextView)findViewById(R.id.text);
                    textView.setText("Nice!");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button change = (Button)findViewById(R.id.change_text);

        change.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_text:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("Nice!");
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
