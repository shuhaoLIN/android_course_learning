package com.example.shiyanba;

import android.os.BaseBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.example.fruit_packge.Fruit;

import java.util.ArrayList;

public class MainActivity_shiyanba extends AppCompatActivity {
    //food
    private static final int REQUEST_CODE_ADD_ITEM = 10;
    private ArrayList<Fruit> fruitsCollection;
//    private ListViewAdapter  myAdapter;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    //数据源
    private String[] titles = {"地图","物价","新闻"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_shiyanba);

        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        viewPager = (ViewPager)findViewById(R.id.viewpager);

        //将adapter，viewPager，tablayout绑定在一起
        MyPagerAdapter myPagerAdapter =  new MyPagerAdapter(getSupportFragmentManager());

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(myPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        //每条之间的分割线
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,R.drawable.layout_divider_vertical));

        //加图标
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setIcon(R.drawable.pic1);
        }

//        //标记浮动按钮
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addFragment addFragment = new addFragment();
//                addFragment.setLayoutID(R.layout.activity_add_item);
//                Bundle bundle = new Bundle();
//                bundle.putString("name","水果（悬浮按钮）");
//                addFragment.setArguments(bundle);
//            }
//        });
    }

    static int[] ID = {R.layout.one_test,R.layout.activity_fruit_main,R.layout.web_fragment};
    //实现将相应的layout注入Fragment
    class MyPagerAdapter extends FragmentPagerAdapter{
        public MyPagerAdapter(FragmentManager fm){
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            TestFragment testFragment = new TestFragment();
            testFragment.setLayoutID(ID[position]);
            return testFragment;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
        @Override
        public int getCount() {
            return titles.length;
        }
    }


}
