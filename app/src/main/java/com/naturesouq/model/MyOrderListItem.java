package com.naturesouq.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SI_Android_Binit on 2/16/2016.
 */
public class MyOrderListItem implements Parcelable {
    private String order_id;
    private String order_date;
    private String sub_total;
    private String order_status;
    private String customer_email;
    private String noofitems;
    private String image_url;
    private boolean stateOfDelete;

    public MyOrderListItem(String order_id, String order_date, String sub_total, String order_status, String customer_email, String noofitems, String image_url,
                           boolean stateOfDelete) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.sub_total = sub_total;
        this.order_status = order_status;
        this.customer_email = customer_email;
        this.noofitems = noofitems;
        this.image_url = image_url;
        this.stateOfDelete=stateOfDelete;
    }
    public  MyOrderListItem(){

    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getSub_total() {
        return sub_total;
    }

    public void setSub_total(String sub_total) {
        this.sub_total = sub_total;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getNoofitems() {
        return noofitems;
    }

    public void setNoofitems(String noofitems) {
        this.noofitems = noofitems;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public boolean getStateOfDelete() {
        return stateOfDelete;
    }

    public void setStateOfDelete(boolean stateOfDelete) {
        this.stateOfDelete = stateOfDelete;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.order_id);
        dest.writeString(this.order_date);
        dest.writeString(this.sub_total);
        dest.writeString(this.order_status);
        dest.writeString(this.customer_email);
        dest.writeString(this.noofitems);
        dest.writeString(this.image_url);
        dest.writeByte((byte) (stateOfDelete ? 1 : 0));  //if myBoolean == true, byte == 1
    }

    protected MyOrderListItem(Parcel in) {
        this.order_id = in.readString();
        this.order_date = in.readString();
        this.sub_total = in.readString();
        this.order_status = in.readString();
        this.customer_email = in.readString();
        this.noofitems = in.readString();
        this.image_url = in.readString();
        stateOfDelete = in.readByte()!=0;  ////myBoolean == true if byte != 0
    }

    public static final Parcelable.Creator<MyOrderListItem> CREATOR = new Parcelable.Creator<MyOrderListItem>() {
        public MyOrderListItem createFromParcel(Parcel source) {
            return new MyOrderListItem(source);
        }

        public MyOrderListItem[] newArray(int size) {
            return new MyOrderListItem[size];
        }
    };
}
