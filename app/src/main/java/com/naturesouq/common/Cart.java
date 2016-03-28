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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.naturesouq.R;
import com.naturesouq.adapter.CartBaseAdapter;
import com.naturesouq.model.CartRowItems;
import com.naturesouq.model.HomeDataProvider;
import com.naturesouq.navigation.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SI_Android_Binit on 10/16/2015.
 */

public class Cart extends Activity implements CartBaseAdapter.ViewClickListener {

    TextView  sbuTotalPrice;
    Button checkout;
    List<CartRowItems> cartRowItemsList ;
    String CART_URL=Utility.baseURL+"cart.php";
    String REMOVE_PRODUCT_URL=Utility.baseURL+"removeCart.php";
    Cart instance ;
    ProgressBar progressBar;
    String shoppingcart_id = "", product_id = "";
    RecyclerView myList;
    private CartBaseAdapter adapter;
    LinearLayoutManager layoutManager;
    int cartitemcount ;
    private String grandTotalAmount;
    private String shippingAmount;
    private String subTotalAmount;
	int pos;
    static Activity activity;
    ImageView cartplaceholder ;
 JSONArray cartSpecificationArray,cartGallery,cartSpecificationKeyArray;
    static String productID[];
    static String productQuantity[];
    static int len;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        instance = this ;
        Intent intent = getIntent() ;
        cartitemcount = intent.getIntExtra("cartitemcount", 0);
        activity=this;
       // product_id = intent.getStringExtra("product_id") ;

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle("Cart");
        progressBar =(ProgressBar)findViewById(R.id.progressBar);
        cartplaceholder = (ImageView)findViewById(R.id.cartplaceholder);
        sbuTotalPrice = (TextView)findViewById(R.id.subtotalPrice);



/*
        sbuTotal = (TextView)findViewById(R.id.subtotalText);
        Typeface face1= Typeface.createFromAsset(getAssets(), "font/Lato_Regular.ttf");
        sbuTotal.setTypeface(face1);

        sbuTotalPrice = (TextView)findViewById(R.id.subtotalPrice);
        Typeface face2= Typeface.createFromAsset(getAssets(), "font/Lato_Bold.ttf");
        sbuTotalPrice.setTypeface(face2);

        shipping = (TextView)findViewById(R.id.shippingText);
        Typeface face3= Typeface.createFromAsset(getAssets(), "font/Lato_Regular.ttf");
        shipping.setTypeface(face3);

        shippingPrice = (TextView)findViewById(R.id.shippingPrice);
        Typeface face4= Typeface.createFromAsset(getAssets(), "font/Lato_Bold.ttf");
        shippingPrice.setTypeface(face4);

        total = (TextView)findViewById(R.id.totalText);
        Typeface face5= Typeface.createFromAsset(getAssets(), "font/Lato_Regular.ttf");
        total.setTypeface(face5);

        totalPrice = (TextView)findViewById(R.id.totalPrice);
        Typeface face6= Typeface.createFromAsset(getAssets(), "font/Lato_Bold.ttf");
        totalPrice.setTypeface(face6);

        */

        checkout = (Button)findViewById(R.id.checkout_btn);
        Typeface face7= Typeface.createFromAsset(getAssets(), "font/Lato_Bold.ttf");
        checkout.setTypeface(face7);
        checkout.setEnabled(false);

         myList = (RecyclerView)findViewById(R.id.my_recycler_view);
         layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
         myList.setLayoutManager(layoutManager);
         myList.addItemDecoration(new VerticalSpaceItemDecoration(5));
        cartRowItemsList = new ArrayList<CartRowItems>();

