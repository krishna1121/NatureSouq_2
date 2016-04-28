package com.naturesouq.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SI-Andriod on 27-Apr-16.
 */

public class CountriesList  implements Parcelable {

    private String countryName ;
    private String countryCode ;
    private String shippingCharge ;

    CountriesList(String name , String code , String charge){
        this.countryName = name ;
        this.countryCode = code ;
        this.shippingCharge = charge ;
    }


    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }



    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }



    public String getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(String shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(countryName);
        dest.writeString(countryCode);
        dest.writeString(shippingCharge);

    }

    protected CountriesList(Parcel in) {
        this.countryName = in.readString();
        this.countryCode = in.readString();
        this.shippingCharge = in.readString();

    }

    public static final Creator<CountriesList> CREATOR = new Creator<CountriesList>() {
        @Override
        public CountriesList createFromParcel(Parcel in) {
            return new CountriesList(in);
        }

        @Override
        public CountriesList[] newArray(int size) {
            return new CountriesList[size];
        }
    };
}
