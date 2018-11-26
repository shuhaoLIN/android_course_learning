package com.example.horizontalListview;

/**
 * Created by lenovo on 2018/11/26.
 */
public class HorizontalDate {
    String option;
    Boolean isChoiced;
    Boolean canBeChoiced;

    public String getOption() {
        return option;
    }

    public void setOption(String month) {
        this.option = month;
    }

    public Boolean getChoiced() {
        return isChoiced;
    }

    public void setChoiced(Boolean choiced) {
        isChoiced = choiced;
    }

    public Boolean getCanBeChoiced() {
        return canBeChoiced;
    }

    public void setCanBeChoiced(Boolean canBeChoiced) {
        this.canBeChoiced = canBeChoiced;
    }
}
