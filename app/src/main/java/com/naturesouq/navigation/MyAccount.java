package com.naturesouq.navigation;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.naturesouq.R;
import com.naturesouq.adapter.MyAccountOrderAdapter;
import com.naturesouq.common.ChangeAddress;
import com.naturesouq.common.ChangePassword;
import com.naturesouq.common.ConnectAsynchronously;
import com.naturesouq.common.CountriesList;
import com.naturesouq.common.DialogBox;
import com.naturesouq.common.Login;
import com.naturesouq.common.MainActivity;
import com.naturesouq.common.MyFavorite;
import com.naturesouq.common.MyOrders;
import com.naturesouq.common.MyOrdersProduct;
import com.naturesouq.common.NatureSouqPrefrences;
import com.naturesouq.common.Utility;
import com.naturesouq.common.VerticalSpaceItemDecoration;
import com.naturesouq.model.AddressdataItem;
import com.naturesouq.model.ChangeAddressListItem;
import com.naturesouq.model.HomeDataProvider;
import com.naturesouq.model.MyAccountOrderItems;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by SI-Andriod on 15-Oct-15.
 */

public class MyAccount extends Fragment implements MyAccountOrderAdapter.ViewClickListener {

    List<MyAccountOrderItems> myAccountOrderItemsList ;
    private static final String MY_ORDER_URL=Utility.baseURL+"myorder.php";
    private static final String UPDATE_INFO_URL=Utility.baseURL+"updateCustomerInfo.php";
    private static final String CHECKOUT_DEFAULTADDRESS=Utility.baseURL+"selectAddress.php";
    ProgressBar progressBar;
    static MyAccount instance ;
    String shoppingcart_id = "", product_id = "", customer_id, address_id;
    int cartitemcount ;
    RecyclerView myList;
    private MyAccountOrderAdapter adapter;
    LinearLayoutManager layoutManager;
    View rootview;
    Button updateInformation, changePassword, changeAddress, viewAllOrders, myReview, myFavorite;
    TextView addressLBL, shippingAddress, myOrderLBL;
    EditText firstName, lastName, contact;
    MaterialDialog mMaterialDialog;
    Activity activity;
    String UserFirstName , UserLastName , ContactNumber;
    boolean fbUserPrefs;
    String email,password,value, address;
    AddressdataItem objectAddress;
    ChangeAddressListItem objectAddress1;
    public static HomeDataProvider provider;
    JSONArray jsonArray;
    Button view_order , logout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fbUserPrefs = NatureSouqPrefrences.getFacebookUser(getActivity().getApplicationContext());
        email = NatureSouqPrefrences.getUserName(getActivity().getApplicationContext());
        password = NatureSouqPrefrences.getUserPass(getActivity().getApplicationContext());
        shoppingcart_id = NatureSouqPrefrences.getShoppingcartId(getActivity());



