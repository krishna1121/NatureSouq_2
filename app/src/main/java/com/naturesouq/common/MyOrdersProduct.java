package com.naturesouq.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.naturesouq.R;
import com.naturesouq.adapter.MyOrderProductAdapter;
import com.naturesouq.model.HomeDataProvider;
import com.naturesouq.model.MyAccountOrderItems;
import com.naturesouq.model.MyOrderListItem;
import com.naturesouq.model.MyOrderProductListItem;
import com.naturesouq.navigation.HomeFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by SI_Android_Binit on 2/16/2016.
 */
public class MyOrdersProduct extends Activity {

    private static final String MY_ORDER_PRODUCT_LIST_URL = Utility.baseURL+"getOrderItem.php";
    RecyclerView myOrdersProduct;
    LinearLayoutManager layoutManager;
    List<MyOrderProductListItem> gridArray;
    MyOrderProductAdapter myOrderProductAdapter;
    String orderNumber, customer_id;
    ProgressBar progress_bar;
    MyAccountOrderItems object;
    MyOrderListItem obj1;
    JSONArray myOrderSpecificationArray,myOrderGallery,myOrderSpecificationKeyArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_orders_product);

        progress_bar =(ProgressBar)findViewById(R.id.progressBar);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle("My Ordered Product");

        myOrdersProduct = (RecyclerView)findViewById(R.id.my_orders_product_recycler);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
       // myOrdersProduct.addItemDecoration(new VerticalSpaceItemDecoration(5));
        myOrdersProduct.setLayoutManager(layoutManager);

        gridArray = new ArrayList<MyOrderProductListItem>();
        customer_id = NatureSouqPrefrences.getCustomer_id(this);

        Intent intent = getIntent();
        String flow = intent.getStringExtra("GridViewToDetail");
        if (flow.equalsIgnoreCase("myAccountOrder")) {
            //For my account Fragment .
            object = this.getIntent().getExtras().getParcelable("feedItemMyAccountOrder");
            orderNumber = object.getOrder_id();
        }else if (flow.equalsIgnoreCase("myOrder")) {
            //For order list.
            obj1 = this.getIntent().getExtras().getParcelable("feedItemMyOrder");
            orderNumber = obj1.getOrder_id();
        }

        if (!TextUtils.isEmpty(orderNumber)){
            try {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("customer_id", customer_id);
                jsonObject.put("orderNumber", orderNumber);
                jsonObject.put("apikey", "naturesouq#123@apikey");

                new MyOrderProductTask(jsonObject).execute(MY_ORDER_PRODUCT_LIST_URL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class MyOrderProductTask extends AsyncTask<String, Void, String> {
        JSONObject obj;

        public MyOrderProductTask(JSONObject jobj) {
            obj = jobj;
        }

        @Override
        protected String doInBackground(String... urls) {
            return ConnectAsynchronously.connectAsynchronously(urls[0], obj);
        }

        @Override
        protected void onPreExecute() {
            try {
                if(progress_bar != null){
                    progress_bar.setVisibility(View.VISIBLE);
                   // progress_bar.setMax(20);
                   // progress_bar.setProgress(20);
                }
                super.onPreExecute();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Exception occur on Loading.. ", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonResponse = new JSONObject(result);
                String status = jsonResponse.getString("status");
                String message = jsonResponse.getString("message");
                if (status.equals("1")) {

                    JSONObject jsonObject = jsonResponse.getJSONObject("data");
                    JSONArray jsonArray = jsonObject.getJSONArray("orderlist");

                    for(int listoforderproduct =0;listoforderproduct<jsonArray.length();listoforderproduct++){

                        JSONObject object = jsonArray.getJSONObject(listoforderproduct);
                        String order_id=object.getString("order_id");
                        String order_date=object.getString("order_date");
                        String qty=object.getString("qty");
                        String product_name=object.getString("product_name");
                        String price=object.getString("price");
                        String product_id=object.getString("product_id");
                        String sku=object.getString("sku");
                        String rating = object.getString("rating");
                        String review= object.getString("review");
                        String description= object.getString("description");
                        String short_description= object.getString("short_description");
                        String image_url= object.getString("image_url");

                        myOrderGallery=object.getJSONArray("gallery");
                        myOrderSpecificationKeyArray=object.getJSONArray("specificationKey");
                        myOrderSpecificationArray=object.getJSONArray("specification");

                        MyOrderProductListItem items = new MyOrderProductListItem(order_id,order_date,qty,product_name,price,product_id,sku,rating,review,description,short_description,image_url,myOrderGallery,myOrderSpecificationKeyArray,myOrderSpecificationArray);
                        gridArray.add(items);

                    }
                    myOrderProductAdapter = new MyOrderProductAdapter(gridArray, MyOrdersProduct.this);
                    myOrdersProduct.setAdapter(myOrderProductAdapter);
                    myOrdersProduct.addOnItemTouchListener(new RecyclerItemClickListener(MyOrdersProduct.this, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            MyOrderProductListItem feedItem = myOrderProductAdapter.getMyOrderProductListItem().get(position);
                            Intent myOrderProduct = new Intent(MyOrdersProduct.this, ProductDetail.class);
                            myOrderProduct.putExtra("GridViewToDetail", "myOrderProduct");
                            myOrderProduct.putExtra("feedItemMyOrderProduct", feedItem);
                            myOrderProduct.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            HomeFragment.provider1=new HomeDataProvider(feedItem.getOrderProductGallery());
                            HomeFragment.provider1.setProductGallery(feedItem.getOrderProductGallery());
                            HomeFragment.provider2=new HomeDataProvider(feedItem.getOrderProductSpecificationKey());
                            HomeFragment.provider2.setProductSpecificationKey(feedItem.getOrderProductSpecificationKey());
                            HomeFragment.provider3=new HomeDataProvider(feedItem.getOrderProductSpecification());
                            HomeFragment.provider3.setProductSpecification(feedItem.getOrderProductSpecification());
                            startActivity(myOrderProduct);

                        }
                    }));

                } else {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
                if(progress_bar != null){
                    progress_bar.setVisibility(View.GONE);
                }

            } catch (Exception e) {
                if(progress_bar != null){
                    progress_bar.setVisibility(View.GONE);
                }
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(result);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
}
