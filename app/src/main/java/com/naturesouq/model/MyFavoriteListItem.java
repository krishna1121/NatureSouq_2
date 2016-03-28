package com.naturesouq.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

/**
 * Created by SI_Android_Binit on 1/25/2016.
 */
public class MyFavoriteListItem implements Parcelable {

    private String product_id;
    private String sku;
    private String name;
    private String set;
    private String type;
    private String rating;
    private String review;
    private String price;
    private String image_url;
    private String shortDescription;
    private boolean stateOfDelete;

    JSONArray homeGallery;
    JSONArray homeSpecificationKey;
    JSONArray homeSpecification;

    public MyFavoriteListItem(String product_id, String sku, String name, String set, String type, String rating, String review,
                              String price, String image_url, String shortDescription,boolean stateOfDelete,JSONArray homeGallery,
                              JSONArray homeSpecificationKey,JSONArray homeSpecification) {
        this.product_id = product_id;
        this.sku = sku;
        this.name = name;
        this.set = set;
        this.type = type;
        this.rating = rating;
        this.review = review;
        this.price = price;
        this.image_url = image_url;
        this.shortDescription = shortDescription;
        this.stateOfDelete=stateOfDelete;
        this.homeGallery=homeGallery;
        this.homeSpecificationKey=homeSpecificationKey;
        this.homeSpecification=homeSpecification;
    }
    public MyFavoriteListItem(){

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public boolean getStateOfDelete() {
        return stateOfDelete;
    }

    public void setStateOfDelete(boolean stateOfDelete) {
        this.stateOfDelete = stateOfDelete;
    }

    public JSONArray getHomeGallery() {
        return homeGallery;
    }

    public void setHomeGallery(JSONArray homeGallery) {
        this.homeGallery = homeGallery;
    }

    public JSONArray getHomeSpecificationKey() {
        return homeSpecificationKey;
    }

    public void setHomeSpecificationKey(JSONArray homeSpecificationKey) {
        this.homeSpecificationKey = homeSpecificationKey;
    }

    public JSONArray getHomeSpecification() {
        return homeSpecification;
    }

    public void setHomeSpecification(JSONArray homeSpecification) {
        this.homeSpecification = homeSpecification;
    }

    @Override
    public String toString() {
        return "MyFavoriteListItem{" +
                "product_id='" + product_id + '\'' +
                ", sku='" + sku + '\'' +
                ", name='" + name + '\'' +
                ", set='" + set + '\'' +
                ", type='" + type + '\'' +
                ", rating='" + rating + '\'' +
                ", review='" + review + '\'' +
                ", price='" + price + '\'' +
                ", image_url='" + image_url + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.product_id);
        dest.writeString(this.sku);
        dest.writeString(this.name);
        dest.writeString(this.set);
        dest.writeString(this.type);
        dest.writeString(this.rating);
        dest.writeString(this.review);
        dest.writeString(this.price);
        dest.writeString(this.image_url);
        dest.writeString(this.shortDescription);
        dest.writeByte((byte)(stateOfDelete? 0 : 1));
    }

    protected MyFavoriteListItem(Parcel in) {
        this.product_id = in.readString();
        this.sku = in.readString();
        this.name = in.readString();
        this.set = in.readString();
        this.type = in.readString();
        this.rating = in.readString();
        this.review = in.readString();
        this.price = in.readString();
        this.image_url = in.readString();
        this.shortDescription = in.readString();
        stateOfDelete = in.readByte()!=1;
    }

    public static final Parcelable.Creator<MyFavoriteListItem> CREATOR = new Parcelable.Creator<MyFavoriteListItem>() {
        public MyFavoriteListItem createFromParcel(Parcel source) {
            return new MyFavoriteListItem(source);
        }

        public MyFavoriteListItem[] newArray(int size) {
            return new MyFavoriteListItem[size];
        }
    };
}
