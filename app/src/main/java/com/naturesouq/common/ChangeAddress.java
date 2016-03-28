package com.naturesouq.common;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.naturesouq.R;
import com.naturesouq.adapter.ChangeAddressAdapter;
import com.naturesouq.model.AddressdataItem;
import com.naturesouq.model.ChangeAddressListItem;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SI_Android_Binit on 12/28/2015.
 */

public class ChangeAddress extends Activity implements ChangeAddressAdapter.ViewClickListener {

    private static final String ADDRESS_LIST_URL = Utility.baseURL + "addresslist.php";
    private static final String DELETE_ADDRESS_LIST_URL = Utility.baseURL + "deleteAddress.php";
    private static final int TAKEEDITEDADDBACK = 200 ;
    RecyclerView myAddress;
    Button addNewAddress;
    LinearLayoutManager layoutManager;
    String customer_id, address_id;
    List<ChangeAddressListItem> rowItems;
    ChangeAddressListItem item;
    ChangeAddressAdapter changeAddressAdapter;
    ProgressBar bar;
    int pos;
    String value, value1;
    AddressdataItem objectAddress;
    ImageView addressplaceholder;
    static Activity activity;
     int editedPosition ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_address);
        activity = this;

        bar = (ProgressBar) findViewById(R.id.progressBar);
        addressplaceholder = (ImageView) findViewById(R.id.addressplaceholder);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle("Change Address");

        addNewAddress = (Button) findViewById(R.id.addNewAddress);
        myAddress = (RecyclerView) findViewById(R.id.my_address_recycler);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myAddress.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        value = intent.getStringExtra("OtherToChangeAddress");

        // Typeface face1= Typeface.createFromAsset(getAssets(), "font/Lato_Regular.ttf");
        Typeface face2 = Typeface.createFromAsset(getAssets(), "font/Lato_Black.ttf");
        addNewAddress.setTypeface(face2);

        boolean networkStatus = new DialogBox(this).checkInternetConnection();
        rowItems = new ArrayList<ChangeAddressListItem>();
        if (networkStatus) {
            try {
                customer_id = NatureSouqPrefrences.getCustomer_id(this);

                JSONObject jobj = new JSONObject();
                jobj.put("customer_id", customer_id);
                jobj.put("apikey", "naturesouq#123@apikey");

                new ChangeAddressTask(jobj, "setAddressList").execute(ADDRESS_LIST_URL);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
        public void onItemClicked(int position) {
            //displayFragment(position);

            ChangeAddressListItem feedItem = changeAddressAdapter.getAddressListItems().get(position);

            if (value.equalsIgnoreCase("fromAccountFragment")){
                Intent setAccountAddress = new Intent();
                setAccountAddress.putExtra("AllData", feedItem);
                setAccountAddress.putExtra("from", "ChangeAddress");
                setAccountAddress.putExtra("AddressID", feedItem.getAddress_id());
                setResult(16, setAccountAddress);
                finish();
            }else if(value.equalsIgnoreCase("fromCheckOut")){
                Intent setAddress = new Intent(ChangeAddress.this, CheckOut.class);
                setAddress.putExtra("from", "ChangeAddress");
                setAddress.putExtra("AddressID", feedItem.getAddress_id());
                startActivity(setAddress);
                finish();
                CheckOut.Instance().finish();

            }

        }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


  /*  @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }*/

    public void onAddNewAddress(View view) {
        if (value != null) {
            if (value.equals("fromAccountFragment")) {
                Intent addNewIntent = new Intent(ChangeAddress.this, AddNewAddress.class);
                addNewIntent.putExtra("OtherToChangeAddress", "fromAccountFragment");
                addNewIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(addNewIntent, 16);
            } else if (value.equals("fromCheckOut")) {
                Intent addNewIntent = new Intent(ChangeAddress.this, AddNewAddress.class);
                addNewIntent.putExtra("OtherToChangeAddress", "fromCheckOut");
                addNewIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(addNewIntent);
            }
        }
    }

    @Override
    public void onSettingClicked(int position, View view) {
    }


    @Override
    public void onEditClicked(int position) {
        editedPosition  = position ;
        ChangeAddressListItem item = changeAddressAdapter.getAddressListItems().get(position);
        Intent updateAddress = new Intent(ChangeAddress.this, AddNewAddress.class);
        updateAddress.putExtra("AddressForUpdate", item);
        //updateAddress.putExtra("ItemPosition",position);
        updateAddress.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(updateAddress, TAKEEDITEDADDBACK);
    }

    @Override
    public void onDeleteClicked(int position) {
        pos = position;
        ChangeAddressListItem item = changeAddressAdapter.getAddressListItems().get(position);
        try {

            JSONObject jObj = new JSONObject();
            jObj.put("customer_id", customer_id);
            jObj.put("addressId", item.getAddress_id());
            jObj.put("apikey", "naturesouq#123@apikey");

            new ChangeAddressTask(jObj, "deleteAddressFromList").execute(DELETE_ADDRESS_LIST_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ChangeAddressTask extends AsyncTask<String, Void, String> {
        JSONObject obj;
        String taskIdentifier;

        //Show registration progress.
        public ChangeAddressTask(JSONObject jobj, String taskIdentifier) {
            obj = jobj;
            this.taskIdentifier = taskIdentifier;
        }

        @Override
        protected String doInBackground(String... urls) {
            return ConnectAsynchronously.connectAsynchronously(urls[0], obj);
        }

        @Override
        protected void onPreExecute() {
            try {
                if (bar != null) {
                    bar.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonResponse = new JSONObject(result);
                String status = jsonResponse.getString("status");
                String message = jsonResponse.getString("message");

                switch (status) {
                    case "0":
                        //Toast.makeText(getActivity(), "Webservice failed with status 0 !", Toast.LENGTH_SHORT).show();
                        break;
                    case "1":
                        //rowItems = new ArrayList<ChangeAddressListItem>();
                        if (taskIdentifier.equals("setAddressList")) {
                            JSONArray jsonArray = jsonResponse.getJSONArray("data");

                            if (jsonArray.length() > 0) {
                                //Get data from MyFavorite web service url .
                                for (int itemCount = 0; itemCount < jsonArray.length(); itemCount++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(itemCount);

                                    address_id = jsonObject.getString("address_id");
                                    String firstname = jsonObject.getString("firstname");
                                    String lastname = jsonObject.getString("lastname");
                                    String company = jsonObject.getString("company");
                                    String city = jsonObject.getString("city");
                                    String country_id = jsonObject.getString("country_id");
                                    String state = jsonObject.getString("state");
                                    String postcode = jsonObject.getString("postcode");
                                    String telephone = jsonObject.getString("telephone");
                                    String street = jsonObject.getString("street");
                                    String customer_id = jsonObject.getString("customer_id");

                                    ChangeAddressListItem item = new ChangeAddressListItem(address_id, firstname, lastname, company, city, country_id, state, postcode, telephone, street, customer_id, false);
                                    rowItems.add(item);
                                }

                                changeAddressAdapter = new ChangeAddressAdapter(ChangeAddress.this.getApplicationContext(), rowItems);
                                changeAddressAdapter.setViewClickListener(ChangeAddress.this);
                                myAddress.setAdapter(changeAddressAdapter);

                                //Save last Address to prefs .
                                ChangeAddressListItem addressItem = rowItems.get(rowItems.size() - 1);
                                String streetAdd = addressItem.getStreet();
                                String City = addressItem.getCity();
                                String Postcode = addressItem.getPostcode();
                                String State = addressItem.getState();
                                String Country_id = addressItem.getCountry_id();
                                String Telephone = addressItem.getTelephone();

                                String lastAdd = streetAdd + "\n" + City + ", " + State + "\n" + Country_id + ", " + Postcode + "\n" + Telephone;
                                NatureSouqPrefrences.setAddress(getApplicationContext(), lastAdd);

                            } else {
                                addressplaceholder.setVisibility(View.VISIBLE);
                            }

                        } else if (taskIdentifier.equals("deleteAddressFromList")) {

                            List<ChangeAddressListItem> address =   changeAddressAdapter.getAddressListItems() ;

                            //Delete Adress Id from prefs .
                            ChangeAddressListItem listItem = address.get(pos);
                            String addressId = listItem.getAddress_id();
                            SharedPreferences prefs = NatureSouqPrefrences.getSharedPreferences(getApplicationContext());
                            //listItem.setStateOfActivite(false);
                            SharedPreferences.Editor editr = prefs.edit();
                            editr.remove(addressId);

                            address.remove(pos);
                            //Update RecyclerView .
                            myAddress.removeViewAt(pos);
                            changeAddressAdapter.notifyItemRemoved(pos);
                            changeAddressAdapter.notifyItemRangeChanged(pos, address.size());


                            //Save Current Address to Prefs .
                            if (address.size() > 0) {
                                ChangeAddressListItem addressItem = address.get(address.size() - 1);
                                String streetAdd = addressItem.getStreet();
                                String City = addressItem.getCity();
                                String Postcode = addressItem.getPostcode();
                                String State = addressItem.getState();
                                String Country_id = addressItem.getCountry_id();
                                String Telephone = addressItem.getTelephone();

                                String lastAdd = streetAdd + "\n" + City + ", " + State + "\n" + Country_id + ", " + Postcode + "\n" + Telephone;

                                NatureSouqPrefrences.setAddress(getApplicationContext(), lastAdd);

                                Intent intent1 = getIntent();
                                value = intent1.getStringExtra("OtherToChangeAddress");
                                if (value.equalsIgnoreCase("fromCheckOut")) {
                                    Intent lastAddIntent = new Intent();
                                    lastAddIntent.putExtra("lastAdd", lastAdd);
                                    setResult(17, lastAddIntent);

                                }else if (value.equalsIgnoreCase("fromAccountFragment")) {
                                    Intent lastAddIntent = new Intent();
                                    lastAddIntent.putExtra("lastAdd", lastAdd);
                                    lastAddIntent.putExtra("Address_id", addressItem.getAddress_id());
                                    setResult(17, lastAddIntent);
                                }
                            }
                            //Show PlaceHolder if all adess has been removed .
                            if (rowItems.size() == 0) {
                                addressplaceholder.setVisibility(View.VISIBLE);
                                NatureSouqPrefrences.setLoginCartList(ChangeAddress.this, 0);
                            }
                        }

                        break;
                    case "2":
                        addressplaceholder.setVisibility(View.VISIBLE);
                        Log.d("ChangeAdress ::", "Address not exist in list");
                        break;
                    default:

                }
                if (bar != null) {
                    bar.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                if (bar != null) {
                    bar.setVisibility(View.GONE);
                }
                e.printStackTrace();
            }
            super.onPostExecute(result);
        }
    }

    public void onSubmit(View view) {
        //do some things
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    public static Activity Instance() {

        return activity;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == 12) {
                try {
                    customer_id = NatureSouqPrefrences.getCustomer_id(this);

                    JSONObject jobj = new JSONObject();
                    jobj.put("customer_id", customer_id);
                    jobj.put("apikey", "naturesouq#123@apikey");

                    new ChangeAddressTask(jobj, "setAddressList").execute(ADDRESS_LIST_URL);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (resultCode == 16) {
                value1 = data.getStringExtra("from");
                if (value1.equalsIgnoreCase("AddNewAddress")) {
                    objectAddress = (AddressdataItem) data.getParcelableExtra("AllData");
                    if (objectAddress != null) {
                        Intent i = new Intent();
                        i.putExtra("AllData", objectAddress);
                        i.putExtra("from", "AddNewAddress");
                        setResult(16, i);
                        finish();
                    }
                }

            }else if(requestCode == TAKEEDITEDADDBACK && resultCode == 201){
                ChangeAddressListItem updatedAddItem  =  data.getParcelableExtra("updatedAddress");
                changeAddressAdapter.getAddressListItems().remove(editedPosition);
                changeAddressAdapter.getAddressListItems().add(editedPosition, updatedAddItem);
                changeAddressAdapter.notifyDataSetChanged();
            }
        }
    }
}
