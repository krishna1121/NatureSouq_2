package com.naturesouq.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.naturesouq.R;
import com.naturesouq.adapter.MyOrderAdapter;
import com.naturesouq.model.MyOrderListItem;
import com.naturesouq.navigation.MyAccount;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SI_Android_Binit on 12/28/2015.
 */

public class MyOrders extends Activity implements MyOrderAdapter.ViewClickListener {

    RecyclerView myOrders;
    LinearLayoutManager layoutManager;
    List<MyOrderListItem> myOrderItemsList;
    MyOrderAdapter myOrderAdapter;
    MyOrderListItem items;
    final View view[] = {null};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_orders);

        ImageView orderplaceholder = (ImageView) findViewById(R.id.orderplaceholder);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle("My Orders");

        myOrders = (RecyclerView) findViewById(R.id.my_orders_recycler);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        // myOrders.addItemDecoration(new VerticalSpaceItemDecoration(5));
        myOrders.setLayoutManager(layoutManager);
        myOrderItemsList = new ArrayList<MyOrderListItem>();

        try {
            JSONArray orderListArray = MyAccount.provider.getOrderList();

            for (int listoforder = 0; listoforder < orderListArray.length(); listoforder++) {

                JSONObject object = orderListArray.getJSONObject(listoforder);
                String order_id = object.getString("order_id");
                String order_date = object.getString("order_date");
                String sub_total = object.getString("sub_total");
                String order_status = object.getString("order_status");
                String customer_email = object.getString("customer_email");
                String noofitems = object.getString("noofitems");
                String image_url = object.getString("image_url");

                items = new MyOrderListItem(order_id, order_date, sub_total, order_status, customer_email, noofitems, image_url, false);
                myOrderItemsList.add(items);

            }
            myOrderAdapter = new MyOrderAdapter(myOrderItemsList, MyOrders.this);
            myOrderAdapter.setViewClickListener(MyOrders.this);
            myOrders.setAdapter(myOrderAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClicked(int position) {
        MyOrderListItem feedItem = myOrderAdapter.getMyOrderItems().get(position);

        Intent myOrderPage = new Intent(MyOrders.this, MyOrdersProduct.class);
        myOrderPage.putExtra("GridViewToDetail", "myOrder");
        myOrderPage.putExtra("feedItemMyOrder", feedItem);
        myOrderPage.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(myOrderPage);
    }

    MyOrderListItem itemsFinal = new MyOrderListItem();

    @Override
    public void onSettingClicked(int position, MyOrderListItem item, View view1) {

        if (view[0] != null) {
            view[0].setVisibility(View.INVISIBLE);
            itemsFinal.setStateOfDelete(false);
            myOrderAdapter.notifyDataSetChanged();
        }

        item.setStateOfDelete(true);
        if (item.getStateOfDelete() == true) {
            view1.setVisibility(View.VISIBLE);
        }
        view[0] = view1;
        itemsFinal = item;

    }

    @Override
    public void onOrderCancelClicked(int position, MyOrderListItem item, View view) {
        //cancel your order here

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

}
