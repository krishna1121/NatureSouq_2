package com.naturesouq.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

/**
 * Created by shahid on 10/21/2015.
 */
public class CartRowItems implements Parcelable {

    private String product_image_url;
    private String product_name;
    private String product_shortDesc;
    private String product_price;
    private String product_id;
    private String qty;
    private String rating;
    private String review;

    JSONArray cartGallery;
    JSONArray cartSpecificationKey;
    JSONArray cartSpecification;

    public CartRowItems(String product_image_url, String product_name, String product_shortDesc, String product_price,String product_id,
    String qty, String rating, String review,JSONArray cartGallery,JSONArray cartSpecificationKey,JSONArray cartSpecification) {
        this.product_image_url = product_image_url;
        this.product_name = product_name;
        this.product_shortDesc = product_shortDesc;
        this.product_price = product_price;
        this.product_id = product_id;
        this.qty = qty;
        this.rating = rating;
        this.review = review;
        this.cartGallery=cartGallery;
        this.cartSpecificationKey=cartSpecificationKey;
        this.cartSpecification=cartSpecification;
    }



    public String getProduct_image_url() {
        return product_image_url;
    }

    public void setProduct_image_url(String product_image_url) {
        this.product_image_url = product_image_url;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_shortDesc() {
        return product_shortDesc;
    }

    public void setProduct_shortDesc(String product_shortDesc) {
        this.product_shortDesc = product_shortDesc;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return rating;
    }

    public void setReview(String rating) {
        this.rating = rating;
    }

    public JSONArray getCartGallery() {
        return cartGallery;
    }

    public void setCartGallery(JSONArray cartGallery) {
        this.cartGallery = cartGallery;
    }

    public JSONArray getCartSpecificationKey() {
        return cartSpecificationKey;
    }

    public void setCartSpecificationKey(JSONArray cartSpecificationKey) {
        this.cartSpecificationKey = cartSpecificationKey;
    }

    public JSONArray getCartSpecification() {
        return cartSpecification;
    }

    public void setCartSpecification(JSONArray cartSpecification) {
        this.cartSpecification = cartSpecification;
    }

    protected CartRowItems(Parcel in) {
        product_image_url = in.readString();
        product_name = in.readString();
        product_shortDesc = in.readString();
        product_price = in.readString();
        product_id = in.readString();
        qty = in.readString();
        rating = in.readString();
        review = in.readString();
    }

    public static final Creator<CartRowItems> CREATOR = new Creator<CartRowItems>() {
        @Override
        public CartRowItems createFromParcel(Parcel in) {
            return new CartRowItems(in);
        }

        @Override
        public CartRowItems[] newArray(int size) {
            return new CartRowItems[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(product_image_url);
        dest.writeString(product_name);
        dest.writeString(product_shortDesc);
        dest.writeString(product_price);
        dest.writeString(product_id);
        dest.writeString(qty);
        dest.writeString(rating);
        dest.writeString(review);
    }
}
