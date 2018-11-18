package com.example.listviewtest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by lenovo on 2018/10/3.
 */
public class BuyLayout extends LinearLayout {
    public BuyLayout(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.buy_friut,this);
        Button button = (Button) findViewById(R.id.buy_it);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"您已经购买了该水果",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
