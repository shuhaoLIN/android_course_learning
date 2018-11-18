package com.example.daygram;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.w3c.dom.Text;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by lenovo on 2018/11/16.
 */
public class AddDataItem extends AppCompatActivity{
    private String[] weeks = {"Monday","Tuesday","Wednesday",
            "Thursday","Friday","Saturday","Sunday"};
    private EditText dataAddData;
    private Button backAddData;
    private Button clockAddData;
    private Button DONEAddData;
    private TextView timeAddData;
    private View rootView;

    private Daydata addDaydata;
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        addDaydata = (Daydata) bundle.get("data"); //或得到了当前的data

        rootView = (View)findViewById(R.id.add_data);
        dataAddData = (EditText)findViewById(R.id.dataAddData);
        backAddData = (Button)findViewById(R.id.backAddData);
        clockAddData = (Button) findViewById(R.id.clockAddData);
        DONEAddData = (Button) findViewById(R.id.DONEAddData);
        timeAddData = (TextView)findViewById(R.id.timeAddData);

        rootView.addOnLayoutChangeListener(new setKeyboardListener());
        backAddData.setOnClickListener(new BackAddDataClick());
        clockAddData.setOnClickListener(new ClockAddDataListener());
        DONEAddData.setOnClickListener(new DONEAddDataListener());
    }

    //初始化timeAddData抬头
    private  void initTimeAddData(){

    }
    //设置DONE监听,实现将软盘隐藏
    private class DONEAddDataListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm.isActive()&&getCurrentFocus()!=null) {
                if (getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
    }

    //设置clockAddData监听：实现添加时间功能
    private class ClockAddDataListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            DateFormat dateFormat = new SimpleDateFormat("a hh时mm分");
            String time = dateFormat.format(new Date(System.currentTimeMillis()));
            dataAddData.append(time);
        }
    }
    //设置键盘弹起监听：实现相应的Button显示或者隐藏
    private class setKeyboardListener implements View.OnLayoutChangeListener{

        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            if(bottom > oldBottom){
                backAddData.setVisibility(View.VISIBLE);
                clockAddData.setVisibility(View.GONE);
                DONEAddData.setVisibility(View.GONE);
            }
            else{
                backAddData.setVisibility(View.GONE);
                clockAddData.setVisibility(View.VISIBLE);
                DONEAddData.setVisibility(View.VISIBLE);
            }
        }

    }
    //设置返回键的功能
    private class BackAddDataClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            String EditData = dataAddData.getText().toString();

            setResult(RESULT_OK, intent);
            AddDataItem.this.finish();
        }
    }

}
