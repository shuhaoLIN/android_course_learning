package com.example.horizontalListview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.daygram.R;

import java.util.ArrayList;

/**
 * Created by lenovo on 2018/11/26.
 */
public class HorizontalListViewAdapter extends BaseAdapter {
    private final Context context;
    ArrayList<HorizontalDate> dates;


    public HorizontalListViewAdapter(Context context, ArrayList<HorizontalDate> dates){
        this.context = context;
        this.dates = dates;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.month_year_item,null);
        TextView t = (TextView) view.findViewById(R.id.month_year_item_text);
        HorizontalDate date = dates.get(position);
        t.setText(date.getOption());
        if(true == date.getChoiced())
            t.setBackgroundResource(R.drawable.item_choiced);
        else
            t.setBackgroundResource(R.drawable.item_not_choiced);

        if(false == date.getCanBeChoiced()){
            view.setEnabled(false);
        }
        return view;
    }

    //实现点击后将相应数据刷一遍
    public void changeIsChoiced(int position){
        for(int i=0;i<dates.size();i++){
            dates.get(i).setChoiced(false);
        }
        dates.get(position).setChoiced(true);
    }
}
