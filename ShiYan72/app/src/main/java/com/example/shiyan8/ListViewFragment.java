package com.example.shiyan8;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.shiyan72.AddItemActivity;
import com.example.shiyan72.FruitMain;
import com.example.shiyan72.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lenovo on 2018/10/31.
 */
public class ListViewFragment extends Fragment {
    //case需要一个常量，所以这里需要编写为final
    protected static final int REQUEST_CODE_ADD_ITEM_FRAGMENT = 10;
    //定义一个adapter适配器方便后面使用
    private FruitMain.ListViewAdapter listViewAdapter;
    public ListViewFragment(){
    }
    //Fragment不建议使用自己的带参数的构造函数，
    //所以最好不要用。
    @SuppressLint("ValidFragment")
    public ListViewFragment(FruitMain.ListViewAdapter theListViewAdapter){
        listViewAdapter = theListViewAdapter;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //String name = this.getArguments().getString("title");
        View contentView = inflater.inflate(R.layout.fragment_list_view, container, false);
        ListView listFruitsView= (ListView) contentView.findViewById(R.id.MyListView);

        listFruitsView.setAdapter(listViewAdapter);
        listFruitsView.setOnItemClickListener(new mListViewItemClick2());

        //使用悬浮按钮实现将数据传至后面的页面
        FloatingActionButton fab = (FloatingActionButton) contentView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),AddItemActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("name","水果(悬浮按钮)");
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_CODE_ADD_ITEM_FRAGMENT);
            }
        });
        registerForContextMenu(listFruitsView);
        return contentView;
        //return webView;
    }
    class mListViewItemClick2 implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(getActivity()
                    ,"您选择的项目是："+i
                    , Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case REQUEST_CODE_ADD_ITEM_FRAGMENT:
                if(resultCode == RESULT_OK){
//                    String[] name = data.getStringExtra("name").split(" ");
                    String[] name = data.getStringArrayExtra("name");
                    listViewAdapter.addItem(name[0],name[1]);
                    listViewAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
