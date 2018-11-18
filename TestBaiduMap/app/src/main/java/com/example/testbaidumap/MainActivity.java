package com.example.testbaidumap;

import android.content.BroadcastReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;

import static com.baidu.mapapi.BMapManager.getContext;

public class MainActivity extends AppCompatActivity {

    TextureMapView mMapView;
    BaiduMap baiduMap;
    MapStatusUpdate msu;
    BroadcastReceiver  broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mMapView = (TextureMapView) findViewById(R.id.bmapView);
        //修改初始位置
        baiduMap = mMapView.getMap();
        LatLng cenpt = new LatLng(22.255925,113.541112);
        //msu = MapStatusUpdateFactory.newLatLng(cenpt);
        MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
        .target(cenpt).zoom(17).build(); // 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        msu = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        baiduMap.setMapStatus(msu);  //改变地图状态

        //根据资源id获取source
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource((R.drawable.ic_launcher));
        //准备marker option添加marker使用
        MarkerOptions markerOptions = new MarkerOptions().icon(bitmap).position(cenpt);
        //获取添加的marker这样便于后续的操作
        Marker marker = (Marker)baiduMap.addOverlay(markerOptions);

        OverlayOptions textOption = new TextOptions().bgColor(0xAAFFFF00).fontSize(50)
                .fontColor(0xFFFF00FF).text("百度地图SDK").rotate(0).position(cenpt);
        baiduMap.addOverlay(textOption);

        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(getContext(), "Marker被点击了！", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
