package com.naturesouq.common;

/**
 * Created by SI-Andriod on 27-Apr-16.
 */

public class CountriesList {

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
}
