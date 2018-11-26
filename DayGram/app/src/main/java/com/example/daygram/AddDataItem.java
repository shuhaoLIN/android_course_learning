package com.example.daygram;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
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
    private static final int REQUEST_CODE_ADD_ITEM = 123;
    private String[] weeks = {"MONDAY","TUESDAY","WEDNESDAY",
            "THURSDAY","FRIDAY","SATURDAY","SUNDAY"};
    private EditText dataAddData;
    private Button backAddData;
    private Button clockAddData;
    private Button DONEAddData;
    private TextView timeAddData;
    private View rootView;

    private Daydata addDayData;
    private int screenHeight;
    private int addDayWeek;
    private String addDatadataString;
    private int addDataPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        addDayData = (Daydata) bundle.get("daydata"); //获得到了当前的data
        addDayWeek = addDayData.getWeekData();  //获得到相应的week
        addDatadataString = (String)bundle.get("data"); //获得相应的string
        addDataPosition = (int) bundle.get("position"); //获得该data的位置

        rootView = (View)findViewById(R.id.add_data);
        dataAddData = (EditText)findViewById(R.id.dataAddData);
        backAddData = (Button)findViewById(R.id.backAddData);
        clockAddData = (Button) findViewById(R.id.clockAddData);
        DONEAddData = (Button) findViewById(R.id.DONEAddData);
        timeAddData = (TextView)findViewById(R.id.timeAddData);

        initTimeAddData(); // 显示

        rootView.addOnLayoutChangeListener(new setKeyboardListener());
        backAddData.setOnClickListener(new BackAddDataClick());
        clockAddData.setOnClickListener(new ClockAddDataListener());
        DONEAddData.setOnClickListener(new DONEAddDataListener());
    }

    //初始化timeAddData抬头,以及传输过来的数据
    private  void initTimeAddData(){
        String day;
        if (addDayWeek == 6){
            day = "<font color='#FF0000'>"+weeks[addDayWeek]+"</font>"+
                    addDatadataString;
        }else{
            day = weeks[addDayWeek]+addDatadataString;
        }
        timeAddData.setText(Html.fromHtml(day));

        if(addDayData.getDataContent() != null)
            dataAddData.append(addDayData.getDataContent());
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
            backAddData.setVisibility(View.VISIBLE);
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
            String editData = dataAddData.getText().toString();
            if(editData.length() != 0){
                addDayData.setDataContent(editData);
                addDayData.setDataFlag(1); // 更新为有内容的
            }

            Bundle bundle = new Bundle();
            bundle.putSerializable("returnDay",addDayData);
            bundle.putInt("returnPosition",addDataPosition);
            intent.putExtras(bundle);//将数据传输回去
            setResult(RESULT_OK, intent); //传回去
            AddDataItem.this.finish();
        }
    }

}
