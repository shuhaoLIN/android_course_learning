package com.example.daygram;

import android.content.Context;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by lenovo on 2018/11/17.
 */
//读取文件所有的信息，
public class DaydataOperater {
    private String file = "Daydatas.dat";
    public ArrayList<Daydata> load(Context context){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        //初始化流对象
        try{
            fis = context.openFileInput(file);
            ois = new ObjectInputStream(fis);
            return (ArrayList<Daydata>) ois.readObject();
        }catch (FileNotFoundException e){}
        catch (Exception e){
            e.printStackTrace();
            //反序列化失败 --- 删除缓存
            if(e instanceof InvalidClassException){
                File data = context.getFileStreamPath(file);
                data.delete();
            }
        }finally {
            try{
                ois.close();
            }catch (Exception e){}
            try{
                fis.close();
            }catch (Exception e){}
        }
        return null;
    }
    public boolean save(Context context, Serializable ser)
    {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(file, Context.MODE_PRIVATE);
            //fos = context.openFileOutput(file,Context.MODE_APPEND);//实现追加内容功能
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        } catch ( Exception e ) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
            } catch  ( Exception e ) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
    }
}
