package com.naturesouq.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by SI_Android_Binit on 10/22/2015.
 */
public class CategoryProductsItem implements Parcelable {

    //for webservice data
    String Product_id;
    String type;
    String set;
    String sku;
    String position;
    String rating;
    String review;
    String price;
    String name;
    String description;
    String short_description;
    String image_url;
    String SmallImage;
    String Thumbnail;

    JSONObject objectBestSeller;
    JSONArray homeGallery;
    JSONArray homeSpecificationKey;
    JSONArray homeSpecification;

    public CategoryProductsItem(JSONObject objectBestSeller) {
        this.objectBestSeller = objectBestSeller;
    }

    public CategoryProductsItem(String product_id, String type, String set, String sku, String position, String rating,
                                String review, String price, String name, String description,
                                String short_description, String image_url, String smallImage, String thumbnail,
                                JSONArray homeGallery,JSONArray homeSpecificationKey,JSONArray homeSpecification) {
        Product_id = product_id;
        this.type = type;
        this.set = set;
        this.sku = sku;
        this.position = position;
        this.rating = rating;
        this.review = review;
        this.price = price;
        this.name = name;
        this.description = description;
        this.short_description = short_description;
        this.image_url = image_url;
        SmallImage = smallImage;
        Thumbnail = thumbnail;
        this.homeGallery=homeGallery;
        this.homeSpecificationKey=homeSpecificationKey;
        this.homeSpecification=homeSpecification;
    }

    public String getProduct_id() {
        return Product_id;
    }

    public void setProduct_id(String product_id) {
        Product_id = product_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSmallImage() {
        return SmallImage;
    }

    public void setSmallImage(String smallImage) {
        SmallImage = smallImage;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
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

    /**
     * Standard basic constructor for non-parcel
     * object creation
     */
    public CategoryProductsItem() { ; };

    /**   Constructor to use when re-constructing object
     from a parcel
     @param in a parcel from which to read this object   */

    public CategoryProductsItem(Parcel in) {

        // We just need to read back each
        // field in the order that it was
        // written to the parcel

        setProduct_id(in.readString());
        setType(in.readString());
        setSet(in.readString());
        setSku(in.readString());
        setPosition(in.readString());
        setRating(in.readString());
        setReview(in.readString());
        setPrice(in.readString());
        setName(in.readString());
        setDescription(in.readString());
        setShort_description(in.readString());
        setImage_url(in.readString());
        setSmallImage(in.readString());
        setThumbnail(in.readString());
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        // We just need to write each field into the
        // parcel. When we read from parcel, they
        // will come back in the same order
        parcel.writeString(getProduct_id());
        parcel.writeString(getType());
        parcel.writeString(getSet());
        parcel.writeString(getSku());
        parcel.writeString(getPosition());
        parcel.writeString(getRating());
        parcel.writeString(getReview());
        parcel.writeString(getPrice());
        parcel.writeString(getName());
        parcel.writeString(getDescription());
        parcel.writeString(getShort_description());
        parcel.writeString(getImage_url());
        parcel.writeString(getSmallImage());
        parcel.writeString(getThumbnail());
    }
    /**
     *
     * This field is needed for Android to be able to
     * create new objects, individually or as arrays.
     *
     * This also means that you can use use the default
     * constructor to create the object and use another
     * method to hyrdate it as necessary.
     *
     * I just find it easier to use the constructor.
     * It makes sense for the way my brain thinks ;-)
     *
     */
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CategoryProductsItem createFromParcel(Parcel in) {
            return new CategoryProductsItem(in);
        }

        public CategoryProductsItem[] newArray(int size) {
            return new CategoryProductsItem[size];
        }
    };
}
