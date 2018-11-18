package com.example.shiyan72;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.example.shiyan8.ListViewFragment;
import com.example.shiyan8.WebViewFragment;
import com.example.shiyan9_10.MapViewFragment;

import java.util.ArrayList;

public class FruitMain extends AppCompatActivity {
    public static final int REQUEST_CODE_ADD_ITEM = 10;
    private ArrayList<Fruit> fruitsCollection;
    private ListViewAdapter  myAdapter;
    private int [] picIds={ R.drawable.apple_pic,R.drawable.bananas_pic,R.drawable.orange_pic,
            R.drawable.watermelon_pic,R.drawable.grapes_pic,R.drawable.strawberry_pic
            ,R.drawable.cherry_pic,R.drawable.mango_pic};

    //实验八添加的内容
    private TabLayout tabLayout;
    private ViewPager viewPager;
    //数据源
    private String[] titles = {"地图","物价","新闻"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fruit_main);

        //读取文件的内容
        FruitCollectionOperater operater=new FruitCollectionOperater();
        fruitsCollection=operater.load(getBaseContext());
        if(fruitsCollection==null){
            fruitsCollection= new ArrayList<Fruit>();
            Fruit one = new Fruit();
            one.setName("苹果");
            one.setContent("苹果一斤五元");
            one.setPicId(R.drawable.apple_pic);
            fruitsCollection.add(one);
        }
        //不需要获得listview了，在ListViewFragment中获取就好了
        //获取到listView,并配置好适配器
//        ListView listViewFruit = (ListView)findViewById(R.id.MyListView);
//        listViewFruit.setAdapter(myAdapter);
//        listViewFruit.setOnItemClickListener(new myListItemClick());
        //注册上下文菜单
//        registerForContextMenu(listViewFruit);

        myAdapter = new ListViewAdapter(fruitsCollection);
        //将adapter，viewPager，tablayout绑定在一起
        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        MyPagerAdapter myPagerAdapter =  new MyPagerAdapter(getSupportFragmentManager());
        //TabLayoutOnPageChangeListener就是绑定联动
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(myPagerAdapter);
            //因为setupWithViewPager源码里面需要到adapter的数据
        tabLayout.setupWithViewPager(viewPager);


        //每条之间的分割线
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,R.drawable.layout_divider_vertical));

//        //加图标
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            tab.setIcon(R.drawable.pic1);
//        }

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public CharSequence getPageTitle(int position){
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            if(0 == position){
                MapViewFragment mapFragment = new MapViewFragment();
                return mapFragment;
            }
            else if(1 == position){
                ListViewFragment listViewFragment = new ListViewFragment(myAdapter);
                return listViewFragment;
            }
            else{
                WebViewFragment webViewFragment = new WebViewFragment();
                return webViewFragment;
            }
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }

    //定义处理回到本页面的数据
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case REQUEST_CODE_ADD_ITEM:
                if(resultCode == RESULT_OK){
//                    String[] name = data.getStringExtra("name").split(" ");
                    String[] name = data.getStringArrayExtra("name");
                    myAdapter.addItem(name[0],name[1]);
                    myAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
    class myListItemClick implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(FruitMain.this,
                    "您选择的项目是"+position,
                    Toast.LENGTH_LONG).show();
        }
    }
    //定义adapter为内部类
    public class ListViewAdapter extends BaseAdapter{
        ArrayList<View> itemViews;
        public ListViewAdapter(ArrayList<Fruit> fruitCollection){
            itemViews = new ArrayList<View>(fruitCollection.size());
            for(int i = 0 ;i<fruitCollection.size();i++){
                itemViews.add(makeItemView(fruitCollection.get(i).getName(),
                        fruitCollection.get(i).getContent(),
                        fruitCollection.get(i).getPicId()));
            }
        }
        //获取一个itemView对象
        public View makeItemView(String name,String context,int picId){
            LayoutInflater inflater = (LayoutInflater)
                    FruitMain.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //使用View对象itemView 和layout进行关联
            View itemView = inflater.inflate(R.layout.listview_item,null);
            //通过id获得各个组件并进行赋值
            TextView itemTitle  = (TextView)itemView.findViewById(R.id.itemTitle);
            itemTitle.setText(name);
            TextView itemContext = (TextView)itemView.findViewById(R.id.itemText);
            itemContext.setText(context);
            ImageView itemImage = (ImageView )itemView.findViewById(R.id.itemImage);
            itemImage.setImageResource(picId);

            return itemView;
        }
        //添加
        public void addItem(String itemTitle,String itemContext){
            Fruit fruit = new Fruit();
            int fruitId = (int)(Math.random() * 8);
            fruit.setName(itemTitle);
            fruit.setContent(itemContext);
            fruit.setPicId(picIds[fruitId]);
            fruitsCollection.add(fruit);

            FruitCollectionOperater operater = new FruitCollectionOperater();
            operater.save(FruitMain.this,fruit);

            View view = makeItemView(itemTitle,itemContext,picIds[fruitId]);
            itemViews.add(view);
        }
        //删除
        public void removeItem(int position){
            itemViews.remove(position);
            fruitsCollection.remove(position);
            FruitCollectionOperater operater = new FruitCollectionOperater();
            operater.save(FruitMain.this,fruitsCollection);
        }

        public int getCount(){return itemViews.size();}
        public View getItem(int position){return itemViews.get(position);}
        public long getItemId(int position){return position;}
        @Override
        //实现上下拉取获得到新的项目
        public View getView(int position, View convertView, ViewGroup parent){
            return itemViews.get(position);
        }

    }
    //设置上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle("操作");
        menu.add(0,1,0,"删除");
        menu.add(0,2,0,"添加");
        super.onCreateContextMenu(menu,v,menuInfo);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch(item.getItemId()){
            case 1:
                myAdapter.removeItem(menuInfo.position);
                myAdapter.notifyDataSetChanged();
                break;
            case 2:
                Intent intent=new Intent(FruitMain.this,AddItemActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("name","火龙果");
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_CODE_ADD_ITEM);
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }
}
