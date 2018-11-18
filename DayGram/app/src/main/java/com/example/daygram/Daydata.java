package com.example.daygram;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/11/15.
 */
//使用Map将数据对从2008年1月1日起的数据的天数.
//2008年1月1日是周二。
public class Daydata implements Serializable{

    private int num;//这个变量记录着是从第几天
    private int weekData; //周的文字
    private String dayData;  //一个月里面的第几天
    private String dataContent; //日记内容
    private int dataFlag; // 有没有日记

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getWeekData() {
        return weekData;
    }

    public void setWeekData(int weekData) {
        this.weekData = weekData;
    }

    public String getDayData() {
        return dayData;
    }

    public void setDayData(String dayData) {
        this.dayData = dayData;
    }

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    public int getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(int dataFlag) {
        this.dataFlag = dataFlag;
    }
}
