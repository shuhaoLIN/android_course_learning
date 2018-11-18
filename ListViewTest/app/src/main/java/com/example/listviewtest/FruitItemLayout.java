package com.example.listviewtest;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by lenovo on 2018/10/3.
 */
public class FruitItemLayout extends LinearLayout{

    public FruitItemLayout(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.fruit_item,this);
        Button button = (Button) findViewById(R.id.fruit_buy);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Buy_comfirm.class);
                context.startActivity(intent);
            }
        });
    }
}
