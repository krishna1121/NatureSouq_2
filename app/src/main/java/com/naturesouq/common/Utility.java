package com.naturesouq.common;

import android.telephony.PhoneNumberUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SI-Andriod on 6/9/2015.
 */
public class Utility {

    private static Pattern pattern;
    private static Matcher matcher;
    //public static String baseURL="http://192.185.24.213/~naturesouq/";
    //public static String baseURL="http://uat.naturesouq.com/webservices/";
    public static String baseURL="https://www.naturesouq.com/webservices/";

    //Payfort Credentials .
    public static final String TEST_PSPID ="naturesouq";
    public static final String TEST_USER_NAME ="somdev1234";
    public static final String TEST_PASSWORD ="asdf@1234";
    public static final String TEST_SHAIN_PASSPHRASE ="NatureSouq@518754";

    public static final String LIVE_PSPID ="naturesouq";
    public static final String LIVE_USER_NAME ="jayadmin";
    public static final String LIVE_PASSWORD ="@almoo$a-&04!!";
    public static final String LIVE_SHAIN_PASSPHRASE ="naturesouq";


    //Email Pattern
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern GLOBAL_PHONE_NUMBER_PATTERN =
            Pattern.compile("[\\+]?[0-9.-]+");


    /**
     * Validate Email with regular expression
     *
     * @param email
     * @return true for Valid Email and false for Invalid Email
     */

    public static boolean validateEmail(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Checks for Null String object
     *
     * @param txt
     * @return true for not null and false for null String object
     */

    public static boolean isNotNull(String txt) {
        return txt != null && txt.trim().length() > 0 ? true : false;
    }

    // validating password with retype password
    public static boolean validatePassword(String pass) {
        if (pass != null && pass.length() >= 6) {
            return true;
        }
        return false;
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean validCellPhone(String number)
    {
        if (number !=null && number.length()>=10 && number.length()<=13){
        return PhoneNumberUtils.isGlobalPhoneNumber(number);
        }
       // return android.util.Patterns.PHONE.matcher(number).matches();
        return false;
    }

    public static boolean validPostalCode(String number){
        if (number !=null && number.length()>2)
        {
            return true;
        }
        return false;
    }
}
