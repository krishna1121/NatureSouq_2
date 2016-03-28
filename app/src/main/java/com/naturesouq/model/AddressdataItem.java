package com.naturesouq.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shahid on 2/10/2016.
 */
public class AddressdataItem implements Parcelable{
    private String id;
    private String street;
    private String city;
    private String zipCode;
    private String state;
    private String country;
    private String phoneNumber;
    private String subTotal;
    private String shipping;
    private String grandTotal;

    public AddressdataItem(String id,String street, String city, String zipCode, String state, String country, String phoneNumber,
    String subTotal,String shipping,String grandTotal) {
        this.id=id;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.state = state;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.subTotal=subTotal;
        this.shipping=shipping;
        this.grandTotal=grandTotal;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(street);
        dest.writeString(city);
        dest.writeString(zipCode);
        dest.writeString(state);
        dest.writeString(country);
        dest.writeString(phoneNumber);
        dest.writeString(subTotal);
        dest.writeString(shipping);
        dest.writeString(grandTotal);
    }

    protected AddressdataItem(Parcel in) {
        id = in.readString();
        street = in.readString();
        city = in.readString();
        zipCode = in.readString();
        state = in.readString();
        country = in.readString();
        phoneNumber = in.readString();
        subTotal = in.readString();
        shipping = in.readString();
        grandTotal = in.readString();
    }

    public static final Creator<AddressdataItem> CREATOR = new Creator<AddressdataItem>() {
        @Override
        public AddressdataItem createFromParcel(Parcel in) {
            return new AddressdataItem(in);
        }

        @Override
        public AddressdataItem[] newArray(int size) {
            return new AddressdataItem[size];
        }
    };

}
