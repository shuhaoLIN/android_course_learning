package com.example.listviewtest;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by lenovo on 2018/10/3.
 */
public class FruitAdapter extends ArrayAdapter<Fruit> {
    private String FUU = "FruitAdapter";
    private int resourceId;
    public FruitAdapter(Context context, int textViewResourceId,List<Fruit> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Log.d(FUU,"加载一个");
        ViewHolder viewHolder;
        Fruit fruit = getItem(position); //获取当前项的Fruit实例
        View view;
        if(convertView == null) {
            view= LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.fruitImage = (ImageView)view.findViewById(R.id.fruit_image);
            viewHolder.fruitName = (TextView)view.findViewById(R.id.fruit_name);
            view.setTag(viewHolder); // 将viewHolder存在view里面
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); //重新获取viewHolder
        }
        //重写了getView方法，该方法在每一个子项被滚动到屏幕内时就会被调用。
        viewHolder.fruitImage.setImageResource(fruit.getImageId());
        viewHolder.fruitName.setText(fruit.getName());
        return view;
    }
    class ViewHolder{
        ImageView fruitImage;
        TextView fruitName;
    }
}
