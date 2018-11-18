package com.example.shiyan9_10;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.example.shiyan72.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/11/2.
 */
public class MapViewFragment extends Fragment {
    @Nullable
    TextureMapView mMapView=null;
    Button mapButtonBoss = null;
    Button mapButtonHome = null;
    BaiduMap mBaidumap = null; // 标记着地图的变量
    ShopCollection shopCollection = null;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //String name = this.getArguments().getString("title");
        View contentView = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (TextureMapView)contentView.findViewById(R.id.bmapView);
        mapButtonBoss = (Button)contentView.findViewById(R.id.map_button_boss);
        mapButtonHome = (Button)contentView.findViewById(R.id.map_button_home);

        //修改百度地图的初始位置
        mBaidumap = mMapView.getMap();

        LatLng cenpt = new LatLng(22.2559,113.541112);//设定中心点坐标,暨南大学
        MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
                .target(cenpt).zoom(17).build();//定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaidumap.setMapStatus(mMapStatusUpdate);//改变地图状态

        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.jinandaxue);
        //准备 marker option 添加 marker 使用
        MarkerOptions markerOption = new MarkerOptions().icon(bitmap).position(cenpt);
        //获取添加的 marker 这样便于后续的操作
        markerOption.perspective(true);
        Marker marker = (Marker) mBaidumap.addOverlay(markerOption);//添加了覆盖的图片

        //cenpt = new LatLng(22.25415,113.541841);
        OverlayOptions textOption = new TextOptions().bgColor(0xAAFFFF00).fontSize(20).fontColor(0xFFFF00FF).text("珠海暨南大学").rotate(0).position(cenpt);
        mBaidumap.addOverlay(textOption); //添加覆盖文本,这个不算是maker，所以不响应点击事件

        mBaidumap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(), "Marker被点击了！", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        mapButtonBoss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double latitude = Math.random() * 120;
                double longitude = Math.random() * 120;
                LatLng boss_loc = new LatLng(latitude,longitude);
                MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
                        .target(boss_loc).zoom(17).build();//定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                mBaidumap.setMapStatus(mMapStatusUpdate);//改变地图状态

                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.yinyang);
                //准备 marker option 添加 marker 使用
                MarkerOptions markerOption = new MarkerOptions().icon(bitmap).position(boss_loc);
                //获取添加的 marker 这样便于后续的操作
                //markerOption.perspective(true);
                Marker marker = (Marker) mBaidumap.addOverlay(markerOption);//添加了覆盖的图片

                OverlayOptions textOption = new TextOptions().bgColor(0xAAFFFF00).fontSize(20).fontColor(0xFFFF00FF).text("怪兽出没地点：").rotate(0).position(boss_loc);
                mBaidumap.addOverlay(textOption);
                boss_loc = new LatLng(latitude-0.002, longitude);
                OverlayOptions textOption2 = new TextOptions().bgColor(0xAAFFFF00).fontSize(20).fontColor(0xFFFF00FF).text(""+((int)latitude)+","+((int)longitude)).rotate(0).position(boss_loc);
                mBaidumap.addOverlay(textOption2);

            }
        });

        mapButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng boss_loc = new LatLng(22.2559,113.541112);
                MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
                        .target(boss_loc).zoom(17).build();//定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                mBaidumap.setMapStatus(mMapStatusUpdate);//改变地图状态
            }
        });

        shopCollection=new ShopCollection();
        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                DrawShops(shopCollection.getShops());
            };

        };

        shopCollection.download(handler);

        return contentView;
        //return webView;
    }
    public void DrawShops(ArrayList<Shop> shops)
    {
        BaiduMap mBaidumap = mMapView.getMap();
        for(int i=1;i<shops.size();i++) {
            Shop shop=shops.get(i);

            LatLng cenpt = new LatLng(shop.getLatitude(),shop.getLongitude());//设定中心点坐标

            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.shop);
            //准备 marker option 添加 marker 使用
            MarkerOptions markerOption = new MarkerOptions().icon(bitmap).position(cenpt);
            //获取添加的 marker 这样便于后续的操作
            Marker marker = (Marker) mBaidumap.addOverlay(markerOption);

            OverlayOptions textOption = new TextOptions().bgColor(0xAAFFFF00).fontSize(50).fontColor(0xFFFF00FF).text(shop.getName()).rotate(0).position(cenpt);
            mBaidumap.addOverlay(textOption);
        }

    }
    public void MDestroy() {
        if(mMapView!=null)mMapView.onDestroy();
    }
    public void MResume() {
        if(mMapView!=null)mMapView.onResume();
    }
    public void MPause() {
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        if(mMapView!=null)mMapView.onPause();
    }
}
