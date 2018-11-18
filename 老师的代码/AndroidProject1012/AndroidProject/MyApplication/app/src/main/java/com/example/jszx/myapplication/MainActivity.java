package com.example.jszx.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1= (TextView) this.findViewById(R.id.textview1);
        textView1.setTextColor(Color.RED);
        textView1.setOnClickListener(new mClick());
    }

    class mClick implements View.OnClickListener {
        public void onClick(View v) {
            MainActivity.this.setTitle("改变标题");
            ((TextView)v).setText("世界你好");
        }
    }

}