        if ((fbUserPrefs) || (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))){
            rootview = inflater.inflate(R.layout.myaccountfragment, container, false);

            view_order = (Button) rootview.findViewById(R.id.view_order);

            progressBar = (ProgressBar) rootview.findViewById(R.id.progressBar);

            activity = getActivity();
            instance = this;
            Bundle bundle = getArguments();
            // cartitemcount = bundle.getInt("cartitemcount", 0);

            updateInformation = (Button) rootview.findViewById(R.id.updateInfo);
            changePassword = (Button) rootview.findViewById(R.id.changePassword);
            changeAddress = (Button) rootview.findViewById(R.id.changeAddress);
            viewAllOrders = (Button) rootview.findViewById(R.id.view_order);
            myReview = (Button) rootview.findViewById(R.id.review_order);
            myFavorite = (Button) rootview.findViewById(R.id.my_fevorite);
            logout = (Button) rootview.findViewById(R.id.logout);

            //Logout existing user .
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog dialog = new Dialog(activity);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                    dialog.setContentView(R.layout.logout_confirm_dialog);

                    Button cancel = (Button) dialog.findViewById(R.id.cancel_dialog);
                    Button ok = (Button) dialog.findViewById(R.id.ok_dialog);

                    TextView dgTitle = (TextView) dialog.findViewById(R.id.dialog_title);
                    final TextView messageText = (TextView) dialog.findViewById(R.id.dialog_message);
                    dgTitle.setText(R.string.passconfirmation);
                    messageText.setText("Do you want to logout? ");
                    cancel.setText("Cancel");
                    ok.setText("Ok");

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
                            SharedPreferences.Editor edt = prefs.edit();
                            edt.clear();
                            edt.commit();

                            //Open sign in Activity
                            Intent login = new Intent(getActivity(), Login.class);
                            login.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            login.putExtra("ComingFrom", "MyAccount");
                            //login.putExtra("shoppingcart_id", "");
                            startActivityForResult(login, 15);

                            //Open Home Frament
                            MainActivity.getInstance().displayView(0, 0, "", "");

                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                }
            });

            addressLBL = (TextView)rootview.findViewById(R.id.add_lbl);
            shippingAddress = (TextView)rootview.findViewById(R.id.shipping_address);
            myOrderLBL = (TextView)rootview.findViewById(R.id.myorderTitle);

            firstName = (EditText) rootview.findViewById(R.id.firstname);
            lastName = (EditText) rootview.findViewById(R.id.lastname);
            contact = (EditText) rootview.findViewById(R.id.contact);

            Typeface face1= Typeface.createFromAsset(getActivity().getAssets(), "font/Lato_Regular.ttf");
            Typeface face2= Typeface.createFromAsset(getActivity().getAssets(), "font/Lato_Black.ttf");
            Typeface faceBold= Typeface.createFromAsset(getActivity().getAssets(), "font/Lato_Bold.ttf");
            changePassword.setTypeface(face2);
            changeAddress.setTypeface(face2);
            viewAllOrders.setTypeface(face2);
            myReview.setTypeface(face2);
            myFavorite.setTypeface(face2);
            shippingAddress.setTypeface(face1);
            firstName.setTypeface(face1);
            lastName.setTypeface(face1);
            contact.setTypeface(face1);
            addressLBL.setTypeface(faceBold);
            myOrderLBL.setTypeface(faceBold);

            myList = (RecyclerView) rootview.findViewById(R.id.my_order_recycler);
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            myList.setLayoutManager(layoutManager);
            myList.addItemDecoration(new VerticalSpaceItemDecoration(5));
            myAccountOrderItemsList = new ArrayList<MyAccountOrderItems>();

            customer_id = NatureSouqPrefrences.getCustomer_id(getActivity());
            UserFirstName = NatureSouqPrefrences.getUserFirstName(getActivity());
            UserLastName = NatureSouqPrefrences.getUserLastName(getActivity());
            ContactNumber = NatureSouqPrefrences.getUserContactNo(getActivity());
            if(!TextUtils.isEmpty(UserFirstName))
            firstName.setText(UserFirstName);
            if(!TextUtils.isEmpty(UserFirstName))
            lastName.setText(UserLastName);
            if(!TextUtils.isEmpty(ContactNumber)){
                if(ContactNumber.length()>=5){
                    contact.setText(ContactNumber);
                }
            }


            ScrollView scrollView =(ScrollView)rootview.findViewById(R.id.maccount);
            scrollView.smoothScrollBy(0, 0);

            if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                scrollView.setNestedScrollingEnabled(false);
            }


            boolean networkStatus = new DialogBox(getActivity()).checkInternetConnection();
            if (networkStatus) {

                address_id = NatureSouqPrefrences.getAddressId(getActivity());
                if(!TextUtils.isEmpty(address_id))
                selectAddress();
                else {
                    shippingAddress.setText("N/A");
                }


                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("customer_id", customer_id);
                    jsonObject.put("apikey", "naturesouq#123@apikey");

                    new MyAccountOrderTask(jsonObject, "setMyAccountOrder").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,MY_ORDER_URL);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (fbUserPrefs) {
                mMaterialDialog = new MaterialDialog(activity);
                mMaterialDialog.setTitle("Warning !");
                mMaterialDialog.setMessage("Currently you are login through facebook, so you are not allowed to change login password !");
                mMaterialDialog.setPositiveButton("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changePassword.setEnabled(false);
                        mMaterialDialog.dismiss();
                    }
                });
                mMaterialDialog.show();
            }

            updateInformation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserFirstName = firstName.getText().toString();
                    UserLastName = lastName.getText().toString();
                    ContactNumber=contact.getText().toString();
                    if(!Utility.isNotNull(UserFirstName)){
                        firstName.setError("First Name Field can't be empty!");
                        firstName.requestFocus();
                    }else if (!Utility.isNotNull(UserLastName)){
                        lastName.setError("Last Name Field can't be empty!");
                        lastName.requestFocus();
                    }else if(!Utility.isNotNull(ContactNumber)){

                        contact.setError("Contact number can't be empty!");
                        contact.requestFocus();
                    }else if(!Utility.validCellPhone(ContactNumber)) {

                        contact.setError("Invalid contact number!");
                        contact.requestFocus();
                    }else {
                        if (!TextUtils.isEmpty(UserFirstName) && !TextUtils.isEmpty(UserLastName) && !TextUtils.isEmpty(ContactNumber)){
                            try {
                                JSONObject jsonObject = new JSONObject();

                                jsonObject.put("customer_id", customer_id);
                                jsonObject.put("firstname", UserFirstName);
                                jsonObject.put("lastname", UserLastName);
                                jsonObject.put("phone", ContactNumber);
                                jsonObject.put("apikey", "naturesouq#123@apikey");

                                new MyAccountOrderTask(jsonObject, "setUpdateInfo").execute(UPDATE_INFO_URL);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
            changePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent changePassIntent = new Intent(getActivity(), ChangePassword.class);
                    changePassIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(changePassIntent);
                }
            });
            changeAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent changeAddIntent = new Intent(getActivity(), ChangeAddress.class);
                    changeAddIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    changeAddIntent.putExtra("OtherToChangeAddress", "fromAccountFragment");
                    startActivityForResult(changeAddIntent, 16);
                }
            });
            viewAllOrders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent allOrderIntent = new Intent(getActivity(), MyOrders.class);
                    provider=new HomeDataProvider();
                    provider.setOrderList(jsonArray);
                    allOrderIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(allOrderIntent);
                }
            });

            myFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myFavoriteIntent = new Intent(getActivity(), MyFavorite.class);
                    myFavoriteIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(myFavoriteIntent);
                }
            });

        }else {
           // if (TextUtils.isEmpty(shoppingcart_id)) {
                Intent login = new Intent(getActivity(), Login.class);
                login.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                login.putExtra("ComingFrom","MyAccount");
                //login.putExtra("shoppingcart_id", "");
                startActivityForResult(login, 15);
           // }
        }
        return rootview;
    }



    public void selectAddress(){

        JSONObject jsonObject=new JSONObject();
        try{
            jsonObject.put("shoppingcart_id",shoppingcart_id);
            jsonObject.put("customer_id",customer_id);
            jsonObject.put("addressId", address_id);
            jsonObject.put("apikey", "naturesouq#123@apikey");

            new MyAccountOrderTask(jsonObject,"selectedAddress").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,CHECKOUT_DEFAULTADDRESS);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onItemClicked(int position) {
        MyAccountOrderItems feedItem = adapter.getMyAccountOrderItems().get(position);
        Intent myAccountOrderPage = new Intent(getActivity(), MyOrdersProduct.class);
        myAccountOrderPage.putExtra("GridViewToDetail", "myAccountOrder");
        myAccountOrderPage.putExtra("feedItemMyAccountOrder", feedItem);
        myAccountOrderPage.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(myAccountOrderPage);
    }

    public class MyAccountOrderTask extends AsyncTask<String, Void, String> {
        JSONObject obj;
        String taskIdentifier;

        public MyAccountOrderTask(JSONObject jobj, String taskIdentifier) {
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
                if(progressBar != null){
                    progressBar.setVisibility(View.VISIBLE);
                }
                super.onPreExecute();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(), "Exception occur on Loading.. ", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonResponse = new JSONObject(result);
                String status = jsonResponse.getString("status");
                String message = jsonResponse.getString("message");
                if (status.equals("1")) {
                    if (taskIdentifier.equals("setMyAccountOrder")){
                        JSONObject jsonObject = jsonResponse.getJSONObject("data");
                        jsonArray = jsonObject.getJSONArray("orderlist");
                        if(jsonArray.length()>0){
                            for(int listoforder =(jsonArray.length()-1);listoforder<jsonArray.length();listoforder++){

                                JSONObject object = jsonArray.getJSONObject(listoforder);
                                String order_id=object.getString("order_id");
                                String order_date=object.getString("order_date");
                                String sub_total=object.getString("sub_total");
                                String order_status=object.getString("order_status");
                                String customer_email=object.getString("customer_email");
                                String noofitems = object.getString("noofitems");
                                String image_url= object.getString("image_url");

                                MyAccountOrderItems items = new MyAccountOrderItems(order_id,order_date,sub_total,order_status,customer_email,noofitems,image_url);
                                myAccountOrderItemsList.add(items);
                            }
                            adapter = new MyAccountOrderAdapter(myAccountOrderItemsList, getActivity());
                            adapter.setViewClickListener((MyAccountOrderAdapter.ViewClickListener) MyAccount.this);
                            myList.setAdapter(adapter);
                        }else{
                            view_order.setEnabled(false);
                            //myList.setPadding(10,0,10,0);
                            //myList.setBackground(getResources().getDrawable(R.drawable.addressplaceholder));
                        }
                    }else if (taskIdentifier.equals("setUpdateInfo")){
                        JSONObject jsonObject = jsonResponse.getJSONObject("data");
                        String firstname=jsonObject.getString("firstname");
                        String lastname=jsonObject.getString("lastname");
                        String telephone = jsonObject.getString("telephone");

                        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        NatureSouqPrefrences.setUserContactNo(getActivity(), telephone);

                        firstName.setText(firstname);
                        lastName.setText(lastname);
                        NatureSouqPrefrences.setUserFirstName(getActivity(),firstname);
                        NatureSouqPrefrences.setUserFirstName(getActivity(), lastname);

                        progressBar.setVisibility(View.INVISIBLE);
                        contact.setText(telephone);
                    }else if (taskIdentifier.equals("selectedAddress")){
                        JSONObject object =jsonResponse.getJSONObject("data");
                        JSONArray  jsonArray= object.getJSONArray("address");
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

                            String firstname = jsonObject.getString("firstname");
                            String lastname = jsonObject.getString("lastname");
                            String postcode = jsonObject.getString("postcode");
                            String region = jsonObject.getString("region");
                            String region_id = jsonObject.getString("region_id");
                            String street = jsonObject.getString("street");
                            String telephone = jsonObject.getString("telephone");

                            String totalAmount=object.getString("subTotalAmount");
                            String grandTotal=object.getString("grandTotalAmount");
                            String shippingAmount=object.getString("shipingAmount");

                            address = street+", "+city+"\n"+region+", "+countryName+", "+postcode+"\n"+telephone;
                            contact.setText(telephone);
                            shippingAddress.setText(address);
                            NatureSouqPrefrences.setAddressId("address_id",getActivity(), address_id);
                            if(progressBar != null){
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    }
                    if(TextUtils.isEmpty(address_id))
                        progressBar.setVisibility(View.INVISIBLE);

                } else {
                    if(progressBar != null){
                        progressBar.setVisibility(View.GONE);
                    }

                    Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                if(progressBar != null){
                    progressBar.setVisibility(View.GONE);
                }
                e.printStackTrace();
                //Toast.makeText(getActivity(), ""+e, Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(result);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 15 ){
            MainActivity.getInstance().displayView(0,-1,"","");
        }

        if(data!=null) {

            if(requestCode == 16 && resultCode == 17) {
                //String lastAdd  = value ;
                address = data.getStringExtra("lastAdd");
                address_id=data.getStringExtra("Address_id");
                shippingAddress.setText(address);
            }else{

                if (requestCode == 16) {
                    value = data.getStringExtra("from");
                    // Toast.makeText(getActivity().getApplicationContext(), "" + value, Toast.LENGTH_SHORT).show();
                    if(value.equalsIgnoreCase("AddNewAddress")){
                        objectAddress=(AddressdataItem)data.getParcelableExtra("AllData");
                        String phoneNumber=data.getStringExtra("PHONE_NUMBER");

                        address=objectAddress.getStreet()+","+ objectAddress.getCity()+"\n"+ objectAddress.getState()+","+objectAddress.getCountry()+","+objectAddress.getZipCode()+
                                "\n"+objectAddress.getPhoneNumber();

                        shippingAddress.setText(address);
                        contact.setText(phoneNumber);

                    }else if(value.equalsIgnoreCase("ChangeAddress")){
                        // Bundle data1 = getActivity().getIntent().getExtras();
                        objectAddress1=(ChangeAddressListItem)data.getParcelableExtra("AllData");
                        address_id = objectAddress1.getAddress_id();
                        address=objectAddress1.getStreet()+","+ objectAddress1.getCity()+"\n"+ objectAddress1.getState()+","+objectAddress1.getCountry_id()+","+objectAddress1.getPostcode()+
                                "\n"+objectAddress1.getTelephone();

                        shippingAddress.setText(address);
                    }
                    //NatureSouqPrefrences.setAddress(getActivity(), address);
                    NatureSouqPrefrences.setAddressId("address_id",getActivity(), address_id);
                }
            }


        }
    }
}
