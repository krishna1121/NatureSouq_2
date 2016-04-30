package com.naturesouq.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGImageView;
import com.caverock.androidsvg.SVGParseException;
import com.naturesouq.R;
import org.json.JSONArray;
import org.json.JSONObject;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SI_Android_Binit on 10/8/2015.
 */

public class OrderStatus extends Activity {

    TextView msg1;
    Button button;
    CashOnDeliveryTask cashOnDeliveryTask;
    private static final String ORDER_DETAIL_URL = "https://www.naturesouq.com/webservices/order_detail.php";
    TextView delivery_dateTxt ,price , orderDate ,orderemail ,item_noTxt ,orderId, customerName , grandtotalTxt , payment_methodTxt ,  customerContactNoTxt , customerEmailTxt , deliveryAddressTxt ;
    String orderIdVal ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash_on_delivery);

        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setHomeButtonEnabled(false);

        /*final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.holo_dark_green), PorterDuff.Mode.SRC_ATOP);
        getActionBar().setHomeAsUpIndicator(upArrow);*/
        //getActionBar().setTitle("Order Status");

        //msg1 = (TextView)findViewById(R.id.message_str);
        //Typeface face= Typeface.createFromAsset(getAssets(), "font/Lato_Regular.ttf");
        //msg1.setTypeface(face);

        button = (Button)findViewById(R.id.continuebtn);
        Typeface face1= Typeface.createFromAsset(getAssets(), "font/Lato_Black.ttf");
        button.setTypeface(face1);

        delivery_dateTxt = (TextView)findViewById(R.id.delivery_date);
        item_noTxt = (TextView)findViewById(R.id.item_no);
        grandtotalTxt = (TextView)findViewById(R.id.grandtotal);
        payment_methodTxt = (TextView)findViewById(R.id.payment_method);
        customerContactNoTxt = (TextView)findViewById(R.id.user_contactNumber);
        customerEmailTxt = (TextView)findViewById(R.id.user_email);
        deliveryAddressTxt = (TextView)findViewById(R.id.user_address);
        customerName = (TextView)findViewById(R.id.customerName);
        orderId = (TextView)findViewById(R.id.orderId);
        orderemail = (TextView)findViewById(R.id.orderemail);
        orderDate = (TextView)findViewById(R.id.orderDate);
        price = (TextView)findViewById(R.id.price);


        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setHomeButtonEnabled(true);

        orderIdVal = getIntent().getStringExtra("orderId");
        try {
            JSONObject jobj = new JSONObject();
            jobj.put("apikey", "naturesouq#123@apikey");
            jobj.put("order_id", orderIdVal);

            cashOnDeliveryTask = new CashOnDeliveryTask(jobj);
            cashOnDeliveryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ORDER_DETAIL_URL);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    private class CashOnDeliveryTask extends AsyncTask<String, Void, String> {
        JSONObject requestedObj;

        CashOnDeliveryTask(JSONObject requestedObj) {
            this.requestedObj = requestedObj;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return ConnectAsynchronously.connectAsynchronously(params[0], requestedObj);
        }

        @Override
        protected void onPostExecute(String s) {

            try{
                JSONObject response = new JSONObject(s);
                String message = response.getString("message");
                String status = response.getString("status");

                if(status.equals("1")){

                    JSONObject data = response.getJSONObject("data");
                    JSONArray customerdetails =  data.getJSONArray("customerdetails");
                    JSONArray orderlist = data.getJSONArray("orderlist");
                    JSONObject orderdata = data.getJSONObject("orderdata");

                    JSONObject customerdetailAt0 =  customerdetails.getJSONObject(0);
                    JSONObject orderlistAt0 =  orderlist.getJSONObject(0);
                    //Get and set details from customerdetails.
                    String email =  customerdetailAt0.getString("email");
                    String telephone = customerdetailAt0.getString("telephone");
                    String firstname = customerdetailAt0.getString("firstname");
                    String lastname = customerdetailAt0.getString("lastname");
                    String CustomerAddressId = customerdetailAt0.getString("CustomerAddressId");
                    String postcode = customerdetailAt0.getString("postcode");
                    String street = customerdetailAt0.getString("street");
                    String city = customerdetailAt0.getString("city");
                    String country_id = customerdetailAt0.getString("country_id");
                    String address_type = customerdetailAt0.getString("address_type");
                    String state = customerdetailAt0.getString("state");

                    String deliveryAdd = street + ","+city + ","+country_id ;


                    customerContactNoTxt.setText(telephone);
                    if(email.equals("null")){
                        customerEmailTxt.setText("N/A");
                        orderemail.setText("N/A");
                    }else{
                        customerEmailTxt.setText(email);
                        orderemail.setText(email);
                    }
                    deliveryAddressTxt.setText(deliveryAdd);
                    customerName.setText(firstname + " " + lastname);
                    orderId.setText(orderIdVal);

                    //Get Order List .
                    String product_name = orderlistAt0.getString("product_name");
                    String image_url = orderlistAt0.getString("image_url");

                    //Get and Set Order Data .
                    String grandtotal = orderdata.getString("grandtotal");
                    String payment_method = orderdata.getString("payment_method");
                    String delivery_date = orderdata.getString("delivery_date");
                    String order_date = orderdata.getString("order_date");

                    orderDate.setText(order_date);
                    grandtotalTxt.setText(grandtotal);
                    payment_methodTxt.setText(payment_method);
                    delivery_dateTxt.setText(delivery_date);
                    price.setText("AED "+grandtotal);

                }else{
                    Toast.makeText(getApplicationContext(), "Invalid Response from server ", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public void onContinueShopping(View view){
        MainActivity.getInstance().displayView(0, -1, "", "");
        Intent data=new Intent();
        data.putExtra("status", "success");
        setResult(9, data);
        if(Cart.getInstance()!=null){
            Cart.getInstance().finish();
        }
        if(ProductDetail.Instance()!=null){
            ProductDetail.Instance().finish();
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* if(item.getItemId() == android.R.id.home){
            finish();
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        MainActivity.getInstance().displayView(0, -1, "", "");
        Intent data=new Intent();
        data.putExtra("status", "success");
        setResult(9, data);
        finish();
    }
}
