package com.example.listviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();
    private String[] data = {
            "Apple苹果","Banana香蕉","Orange橘子","Watermelon西瓜","Grape葡萄",
            "Strawberry草莓","Cherry樱桃","Mango芒果",
            "Apple","Banana","Orange","Watermelon","Grape",
            "Strawberry","Cherry","Mango"
    };
    private String FUU = "daozhelile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                MainActivity.this,android.R.layout.simple_list_item_1,data);
//        ListView listView = (ListView)findViewById(R.id.list_view);
//        listView.setAdapter(adapter);

        initFruits();
        ListView listView = (ListView)findViewById(R.id.list_view);
        //设置头部
        TextView head = new TextView(this);
        head.setText("水果商城");
        head.setTextSize(24);
        listView.addHeaderView(head);
        //设置尾部
        TextView foot = new TextView(this);
        foot.setText("您已购买：");
        foot.setTextSize(24);
        listView.addFooterView(foot);


        FruitAdapter adapter = new FruitAdapter(MainActivity.this,R.layout.friut_item_layout,fruitList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(FUU,"到这里了"+position);
                Fruit fruit = fruitList.get(position);
                Toast.makeText(MainActivity.this,fruit.getName(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initFruits() {
        for(int i=0;i<3;i++){
            Fruit apple = new Fruit(data[0],R.drawable.apple_pic);
            Fruit banana = new Fruit(data[1],R.drawable.bananas_pic);
            Fruit Orange = new Fruit(data[2],R.drawable.orange_pic);
            Fruit Watermelon = new Fruit(data[3],R.drawable.watermelon_pic);
            Fruit Grape = new Fruit(data[4],R.drawable.grapes_pic);
            Fruit Strawberry = new Fruit(data[5],R.drawable.strawberry_pic);
            Fruit Cherry = new Fruit(data[6],R.drawable.cherry_pic);
            Fruit Mango = new Fruit(data[7],R.drawable.mango_pic);
            fruitList.add(apple);
            fruitList.add(banana);
            fruitList.add(Orange);
            fruitList.add(Watermelon);
            fruitList.add(Grape);
            fruitList.add(Strawberry);
            fruitList.add(Cherry);
            fruitList.add(Mango);
        }
    }
}
