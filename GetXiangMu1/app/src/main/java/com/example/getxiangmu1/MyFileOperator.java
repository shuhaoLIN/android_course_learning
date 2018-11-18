package com.example.getxiangmu1;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hasee on 2018/11/7.
 */

public class MyFileOperator {
    private static final String FS = ".dat";
    //file 格式：年份_月份.dat ；

    //读取某年某月的数据；
    public ArrayList<DayData> load(Context context, int theYear, int theMonth) {
        String file = theYear + "_" + theMonth + FS;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = context.openFileInput(file);
            ois = new ObjectInputStream(fis);
            return (ArrayList<DayData>) ois.readObject();
        }catch (FileNotFoundException e) {}
        catch (Exception e) {
            e.printStackTrace();;
            /*if(e instanceof InvalidClassException) {
                File data = context.getFileStreamPath(file);
                data.delete();
            }*/
        }finally {
            try {
                ois.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                fis.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return new ArrayList<DayData>();
    }

    //保存某年某月的数据；
    public boolean save(Context context, Serializable ser,
                     int theYear, int theMonth) {
        String file = theYear + "_" + theMonth + FS;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(file, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            try{
                oos.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                fos.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //删除某年某月的文件；
    public void delFile(Context context, int theYear, int theMonth) {
        String file = theYear + "_" + theMonth + FS;
        File data = context.getFileStreamPath(file);
        data.delete();
    }

}
