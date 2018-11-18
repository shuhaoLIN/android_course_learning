package com.example.getxiangmu1;

import java.io.Serializable;

/**
 * Created by hasee on 2018/11/6.
 */

public class DayData implements Serializable {
    private int year;
    private int month;
    private int day;
    private String weekday;
    private String text;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }
}
