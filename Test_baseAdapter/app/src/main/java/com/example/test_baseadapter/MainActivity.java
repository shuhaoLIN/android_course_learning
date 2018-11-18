package com.example.test_baseadapter;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.example.test_baseadapter.R;

import java.util.List;

public class MainActivity extends Activity {
    String TAG = "MainActivity";
    ListView listView;
    ListViewAdapter adapter;
    String [] titles={"Apple苹果","Banana香蕉","Orange橘子","Watermelon西瓜","Grape葡萄",
            "Strawberry草莓","Cherry樱桃","Mango芒果"};
    String [] texts={"苹果五元/个","香蕉五元/个","橘子五元/个","西瓜五元/个","葡萄五元/个",
            "草莓五元/个","樱桃五元/个","芒果五元/个"};
    int [] resIds={ R.drawable.apple_pic,R.drawable.bananas_pic,R.drawable.orange_pic,
            R.drawable.watermelon_pic,R.drawable.grapes_pic,R.drawable.strawberry_pic
            ,R.drawable.cherry_pic,R.drawable.mango_pic};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("实验");
        listView=(ListView)this.findViewById(R.id.MyListView);    //获取到listView
        adapter = new ListViewAdapter(titles,texts,resIds,MainActivity.this);
        listView.setAdapter(adapter);  //获取到adapter对象
        this.registerForContextMenu(listView);  //注册listView

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, BActivity.class);
                    Bundle bundle = new Bundle();
                    ItemClass itemClass = new ItemClass(
                            (String)(((TextView)listView.getChildAt(position).findViewById(R.id.itemTitle)).getText()),
                            (String)(((TextView)listView.getChildAt(position).findViewById(R.id.itemText)).getText()));
                    bundle.putSerializable("itemClass",itemClass);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 1);
                }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    String[] returnData = data.getStringExtra("data_return").split(" ");
                    Toast.makeText(MainActivity.this,returnData[0]+returnData[1],Toast.LENGTH_LONG).show();
                    adapter.addItem(returnData);
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
                    adapter.delItem(pos);
                    listView.setAdapter(adapter);
                }
                break;
            case 1:
                String[] text = {"这是通过menu添加的","无价的哦"};
                adapter.addItem(text);
                listView.setAdapter(adapter);
                break;
        }
        return super.onContextItemSelected(item);
    }
}