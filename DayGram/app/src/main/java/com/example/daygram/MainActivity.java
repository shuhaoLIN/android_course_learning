package com.example.daygram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.horizontalListview.HorizontalDate;
import com.example.horizontalListview.HorizontalListView;
import com.example.horizontalListview.HorizontalListViewAdapter;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_ITEM = 123;
    Date beginDate2008;
    ListView dataListview;
    ArrayList<Daydata> dayCollection;
    StaticMap daydataMap; // 读取完所有数据后，进行的一个存储的操作。
    MyListviewAdapter myListviewAdapter;
    Button addBtnButtom;
    LinearLayout allMenu; // 底部的按钮内容

    Button yearChioceBtn; // 下面的选择按钮
    Button monthChioceBtn; //下面的选择按钮
    HorizontalListView monthHorizontalHLV;
    LinearLayout monthItemLL;
    HorizontalListView yearHorizontalHLV;
    LinearLayout yearItemLL;

    ImageView showMonthImage;//显示全月预览模式

    Boolean IsMonth;//是否是month模式

    //下面是初始化Horizontal的数据
    ArrayList<HorizontalDate> monthHorizontalDates;
    HorizontalListViewAdapter monthHorizontalAdapter;
    String[] monthHLV  = new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUNE", "JULY", "AUG", "SEPT", "OCT", "NOV", "DEC"};
    Boolean[] isChoicedMonthHLV = new Boolean[]{false,false,false,false,false,false,false,false,false,false,false,false};
    Boolean[] canBeChoiced = new Boolean[]{true,true,true,true,true,true,true,true,true,true,true,true};
    ArrayList<HorizontalDate> yearHorizontalDates;
    HorizontalListViewAdapter yearHorizontalAdapter;

    private String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    private String[] yearsAndHLV = {"2018","2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009", "2008"};
    Boolean[] isChoicedYearHLV = new Boolean[]{false,false,false,false,false,false,false,false,false,false,false,false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allMenu = (LinearLayout)findViewById(R.id.all_menu);
        //yearChioceBtn  monthChioceBtn
        yearChioceBtn = (Button) findViewById(R.id.get_year_item);
        monthChioceBtn = (Button) findViewById(R.id.get_month_item);

        //完成测试初始化数据
        IsMonth = false;
        //addbutton,添加当前页面的最后一个项目。
        addBtnButtom = (Button)findViewById(R.id.addBtnButtom);
        addBtnButtom.setOnClickListener(new addBtnButtomListener());

        //showButton
        showMonthImage = (ImageView)findViewById(R.id.showMonthBtn);
        showMonthImage.setOnClickListener(new showMonthListener());

        //初始化ListView数据
        dayCollection = new ArrayList<>();
        //加载数据
        loadingData();
        //配置adapter
        dataListview = (ListView)findViewById(R.id.dataListView);
        myListviewAdapter = new MyListviewAdapter(dayCollection,MainActivity.this);
        dataListview.setAdapter(myListviewAdapter);
        dataListview.setOnItemClickListener(new dataItemListListener());

        monthHorizontalHLV = (HorizontalListView)findViewById(R.id.month_hlv);
        monthItemLL = (LinearLayout)findViewById(R.id.month_item);
        yearHorizontalHLV = (HorizontalListView)findViewById(R.id.year_hlv);
        yearItemLL = (LinearLayout)findViewById(R.id.year_item);
        //初始化monthHorizon
        monthHorizontalDates = new ArrayList<>();
        initMonthHorizon();
        monthHorizontalAdapter = new HorizontalListViewAdapter(MainActivity.this, monthHorizontalDates);
        monthHorizontalHLV.setAdapter(monthHorizontalAdapter);
        monthHorizontalHLV.setOnItemClickListener(new monthHorizontalHLVListener());
        //初始化yearHorizon
        yearHorizontalDates = new ArrayList<>();
        initYearHorizon();
        yearHorizontalAdapter = new HorizontalListViewAdapter(MainActivity.this, yearHorizontalDates);
        yearHorizontalHLV.setAdapter(yearHorizontalAdapter);
        yearHorizontalHLV.setOnItemClickListener(new yearHorizontalHLVListener());
        monthChioceBtn.setOnClickListener(new monthChioceBtnListener());
        yearChioceBtn.setOnClickListener(new yearChoiceBtnListener());

    }
    //初始化Horizon数据
    public void initMonthHorizon(){
        for(int i=0;i<monthHLV.length;i++){
            HorizontalDate date = new HorizontalDate();
            date.setOption(monthHLV[i]);
            date.setChoiced(isChoicedMonthHLV[i]);
            date.setCanBeChoiced(canBeChoiced[i]);
            monthHorizontalDates.add(date);
        }
    }
    public void initYearHorizon(){
        for(int i=0;i<yearsAndHLV.length;i++){
            HorizontalDate date = new HorizontalDate();
            date.setOption(yearsAndHLV[i]);
            date.setChoiced(isChoicedYearHLV[i]);
            date.setCanBeChoiced(canBeChoiced[i]);
            yearHorizontalDates.add(date);
        }
    }

    //monthHorizontalHLV监听事件
    class monthHorizontalHLVListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            monthHorizontalAdapter.changeIsChoiced(position);
            monthHorizontalAdapter.notifyDataSetChanged();

            monthItemLL.setVisibility(View.GONE);
            allMenu.setVisibility(View.VISIBLE);
            monthChioceBtn.setText(months[position]);

            //更新数据
            Calendar calendarnow = Calendar.getInstance();
            calendarnow.set(Integer.parseInt(yearChioceBtn.getText().toString()),
                    position,1); //获取到这个月开始的时间
            Date nowdate = calendarnow.getTime(); // 获得这个月开始时间
            long beginDay = (long)((nowdate.getTime() - beginDate2008.getTime()) / 1000 / 60 / 60 / 24); //获得这个月的开始时间的距离
            int total = calendarnow.getActualMaximum(Calendar.DATE); // 获得这个月的时间长度
            //获取数据
            Calendar calendarThisMonth = Calendar.getInstance();//这是一个用来比较当前月份的数据
            if((calendarnow.get(calendarnow.MONTH) == calendarThisMonth.get(calendarThisMonth.MONTH)) &&
                    (calendarnow.get(calendarnow.YEAR) == calendarThisMonth.get(calendarThisMonth.YEAR))){
                total = calendarThisMonth.get(Calendar.DAY_OF_MONTH); // 如果是当前的月份，则获取当前月份前些日子
            }
            dayCollection = daydataMap.getCollections((int)beginDay,(int)(beginDay + total-1));
            System.out.println(dayCollection.isEmpty()+"  "+dayCollection.size()+"\n"+dayCollection.get(1).getDataFlag()+"\n\n");
            myListviewAdapter.updateAdapter(dayCollection,MainActivity.this); //更新整个adapter
            myListviewAdapter.notifyDataSetChanged();
        }
    }
    //YearHorizontalHLV监听事件
    class yearHorizontalHLVListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            yearHorizontalAdapter.changeIsChoiced(position);
            yearHorizontalAdapter.notifyDataSetChanged();
            yearItemLL.setVisibility(View.GONE);
            allMenu.setVisibility(View.VISIBLE);
            yearChioceBtn.setText(yearsAndHLV[position]);

            //更新数据
            Calendar calendarnow = Calendar.getInstance();
            calendarnow.set(2018 - position,
                    getCurMonth(),1); //获取到这个月开始的时间
            Date nowdate = calendarnow.getTime(); // 获得这个月开始时间
            long beginDay = (long)((nowdate.getTime() - beginDate2008.getTime()) / 1000 / 60 / 60 / 24); //获得这个月的开始时间的距离
            int total = calendarnow.getActualMaximum(Calendar.DATE); // 获得这个月的时间长度
            //获取数据
            Calendar calendarThisMonth = Calendar.getInstance();//这是一个用来比较当前月份的数据
            if((calendarnow.get(calendarnow.MONTH) == calendarThisMonth.get(calendarThisMonth.MONTH)) &&
                    (calendarnow.get(calendarnow.YEAR) == calendarThisMonth.get(calendarThisMonth.YEAR))){
                total = calendarThisMonth.get(Calendar.DAY_OF_MONTH); // 如果是当前的月份，则获取当前月份前些日子
            }
            dayCollection = daydataMap.getCollections((int)beginDay,(int)(beginDay + total-1));
            System.out.println(dayCollection.isEmpty()+"  "+dayCollection.size()+"\n"+dayCollection.get(1).getDataFlag()+"\n\n");
            myListviewAdapter.updateAdapter(dayCollection,MainActivity.this); //更新整个adapter
            myListviewAdapter.notifyDataSetChanged();
        }
    }
    //一个辅助获得月份数据的函数
    public int getCurMonth(){
        String s = monthChioceBtn.getText().toString();
        for(int i=0;i<months.length;i++){
            if(s.equals(months[i])) return i;
        }
        return 1;
    }

    //加载数据:加载当前月份的数据到collection中去（设置monthHLV数据？？？）
    public void loadingData(){
        DaydataOperater daydataOperater = new DaydataOperater();
        dayCollection = daydataOperater.load(getBaseContext());
        //将所有的数据保存起来。
        daydataMap = new StaticMap();
        daydataMap.createMap(dayCollection); //创建Map内容

        //计算今天相距以前多少天。
        Date nowdate = new Date(System.currentTimeMillis());
        //获取2008年1月1日的时间
        Calendar calendarBegin = Calendar.getInstance();
        calendarBegin.set(2008,0,1);
        beginDate2008 = calendarBegin.getTime();
        //获取当前时间
        Calendar calendarNow = Calendar.getInstance(Locale.CHINA);
        calendarNow.setTime(nowdate);
        //计算相应的差距的天数
        long finallDay = (long)((nowdate.getTime() - beginDate2008.getTime()) / 1000 / 60 / 60 / 24); // 计算当日距离开始时间的天数

        //获取当前月月初
        Calendar calendarMonthBegin = Calendar.getInstance();
        calendarMonthBegin.set(calendarNow.get(Calendar.YEAR),
                calendarNow.get(Calendar.MONTH),1);
        Date monthBegin = calendarMonthBegin.getTime();
        long beginDay = (long)((monthBegin.getTime() - beginDate2008.getTime()) / 1000 / 60 / 60 / 24); // 计算当月距离开始时间相应的天数

        System.out.println("beginDay"+beginDay+"finallDay"+finallDay);
        //设置选中的项目
        monthChioceBtn.setText(months[calendarNow.get(Calendar.MONTH)]); //设置展示当前月的
        yearChioceBtn.setText(yearsAndHLV[calendarNow.get(Calendar.YEAR) - 2018]); //展示当前年份
        // 设置选中按钮的背景图
        isChoicedMonthHLV[calendarNow.get(Calendar.MONTH)] = true;
        isChoicedYearHLV[calendarNow.get(Calendar.YEAR) - 2018] = true;

        dayCollection = daydataMap.getCollections((int)beginDay,(int)finallDay);
    }

    //monthChioceBtn监听事件
    class monthChioceBtnListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            System.out.println("点击了monthbt");
            allMenu.setVisibility(View.GONE);
            monthItemLL.setVisibility(View.VISIBLE);
        }
    }

    //yearChioceBtn监听事件
    class yearChoiceBtnListener  implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            System.out.println("点击了yearbt");
            allMenu.setVisibility(View.GONE);
            yearItemLL.setVisibility(View.VISIBLE);
        }
    }

