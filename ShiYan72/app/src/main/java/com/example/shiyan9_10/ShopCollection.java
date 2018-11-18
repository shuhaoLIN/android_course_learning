package com.example.shiyan9_10;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by lenovo on 2018/11/2.
 */
public class ShopCollection {
    ArrayList<Shop> shops;
    String text;
    public ShopCollection(){
        this.shops = new ArrayList<Shop>();
    }
    public ArrayList<Shop> getShops(){
        return shops;
    }
    public String getText(){
        return text;
    }
    public void download(final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //从网上进行获取文件
                    String url_s =  "http://www.jiaozuoye.com/joj/backup/my.json";
                    URL url = new URL(url_s);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    //设置连接属性，不喜欢的话可以直接默认
                    conn.setConnectTimeout(5000); //设置连接最长时间
                    conn.setUseCaches(false); //数据不多时可以不使用缓存

                    //这里连接了
                    conn.connect();
                    //这里才真正获取到数据
                    InputStream inputStream = conn.getInputStream();
                    InputStreamReader input = new InputStreamReader(inputStream);
                    BufferedReader buffer = new BufferedReader(input);
                    if(conn.getResponseCode() == 200){
                        //返回两百意味着ok
                        String inputLine;
                        StringBuffer resultData = new StringBuffer();
                        while((inputLine = buffer.readLine()) != null){
                            resultData.append(inputLine);
                        }
                        text = resultData.toString();
                        parseJson(text);//获取
                        handler.sendEmptyMessage(1);
//                        Message msg = new Message();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("name","你好啊！");
//
//                        msg.setData(bundle);
//                        handler.sendMessage(msg);
                        //sendMessage（）允许处理Message对象（可以包含数据）
                        //sendEmptyMessage（int what）只能放数据，类似于区分0-1

                        Log.v("out--------->",text);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void parseJson(String text){
        try{
            //这里的text就是上边获得到的数据，一个String
            JSONObject jsonObject = new JSONObject(text);
            JSONArray jsonDatas = jsonObject.getJSONArray("shops");
            int length = jsonDatas.length();
            String test;
            for(int i = 0; i < length; i++){
                JSONObject shopJson = jsonDatas.getJSONObject(i);
                Shop shop = new Shop();
                shop.setName(shopJson.getString("name"));
                shop.setLatitude(shopJson.getDouble("latitude"));
                shop.setLongitude(shopJson.getDouble("longitude"));
                shop.setMemo(shopJson.getString("memo"));
                shops.add(shop);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
