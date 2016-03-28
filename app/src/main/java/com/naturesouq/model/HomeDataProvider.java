package com.naturesouq.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

/**
 * Created by shahid on 2/5/2016.
 */
public class HomeDataProvider implements Parcelable {
    private JSONArray homeData;
    private  JSONArray userAddress;
    private  JSONArray orderList;
    private JSONArray productSpecification;
    private JSONArray productSpecificationKey;
    private JSONArray productGallery;


    public HomeDataProvider(JSONArray homeData) {
        this.homeData = homeData;
    }
    public HomeDataProvider() {

    }


    protected HomeDataProvider(Parcel in) {
    }


    public JSONArray getHomeData() {
        return homeData;
    }

    public void setHomeData(JSONArray homeData) {
        this.homeData = homeData;
    }

    public JSONArray getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(JSONArray userAddress) {
        this.userAddress = userAddress;
    }

    public JSONArray getOrderList() {
        return orderList;
    }

    public void setOrderList(JSONArray orderList) {
        this.orderList = orderList;
    }


    public JSONArray getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(JSONArray productSpecification) {
        this.productSpecification = productSpecification;
    }

    public JSONArray getProductSpecificationKey() {
        return productSpecificationKey;
    }

    public void setProductSpecificationKey(JSONArray productSpecificationKey) {
        this.productSpecificationKey = productSpecificationKey;
    }

    public JSONArray getProductGallery() {
        return productGallery;
    }

    public void setProductGallery(JSONArray productGallery) {
        this.productGallery = productGallery;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Creator<HomeDataProvider> CREATOR = new Creator<HomeDataProvider>() {
        @Override
        public HomeDataProvider createFromParcel(Parcel in) {
            return new HomeDataProvider(in);
        }

        @Override
        public HomeDataProvider[] newArray(int size) {
            return new HomeDataProvider[size];
        }
    };

}
