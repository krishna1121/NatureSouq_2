package com.naturesouq.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.naturesouq.R;
import com.naturesouq.adapter.CustomSpinnerAdapter;
import com.naturesouq.model.AddressdataItem;
import com.naturesouq.model.ChangeAddressListItem;
import com.naturesouq.model.HomeDataProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by SI_Android_Binit on 10/15/2015.
 */

public class AddNewAddress extends Activity {
        TextView title;
        Button addAddress;
        public static final int UPDATED_ADD_INTENT_CODE =  201 ;
        EditText street, city, zipcode, state, phoneNumber;
       // final String CHANGE_ADDRESS_URL="http://dev.smartideaz.org/php_web_services/naturesouqWS/setCustomerCartAddress.php";
        final String CHANGE_ADDRESS_URL=Utility.baseURL+"addNewaddress.php";
        final String UPDATE_ADDRESS_URL=Utility.baseURL+"updateAddress.php";
        String Street_Add,CITY,ZIP_CODE,STATE,PHONE_NUMBER,customer_id, value,address_id;
        ProgressBar progressBar;
        Spinner country;
        static HomeDataProvider homeDataProvider;
        ChangeAddressListItem object;
        CustomSpinnerAdapter spinAdapter;
        //ArrayList<CountriesList> countriesList ;
        String country_name , country_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_addresss);
        progressBar =(ProgressBar)findViewById(R.id.progressBar);

        //countriesList = getIntent().getParcelableArrayListExtra("countrieslist") ;

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle("Add New");

        if(this.getIntent().getExtras() != null)
        object = this.getIntent().getExtras().getParcelable("AddressForUpdate");

        title = (TextView)findViewById(R.id.shipment_info);
        Typeface face1= Typeface.createFromAsset(getAssets(), "font/Lato_Black.ttf");
        title.setTypeface(face1);

        street = (EditText)findViewById(R.id.street_address);
        Typeface face2= Typeface.createFromAsset(getAssets(), "font/Lato_Regular.ttf");
        street.setTypeface(face2);

        city = (EditText)findViewById(R.id.city);
        Typeface face3= Typeface.createFromAsset(getAssets(), "font/Lato_Regular.ttf");
        city.setTypeface(face3);

        zipcode = (EditText)findViewById(R.id.zip);
        Typeface face4= Typeface.createFromAsset(getAssets(), "font/Lato_Regular.ttf");
        zipcode.setTypeface(face4);

        state = (EditText)findViewById(R.id.state);
        Typeface face5= Typeface.createFromAsset(getAssets(), "font/Lato_Regular.ttf");
        state.setTypeface(face5);

        phoneNumber = (EditText)findViewById(R.id.phone);
        Typeface face6= Typeface.createFromAsset(getAssets(), "font/Lato_Regular.ttf");
        phoneNumber.setTypeface(face6);

        country = (Spinner)findViewById(R.id.countary);
        //Typeface face7= Typeface.createFromAsset(getAssets(), "font/Lato_Regular.ttf");
        //country.setTypeface(face7);
        country.setEnabled(true);
        addCountrySpinner();

        addAddress = (Button)findViewById(R.id.newaddress_btn);
        Typeface face8= Typeface.createFromAsset(getAssets(), "font/Lato_Black.ttf");
        addAddress.setTypeface(face8);

        if (!(object==null)){
            updateAddress();
        }
    }

    public void addCountrySpinner() {
        spinAdapter = new CustomSpinnerAdapter(getApplicationContext(), NatureSouqPrefrences.countriesList);
        country.setAdapter(spinAdapter);
        country.setSelection(1);
        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                CountriesList lisItem = spinAdapter.getData().get(position) ;
                String shippingCharge = lisItem.getShippingCharge() ;
                country_name = lisItem.getCountryName() ;
                country_id = lisItem.getCountryCode() ;
                NatureSouqPrefrences.setShippingCharge(getApplicationContext() , shippingCharge);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    public void newAddress(View view){

        Street_Add = street.getText().toString();
        CITY = city.getText().toString();
        ZIP_CODE = zipcode.getText().toString();
        STATE = state.getText().toString();
        PHONE_NUMBER = phoneNumber.getText().toString();
        customer_id = NatureSouqPrefrences.getCustomer_id(AddNewAddress.this);

        if (!Utility.isNotNull(Street_Add)) {

            street.setError("Street can't be empty!");
            street.requestFocus();
        } else if (!Utility.isNotNull(CITY)) {

            city.setError("City can't be empty!");
            city.requestFocus();
        } else if (!Utility.isNotNull(STATE)) {

            state.setError("State can't be empty!");
            state.requestFocus();
        } else if (!Utility.isNotNull(PHONE_NUMBER)) {

            phoneNumber.setError("Contact number can't be empty!");
            phoneNumber.requestFocus();
        } else if (!Utility.validCellPhone(PHONE_NUMBER)) {

            phoneNumber.setError("Invalid contact number!");
            phoneNumber.requestFocus();
        } else {

        JSONObject jsonObject = new JSONObject();
        try {
            String shopingcartId=NatureSouqPrefrences.getShoppingcartId(AddNewAddress.this);

            if (!(object==null)){

                jsonObject.put("addressId", object.getAddress_id());
                jsonObject.put("company", "NatureSouq");
                jsonObject.put("street", Street_Add);
                jsonObject.put("city", CITY);
                jsonObject.put("region", STATE);
                jsonObject.put("region_id", "2");
                jsonObject.put("postcode", ZIP_CODE);
                jsonObject.put("country_id", country_id);
                jsonObject.put("telephone", PHONE_NUMBER);
                jsonObject.put("apikey", "naturesouq#123@apikey");
                new ChangeAddTask(jsonObject, "updateAddress").execute(UPDATE_ADDRESS_URL);

            }else{
                jsonObject.put("shoppingcart_id", shopingcartId);
                jsonObject.put("customerId", customer_id);
                jsonObject.put("company", "NatureSouq");
                jsonObject.put("street", Street_Add);
                jsonObject.put("city", CITY);
                jsonObject.put("region", STATE);
                jsonObject.put("region_id", "2");
                jsonObject.put("postcode", ZIP_CODE);
                jsonObject.put("country_id", country_id);
                jsonObject.put("telephone", PHONE_NUMBER);
                jsonObject.put("apikey", "naturesouq#123@apikey");

                new ChangeAddTask(jsonObject, "addNewAddress").execute(CHANGE_ADDRESS_URL);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    }

    public class AddNewTask extends AsyncTask<String , Void , String>{

        JSONObject jobj ;

        AddNewTask(JSONObject jobj){
            this.jobj = jobj ;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params) {
            ConnectAsynchronously.connectAsynchronously(params[0],jobj);
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public void updateAddress(){

        if (!TextUtils.isEmpty(object.getStreet())) {
            street.setText(object.getStreet());
        } else {
            street.setText("");
        }
        if (!TextUtils.isEmpty(object.getCity())) {
            city.setText(object.getCity());
        } else {
            city.setText("");
        }
        if (!TextUtils.isEmpty(object.getPostcode())) {
            zipcode.setText(object.getPostcode());
        } else {
            zipcode.setText("");
        }
        if (!TextUtils.isEmpty(object.getState())) {
            state.setText(object.getState());
        } else {
            state.setText("");
        }
        if (!TextUtils.isEmpty(object.getCountry_id())) {
            int pos = 0 ;
            for(CountriesList countryItem : NatureSouqPrefrences.countriesList){
                pos++ ;
                if(countryItem.getCountryCode().equals(object.getCountry_id())){
                    country.setSelection(pos);
                }
            }
        } else {
            int pos = 0 ;
            for(CountriesList countryItem : NatureSouqPrefrences.countriesList){
                pos++ ;
                if(countryItem.getCountryName().equals("United Arab Emirates")){
                    country.setSelection(pos);
                }
            }
        }
        if (!TextUtils.isEmpty(object.getTelephone())) {
            phoneNumber.setText(object.getTelephone());
        } else {
            phoneNumber.setText("");
        }

        getActionBar().setTitle("Update Address");
        addAddress.setText("UPDATE ADDRESS");
    }

    public class ChangeAddTask extends AsyncTask<String, Void, String> {
        JSONObject obj;
        String taskIdentifier;

        //Show registration progress.
        public ChangeAddTask(JSONObject jobj, String taskIdentifier) {
            obj = jobj;
            this.taskIdentifier = taskIdentifier;
        }

        @Override
        protected String doInBackground(String... urls) {
            return ConnectAsynchronously.connectAsynchronously(urls[0], obj);
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonResponse = new JSONObject(result);
                String status = jsonResponse.getString("status");
                String message = jsonResponse.getString("message");

                if(status.equals("1")){

                    if (taskIdentifier.equals("updateAddress")){
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                        Intent updatedAddIntent = new Intent();
                        ChangeAddressListItem item = new ChangeAddressListItem(object.getAddress_id(), object.getFirstname(), object.getLastname(), object.getCompany(), CITY, country_id, STATE, ZIP_CODE, PHONE_NUMBER, Street_Add, object.getCustomer_id(), false);
                        updatedAddIntent.putExtra("updatedAddress",item);
                        setResult(UPDATED_ADD_INTENT_CODE ,updatedAddIntent);
                        finish();

                    }else if(taskIdentifier.equals("addNewAddress")){

                    AddressdataItem addressdataItem=null;
                    JSONObject object =jsonResponse.getJSONObject("data");
                    JSONArray  jsonArray= object.getJSONArray("address");

                    ArrayList<AddressdataItem> list=new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        address_id = jsonObject.getString("customer_address_id");

                        String city = jsonObject.getString("city");
                        String company = jsonObject.getString("company");
                        String country_id = jsonObject.getString("country_id");

                        String countryName = "";
                        for(CountriesList listItem : NatureSouqPrefrences.countriesList) {
                            if (listItem.getCountryCode().equals(country_id)) {
                                countryName = listItem.getCountryName();
                                break;
                            }
                        }

                        String postcode = jsonObject.getString("postcode");
                        String region = jsonObject.getString("region");
                        String region_id = jsonObject.getString("region_id");
                        String street = jsonObject.getString("street");
                        String telephone = jsonObject.getString("telephone");

                        String totalAmount=object.getString("subTotalAmount");
                        String grandTotal=object.getString("grandTotalAmount");
                        String shippingAmount=object.getString("shipingAmount");
                        NatureSouqPrefrences.setAddressId("address_id",AddNewAddress.this, address_id);
                        NatureSouqPrefrences.setUserContactNo(AddNewAddress.this,telephone);

                        //String Address_street = street+", "+city+"\n"+region+", "+country_id+", "+postcode+"\n"+telephone;
                        addressdataItem=new AddressdataItem(address_id,street,city,postcode,region,countryName, telephone,totalAmount,shippingAmount,grandTotal);
                        list.add(addressdataItem);

                    }

                        //Save last Address to prefs .
                        AddressdataItem addressItem = list.get(list.size()-1);
                        String streetAdd =  addressItem.getStreet() ;
                        String City =  addressItem.getCity();
                        String Postcode =  addressItem.getZipCode();
                        String State =  addressItem.getState() ;
                        String Country =  addressItem.getCountry();
                        String PhoneNumber =  addressItem.getPhoneNumber();
                        String lastAdd = streetAdd + "\n" + City + ", " + State + "\n" + Country + ", "+ Postcode + "\n" + PhoneNumber ;
                        NatureSouqPrefrences.setAddress(getApplicationContext(), lastAdd);


                        NatureSouqPrefrences.setLoginCartList(AddNewAddress.this, 1);

                        Intent intent1=getIntent();
                        value=intent1.getStringExtra("OtherToChangeAddress");
                        if(!TextUtils.isEmpty(value)){
                            if (value.equalsIgnoreCase("fromCheckOut")){

                                Intent intent=new Intent(AddNewAddress.this,CheckOut.class);
                                intent.putExtra("AllData", addressdataItem);
                                intent.putExtra("from", "AddNewAddress");
                                startActivity(intent);
                                if(CheckOut.Instance()!=null)
                                CheckOut.Instance().finish();
                                if(ChangeAddress.Instance()!=null)
                                ChangeAddress.Instance().finish();
                                finish();
                            }else if (value.equalsIgnoreCase("fromAccountFragment")){
                                MainActivity.getInstance().displayView(1, 1, "", "My Account");
                                Intent forAccount = new Intent();
                                forAccount.putExtra("AllData", addressdataItem);
                                forAccount.putExtra("PHONE_NUMBER",PhoneNumber);
                                forAccount.putExtra("from", "AddNewAddress");
                                setResult(16, forAccount);
                                finish();
                            }
                        }else{
                            Intent intent=new Intent(AddNewAddress.this,CheckOut.class);
                            intent.putExtra("AllData",addressdataItem);
                            intent.putExtra("from", "AddNewAddress");
                            startActivity(intent);
                            if(CheckOut.Instance()!=null)
                                CheckOut.Instance().finish();
                            if(ChangeAddress.Instance()!=null)
                            ChangeAddress.Instance().finish();
                            finish();
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                }


            } catch (Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            progressBar.setVisibility(View.INVISIBLE);
            super.onPostExecute(result);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            String dataValue=data.getStringExtra("status");
            if(!TextUtils.isEmpty(dataValue)){

                if(dataValue.equalsIgnoreCase("success")){
                    Intent value=new Intent();
                    value.putExtra("status","success");
                    setResult(1,value);
                    finish();
                }
            }

        }
    }
}
