package com.example.getxiangmu1;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by hasee on 2018/11/7.
 */

public class MonthPage {
    private ArrayList<DayData> thePage;     //月份页面;
    private int pageYear;               //页面所在年份；
    private int pageMonth;              //页面月份；
    private int toYear;                 //系统年份；
    private int toMonth;                //系统月份；
    private int today;                  //系统日期；

    public MonthPage(Context context) {
        //测试使用：删除之前保存的所有数据；
        //delAllPage(context);
        //
        this.backToday(context);
    }

    public ArrayList<DayData> getThePage() {
        return thePage;
    }

    public int getPageYear() {
        return pageYear;
    }

    public int getPageMonth() {
        return pageMonth;
    }

    public int getToYear() {
        return toYear;
    }

    public int getToMonth() {
        return toMonth;
    }

    public int getToday() {
        return today;
    }

    //记录某天日记；
    public boolean setDayText(Context context, int theDay, String dayText) {
        if(theDay > thePage.size())
            return false;
        thePage.get(theDay - 1).setText(dayText);
        MyFileOperator operator = new MyFileOperator();
        return operator.save(context, thePage, pageYear, pageMonth);

    }

    public String readDayText(int theDay) {
        return thePage.get(theDay - 1).getText();
    }

    public DayData readDay(int theDay) {
        return thePage.get(theDay - 1);
    }

    //读取某年某月的页面；
    public void loadPage(Context context, int theYear, int theMonth) {
        MyFileOperator operator = new MyFileOperator();
        //先保存当前页面；
        thePage = operator.load(context, pageYear, pageMonth);
        pageYear = theYear;
        pageMonth = theMonth;
        //页面读取；
        thePage = operator.load(context, pageYear, pageMonth);
        update(context);
    }

    //读取今天所在月份页面；
    public void backToday(Context context) {
        Calendar calendar = Calendar.getInstance();
        pageYear = toYear = calendar.get(Calendar.YEAR);
        pageMonth = toMonth = calendar.get(Calendar.MONTH) + 1;
        today = calendar.get(Calendar.DAY_OF_MONTH);
        //运行测试时app空间会保存错误的文件，使用下面语句删除某月页面；
        //delThePage(context, pageYear, pageMonth);
        //
        MyFileOperator operator = new MyFileOperator();
        thePage = operator.load(context, pageYear, pageMonth);
        //读取文件不存在时thePage.size() == 0，或之前月份不完整，更新；
        update(context);
    }

    //更新，将页面自动扩展，本月的扩展到today， 其他扩展到该月天数；
    private void update(Context context) {
        int theDay = 1;
        //页面不是本月的；
        if(pageYear != toYear || pageMonth != toMonth)
            theDay = MonthDaysCount(pageYear, pageMonth);
        else //页面是本月的；
            theDay = today;
        if(thePage.size() < theDay) {
            for(int i = thePage.size() + 1; i <= theDay; i++) {
                DayData dayData = new DayData();
                dayData.setYear(pageYear);
                dayData.setMonth(pageMonth);
                dayData.setDay(i);
                dayData.setWeekday(getWeekDay(pageYear, pageMonth, i));
                thePage.add(dayData);
            }
            MyFileOperator operator = new MyFileOperator();
            operator.save(context, thePage, pageYear, pageMonth);
        }
    }

    //计算某一日期是星期几；
    private String getWeekDay(int theYear, int theMonth, int theDay) {
        String[] weekday = {"MONDAY", "TUESDAY", "WEDNESDAY",
                "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
        int wpt = 4;
        int allDays = 0;
        int yearSize = (theYear > 2010) ? theYear -2010 : 2010 - theYear;
        allDays += yearSize * 365 + LeapYearCount(theYear);
        for(int i = 1; i < theMonth; i++)
            allDays += MonthDaysCount(theYear, i);
        //与2010年1月1日相差的天数；
        allDays += (theDay - 1);
        wpt = (wpt + allDays) % 7;
        return weekday[wpt];
    }

    //计算从 2010 到 theYear 期间闰年的个数；
    private int LeapYearCount(int theYear) {
        int conut = 0;
        for(int i = 2010; i < theYear; i++) {
            if(isLeapYear(i))
                conut++;
        }
        return conut;
    }

    //Leap Year能被 4 整除， 不能被 100 整除；
    private boolean isLeapYear(int theYear) {
        if((theYear % 4) == 0 && (theYear % 100) != 0)
            return true;
        return false;
    }

    //计算月份天数；
    private int MonthDaysCount(int theYear, int theMonth) {
        if(theMonth == 4 || theMonth == 6 || theMonth == 9
                || theMonth == 11)
            return 30;
        else if(theMonth == 2)
            return ( 28 + (isLeapYear(theYear) ? 1 : 0) );
        else
            return 31;
    }

    //删除某月页面；
    private void delThePage(Context context, int theYear, int theMonth) {
        ArrayList<DayData> pageToDel = new ArrayList<>();
        //如果是thePage页面，需要更新thePage；
        if(theYear == pageYear && theMonth == pageMonth) {
            thePage = pageToDel;
            update(context);
        }
        else {
            MyFileOperator operator = new MyFileOperator();
            operator.save(context, pageToDel, theYear, theMonth);
        }
    }

    //删除所有数据；有BUG，待改；
    private void delAllPage(Context context) {
        MyFileOperator operator = new MyFileOperator();
        for(int i = 2010; i <= toYear; i++) {
            for(int j = 1; j <= toMonth; j++)
                operator.delFile(context, i, j);
        }
    }
}
