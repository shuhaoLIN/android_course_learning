package com.example.hasee.daygram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


/**
 * Created by hasee on 2018/11/8.
 */

public class DayShowActivity extends AppCompatActivity {

    private String[] months = {
            "JANUARY", "FEBRUARY", "MARCH", "APRIL",
            "MAY", "JUNE", "JULY", "AUGUST",
            "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
    };
    private TextView monthTitle;
    private TextView weekTitle;
    private TextView yearTitle;
    private TextView dayTitle;
    private EditText dayEdit;
    private Button clockBtn;
    private Button doneBtn;
    private Button backBtn;
    private boolean START = false;
    private boolean isBtnShow = false;
    private boolean isDataChange = false;
    private DayData theDayData;
    private View theMainView;
    private int screenHeight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_show);
        theDayData = (DayData)getIntent().getSerializableExtra("dayData");
        //获取布局中的组件；
        initViews();
        //设置组件的文本；
        initText();
        //设置点击监听；
        initClick();
        //设置监听软键盘显隐；
        setKeyboardListener();
        String isCallEditNow = (String)getIntent().getStringExtra("CALL_EDIT_NOW");
        if(isCallEditNow.equals("YES")) {
            dayEdit.setCursorVisible(true);
            //ShowKeyboard();
            Toast.makeText(DayShowActivity.this, "YES", Toast.LENGTH_SHORT).show();
        }
    }

    //获取布局中的组件；
    private void initViews() {
        theMainView = (View)findViewById(R.id.DayShowMainView);
        monthTitle = (TextView)findViewById(R.id.monthTitle);
        weekTitle = (TextView)findViewById(R.id.weekTitle);
        yearTitle = (TextView)findViewById(R.id.yearTitle);
        dayTitle = (TextView)findViewById(R.id.dayTitle);
        dayEdit = (EditText)findViewById(R.id.dayEdit);
        clockBtn = (Button)findViewById(R.id.clockBtn);
        doneBtn = (Button)findViewById(R.id.doneBtn);
        backBtn = (Button)findViewById(R.id.back_btn);
    }

    //设置组件的文本；
    private void initText() {
        monthTitle.setText(months[theDayData.getMonth() - 1]);
        dayTitle.setText(String.valueOf(theDayData.getDay()));
        weekTitle.setText(theDayData.getWeekday());
        if(theDayData.getWeekday().equals("SUNDAY"))
            weekTitle.setTextColor(Color.RED);
        yearTitle.setText(String.valueOf(theDayData.getYear()));
        dayEdit.setText(theDayData.getText());
    }

    //设置点击监听；
    private void initClick() {
        //光标移动到最后；
        dayEdit.setSelection(dayEdit.getText().length());
        //编辑框设置点击监听；
        dayEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设为可编辑状态；
                dayEdit.setCursorVisible(true);
            }
        });
        backBtn.setOnClickListener(new myEditInfoClick());
        clockBtn.setOnClickListener(new myEditInfoClick());
        doneBtn.setOnClickListener(new myEditInfoClick());
    }

    //设置监听软键盘显隐；
    private void setKeyboardListener() {
        //获取屏幕高度；
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        theMainView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //获取Window可见区域框架；
                Rect rect = new Rect();
                theMainView.getWindowVisibleDisplayFrame(rect);
                //屏幕与Window可见区域的高度差；
                int heightDifference = screenHeight - (rect.bottom - rect.top);
                //如果高度差大于1/3，代表软键盘显示着；
                boolean isKeyboardShow = heightDifference > screenHeight/3;
                //按钮与软键盘的显隐都会调到此监听，以两者同时来判断；
                if(isKeyboardShow && !isBtnShow) {
                    //Toast.makeText(DayShowActivity.this, "SHOW", Toast.LENGTH_SHORT).show();
                    ShowBtn();
                }else if(!isKeyboardShow && isBtnShow) {
                    //Toast.makeText(DayShowActivity.this, "CLOSE", Toast.LENGTH_SHORT).show();
                    HideBtn();
                    //设为不可编辑；
                    dayEdit.setCursorVisible(false);
                    //光标移动到最后；
                    dayEdit.setSelection(dayEdit.getText().length());
                }
            }
        });
    }

    //显示键盘；
    private void ShowKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(dayEdit, 0);
    }

    //隐藏软键盘；
    private void HideKeyBoard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(dayEdit.getWindowToken(), 0);
    }

    //隐藏 CLOCK、DONE 按钮；
    private void HideBtn() {
        isBtnShow = false;
        clockBtn.setVisibility(View.GONE);
        doneBtn.setVisibility(View.GONE);
    }

    //显示隐藏的 CLOCK、DONE 按钮；
    private void ShowBtn() {
        isBtnShow = true;
        clockBtn.setVisibility(View.VISIBLE);
        doneBtn.setVisibility(View.VISIBLE);
    }

    //重写返回键；
    @Override
    public void onBackPressed() {
        //Toast.makeText(DayShowActivity.this, "onBackPressed", Toast.LENGTH_SHORT).show();
        dataUpdate();
        if(isDataChange)
            setReturnOK();
        super.onBackPressed();
    }

    //判断并记录数据变化；
    private void dataUpdate() {
        String text = dayEdit.getText().toString();
        //Toast.makeText(DayShowActivity.this, text+"\n"+theDayData.getText(), Toast.LENGTH_SHORT).show();
        //text长度为0，theDayData.getText()为null 时，两者算不相等；
        if( !text.equals(theDayData.getText()) && (text.length() != 0 || theDayData.getText() != null)) {
            //Toast.makeText(DayShowActivity.this, text+"\n"+theDayData.getText(), Toast.LENGTH_SHORT).show();
            theDayData.setText(text);
            isDataChange = true;
        }
    }

    //返回数据包；
    private void setReturnOK() {
        //Toast.makeText(DayShowActivity.this, "RETURN OK", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("backDayData", theDayData);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
    }

    //相关点击监听；
    private class myEditInfoClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //返回主界面，若有修改数据，返回数据;
            if(view ==  backBtn){
                //Toast.makeText(DayShowActivity.this, "BACK CLICK", Toast.LENGTH_SHORT).show();
                if(isDataChange)
                    setReturnOK();
                DayShowActivity.this.finish();
            }//backBtn;
            //插入时间；
            else if(view == clockBtn) {
                Calendar calender = Calendar.getInstance();
                int theHour = calender.get(Calendar.HOUR_OF_DAY);
                String AMorPM = (theHour < 12) ? "上午" : "下午";
                if(theHour > 12)
                    theHour -= 12;
                int theMinute = calender.get(Calendar.MINUTE);
                String clockStr = AMorPM + String.valueOf(theHour) + ":"
                        + String.valueOf(theMinute) + "分 ";
                String text = dayEdit.getText().toString();
                text += clockStr;
                dayEdit.setText(text);
                dayEdit.setSelection(dayEdit.getText().length());
            }//clockBtn;
            //保存修改的数据；
            else if(view == doneBtn) {
                //HideBtn();
                HideKeyBoard();
                dataUpdate();
                //Toast.makeText(DayShowActivity.this, "DONE CLICK", Toast.LENGTH_SHORT).show();
            }//doneBtn;
        }
    }
}
