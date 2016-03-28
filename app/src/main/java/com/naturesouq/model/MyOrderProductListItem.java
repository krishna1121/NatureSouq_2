package com.naturesouq.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

/**
 * Created by SI_Android_Binit on 2/16/2016.
 */
public class MyOrderProductListItem implements Parcelable {
    private String order_id;
    private String order_date;
    private String qty;
    private String name;
    private String price;
    private String product_id;
    private String sku;
    private String rating;
    private String review;
    private String description;
    private String short_description;
    private String image_url;

    JSONArray orderProductGallery;
    JSONArray orderProductSpecificationKey;
    JSONArray orderProductSpecification;

    public MyOrderProductListItem(String order_id, String order_date, String qty, String name, String price, String product_id, String sku, String rating, String review, String description, String short_description,
                                  String image_url,JSONArray orderProductGallery,JSONArray orderProductSpecificationKey,JSONArray orderProductSpecification) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.qty = qty;
        this.name = name;
        this.price = price;
        this.product_id = product_id;
        this.sku = sku;
        this.rating = rating;
        this.review = review;
        this.description = description;
        this.short_description = short_description;
        this.image_url = image_url;
        this.orderProductGallery=orderProductGallery;
        this.orderProductSpecificationKey=orderProductSpecificationKey;
        this.orderProductSpecification=orderProductSpecification;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public JSONArray getOrderProductGallery() {
        return orderProductGallery;
    }

    public void setOrderProductGallery(JSONArray orderProductGallery) {
        this.orderProductGallery = orderProductGallery;
    }

    public JSONArray getOrderProductSpecificationKey() {
        return orderProductSpecificationKey;
    }

    public void setOrderProductSpecificationKey(JSONArray orderProductSpecificationKey) {
        this.orderProductSpecificationKey = orderProductSpecificationKey;
    }

    public JSONArray getOrderProductSpecification() {
        return orderProductSpecification;
    }

    public void setOrderProductSpecification(JSONArray orderProductSpecification) {
        this.orderProductSpecification = orderProductSpecification;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.order_id);
        dest.writeString(this.order_date);
        dest.writeString(this.qty);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.product_id);
        dest.writeString(this.sku);
        dest.writeString(this.rating);
        dest.writeString(this.review);
        dest.writeString(this.description);
        dest.writeString(this.short_description);
        dest.writeString(this.image_url);
    }

    protected MyOrderProductListItem(Parcel in) {
        this.order_id = in.readString();
        this.order_date = in.readString();
        this.qty = in.readString();
        this.name = in.readString();
        this.price = in.readString();
        this.product_id = in.readString();
        this.sku = in.readString();
        this.rating = in.readString();
        this.review = in.readString();
        this.description = in.readString();
        this.short_description = in.readString();
        this.image_url = in.readString();
    }

    public static final Creator<MyOrderProductListItem> CREATOR = new Creator<MyOrderProductListItem>() {
        public MyOrderProductListItem createFromParcel(Parcel source) {
            return new MyOrderProductListItem(source);
        }

        public MyOrderProductListItem[] newArray(int size) {
            return new MyOrderProductListItem[size];
        }
    };
}
