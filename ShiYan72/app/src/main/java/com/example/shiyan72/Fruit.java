package com.example.shiyan72;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/10/18.
 */
public class Fruit implements Serializable {
    private static final long serialVersionUID = 3L;
    private String name;
    private String content;
    private int picId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }
}
