package com.example.test_baseadapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity extends Activity {
    String TAG = "MainActivity";
    ListView listView;
    ListViewAdapter adapter;
    FloatingActionButton floatingActionButton;
    FruitCollectionOperater operater;
    ArrayList<ItemClass> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(  R.layout.main);
        this.setTitle("实验");
        floatingActionButton = (FloatingActionButton)findViewById(  R.id.fab); //设置悬浮按钮
        listView=(ListView)this.findViewById(  R.id.MyListView);    //获取到listView
        operater = new FruitCollectionOperater();
        items = operater.load(MainActivity.this);
        if (items == null){
            items.add(new ItemClass("第一个","这是第一个自己生成的",R.drawable.watermelon_pic));
        }
        adapter = new ListViewAdapter(items,MainActivity.this);
        listView.setAdapter(adapter);  //获取到adapter对象
        this.registerForContextMenu(listView);  //注册listView

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, BActivity.class);
                    Bundle bundle = new Bundle();
                    ItemClass itemClass = new ItemClass(items.get(position).getItemName(),
                            items.get(position).getItemContent(),items.get(position).getItemPicID());
                    bundle.putSerializable("itemClass",itemClass);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 1);
                }
            });
        floatingActionButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ItemClass itemClass = new ItemClass("这是通过悬浮按钮添加的哦","无价的哦",   R.drawable.watermelon_pic);
                adapter.addItem(itemClass);
                operater.save(MainActivity.this,itemClass);
                listView.setAdapter(adapter);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    Bundle returnItem = data.getExtras();
                    ItemClass returnItem1 = (ItemClass)returnItem.getSerializable("data_return");
                    items.add(returnItem1);
                    operater.save(MainActivity.this,returnItem1);
                    adapter.addItem(returnItem1);
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);

                    Log.d(TAG,adapter.getCount()+"");
                }
                break;
            default:
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        menu.setHeaderTitle("提示信息");
        menu.add(0,0,0,"删除");
        menu.add(0,1,0,"添加");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()){
            case 0:
                int pos = menuInfo.position;
                if(adapter.getCount() != 0){
                    items.remove(pos);
                    adapter.delItem(pos);
                    //没办法实现定位删除啊，所以打算以最后终止的方式进行存储。
                    listView.setAdapter(adapter);
                }
                break;
            case 1:

                ItemClass itemClass = new ItemClass("这是通过menu添加的","无价的哦",   R.drawable.watermelon_pic);
                adapter.addItem(itemClass);
                operater.save(MainActivity.this,itemClass);
                listView.setAdapter(adapter);
                break;
        }
        return super.onContextItemSelected(item);
    }
}