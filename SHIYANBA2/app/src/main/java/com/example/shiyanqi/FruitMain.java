package com.example.shiyanqi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.*;
import com.example.shiyanba2.R;

import java.util.ArrayList;

public class FruitMain extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_ITEM = 10;
    private ArrayList<Fruit> fruitsCollection;
    private ListViewAdapter  myAdapter;
    private int [] picIds={ R.drawable.apple_pic, R.drawable.bananas_pic,R.drawable.orange_pic,
            R.drawable.watermelon_pic,R.drawable.grapes_pic,R.drawable.strawberry_pic
            ,R.drawable.cherry_pic,R.drawable.mango_pic};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fruit_main);
        //设置actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //使用悬浮按钮实现将数据传至后面的页面
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FruitMain.this,AddItemActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("name","水果(悬浮按钮)");
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_CODE_ADD_ITEM);
            }
        });
        //读取文件的内容
        FruitCollectionOperater operater=new FruitCollectionOperater();
        fruitsCollection=operater.load(getBaseContext());
        if(fruitsCollection==null)
            fruitsCollection= new ArrayList<Fruit>();
        //获取到listView,并配置好适配器
        ListView listViewFruit = (ListView)findViewById(R.id.MyListView);
        myAdapter = new ListViewAdapter(fruitsCollection);
        listViewFruit.setAdapter(myAdapter);
        listViewFruit.setOnItemClickListener(new myListItemClick());

        //注册上下文菜单
        registerForContextMenu(listViewFruit);
    }
    //定义处理回到本页面的数据
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case REQUEST_CODE_ADD_ITEM:
                if(resultCode == RESULT_OK){
                    String[] name = data.getStringExtra("name").split(" ");
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
            operater.save(FruitMain.this,fruitsCollection);

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
                myAdapter.addItem("水果(上下文)","该水果3元/个");
                myAdapter.notifyDataSetChanged();
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

}