//    class yearChoiceBtnListener implements AdapterView.OnItemSelectedListener{
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            Log.d("得到position,年份",position+"");
//            //更新数据就好了
//            //构建现在选择的时间。
//            Calendar calendarnow = Calendar.getInstance();
////            calendarnow.set(2018 - yearChioceBtn.getSelectedItemPosition(),
////                    monthChioceBtn.getSelectedItemPosition(),1); // 获取现在的展示的日期开始。
//            calendarnow.set(2018 - yearChioceBtn.getSelectedItemPosition(),
//                    10,1);
//            Date nowdate = calendarnow.getTime(); // 获得这个月开始时间
//            long beginDay = (long)((nowdate.getTime() - beginDate2008.getTime()) / 1000 / 60 / 60 / 24); //获得这个月的开始时间的距离
//            int total = calendarnow.getActualMaximum(Calendar.DATE); // 获得这个月的时间长度
//            //获取数据
//            Calendar calendarThisMonth = Calendar.getInstance();//这是一个用来比较当前月份的数据
//            if((calendarnow.get(calendarnow.MONTH) == calendarThisMonth.get(calendarThisMonth.MONTH)) &&
//                    (calendarnow.get(calendarnow.YEAR) == calendarThisMonth.get(calendarThisMonth.YEAR))){
//                total = calendarThisMonth.get(Calendar.DAY_OF_MONTH); // 如果是当前的月份，则获取当前月份前些日子
//            }
//            dayCollection = daydataMap.getCollections((int)beginDay,(int)(beginDay + total-1));
//            System.out.println(dayCollection.isEmpty()+"  "+dayCollection.size()+"\n"+dayCollection.get(1).getDataFlag()+"\n\n");
//            myListviewAdapter.updateAdapter(dayCollection,MainActivity.this); //更新整个adapter
//            myListviewAdapter.notifyDataSetChanged();
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {
//
//        }
//    }

    //addbtn 监听事件
    class addBtnButtomListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,AddDataItem.class);
            Bundle bundle = new Bundle();
            //bundle.putInt("weeks",dayCollection.get(position).getWeekData());
            bundle.putSerializable("daydata",dayCollection.get(dayCollection.size()-1)); //将最后一个dayData数据结构发给下一页。
            bundle.putString("data"," / "+monthChioceBtn.getText().toString()+
                    " "+dayCollection.get(dayCollection.size()-1).getDayData()+
                    " / "+yearChioceBtn.getText().toString()); //SUNDAY / NOVEMBER 4 / 2018
            bundle.putInt("position",dayCollection.size()-1);
            intent.putExtras(bundle);
            startActivityForResult(intent,REQUEST_CODE_ADD_ITEM);
        }
    }

    //showMonthListener 监听事件
    class showMonthListener implements View.OnClickListener{
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
    }
    //数据项目点击事件
    class dataItemListListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(MainActivity.this,AddDataItem.class);
            Bundle bundle = new Bundle();
            //bundle.putInt("weeks",dayCollection.get(position).getWeekData());
            bundle.putSerializable("daydata",dayCollection.get(position)); //将一整个dayData数据结构发给下一页。
            bundle.putString("data"," / "+monthChioceBtn.getText().toString()+
                    " "+dayCollection.get(position).getDayData()+
                    " / "+yearChioceBtn.getText().toString()); //SUNDAY / NOVEMBER 4 / 2018
            bundle.putInt("position",position);
            intent.putExtras(bundle);
            startActivityForResult(intent,REQUEST_CODE_ADD_ITEM);
        }
    }
    //定义处理回到本页面的数据
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case REQUEST_CODE_ADD_ITEM:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    Daydata daydata = (Daydata) bundle.get("returnDay");
                    int position = (int) bundle.get("returnPosition");

                    dayCollection.set(position,daydata); // 更新collection
                    daydataMap.updateMapData(daydata);   //更新map
                    myListviewAdapter.updateDaydatas(IsMonth,position, daydata); //更新adapter
                    myListviewAdapter.notifyDataSetChanged();
                    dataListview.setAdapter(myListviewAdapter);
                }
                break;
        }
    }
    //结束事件实现数据的存储
    @Override
    protected void onPause(){
        super.onPause();
        ArrayList<Daydata> finallCollection = daydataMap.getFinallMapData();
        for(Daydata daydata : finallCollection){
            System.out.println(daydata.getNum()+""+daydata.getWeekData()+""+daydata.getDataContent());
        }
        DaydataOperater daydataOperater = new DaydataOperater();
        daydataOperater.save(MainActivity.this, finallCollection);
    }
}
