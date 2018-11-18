package com.example.hasee.daygram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hasee.HorizontalListView;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private static final int RESQUEST_CODE_CHANGE = 10;
    private String[] months = {
            "JANUARY", "FEBRUARY", "MARCH", "APRIL",
            "MAY", "JUNE", "JULY", "AUGUST",
            "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
    };
    //底部按钮；
    private Button monthBtn, yearBtn, addBtn, viewBtn;
    //底部隐藏布局及其HorizontalListView；
    private LinearLayout mLinear, yLinear, viewLinear;
    private TextView viewText;
    private HorizontalListView monthTab, yearTab;
    //底部隐藏栏是否显示参数；
    private static boolean IS_TAB_SHOWN = false;
    //浏览页面还是日记页面；
    private static boolean IS_VIEW_MODE = false;
    //主界面的ListView及其适配器；
    private ListView thePageList;
    private myListViewAdapter thePageAdapter;
    //在显示在主页面的页面；
    private MonthPage theMonthPage;
    private static final boolean CALL_EDIT_NOW = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //读取本月的页面；
        theMonthPage = new MonthPage(getBaseContext());
        //初始化View组件；
        initViews();
        //thePage 绑定 thePageList；
        initPageList();
        //设置按钮响应；
        initClick();
        //设置底部隐藏Tab；
        initTab();
        //设置隐藏的浏览页面；
        viewTextUpdate();
    }

    //thePage 绑定的页面；
    private void initPageList() {
        thePageList = (ListView)findViewById(R.id.dayList);
        thePageAdapter = new myListViewAdapter(theMonthPage.getThePage());
        thePageList.setAdapter(thePageAdapter);
        //thePageList.setOnItemClickListener(new myDayListClick());
        thePageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this, "Item " + i, Toast.LENGTH_SHORT).show();
                ToDayShowInfo(i + 1, !CALL_EDIT_NOW);
            }
        });
    }

    //切换到子页面DayShowActivity相关；
    private void ToDayShowInfo(int theDay, boolean isToCallEditNoew) {
        Intent intent = new Intent(MainActivity.this, DayShowActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dayData", theMonthPage.readDay(theDay));
        if(isToCallEditNoew)
            bundle.putString("CALL_EDIT_NOW", "YES");
        else
            bundle.putString("CALL_EDIT_NOW", "NO");
        intent.putExtras(bundle);
        startActivityForResult(intent, RESQUEST_CODE_CHANGE);
    }

    //子页面返回监听；
   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESQUEST_CODE_CHANGE:
                if(resultCode == RESULT_OK) {
                    DayData dayData = (DayData)data.getSerializableExtra("backDayData");
                    //待改，数据没保存；
                    theMonthPage.setDayText(getBaseContext(), dayData.getDay(), dayData.getText());
                    thePageAdapter.update(dayData);
                    thePageAdapter.notifyDataSetChanged();
                    //Toast.makeText(MainActivity.this, ""+dayData.getDay(), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    //更新隐藏的浏览页面；
    private void viewTextUpdate() {
        String text = "";
        ArrayList<DayData> days = theMonthPage.getThePage();
        int size = days.size();
        for(int i = 0; i < size; i++) {
            String str = days.get(i).getText();
            if(str != null)
                text += days.get(i).getDay() + " " + days.get(i).getWeekday()
                        + " / " + str + "\n\n";
        }
        viewText.setText(text);
    }

    //初始化View组件；
    private void initViews() {
        monthBtn = (Button)findViewById(R.id.monthBtn);
        monthBtn.setText(months[theMonthPage.getPageMonth() - 1]);
        yearBtn = (Button)findViewById(R.id.yearBtn);
        yearBtn.setText(String.valueOf(theMonthPage.getPageYear()));
        addBtn = (Button)findViewById(R.id.addBtn);
        viewBtn = (Button)findViewById(R.id.viewBtn);
        mLinear = (LinearLayout)findViewById(R.id.mLinear);
        monthTab = (HorizontalListView) findViewById(R.id.monthTab);
        yLinear = (LinearLayout)findViewById(R.id.yLinear);
        yearTab = (HorizontalListView)findViewById(R.id.yearTab);
        viewLinear = (LinearLayout)findViewById(R.id.viewLinear);
        viewText = (TextView)findViewById(R.id.viewText);
    }

    //设置按钮响应；
    private void initClick() {
        monthBtn.setOnClickListener(new myBtnClick());
        yearBtn.setOnClickListener(new myBtnClick());
        addBtn.setOnClickListener(new myBtnClick());
        viewBtn.setOnClickListener(new myBtnClick());
    }

    //设置底部隐藏Tab；
    private void initTab() {
        String[] monthsTitle = {
                "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
                "JUL", "AUG", "SEPT", "OCT", "NOV", "DEC"
        };
        int toLight = monthsTitle.length;
        if(theMonthPage.getPageYear() == theMonthPage.getToYear())
            toLight = theMonthPage.getToMonth();
        monthTab.setAdapter(new myTabAdapter(monthsTitle, toLight));
        String[] yearTitle = new String[theMonthPage.getToYear() - 2010 + 1];
        for(int i = 0; i <yearTitle.length; i++)
            yearTitle[i] = String.valueOf(2010 + i);
        yearTab.setAdapter(new myTabAdapter(yearTitle, yearTitle.length));
        monthTab.setOnItemClickListener(new monthTabClick());
        yearTab.setOnItemClickListener(new yearTabClick());
    }

    //重写返回键；
    @Override
    public void onBackPressed() {
        //如果有Tab显示中，按返回键隐藏；
        if(IS_TAB_SHOWN) {
            mLinear.setVisibility(View.GONE);
            yLinear.setVisibility(View.GONE);
            IS_TAB_SHOWN = false;
        }else
            super.onBackPressed();
    }

    //底部按钮点击监听；
    private class myBtnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(view == monthBtn) {
                mLinear.setVisibility(View.VISIBLE);
                mLinear.bringToFront();
                IS_TAB_SHOWN = true;
            }
            else if(view == yearBtn) {
                yLinear.setVisibility(View.VISIBLE);
                yLinear.bringToFront();
                IS_TAB_SHOWN = true;
            }
            else if(view == addBtn) {
                //返回本月页面；
                theMonthPage.backToday(getBaseContext());
                thePageAdapter.changePage(theMonthPage.getThePage());
                ToDayShowInfo(theMonthPage.getToday(),CALL_EDIT_NOW);
                thePageAdapter.notifyDataSetChanged();
            }
            else if(view == viewBtn){
                //
                viewTextUpdate();
                viewLinear.setVisibility((IS_VIEW_MODE) ? View.GONE : View.VISIBLE);
                IS_VIEW_MODE = !IS_VIEW_MODE;
            }
        }
    }

    //主界面页面ListView适配器；
    private class myListViewAdapter extends BaseAdapter{
        private View[] itemViews;
        private int length;

        public myListViewAdapter(ArrayList<DayData> thePage) {
            itemViews = new View[31];
            length = 31;
            makeViews(thePage);
        }

        //更换itemView；
        public void update(DayData dayData) {
            int dpt = dayData.getDay() - 1;
            String text = dayData.getText();
            if(text.length() != 0)
                itemViews[dpt] = makeDayView(dayData.getWeekday(), dayData.getDay(),
                        dayData.getText());
            else
                itemViews[dpt] = makeBtnView(dayData.getWeekday());
        }

        //全部替换；
        private void pageUpdate(ArrayList<DayData> thePage) {
            makeViews(thePage);
        }

        //切换到其他页面；
        public void changePage(ArrayList<DayData> thePage) {
            makeViews(thePage);
        }

        //创建views；
        private void makeViews(ArrayList<DayData> thePage) {
            length = (thePage.size() < 31) ? thePage.size() : 31;
            for(int i = 0; i < length; i++) {
                if(thePage.get(i).getText() == null)
                    itemViews[i] = makeBtnView(thePage.get(i).getWeekday());
                else
                    itemViews[i] = makeDayView(thePage.get(i).getWeekday(),
                            thePage.get(i).getDay(), thePage.get(i).getText());
            }
        }

        private View makeDayView(String weekday, int day, String text) {
            LayoutInflater inflater = (LayoutInflater)MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.list_view_daydata, null);
            //星期取前 3 个字符；
            String str = weekday.substring(0, 3);
            ((TextView)itemView.findViewById(R.id.weekItemTitle)).setText(str);
            TextView dayTitle = (TextView)itemView.findViewById(R.id.dayItemTitle);
            dayTitle.setText(String.valueOf(day));
            if(weekday.equals("SUNDAY"))
                dayTitle.setTextColor(Color.RED);
            //截取一段字符串；
            boolean isTooLong = text.length() > 30;
            str = text.substring(0, ((isTooLong) ? 30 : text.length()));
            if(isTooLong)
                str += ". . .";
            ((TextView)itemView.findViewById(R.id.dayItemText)).setText(str);
            return itemView;
        }

        private View makeBtnView(String weekday) {
            LayoutInflater inflater = (LayoutInflater)MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.list_view_btn, null);
            TextView addText = (TextView)itemView.findViewById(R.id.dataAddBtn);
            if(weekday.equals("SUNDAY"))
                addText.setBackgroundResource(R.drawable.sunday_btn);
            return itemView;
        }

        @Override
        public int getCount() {
            return length;
        }

        @Override
        public Object getItem(int i) {
            return itemViews[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return itemViews[i];
        }
    }

    //底部隐藏栏HorizontalListView适配器；
    private class myTabAdapter extends BaseAdapter {
        private ArrayList<View> itemViews;

        public myTabAdapter(String[] titles, int toLight) {
            itemViews = new ArrayList<View>(titles.length);
            //for (String title : titles) itemViews.add(makeItemView(title));
            for(int i = 0; i < titles.length; i++) {
                itemViews.add(makeItemView(titles[i], (i < toLight)));
            }
        }

        private View makeItemView(String title, boolean isLight) {
            LayoutInflater inflater = (LayoutInflater)MainActivity.this
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.list_view_item, null);
            TextView itemTitle = (TextView)itemView.findViewById(R.id.itemTitle);
            itemTitle.setText(title);
            if(isLight)
                itemTitle.setTextColor(Color.BLACK);
            return itemView;
        }

        @Override
        public int getCount() {
            return itemViews.size();
        }

        @Override
        public Object getItem(int i) {
            return itemViews.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return itemViews.get(i);
        }
    }

    //底部monthTab 点击监听；
    private class monthTabClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //当是 往年月份 或 今年本月及之前的月份，即已经的月份，点击有效；
            if(theMonthPage.getPageYear() != theMonthPage.getToYear() ||
                    (theMonthPage.getPageYear() == theMonthPage.getToYear()
                            && i < theMonthPage.getToMonth())) {
                //Toast.makeText(MainActivity.this, "TOUCH", Toast.LENGTH_SHORT).show();
                theMonthPage.loadPage(getBaseContext(), theMonthPage.getToYear(), i + 1);
                thePageAdapter.changePage(theMonthPage.getThePage());
                thePageAdapter.notifyDataSetChanged();
                monthBtn.setText(months[theMonthPage.getPageMonth() - 1]);
                onBackPressed();
                viewTextUpdate();
            }
        }
    }

    //底部yearTab 点击监听；
    private class yearTabClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            theMonthPage.loadPage(getBaseContext(), 2010 + i, theMonthPage.getPageMonth());
            thePageAdapter.changePage(theMonthPage.getThePage());
            thePageAdapter.notifyDataSetChanged();
            yearBtn.setText(String.valueOf(theMonthPage.getPageYear()));
            onBackPressed();
            viewTextUpdate();
        }
    }
}
