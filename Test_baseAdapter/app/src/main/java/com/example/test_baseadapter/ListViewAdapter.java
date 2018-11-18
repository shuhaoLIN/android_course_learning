package com.example.test_baseadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/10/12.
 */
public class ListViewAdapter extends BaseAdapter {
    private List<View> itemViews ;
    private Context Mycontext;
    public ListViewAdapter(String [] itemTitles, String [] itemTexts,
                           int [] itemImageRes, Context context){
        Mycontext = context;
        itemViews  = new ArrayList<View>();
        for (int i=0; i<itemTitles.length; i++){
            itemViews.add(makeItemView(itemTitles[i], itemTexts[i],
                    itemImageRes[i]));
        }
    }

    public int getCount()  {
        return itemViews.size();
    }

    public View getItem(int position)  {
        return itemViews.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    private View makeItemView(String strTitle, String strText, int resId) {
        LayoutInflater inflater = (LayoutInflater)Mycontext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 使用View的对象itemView与R.layout.item关联
        View itemView = inflater.inflate(R.layout.listview_item, null);

        // 通过findViewById()方法实例R.layout.item内各组件
        TextView title = (TextView)itemView.findViewById(R.id.itemTitle);
        title.setText(strTitle);
        TextView text = (TextView)itemView.findViewById(R.id.itemText);
        text.setText(strText);
        ImageView image = (ImageView)itemView.findViewById(R.id.itemImage);
        image.setImageResource(resId);

        return itemView;
    }

    //添加item数据
    public void addItem(String[] text){
        itemViews.add(itemViews.size()-1,makeItemView(text[0], text[1],
                R.drawable.new_item));
    }
    //移除item数据
    public void delItem(int position){
        itemViews.remove(position);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            return itemViews.get(position);
        return convertView;
    }
}