        boolean networkStatus = new DialogBox(this).checkInternetConnection();
        if(networkStatus){
            JSONObject jsonObject=new JSONObject();
            try {
                shoppingcart_id = NatureSouqPrefrences.getShoppingcartId(this);
                jsonObject.put("shoppingcart_id",shoppingcart_id);
                jsonObject.put("apikey","naturesouq#123@apikey");

                new CartTask(jsonObject, "cartProduct").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,CART_URL);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void onCheckout(View view){

        boolean fb = NatureSouqPrefrences.getFacebookUser(Cart.this);
        String email= NatureSouqPrefrences.getUserName(Cart.this);
        String pass=NatureSouqPrefrences.getUserPass(Cart.this);
        len=cartRowItemsList.size();
        productID=new String[len];
        productQuantity=new String[len];
        for(int i=0;i<len;i++){
            productID[i]= cartRowItemsList.get(i).getProduct_id();
            productQuantity[i]=cartRowItemsList.get(i).getQty();
        }



        if((!(TextUtils.isEmpty(email) && TextUtils.isEmpty(pass))) || fb){
            int addList=NatureSouqPrefrences.getLoginCartList(Cart.this);
            if(addList>0){
                Intent home = new Intent(Cart.this, CheckOut.class);
                home.putExtra("from","cart");
                home.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(home, 1);
            }else{
                Intent home = new Intent(Cart.this, AddNewAddress.class);
                startActivityForResult(home, 1);

            }

        }else{
            Intent loginIntent = new Intent(Cart.this, Login.class);
            startActivityForResult(loginIntent, 1);
        }

    }

    @Override
    public void onImageClicked(int position) {
        pos=position;
        CartRowItems feedItem = adapter.getCartRowItems().get(position);
        Intent detailPage = new Intent(Cart.this, ProductDetail.class);
        detailPage.putExtra("GridViewToDetail","cart");
        detailPage.putExtra("feedItemCart", feedItem);
        detailPage.putExtra("cartitemcount", cartitemcount);
        detailPage.putExtra("product_id", feedItem.getProduct_id());
        detailPage.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        HomeFragment.provider1=new HomeDataProvider(feedItem.getCartGallery());
        HomeFragment.provider1.setProductGallery(feedItem.getCartGallery());
        HomeFragment.provider2=new HomeDataProvider(feedItem.getCartSpecificationKey());
        HomeFragment.provider2.setProductSpecificationKey(feedItem.getCartSpecificationKey());
        HomeFragment.provider3=new HomeDataProvider(feedItem.getCartSpecification());
        HomeFragment.provider3.setProductSpecification(feedItem.getCartSpecification());
        startActivity(detailPage);
    }

    @Override
    public void onRemoveClicked(int position) {
        CartRowItems feedItem1 = adapter.getCartRowItems().get(position);
        JSONObject jsonObject=new JSONObject();
        try {
            shoppingcart_id = NatureSouqPrefrences.getShoppingcartId(this);
            product_id = feedItem1.getProduct_id();

            jsonObject.put("shoppingcart_id",shoppingcart_id);
            jsonObject.put("product_id",product_id);
            jsonObject.put("qty","1");
            jsonObject.put("apikey","naturesouq#123@apikey");

            new CartTask(jsonObject, "removeCartProduct").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,REMOVE_PRODUCT_URL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
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


    public class CartTask extends AsyncTask<String, Void, String> {
        JSONObject obj;
        String taskIdentifier;

        public CartTask(JSONObject jobj,String taskIdentifier) {
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
                if(progressBar != null)
                    progressBar.setVisibility(View.VISIBLE);
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
                    if (taskIdentifier.equals("cartProduct")){
                        JSONObject jsonObject = jsonResponse.getJSONObject("data");
                        shoppingcart_id = jsonObject.getString("shoppingcart_id");
                        subTotalAmount = jsonObject.getString("subTotalAmount");
                        shippingAmount = jsonObject.getString("shipingAmount");
                        grandTotalAmount = jsonObject.getString("grandTotalAmount");
                        JSONArray jsonArray = jsonObject.getJSONArray("shoppingCartProducts");
 						String ProductPrice[]=new String[jsonArray.length()];

                        if(jsonArray.length() > 0){
                            for(int orderlist =0;orderlist<jsonArray.length();orderlist++){

                                JSONObject object = jsonArray.getJSONObject(orderlist);
                                String product_id=object.getString("product_id");
                                String sku=object.getString("sku");
                                String name=object.getString("name");
                                String set=object.getString("set");
                                String type=object.getString("type");
                                String rating= object.getString("rating");
                                String review = object.getString("review");
                                String price=object.getString("price");
                                String shortDescription=object.getString("shortDescription");
                                String image_url=object.getString("image_url");

                                cartGallery=object.getJSONArray("gallery");
                                cartSpecificationKeyArray=object.getJSONArray("specificationKey");
                                cartSpecificationArray=object.getJSONArray("specification");

                                CartRowItems items = new CartRowItems(image_url,name,shortDescription,price, product_id,"1",rating,review,cartGallery,cartSpecificationKeyArray,cartSpecificationArray);
                                cartRowItemsList.add(items);
                            }
                            adapter = new CartBaseAdapter(cartRowItemsList,Cart.this,sbuTotalPrice);
                            adapter.setViewClickListener(Cart.this);
                            myList.setAdapter(adapter);

                        }else{
                            //Set Empty Cart Placeholder .
                            cartplaceholder.setVisibility(View.VISIBLE);
                        }



 					if(jsonArray.length()>0){
                        checkout.setEnabled(true);
                    }
                    }
                    else if (taskIdentifier.equals("removeCartProduct")){

                        Toast.makeText(getApplicationContext(), "Product Removed from Cart", Toast.LENGTH_SHORT).show();

                        cartRowItemsList.remove(pos);
                        adapter.notifyDataSetChanged();
                        adapter=new CartBaseAdapter(cartRowItemsList,Cart.this,sbuTotalPrice);
                        NatureSouqPrefrences.setCartCounter(Cart.this, "" + cartRowItemsList.size());

                        if(cartRowItemsList.size()<1){
                            checkout.setEnabled(false);
                            sbuTotalPrice.setText("");
                            //Show Cart Placeholder .
                            cartplaceholder.setVisibility(View.VISIBLE);
                        }
                    }

                }else if(status.equalsIgnoreCase("2")){
                    cartplaceholder.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }

                if(progressBar != null)
                    progressBar.setVisibility(View.GONE);
            } catch (Exception e) {
                if(progressBar != null)
                    progressBar.setVisibility(View.GONE);
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(result);
        }
    }
    public static Activity getInstance(){
        return activity;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data!=null){
            String value=data.getStringExtra("status");
            if(!TextUtils.isEmpty(value)){

                if(value.equalsIgnoreCase("success")){
                    Intent i=new Intent();
                    i.putExtra("status","success");
                    setResult(3,i);
                    finish();
                }
            }
        }

        //else{
//           if(resultCode==1 && requestCode==0){
//               Intent i=new Intent();
//               i.putExtra("status","success");
//               setResult(3,i);
//               finish();
//           }
//        }

    }
}
