package com.naturesouq.common;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

/**
 * Created by shahid on 10/22/2015.
 */

public class NatureSouqPrefrences extends Application{
    NatureSouqPrefrences natureSouqPrefrences;

    static final String SHOPPING_CART_ID = "shopping_cart_id";
    static final String PRODUCT_ID = "product_id";
    static final String USER_NAME = "user_name";
    static final String USER_PASS = "user_pass";
    static final String USER_ADDRESS = "user_address";
    static final String CART_COUNTER = "cart_counter";
    static final String CUSTOMER_ID = "customer_id";
    static final String FACEBOOK_PREFS = "facebook_prefs";
    static final String LOGIN_STATUS = "login_status";
    static final String ORDER_ID = "order_id";
    static final String MERCHANT_ID = "merchant_id";
    static final String ADDRESS_COUNT = "adress_count";
    static final String USER_FIRST_NAME = "first_name";
    static final String USER_LAST_NAME = "last_name";
    static final String USER_CONTACT_NO = "contact_no";
    static final String ADDRESS_ID = "address_id";

    @Override
    public void onCreate() {
        super.onCreate();
        natureSouqPrefrences = this;

    }

    public static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }
    //get and set Shopping CartId in prefrence
    public static void setShoppingcartId(Context ctx, String id) {

        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(SHOPPING_CART_ID, id);
        editor.commit();
    }

    public static String getShoppingcartId(Context ctx) {
        String id = getSharedPreferences(ctx).getString(SHOPPING_CART_ID,"");
        return id;
    }

    //get and set Shopping Cart Counter in prefrence
    public static void setCartCounter(Context ctx, String counter) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(CART_COUNTER, counter);
        editor.commit();
    }

    public static String getCartCounter(Context ctx) {
        String counter = getSharedPreferences(ctx).getString(CART_COUNTER, "");
        return counter;
    }

    //get and set Product Id in prefrence
    public static void setProductId(Context ctx, String id) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PRODUCT_ID, id);
        editor.commit();
    }

    public static String getProductId(Context ctx) {
        String id = getSharedPreferences(ctx).getString(PRODUCT_ID, "");
        return id;
    }
    //get and set userName in prefrence
    public static void setUserName(Context ctx, String usrname) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(USER_NAME, usrname);
        editor.commit();
    }

    public static String getUserName(Context ctx) {
        String id = getSharedPreferences(ctx).getString(USER_NAME, "");
        return id;
    }

    //get and set userPassword in prefrence
    public static void setUserPass(Context ctx, String password) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(USER_PASS, password);
        editor.commit();
    }
    public static String getUserPass(Context ctx) {
        String id = getSharedPreferences(ctx).getString(USER_PASS, "");
        return id;
    }
    //get and set userAddress in prefrence
    public static void setAddress(Context ctx, String address) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(USER_ADDRESS, address);
        editor.commit();
    }
    public static String getaddress(Context ctx) {
        String id = getSharedPreferences(ctx).getString(USER_ADDRESS, "");
        return id;
    }

    //get and set customerId in prefrence
    public static void setCustomer_id(Context ctx, String customer_id) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(CUSTOMER_ID, customer_id);
        editor.commit();
    }
    public static String getCustomer_id(Context ctx) {
        String id = getSharedPreferences(ctx).getString(CUSTOMER_ID, "");
        return id;
    }

    public static void setMerchantId(Context ctx, String id) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(MERCHANT_ID, id);
        editor.commit();
    }

    public static String getMerchantId(Context ctx) {
        String id = getSharedPreferences(ctx).getString(MERCHANT_ID, "");
        return id;
    }

    public static void setOrderId(Context ctx, String id) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(ORDER_ID, id);
        editor.commit();
    }

    public static String getOrderId(Context ctx) {
        String id = getSharedPreferences(ctx).getString(ORDER_ID, "");
        return id;
    }

    public static void setFacebookUser(Context ctx, boolean facebookPrefs) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(FACEBOOK_PREFS, facebookPrefs);
        editor.commit();
    }

    public static boolean getFacebookUser(Context ctx) {
        return getSharedPreferences(ctx).getBoolean(FACEBOOK_PREFS, false);
    }
    public static void setLoginStatus(Context ctx, boolean login_status) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(LOGIN_STATUS, login_status);
        editor.commit();
    }

    public static boolean getLoginStatus(Context ctx) {
        return getSharedPreferences(ctx).getBoolean(LOGIN_STATUS, false);
    }
    public static void setLoginCartList(Context ctx, int count) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(ADDRESS_COUNT, count);
        editor.commit();
    }

    public static int getLoginCartList(Context ctx) {
        return getSharedPreferences(ctx).getInt(ADDRESS_COUNT, 0);
    }

    //get and set userFirstName in prefrence
    public static void setUserFirstName(Context ctx, String fName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(USER_FIRST_NAME, fName);
        editor.commit();
    }
    public static String getUserFirstName(Context ctx) {
        String id = getSharedPreferences(ctx).getString(USER_FIRST_NAME, "");
        return id;
    }

    //get and set userLastName in prefrence
    public static void setUserLastName(Context ctx, String lName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(USER_LAST_NAME, lName);
        editor.commit();
    }
    public static String getUserLastName(Context ctx) {
        String id = getSharedPreferences(ctx).getString(USER_LAST_NAME, "");
        return id;
    }

    //get and set userContact in prefrence
    public static void setUserContactNo(Context ctx, String contact) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(USER_CONTACT_NO, contact);
        editor.commit();
    }
    public static String getUserContactNo(Context ctx) {
        String id = getSharedPreferences(ctx).getString(USER_CONTACT_NO, "");
        return id;
    }

    public static void setAddressId(String key ,Context ctx, String id) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(key, id);
        editor.commit();
    }

    public static String getAddressId(Context ctx) {
        String id = getSharedPreferences(ctx).getString(ADDRESS_ID, "");
        return id;
    }

}
