package com.example.test_baseadapter;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/10/5.
 */
public class ItemClass implements Serializable{
    private static final long serialVersionUID = 1L;
    private String itemName;
    private String itemContent;

    public ItemClass(String itemName,String itemContent){
        this.itemName = itemName;
        this.itemContent = itemContent;
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
}
