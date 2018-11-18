package com.example.daygram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private int REQUEST_CODE_ADD_ITEM = 123;

    ListView dataListview;
    ArrayList<Daydata> dayCollection;
    Map<Integer ,Daydata> daydataMap; // 读取完所有数据后，进行的一个存储的操作。
    MyListviewAdapter myListviewAdapter;
    Button addBtnButtom;
    Button yearChioceBtn; // 下面的选择按钮
    Button monthChioceBtn; //下面的选择按钮
    ImageView showMonthImage;

    Boolean IsMonth;

    int[] weeks = {1,2,3,4,5,6,7};
    String[] days ={"1","2","3","4","5","6","7"};
    String[] contents = {"这是周一","这是周二","这是周三","这是周四","这是周一",
            "这是周一","这是周一"};
    int[] flags = {1,1,0,0,1,1,1};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //完成测试初始化数据
        IsMonth = false;

        dayCollection = new ArrayList<>();
        Daydata[] daydatas = new Daydata[7];
        for(int i=0;i<7;i++){
            daydatas[i]  = new Daydata();
            daydatas[i].setNum(i);
            daydatas[i].setWeekData(weeks[i]);
            daydatas[i].setDayData(days[i]);
            daydatas[i].setDataContent(contents[i]);
            daydatas[i].setDataFlag(flags[i]);
            dayCollection.add(daydatas[i]);
        }
        //加载数据


        //配置adapter
        dataListview = (ListView)findViewById(R.id.dataListView);
        myListviewAdapter = new MyListviewAdapter(dayCollection,MainActivity.this);
        dataListview.setAdapter(myListviewAdapter);
        dataListview.setOnItemClickListener(new dataItemListListener());

        //addbutton
        addBtnButtom = (Button)findViewById(R.id.addBtnButtom);
        addBtnButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddDataItem.class);
                Bundle bundle = new Bundle();
                //bundle.putInt("weeks",dayCollection.get(position).getWeekData());
                bundle.putSerializable("data",dayCollection.get(dayCollection.size())); //将最后一个dayData数据结构发给下一页。
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_CODE_ADD_ITEM);
            }
        });

        //showButton
        showMonthImage = (ImageView)findViewById(R.id.showMonthBtn);
        showMonthImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(false == IsMonth){
                    myListviewAdapter.changeToMonthShow();
                    myListviewAdapter.notifyDataSetChanged();
                    IsMonth = true;
                }else{
                    myListviewAdapter.getAllDataItemView();
                    myListviewAdapter.notifyDataSetChanged();
                    IsMonth = false;
                }
            }
        });
    }

    //加载数据:还未完成
    public void loadingData(){
        DaydataOperater daydataOperater = new DaydataOperater();
        dayCollection = daydataOperater.load(getBaseContext());
        //将所有的数据保存起来。
        for(Daydata data: dayCollection){
            daydataMap.put(data.getNum(),data);
        }
        //计算今天相距以前多少天。

    }
    //计算天数

    //Open add页面
//    public void openAddData(){
//        Intent intent = new Intent(MainActivity.this,AddDataItem.class);
//        Bundle bundle = new Bundle();
//        startActivityForResult(intent,RESULT_OK);
//    }

    class dataItemListListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(MainActivity.this,AddDataItem.class);
            Bundle bundle = new Bundle();
            //bundle.putInt("weeks",dayCollection.get(position).getWeekData());
            bundle.putSerializable("data",dayCollection.get(position)); //将一整个dayData数据结构发给下一页。
            intent.putExtras(bundle);
            startActivityForResult(intent,REQUEST_CODE_ADD_ITEM);
        }
    }
}
