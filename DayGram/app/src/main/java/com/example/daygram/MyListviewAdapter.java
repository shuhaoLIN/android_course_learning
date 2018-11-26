package com.example.daygram;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by lenovo on 2018/11/15.
 */
public class MyListviewAdapter extends BaseAdapter{
    private String[] fullweeks = {"MONDAY","TUESDAY","WEDNESDAY",
            "THURSDAY","FRIDAY","SATURDAY","SUNDAY"};
    private ArrayList<View> dataItemViews;
    private ArrayList<Daydata> daydatas;
    private Context listContext;
    private String[] weeks = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
    public MyListviewAdapter(ArrayList<Daydata> dayCollection,Context context){
        daydatas = dayCollection;
        dataItemViews = new ArrayList<>(dayCollection.size());
        listContext = context;
        getAllDataItemView();
    }
    //更新页面即是更新新的月份的数据
    public void updateAdapter(ArrayList<Daydata> dayCollection, Context context){
        daydatas = dayCollection;
        dataItemViews = new ArrayList<>(dayCollection.size());
        listContext = context;
        getAllDataItemView();
    }
    //获取全部的view,只处理正常页面的。
    public void getAllDataItemView(){
        dataItemViews.clear();
        for(int i=0;i<daydatas.size();i++){
            dataItemViews.add(getItemView(daydatas.get(i)));
        }
    }
    public View getItemView(Daydata data){
        LayoutInflater inflater = (LayoutInflater)
                listContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView;
        if(data.getDataFlag() == 1){
            itemView = inflater.inflate(R.layout.list_item_data,null);
            TextView weekText = (TextView) itemView.findViewById(R.id.weekText);
            TextView dayText = (TextView) itemView.findViewById(R.id.dayText);
            TextView dataText = (TextView) itemView.findViewById(R.id.dataText);
            weekText.setText(weeks[data.getWeekData()]);
            dayText.setText(data.getDayData());
            if(6 == data.getWeekData()){ //6代表是周日
                dayText.setTextColor(Color.RED);
            }
            dataText.setText(data.getDataContent());

        }
        else{
            itemView = inflater.inflate(R.layout.list_item_point,null);
            if (6 == data.getWeekData()){
                ((ImageView)itemView.findViewById(R.id.itemPoint)).setImageResource(R.drawable.sunday_point);
            }
        }
        return itemView;
    }
    //变换为monthshow的模式
    public void changeToMonthShow(){
        dataItemViews.clear();
        for(int i=0;i<daydatas.size();i++){
            if(daydatas.get(i).getDataFlag() == 1)
                dataItemViews.add(getMonthItemView(daydatas.get(i)));
        }
    }
    public View getMonthItemView(Daydata data){
        LayoutInflater inflater = (LayoutInflater)
                listContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView;
        itemView = inflater.inflate(R.layout.list_item_month,null);
        TextView textView = (TextView)itemView.findViewById(R.id.textMonthShow);
        String str;
        if(6 == data.getWeekData()){
            str = data.getDayData()+" <font color='#FF0000'>"+fullweeks[data.getWeekData()]+"</font> /"+data.getDataContent();
        }
        else{
            str = data.getDayData()+" "+fullweeks[data.getWeekData()]+" /"+data.getDataContent();
        }
        textView.setText(Html.fromHtml(str));
        return itemView;
    }

    //更新数据
    public void updateDaydatas(Boolean ismonth,int position, Daydata daydata){
        daydatas.set(position, daydata);
        if(ismonth)
            changeToMonthShow();
        else {
            dataItemViews.set(position,getItemView(daydata));
        }
    }


    @Override
    public int getCount() {
        return dataItemViews.size();
    }

    @Override
    public Object getItem(int position) {
        return dataItemViews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return dataItemViews.get(position);
    }
}
