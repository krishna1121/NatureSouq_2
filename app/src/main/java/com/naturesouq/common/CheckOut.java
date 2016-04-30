package com.naturesouq.common;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.naturesouq.R;
import com.naturesouq.model.AddressdataItem;
import com.op.android.activities.OPActivity;
import com.op.android.card.OPCardListItem;
import com.op.android.card.OPCredentials;
import com.op.android.card.OPPayData;
import com.op.android.card.OPPayType;
import com.op.android.net.OPErrorAction;
import com.op.android.net.OPServerResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SI_Android_Binit on 10/19/2015.
 */

public class CheckOut extends OPActivity {

    static enum backend {TEST, PRODUCTION};
    private static final String CHECKOUT_CURRENT_ADDRESS_URL = Utility.baseURL + "cartaddress.php";
    private static final String CHECKOUT_DEFAULTADDRESS = Utility.baseURL + "selectAddress.php";
    private static final String ORDERID_URL = Utility.baseURL + "order.php";
    private static final String COD_URL = Utility.baseURL + "cod_order.php";
    private static final String UPDATE_CART_URL = Utility.baseURL + "updateCart.php";
    private static final String generateOrderURL = Utility.baseURL + "orderstatus.php";
    AddressdataItem objectAddress;
    TextView billingAddress, subtotalPrice, shippingPrice, totalPrice;
    ProgressBar progressBar;
    Button changeAddresButton, onlinePayment;
    static Activity activity;
    String orderId, merchantId;
    String price = "";
    String shoppingCartId = "";
    String value, address, addressID;
    Button cashOnDelvry;
    CheckOutAddressTask checkOutAddressTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.check_out);

        activity = this;
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle("Check Out");

        billingAddress = (TextView) findViewById(R.id.shipping_address);
        subtotalPrice = (TextView) findViewById(R.id.subtotal_Price);
        shippingPrice = (TextView) findViewById(R.id.shipping_Price);
        totalPrice = (TextView) findViewById(R.id.total_Price);
        changeAddresButton = (Button) findViewById(R.id.changeAddresButton);
        cashOnDelvry = (Button) findViewById(R.id.CASH_ON_DELIVERY);
        onlinePayment = (Button) findViewById(R.id.onlinePayment);

        boolean networkStatus = new DialogBox(this).checkInternetConnection();

        if (networkStatus) {
            try {
                shoppingCartId = NatureSouqPrefrences.getShoppingcartId(CheckOut.this);

                String PRODUCT_ID = "";
                String PRODUCT_QYT = "";
                JSONArray jsonArrayID = new JSONArray();
                JSONArray jsonArrayQty = new JSONArray();

                for (int i = 0; i < Cart.len; i++) {
                    jsonArrayID.put(Cart.productID[i]);
                    jsonArrayQty.put(Cart.productQuantity[i]);
                }

                String ProductID = jsonArrayID.toString();
                String ProductQTY = jsonArrayQty.toString();
                PRODUCT_ID = ProductID.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", "");              //remove all spaces
                PRODUCT_QYT = ProductQTY.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", "");

                JSONObject jsonObjectUpdateCart = new JSONObject();
                jsonObjectUpdateCart.put("shoppingcart_id", shoppingCartId);
                jsonObjectUpdateCart.put("product_id", PRODUCT_ID);
                jsonObjectUpdateCart.put("qty", PRODUCT_QYT);
                jsonObjectUpdateCart.put("apikey", "naturesouq#123@apikey");

                checkOutAddressTask = new CheckOutAddressTask(jsonObjectUpdateCart, "UPDATE_CART");
                checkOutAddressTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, UPDATE_CART_URL);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public class CheckOutAddressTask extends AsyncTask<String, Void, String> {
        JSONObject obj;
        String value1;

        //Show registration progress.
        public CheckOutAddressTask(JSONObject jobj, String value1) {
            obj = jobj;
            this.value1 = value1;
        }

        @Override
        protected String doInBackground(String... urls) {
            return ConnectAsynchronously.connectAsynchronously(urls[0], obj);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

            //Disable CashOn Delivery and OnLinePaymeht here .

        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonResponse = new JSONObject(result);
                String status = jsonResponse.getString("status");
                String message = jsonResponse.getString("message");
                progressBar.setVisibility(View.GONE);

                if (status.equals("1")) {

                    if (!TextUtils.isEmpty(value1)) {
                        if (value1.equalsIgnoreCase("ORDER")) {
                            JSONObject object = jsonResponse.getJSONObject("data");
                            object.getString("marchant_id");
                            orderId = object.getString("order_id");

                            //Now Order is success so Start Payment Activity .
                            OPCredentials opCredentials = new OPCredentials();
                            opCredentials.initOPParams(Utility.TEST_PSPID, Utility.TEST_USER_ID, Utility.TEST_PASSWORD, Utility.TEST_SHAIN_PASSPHRASE);
                            //opCredentials.initOPParams(Utility.LIVE_PSPID, Utility.LIVE_USER_NAME, Utility.LIVE_PASSWORD, Utility.LIVE_SHAIN_PASSPHRASE);
                            OPPayData payData = new OPPayData();
                            payData.setPayType(OPPayType.NewPayment);
                            if (!TextUtils.isEmpty(orderId))
                                payData.setOrderId(orderId);
                            // the merchant's order

                            if (!TextUtils.isEmpty(totalPrice.getText())) {
                                price = (String) totalPrice.getText();
                                int priceToPassToPayfort = Integer.parseInt(price) * 100;
                                payData.setAmount(priceToPassToPayfort);
                            }

                            payData.setCurrency("AED");

                            ArrayList<OPCardListItem> paymethods = new ArrayList<OPCardListItem>();
                            paymethods.add(OPCardListItem.createAmericanExpress());
                            paymethods.add(OPCardListItem.createVisaCard());
                            paymethods.add(OPCardListItem.createDinersClub());
                            paymethods.add(OPCardListItem.createMasterCard());
                            paymethods.add(OPCardListItem.createMaestro());
                            paymethods.add(OPCardListItem.createBcmc());
                            paymethods.add(OPCardListItem.createDirectdebitsAt());
                            //paymethods.add(OPCardListItem.createDirectdebitsNl());
                            paymethods.add(OPCardListItem.createPostFinance());
                            paymethods.add(OPCardListItem.createDirectdebitsDe());
                            paymethods.add(OPCardListItem.createJcb());
                            //sendNewRequest(payData, opCredentials, paymethods, Backend.PRODUCTION);
                            sendNewRequest(payData, opCredentials, paymethods, Backend.TEST);

                        }else if(value1.equals("COD")){
                            JSONObject object = jsonResponse.getJSONObject("data");
                            object.getString("marchant_id");
                            String orderIdCod = object.getString("order_id");

                            if (!TextUtils.isEmpty(orderIdCod)) {

                                //Make shopping cart id empty .
                                NatureSouqPrefrences.setShoppingcartId(CheckOut.this, "");
                                //Make cart counter empty .
                                NatureSouqPrefrences.setCartCounter(CheckOut.this, "");

                                Intent cashOnDelIntent = new Intent(CheckOut.this, OrderStatus.class);
                                cashOnDelIntent.putExtra("orderId", orderIdCod);
                                startActivityForResult(cashOnDelIntent, 9);
                                //startActivity(cashOnDelIntent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Comminication failed with Server, Please do checkout again !", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if (value1.equalsIgnoreCase("OrderSuccess")) {
                            try {
                                //progressBar.setVisibility(View.INVISIBLE);

                                NatureSouqPrefrences.setShoppingcartId(CheckOut.this, "");
                                NatureSouqPrefrences.setCartCounter(CheckOut.this, "");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(CheckOut.this, OrderStatus.class);
                                i.putExtra("orderId", orderId);
                                startActivityForResult(i, 9);
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                                //Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
                            }
                        } else if (value1.equalsIgnoreCase("UPDATE_CART")) {

                            Intent intent = getIntent();
                            value = intent.getStringExtra("from");
                            String customerId = NatureSouqPrefrences.getCustomer_id(CheckOut.this);

                            if (value.equalsIgnoreCase("AddNewAddress")) {

                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("shoppingcart_id", shoppingCartId);
                                jsonObject.put("customer_id", customerId);
                                jsonObject.put("apikey", "naturesouq#123@apikey");
                                changeAddresButton.setEnabled(false);
                                onlinePayment.setEnabled(false);
                                cashOnDelvry.setEnabled(false);

                                checkOutAddressTask = new CheckOutAddressTask(jsonObject, "");
                                checkOutAddressTask.execute(CHECKOUT_CURRENT_ADDRESS_URL);

                            } else if (value.equalsIgnoreCase("cart")) {

                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("shoppingcart_id", shoppingCartId);
                                jsonObject.put("customer_id", customerId);
                                jsonObject.put("apikey", "naturesouq#123@apikey");
                                changeAddresButton.setEnabled(false);
                                onlinePayment.setEnabled(false);
                                cashOnDelvry.setEnabled(false);

                                checkOutAddressTask = new CheckOutAddressTask(jsonObject, "");
                                checkOutAddressTask.execute(CHECKOUT_CURRENT_ADDRESS_URL);

                            } else if (value.equalsIgnoreCase("ChangeAddress")) {
                                addressID = intent.getStringExtra("AddressID");

                                NatureSouqPrefrences.setAddressId(addressID, CheckOut.this, addressID);

                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("shoppingcart_id", shoppingCartId);
                                jsonObject.put("customer_id", customerId);
                                jsonObject.put("addressId", addressID);
                                jsonObject.put("apikey", "naturesouq#123@apikey");
                                changeAddresButton.setEnabled(false);
                                onlinePayment.setEnabled(false);
                                cashOnDelvry.setEnabled(false);
                                checkOutAddressTask = new CheckOutAddressTask(jsonObject, "");
                                checkOutAddressTask.execute(CHECKOUT_DEFAULTADDRESS);

                            } else if (value.equalsIgnoreCase("Login")) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("shoppingcart_id", shoppingCartId);
                                jsonObject.put("customer_id", customerId);
                                jsonObject.put("apikey", "naturesouq#123@apikey");
                                changeAddresButton.setEnabled(false);
                                onlinePayment.setEnabled(false);
                                cashOnDelvry.setEnabled(false);
                                checkOutAddressTask = new CheckOutAddressTask(jsonObject, "");
                                checkOutAddressTask.execute(CHECKOUT_CURRENT_ADDRESS_URL);
                            }
                            /*JSONObject jsonObject = new JSONObject();
                            jsonObject.put("apikey", "naturesouq#123@apikey");
                            jsonObject.put("shoppingcart_id", shoppingCartId);
                            checkOutAddressTask = new CheckOutAddressTask(jsonObject, "ORDER");
                            checkOutAddressTask.execute(ORDERID_URL);*/
                        }
                    } else {
                        JSONObject object = jsonResponse.getJSONObject("data");
                        JSONArray jsonArray = object.getJSONArray("address");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            addressID = jsonObject.getString("customer_address_id");
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
                            //String region_id = jsonObject.getString("region_id");
                            String street = jsonObject.getString("street");
                            String telephone = jsonObject.getString("telephone");

                            String totalAmount = object.getString("subTotalAmount");
                            String grandTotal = object.getString("grandTotalAmount");
                            String shippingAmount = object.getString("shipingAmount");

                            String Address_street = street + ", " + city + "\n" + region + ", " + countryName + ", " + postcode + "\n" + telephone;

                            billingAddress.setText(Address_street);
                            subtotalPrice.setText(totalAmount);
                            shippingPrice.setText(shippingAmount);

                            //String shippingCharge = NatureSouqPrefrences.getShippingCharge(getApplicationContext());
                            //shippingPrice.setText(shippingCharge);

                            totalPrice.setText(grandTotal);
                            changeAddresButton.setEnabled(true);
                            onlinePayment.setEnabled(true);
                            cashOnDelvry.setEnabled(true);

                            NatureSouqPrefrences.setAddressId(addressID, CheckOut.this, addressID);

                        }
                    }
                } else if (status.equals("2")) {
                    //progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    //progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                //progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            super.onPostExecute(result);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_checkout, menu);
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

    public void onChangeAddress(View view) {
        Intent newAddressIntent = new Intent(CheckOut.this, ChangeAddress.class);
        newAddressIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        newAddressIntent.putExtra("OtherToChangeAddress", "fromCheckOut");
        startActivityForResult(newAddressIntent, 19);
    }

    // Do cash on delivery .
    public void onCashDelivery(View v) {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("apikey", "naturesouq#123@apikey");
            jsonObject.put("shoppingcart_id", shoppingCartId);
            checkOutAddressTask = new CheckOutAddressTask(jsonObject, "COD");
            checkOutAddressTask.execute(COD_URL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onCompleteOrder(View view) {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("apikey", "naturesouq#123@apikey");
            jsonObject.put("shoppingcart_id", shoppingCartId);
            checkOutAddressTask = new CheckOutAddressTask(jsonObject, "ORDER");
            checkOutAddressTask.execute(ORDERID_URL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResultSuccess(OPServerResponse opServerResponse) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("payment_status", "1");
            jsonObject.put("order_Id", opServerResponse.getOrderId());
            jsonObject.put("shoppingcart_id", shoppingCartId);
            jsonObject.put("apikey", "naturesouq#123@apikey");
            checkOutAddressTask = new CheckOutAddressTask(jsonObject, "OrderSuccess");
            checkOutAddressTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, generateOrderURL);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResultError(OPPayType opPayType, OPErrorAction opErrorAction) {
        Toast.makeText(getApplicationContext(), opErrorAction.getErrorDescription(getApplicationContext()), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResultCancel() {
        Toast.makeText(getApplicationContext(), " Result canceled ", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {

            if (requestCode == 19 && resultCode == 17) {
                value = data.getStringExtra("lastAdd");
                billingAddress.setText(value);

            } else {
                String dataValue = data.getStringExtra("status");
                if (!TextUtils.isEmpty(dataValue)) {
                    if (dataValue.equalsIgnoreCase("success")) {
                        Intent value = new Intent();
                        value.putExtra("status", "success");
                        setResult(1, value);
                        finish();
                    }
                }
            }
        }
    }

    public static Activity Instance() {

        return activity;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
