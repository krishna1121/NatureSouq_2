package com.naturesouq.model;

/**
 * Created by SI-Andriod on 24-Feb-16.
 */
public class VlauesItem {

    String valueItemLabel ;
    String id ;
    String count ;
    boolean isChecked ;

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }


    public String getValueItemLabel() {
        return valueItemLabel;
    }

    public void setValueItemLabel(String valueItemLabel) {
        this.valueItemLabel = valueItemLabel;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VlauesItem(String count, String valueItemLabel, String id ,boolean isChecked){
        this.count = count ;
        this.valueItemLabel = valueItemLabel ;
        this.id = id ;
        this.isChecked = isChecked ;
    }
}
