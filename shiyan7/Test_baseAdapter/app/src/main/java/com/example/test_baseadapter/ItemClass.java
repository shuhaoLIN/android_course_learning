package com.example.test_baseadapter;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/10/5.
 */
public class ItemClass implements Serializable{
    private static final long serialVersionUID = 1L;
    private String itemName;
    private String itemContent;
    private int itemPicID;

    public ItemClass(String itemName,String itemContent,int itemPicID){
        this.itemName = itemName;
        this.itemContent = itemContent;
        this.itemPicID = itemPicID;
    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }

    public int getItemPicID() {
        return itemPicID;
    }

    public void setItemPicID(int itemPicID) {
        this.itemPicID = itemPicID;
    }
}
