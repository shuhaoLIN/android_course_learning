package com.example.jszx.shiyan6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getName();
    private ListView lv;
    private MyListViewAdapter adapter;
    private String text;
    private static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        // 实例化 adapter
        adapter = new MyListViewAdapter(MainActivity.this);
        // listview绑定adapter适配器
        lv.setAdapter(adapter);

    }

    /** 添加item */
    public void add(View view) {
        text = ((EditText)findViewById(R.id.edit_view)).getText().toString();
        adapter.addData(text);
        adapter.notifyDataSetChanged();
        count++;
        Log.i(TAG, "count---" + count);
    }

    /** 移除item */
    public void del(View view) {
        adapter.delData();
        adapter.notifyDataSetChanged();
        // 判断是否>0
        if (count > 0)
            count--;
        Log.i(TAG, "count---" + count);
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.lv);//初始化控件

    }

}
