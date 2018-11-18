package com.example.recyclerviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();
    private String[] data = {
            "Apple苹果","Banana香蕉","Orange橘子","Watermelon西瓜","Grape葡萄",
            "Strawberry草莓","Cherry樱桃","Mango芒果",
            "Apple","Banana","Orange","Watermelon","Grape",
            "Strawberry","Cherry","Mango"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFruits(); // 初始化水果数据
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);  //设置布局的排列方向，默认是纵向
        //瀑布流写法
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);
    }

    private void initFruits(){
        for(int i=0;i<3;i++){
            Fruit apple = new Fruit(
                    getRandomLengthName(data[0]),R.drawable.apple_pic);
            Fruit banana = new Fruit(
                    getRandomLengthName(data[1]),R.drawable.bananas_pic);
            Fruit Orange = new Fruit(
                    getRandomLengthName(data[2]),R.drawable.orange_pic);
            Fruit Watermelon = new Fruit(
                    getRandomLengthName(data[3]),R.drawable.watermelon_pic);
            Fruit Grape = new Fruit(
                    getRandomLengthName(data[4]),R.drawable.grapes_pic);
            Fruit Strawberry = new Fruit(
                    getRandomLengthName(data[5]),R.drawable.strawberry_pic);
            Fruit Cherry = new Fruit(
                    getRandomLengthName(data[6]),R.drawable.cherry_pic);
            Fruit Mango = new Fruit(
                    getRandomLengthName(data[7]),R.drawable.mango_pic);
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
    private String getRandomLengthName(String name){
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < length; i++){
            builder.append(name);
        }
        return builder.toString();
    }
}
