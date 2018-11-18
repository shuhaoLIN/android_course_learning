package com.jndx.csp.goodsprice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jszx.myapplication.R;

import java.util.ArrayList;

public class GoodsPriceActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_ITEM = 10;
    String [] titles={"青椒","瘦肉","鱼"
    };
    String [] texts={"8.0元 2018-9-28","16.0元 2018-9-28","13.0元 2018-9-28"
    };
    int [] resIds={R.drawable.a1,R.drawable.a2,R.drawable.a3
    };
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle("操作");
        menu.add(0, 1, 0, "删除");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        AdapterView.AdapterContextMenuInfo itemInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 1:
                theListAdapter.removeItem(itemInfo.position);
                theListAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }
    public class ListViewAdapter extends BaseAdapter {
        ArrayList<View> itemViews;

        public ListViewAdapter(String [] itemTitles, String [] itemTexts,
                               int [] itemImageRes){
            itemViews = new ArrayList<View>(itemTitles.length);

            for (int i=0; i<itemTitles.length; ++i){
                itemViews.add(makeItemView(itemTitles[i], itemTexts[i],
                        itemImageRes[i]));
            }
        }

        public void addItem(String itemTitle){
            View view=makeItemView(itemTitle, "我是新增项",
                    R.drawable.a1);
            itemViews.add(view);
        }
        public void removeItem(int positon){
            itemViews.remove(positon);
        }
        public int getCount() {
            return itemViews.size();
        }

        public View getItem(int position) {
            return itemViews.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        private View makeItemView(String strTitle, String strText, int resId) {
            LayoutInflater inflater = (LayoutInflater)GoodsPriceActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            View itemView = inflater.inflate(R.layout.list_view_item_goods_price, null);

            // 通过findViewById()方法实例R.layout.item内各组件
            TextView title = (TextView)itemView.findViewById(R.id.itemTitle);
            title.setText(strTitle);
            TextView text = (TextView)itemView.findViewById(R.id.itemText);
            text.setText(strText);
            ImageView image = (ImageView)itemView.findViewById(R.id.itemImage);
            image.setImageResource(resId);

            return itemView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //if (convertView == null)
                return itemViews.get(position);
            //return convertView;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_ADD_ITEM:
                if (resultCode == RESULT_OK){
                    String name = data.getStringExtra("name");
                    theListAdapter.addItem(name);
                    theListAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
    ListViewAdapter theListAdapter;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_price);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GoodsPriceActivity.this,AddItemActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("name","胡萝卜");
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_CODE_ADD_ITEM);
            }
        });
        ListView listViewGoodsPrice= (ListView) this.findViewById(R.id.listwiew_goods_price);
        theListAdapter=new ListViewAdapter(titles,texts,resIds);
        listViewGoodsPrice.setAdapter(theListAdapter);
        listViewGoodsPrice.setOnItemClickListener(new mListViewItemClick2());

        registerForContextMenu(listViewGoodsPrice);
        /*String[] data={
                "(1)荷塘月色"
                ,"(2)荷塘月色"
                ,"(3)荷塘月色"
                ,"(4)荷塘月色"
                ,"(5)荷塘月色"
        };
        listViewGoodsPrice.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data));
        listViewGoodsPrice.setOnItemClickListener(new mListViewItemClick());
        */
    }

    class mListViewItemClick implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(GoodsPriceActivity.this
                    ,"您选择的项目是："+((TextView)view).getText()
                    , Toast.LENGTH_SHORT).show();
        }
    }
    class mListViewItemClick2 implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(GoodsPriceActivity.this
                    ,"您选择的项目是："+i
                    , Toast.LENGTH_SHORT).show();
        }
    }
}
