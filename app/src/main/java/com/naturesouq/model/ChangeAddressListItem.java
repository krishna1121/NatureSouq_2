package com.naturesouq.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SI_Android_Binit on 2/1/2016.
 */
public class ChangeAddressListItem implements Parcelable {
    private String address_id;
    private String firstname;
    private String lastname;
    private String company;
    private String city;
    private String country_id;
    private String state;
    private String postcode;
    private String telephone;
    private String street;
    private String customer_id;
    private boolean stateOfActivite = false;

    public ChangeAddressListItem(String address_id, String firstname, String lastname, String company, String city, String country_id, String state,
                                 String postcode, String telephone, String street, String customer_id,boolean stateOfActivite) {
        this.address_id = address_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.company = company;
        this.city = city;
        this.country_id = country_id;
        this.state = state;
        this.postcode = postcode;
        this.telephone = telephone;
        this.street = street;
        this.customer_id = customer_id;
        this.stateOfActivite=stateOfActivite;
    }
    public  ChangeAddressListItem(){

    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public boolean isStateOfActivite() {
        return stateOfActivite;
    }

    public void setStateOfActivite(boolean stateOfActivite) {
        this.stateOfActivite = stateOfActivite;
    }

    @Override
    public String toString() {
        return "ChangeAddressListItem{" +
                "address_id='" + address_id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", company='" + company + '\'' +
                ", city='" + city + '\'' +
                ", country_id='" + country_id + '\'' +
                ", state='" + state + '\'' +
                ", postcode='" + postcode + '\'' +
                ", telephone='" + telephone + '\'' +
                ", street='" + street + '\'' +
                ", customer_id='" + customer_id + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address_id);
        dest.writeString(this.firstname);
        dest.writeString(this.lastname);
        dest.writeString(this.company);
        dest.writeString(this.city);
        dest.writeString(this.country_id);
        dest.writeString(this.state);
        dest.writeString(this.postcode);
        dest.writeString(this.telephone);
        dest.writeString(this.street);
        dest.writeString(this.customer_id);
        dest.writeByte((byte) (this.stateOfActivite ? 1 : 0));

    }

    protected ChangeAddressListItem(Parcel in) {
        this.address_id = in.readString();
        this.firstname = in.readString();
        this.lastname = in.readString();
        this.company = in.readString();
        this.city = in.readString();
        this.country_id = in.readString();
        this.state = in.readString();
        this.postcode = in.readString();
        this.telephone = in.readString();
        this.street = in.readString();
        this.customer_id = in.readString();
        this.stateOfActivite = in.readByte() != 0;

    }

    public static final Parcelable.Creator<ChangeAddressListItem> CREATOR = new Parcelable.Creator<ChangeAddressListItem>() {
        public ChangeAddressListItem createFromParcel(Parcel source) {
            return new ChangeAddressListItem(source);
        }

        public ChangeAddressListItem[] newArray(int size) {
            return new ChangeAddressListItem[size];
        }
    };
}
