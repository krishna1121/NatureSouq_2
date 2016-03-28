package com.naturesouq.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.naturesouq.R;

/**
 * Created by SI_Android_Binit on 10/20/2015.
 */
public class ShippingAddress extends Activity {

    TextView shippingAddress;
    Button addNewAddressbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_shipping_address);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle("Shipping Address");

        shippingAddress = (TextView)findViewById(R.id.message_str);
        Typeface face= Typeface.createFromAsset(getAssets(), "font/Lato_Bold.ttf");
        shippingAddress.setTypeface(face);

        addNewAddressbtn = (Button)findViewById(R.id.continuebtn);
        Typeface btnface= Typeface.createFromAsset(getAssets(), "font/Lato_Black.ttf");
        addNewAddressbtn.setTypeface(btnface);
    }

    public  void addNewAddress(View view){
        Intent newAddressIntent = new Intent(ShippingAddress.this, AddNewAddress.class);
        newAddressIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(newAddressIntent);
    }
}
