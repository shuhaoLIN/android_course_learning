package com.example.wuliansuijihanshu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.sql.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    EditText allPicNum;
    EditText needPicNum;
    Button OKButton;
    TextView showText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allPicNum = (EditText) findViewById(R.id.allPicNum);
        needPicNum = (EditText) findViewById(R.id.needPicNum);
        OKButton = (Button) findViewById(R.id.OKButton);
        showText = (TextView) findViewById(R.id.showText);
        showText.setMovementMethod(new ScrollingMovementMethod());

        OKButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int allNum = Integer.parseInt(allPicNum.getText().toString());
                int needNum = Integer.parseInt(needPicNum.getText().toString());
                Set<Integer> order = new HashSet<>();
                Random ran = new Random();
                while (order.size() < needNum){
                    order.add(ran.nextInt(allNum));
                }

                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                String time = dateFormat.format(new Date(System.currentTimeMillis()));
                showText.append("时间："+time+"\n获得图片编号为：");
                for(Integer nums: order){
                    showText.append(nums+",");
                }
                int offset=showText.getLineCount()*showText.getLineHeight();
                if(offset>showText.getHeight()){
                    showText.scrollTo(0,offset-showText.getHeight());
                }
                showText.append("\n\n");
            }
        });
    }
}
