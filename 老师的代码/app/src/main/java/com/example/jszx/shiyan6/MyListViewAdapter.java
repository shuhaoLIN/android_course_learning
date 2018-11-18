package com.example.jszx.shiyan6;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MyListViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> datas = new ArrayList<String>();

    public MyListViewAdapter(Context context) {
        this.context = context;
    }

    /** 添加item数据 */
    public void addData(String text) {
            datas.add(text);// 添加数据
    }

    /** 移除item数据 */
    public void delData() {
        if (datas != null && datas.size() > 0)
            datas.remove(datas.size() - 1);// 移除最后一条数据
        else Toast.makeText(context,"已经没有可以删除的了",Toast.LENGTH_LONG).show();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (datas == null)
            return 0;
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    /**
     * listview要判断item的位置，第一条，最后一条和中间的item是不一样的。
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        // 判断datas数据条数是否>1
        convertView = View.inflate(context,R.layout.list_item_top,null);

        String text = datas.get(position);
        // 设置文本
        ((TextView) convertView.findViewById(R.id.title)).setText(text);
        return convertView;
    }

}
