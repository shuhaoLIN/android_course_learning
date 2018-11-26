package com.example.daygram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2018/11/19.
 */
public class StaticMap {
    static Map<Integer, Daydata> dataMap = new HashMap<>();
    //将所有的日记文件读取出来，存放到Map中去。
    public static void createMap(ArrayList<Daydata> collection){
        if(collection != null){
            for(Daydata daydata : collection){
                dataMap.put(daydata.getNum(),daydata);
            }
        }else {
            //不做什么
        }
    }
    //获取相应的月数的信息 begin为月初一，end为月末
    public static ArrayList<Daydata> getCollections(int begin, int end){
        ArrayList<Daydata> collections = new ArrayList<>();
        for(int i = begin;i <= end;i++){
            if(dataMap.get(i)  != null){
                collections.add(dataMap.get(i));
            }else{
                Daydata d = new Daydata();
                d.setNum(i);         //距离多少天
                d.setDataContent("");
                d.setDataFlag(0);  //没有内容的flag为0
                int day = i - begin + 1; //当月的日期
                d.setDayData(day+"");
                d.setWeekData((i % 7 + 1) % 7); //就是星期的下标
                collections.add(d);
            }
        }
        return collections;
    }
    //实现添加保存机制
    public static void addMapData(Daydata daydata){
        dataMap.put(daydata.getNum(),daydata);
    }
    //实现修改机制
    public static void updateMapData(Daydata daydata){
        dataMap.remove(daydata.getNum());
        dataMap.put(daydata.getNum(), daydata);
    }
    //获取最后的结果进行保存
    public static ArrayList<Daydata> getFinallMapData(){
        ArrayList<Daydata> collections = new ArrayList<>();
        for (Map.Entry<Integer, Daydata> entry : dataMap.entrySet()){
            collections.add(entry.getValue());
        }
        return collections;
    }

}
