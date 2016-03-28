package com.naturesouq.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.naturesouq.R;
import com.naturesouq.model.CartRowItems;
import com.naturesouq.model.CategoryProductsItem;
import com.naturesouq.model.HomeProductsItem;
import com.naturesouq.model.MyFavoriteListItem;
import com.naturesouq.model.MyOrderProductListItem;
import com.naturesouq.model.SearchListItem;
import com.naturesouq.navigation.HomeFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by SI_Android_Binit on 10/15/2015.
 */

public class ProductDetail extends Activity {

    String AddToCartUrl = Utility.baseURL+"addToCart.php";
    String AddToFavoriteUrl = Utility.baseURL+"addRemoveFavoritet.php";
    String FavoriteProductStatusUrl = Utility.baseURL+"showWishlistProductStatus.php";
    ProgressBar progressBar ;
    private int cartitemcount;
    private TextView countTextView = null, productdetail;
    String shoppingcart_id = "", product_id, favourite, customer_id;
    ViewPager viewPager, viewPagerDescription ;
    int[] anim_circle ;
    ImageView circleAnimImageView ;
    HomeProductsItem object;
    CategoryProductsItem obj1;
    CartRowItems obj2;
    MyFavoriteListItem obj3;
    SearchListItem obj4;
    MyOrderProductListItem obj5;
    String noOfItems;
    Set<String> set;
    SharedPreferences sharedPreferences;
    TableLayout table_layout;
    RelativeLayout myDescription, mySpecification;
    int flagDec=0, flagSpec=0;
    JSONArray homeSpecificationArray,homeSpecificationKeyArray,homeGallery;
    String[] backgroundImage;
    static Activity activity;
    String dataChange="false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        activity=this;

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Intent intent = getIntent();
        cartitemcount = intent.getIntExtra("cartitemcount", 0);
        product_id = intent.getStringExtra("product_id");

        String counter = NatureSouqPrefrences.getCartCounter(this);
        if (!TextUtils.isEmpty(counter)) {
            updateHotCount(Integer.parseInt(counter));
        }
        viewPager = (ViewPager) findViewById(R.id.productViewPager);
        //circleAnimImageView = (ImageView) findViewById(R.id.circleAnim);
        productdetail = (TextView) findViewById(R.id.productDesc);
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);

        myDescription=(RelativeLayout)findViewById(R.id.description_lbl);
        mySpecification=(RelativeLayout)findViewById(R.id.specification_lbl);

        final ImageView imageViewDesc =(ImageView)findViewById(R.id.arrow_desc);
        final ImageView imageViewSpec =(ImageView)findViewById(R.id.arrow_spec);

        final LinearLayout decsContent=(LinearLayout)findViewById(R.id.productDesc_lbl);
        final ViewGroup layoutDesc = (ViewGroup) decsContent.getParent();
        myDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flagDec == 0) {

                    imageViewDesc.setImageResource(R.drawable.arrow_right);
                    layoutDesc.removeView(decsContent);
                    flagDec = 1;

                } else {

                    imageViewDesc.setImageResource(R.drawable.arrow_down);
                    layoutDesc.addView(decsContent, 1);
                    flagDec = 0;
                }

            }
        });

        final LinearLayout specContent=(LinearLayout)findViewById(R.id.tableLayout);
        final ViewGroup layoutSpec = (ViewGroup) specContent.getParent();
        mySpecification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flagSpec == 0) {

                    imageViewSpec.setImageResource(R.drawable.arrow_right);
                    layoutSpec.removeView(specContent);
                    flagSpec = 1;

                } else {

                    imageViewSpec.setImageResource(R.drawable.arrow_down);
                    layoutSpec.addView(specContent, 1);
                    flagSpec = 0;
                }

            }
        });

        try{
            homeSpecificationArray = HomeFragment.provider3.getProductSpecification();
            if (homeSpecificationArray!=null){
                int rows = homeSpecificationArray.length();
                int cols = Integer.parseInt("2");
                table_layout.setColumnShrinkable(1,true);
                table_layout.removeAllViews();
                BuildTable(rows, cols);
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle("Product Detail");

        //get Font Style From raw folder
        Typeface face = Typeface.createFromAsset(getAssets(), "font/Lato_Black.ttf");
        Typeface faceBold = Typeface.createFromAsset(getAssets(), "font/Lato_Bold.ttf");
        Typeface faceRegular = Typeface.createFromAsset(getAssets(), "font/Lato_Regular.ttf");

        TextView productname = (TextView) findViewById(R.id.productName);
        productname.setTypeface(face);
        TextView productcost = (TextView) findViewById(R.id.prductPrice);
        productcost.setTypeface(faceBold);
        //TextView productdetail = (TextView) findViewById(R.id.productDetail);

        TextView productReviw = (TextView) findViewById(R.id.ratePoints);
        productReviw.setTypeface(faceBold);
        RatingBar productRating = (RatingBar) findViewById(R.id.ratingBar);
        Button addcartText = (Button) findViewById(R.id.addcartbtn);
        addcartText.setTypeface(faceBold);
        //ImageView productImg = (ImageView) findViewById(R.id.productImg);
        //Bundle b = this.getIntent().getExtras();
        sharedPreferences = getApplicationContext().getSharedPreferences("com.naturesouq.common", MODE_PRIVATE);
        customer_id = NatureSouqPrefrences.getCustomer_id(this);
        set = new HashSet<String>();
        set = sharedPreferences.getStringSet("key", new HashSet<String>());
        ImageView favorite = (ImageView) findViewById(R.id.img_favorite);
        //Toast.makeText(getApplicationContext(),""+set,Toast.LENGTH_LONG).show();
        if (set != null) {

            for (Iterator<String> it = set.iterator(); it.hasNext(); ) {
                String f = it.next();
                if (f.equals(product_id)) {
                    favorite.setImageResource(R.drawable.favorite_product);
                }

            }

            Intent DetailIntent = getIntent();
            favourite = DetailIntent.getStringExtra("MY_FAVOURITE");

            if ((!TextUtils.isEmpty(favourite)) && favourite.equals(product_id)) {
                ImageView favorit = (ImageView) findViewById(R.id.img_favorite);
                favorit.setImageResource(R.drawable.favorite_product);
            }

            LinearLayout layout = (LinearLayout) findViewById(R.id.favorite_lbl);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject jsonObject = new JSONObject();
                    try {

                        if (TextUtils.isEmpty(customer_id)) {
                            Toast.makeText(getApplicationContext(), "Without Login you can't mark item as a favorite!", Toast.LENGTH_LONG).show();
                        } else if (!TextUtils.isEmpty(customer_id)) {
                            jsonObject.put("customer_id", customer_id);
                            jsonObject.put("product_id", product_id);
                            jsonObject.put("apikey", "naturesouq#123@apikey");
                            new AddToCartTask(jsonObject, "setFavoriteItem").execute(AddToFavoriteUrl);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            String flow = intent.getStringExtra("GridViewToDetail");

            if (flow.equalsIgnoreCase("homeFragment")) {
                //For Home Fragment .
                object = this.getIntent().getExtras().getParcelable("feedItemHomeFragment");
                if (!TextUtils.isEmpty(object.getName())) {
                    productname.setText(object.getName());
                } else {
                    productname.setText("N/A");
                }
                if (!TextUtils.isEmpty(object.getPrice())) {
                    productcost.setText("AED " +object.getPrice());
                } else {
                    productcost.setText("N/A");
                }
                if (!TextUtils.isEmpty(object.getDescription())) {
                    productdetail.setText(object.getDescription());
                } else {
                    productdetail.setText("N/A");
                }
                if (!TextUtils.isEmpty(object.getRating())) {
                    productRating.setRating(Float.parseFloat(object.getRating()));
                } else {
                    productRating.setRating(Float.parseFloat("N/A"));
                }
                if (!TextUtils.isEmpty(object.getReview())) {
                    productReviw.setText(object.getReview());
                } else {
                    productReviw.setText("N/A");
                }

                //set product Image
                homeGallery = HomeFragment.provider1.getProductGallery();
                if (homeGallery!=null){
                    backgroundImage=new String[homeGallery.length()];
                    for (int imageURL=0; imageURL<homeGallery.length();imageURL++){
                        try {
                            backgroundImage[imageURL] =homeGallery.getString(imageURL);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ImageAdapter adapter = new ImageAdapter(this, backgroundImage);
                    // Binds the Adapter to the ViewPager
                    viewPager.setAdapter(adapter);
                    viewPager.setOnPageChangeListener(adapter);
                }

            } else if (flow.equalsIgnoreCase("particularCategory")) {
                //For Particular Category Products .
                obj1 = this.getIntent().getExtras().getParcelable("feedItemCategoryDetail");

                if (!TextUtils.isEmpty(obj1.getName())) {
                    productname.setText(obj1.getName());
                } else {
                    productname.setText("N/A");
                }
                if (!TextUtils.isEmpty(obj1.getPrice())) {
                    productcost.setText("AED " +obj1.getPrice());
                } else {
                    productcost.setText("N/A");
                }
                if (!TextUtils.isEmpty(obj1.getDescription())) {
                    productdetail.setText(obj1.getDescription());
                } else {
                    productdetail.setText("N/A");
                }
                if (!TextUtils.isEmpty(obj1.getRating())) {
                    productRating.setRating(Float.parseFloat(obj1.getRating()));
                } else {
                    productRating.setRating(Float.parseFloat("N/A"));
                }
                if (!TextUtils.isEmpty(obj1.getReview())) {
                    productReviw.setText(obj1.getReview());
                } else {
                    productReviw.setText("N/A");
                }

                //set product Image
                homeGallery = HomeFragment.provider1.getProductGallery();
                if (homeGallery!=null){
                    backgroundImage=new String[homeGallery.length()];
                    for (int imageURL=0; imageURL<homeGallery.length();imageURL++){
                        try {
                            backgroundImage[imageURL] =homeGallery.getString(imageURL);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ImageAdapter adapter = new ImageAdapter(this, backgroundImage);
                    // Binds the Adapter to the ViewPager
                    viewPager.setAdapter(adapter);
                    viewPager.setOnPageChangeListener(adapter);
                }
            } else if (flow.equalsIgnoreCase("cart")) {
                //For Particular Category Products .
                obj2 = this.getIntent().getExtras().getParcelable("feedItemCart");

                if (!TextUtils.isEmpty(obj2.getProduct_name())) {
                    productname.setText(obj2.getProduct_name());
                } else {
                    productname.setText("N/A");
                }
                if (!TextUtils.isEmpty(obj2.getProduct_price())) {
                    productcost.setText("AED " +obj2.getProduct_price());
                } else {
                    productcost.setText("N/A");
                }
                if (!TextUtils.isEmpty(obj2.getProduct_shortDesc())) {
                    productdetail.setText(obj2.getProduct_shortDesc());
                } else {
                    productdetail.setText("N/A");
                }
                if (!TextUtils.isEmpty(obj2.getRating())) {
                    productRating.setRating(Float.parseFloat(obj2.getRating()));
                } else {
                    productRating.setRating(Float.parseFloat("N/A"));
                }
                if (!TextUtils.isEmpty(obj2.getReview())) {
                    productReviw.setText(obj2.getReview());
                } else {
                    productReviw.setText("N/A");
                }
                //set product Image
                homeGallery = HomeFragment.provider1.getProductGallery();
                if (homeGallery!=null){
                    backgroundImage=new String[homeGallery.length()];
                    for (int imageURL=0; imageURL<homeGallery.length();imageURL++){
                        try {
                            backgroundImage[imageURL] =homeGallery.getString(imageURL);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ImageAdapter adapter = new ImageAdapter(this, backgroundImage);
                    // Binds the Adapter to the ViewPager
                    viewPager.setAdapter(adapter);
                    viewPager.setOnPageChangeListener(adapter);
                }

            } else if (flow.equalsIgnoreCase("myFavorite")) {
                //For Particular Category Products .
                obj3 = this.getIntent().getExtras().getParcelable("feedItemMyFavorite");

                if (!TextUtils.isEmpty(obj3.getName())) {
                    productname.setText(obj3.getName());
                } else {
                    productname.setText("N/A");
                }
                if (!TextUtils.isEmpty(obj3.getPrice())) {
                    productcost.setText("AED " +obj3.getPrice());
                } else {
                    productcost.setText("N/A");
                }
                if (!TextUtils.isEmpty(obj3.getShortDescription())) {
                    productdetail.setText(obj3.getShortDescription());
                } else {
                    productdetail.setText("N/A");
                }
                if (!TextUtils.isEmpty(obj3.getRating())) {
                    productRating.setRating(Float.parseFloat(obj3.getRating()));
                } else {
                    productRating.setRating(Float.parseFloat("N/A"));
                }
                if (!TextUtils.isEmpty(obj3.getReview())) {
                    productReviw.setText(obj3.getReview());
                } else {
                    productReviw.setText("N/A");
                }
                //set product Image
                homeGallery = HomeFragment.provider1.getProductGallery();
                if (homeGallery!=null){
                    backgroundImage=new String[homeGallery.length()];
                    for (int imageURL=0; imageURL<homeGallery.length();imageURL++){
                        try {
                            backgroundImage[imageURL] =homeGallery.getString(imageURL);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ImageAdapter adapter = new ImageAdapter(this, backgroundImage);
                    // Binds the Adapter to the ViewPager
                    viewPager.setAdapter(adapter);
                    viewPager.setOnPageChangeListener(adapter);
                }

            } else if (flow.equalsIgnoreCase("mySearch")) {
                //For Particular Category Products .
                obj4 = this.getIntent().getExtras().getParcelable("feedItemSearch");

                if (!TextUtils.isEmpty(obj4.getName())) {
                    productname.setText(obj4.getName());
                } else {
                    productname.setText("N/A");
                }
                if (!TextUtils.isEmpty(obj4.getPrice())) {
                    productcost.setText("AED " +obj4.getPrice());
                } else {
                    productcost.setText("N/A");
                }
                if (!TextUtils.isEmpty(obj4.getDescription())) {
                    productdetail.setText(obj4.getDescription());
                } else {
                    productdetail.setText("N/A");
                }
                if (!TextUtils.isEmpty(obj4.getRating())) {
                    productRating.setRating(Float.parseFloat(obj4.getRating()));
                } else {
                    productRating.setRating(Float.parseFloat("N/A"));
                }
                if (!TextUtils.isEmpty(obj4.getReview())) {
                    productReviw.setText(obj4.getReview());
                } else {
                    productReviw.setText("N/A");
                }

                //set product Image
                homeGallery = HomeFragment.provider1.getProductGallery();
                if (homeGallery!=null){
                    backgroundImage=new String[homeGallery.length()];
                    for (int imageURL=0; imageURL<homeGallery.length();imageURL++){
                        try {
                            backgroundImage[imageURL] =homeGallery.getString(imageURL);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ImageAdapter adapter = new ImageAdapter(this, backgroundImage);
                    // Binds the Adapter to the ViewPager
                    viewPager.setAdapter(adapter);
                    viewPager.setOnPageChangeListener(adapter);
                }
            } else if (flow.equalsIgnoreCase("myOrderProduct")) {
                //For Particular Category Products .
                obj5 = this.getIntent().getExtras().getParcelable("feedItemMyOrderProduct");
                if (!TextUtils.isEmpty(obj5.getName())) {
                    productname.setText(obj5.getName());
                } else {
                    productname.setText("N/A");
                }
                if (!TextUtils.isEmpty(obj5.getPrice())) {
                    productcost.setText("AED " +obj5.getPrice());
                } else {
                    productcost.setText("N/A");
                }
                if (!TextUtils.isEmpty(obj5.getDescription())) {
                    productdetail.setText(obj5.getDescription());
                } else {
                    productdetail.setText("N/A");
                }
                if (!TextUtils.isEmpty(obj5.getRating())) {
                    productRating.setRating(Float.parseFloat(obj5.getRating()));
                } else {
                    productRating.setRating(Float.parseFloat("N/A"));
                }
                if (!TextUtils.isEmpty(obj5.getReview())) {
                    productReviw.setText(obj5.getReview());
                } else {
                    productReviw.setText("N/A");
                }

                //set product Image
                homeGallery = HomeFragment.provider1.getProductGallery();
                if (homeGallery!=null){
                    backgroundImage=new String[homeGallery.length()];
                    for (int imageURL=0; imageURL<homeGallery.length();imageURL++){
                        try {
                            backgroundImage[imageURL] =homeGallery.getString(imageURL);
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ImageAdapter adapter = new ImageAdapter(this, backgroundImage);
                    // Binds the Adapter to the ViewPager
                    viewPager.setAdapter(adapter);
                    viewPager.setOnPageChangeListener(adapter);
                }
            }
        }

    }
        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.product_menu, menu);

            final View menu_productDetail = menu.findItem(R.id.cart).getActionView();

            countTextView = (TextView) menu_productDetail.findViewById(R.id.hotlist_hot);
            updateHotCount(cartitemcount);
            new MyMenuItemStuffListener(menu_productDetail, "Show hot message") {
                @Override
                public void onClick(View v) {
                    //Open New Activity here .
                    Intent cartintent = new Intent(getApplicationContext(), Cart.class);
                    cartintent.putExtra("cartitemcount", cartitemcount);
                    startActivityForResult(cartintent, 3);
                }
            };

            return true;
        }
    public static Activity Instance(){
        return activity;
    }

    private void BuildTable(int rows, int cols) {

        // outer for loop
        for (int i = 0; i < rows; i++) {

            TableRow row = new TableRow(this);
           // row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            // Inner for loop
            for (int j = 1; j <= 2; j++) {

                TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                //tv.setBackgroundResource(R.drawable.cell_shape);
                //tv.setPadding(10, 10, 10, 10);

                    try{
                        homeSpecificationKeyArray = HomeFragment.provider2.getProductSpecificationKey();
                        if (homeSpecificationArray!=null && homeSpecificationKeyArray!=null){

                            if(j==1) {
                                //for (int mySpecificationKey = 0; mySpecificationKey < childSpecificationKeyArray.length(); mySpecificationKey++) {
                                tv.setText(homeSpecificationKeyArray.getString(i)+" :");
                                //}
                            }else {
                                //for (int mySpecification = 0; mySpecification < childSpecificationArray.length(); mySpecification++) {
                                tv.setText(homeSpecificationArray.getString(i));
                                //}
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                row.addView(tv);
            }
            table_layout.addView(row);
        }
    }


        static abstract class MyMenuItemStuffListener implements View.OnClickListener, View.OnLongClickListener {
            private String hint;
            private View view;

            MyMenuItemStuffListener(View view, String hint) {
                this.view = view;
                this.hint = hint;
                view.setOnClickListener(this);
                view.setOnLongClickListener(this);
            }

            @Override
            abstract public void onClick(View v);

            @Override
            public boolean onLongClick(View v) {
                final int[] screenPos = new int[2];
                final Rect displayFrame = new Rect();
                view.getLocationOnScreen(screenPos);
                view.getWindowVisibleDisplayFrame(displayFrame);
                final Context context = view.getContext();
                final int width = view.getWidth();
                final int height = view.getHeight();
                final int midy = screenPos[1] + height / 2;
                final int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
                Toast cheatSheet = Toast.makeText(context, hint, Toast.LENGTH_SHORT);
                if (midy < displayFrame.height()) {
                    cheatSheet.setGravity(Gravity.TOP | Gravity.RIGHT,
                            screenWidth - screenPos[0] - width / 2, height);
                } else {
                    cheatSheet.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, height);
                }
                cheatSheet.show();
                return true;
            }
        }

    // call the updating code on the main thread,
// so we can call this asynchronously
    public void updateHotCount(final int new_hot_number) {
        cartitemcount = new_hot_number ;
        NatureSouqPrefrences.setCartCounter(ProductDetail.this, new_hot_number+"");
        if (countTextView == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (new_hot_number == 0)
                    countTextView.setVisibility(View.INVISIBLE);
                else {
                    countTextView.setVisibility(View.VISIBLE);
                    countTextView.setText(Integer.toString(new_hot_number));
                }
            }
        });

//        Intent intent = getIntent();
//        intent.putExtra("counterValue", new_hot_number);
//        setResult(11, intent);
//        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.searchItem:
                //Open New Activity here .
                Intent searchintent = new Intent(this, Search.class);
                searchintent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(searchintent);
                break;

            case R.id.cart:
                //Open New Activity here .
                Intent cartintent = new Intent(this, Cart.class);
                cartintent.putExtra("cartitemcount", cartitemcount);
                startActivityForResult(cartintent, 3);
                break;

            case android.R.id.home:
                Intent intent =new Intent();
                intent.putExtra("IsRefreshPage",dataChange);
                setResult(12,intent);
                finish();
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    public void addToCart(View view){
        JSONObject jsonObject =new JSONObject();
        try {
            shoppingcart_id= NatureSouqPrefrences.getShoppingcartId(ProductDetail.this);

                jsonObject.put("shoppingcart_id", shoppingcart_id);//shoppingcart_id
                jsonObject.put("product_id", product_id);
                jsonObject.put("qty", "1");
                jsonObject.put("apikey", "naturesouq#123@apikey");

            new AddToCartTask(jsonObject, "addToCart").execute(AddToCartUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    public void addToFavorite(View view) {
//        JSONObject jsonObject = new JSONObject();
//        try {
//
//            jsonObject.put("customer_id", "37");
//            jsonObject.put("product_id", product_id);
//            jsonObject.put("apikey", "naturesouq#123@apikey");
//
//            new AddToCartTask(jsonObject, "setFavoriteItem").execute(AddToFavoriteUrl);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        String counter= NatureSouqPrefrences.getCartCounter(ProductDetail.this);
        if(!TextUtils.isEmpty(counter)){
            updateHotCount(Integer.parseInt(counter));
        }
    }

    public class AddToCartTask extends AsyncTask<String, Void, String> {
        JSONObject obj;
        String taskIdentifier;

        public AddToCartTask(JSONObject jobj, String taskIdentifier) {
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
                super.onPreExecute();
                if(progressBar!=null)
                progressBar.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Exception occur on Loading.. ", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try{
                JSONObject jsonResponse = new JSONObject(result);
                String status = jsonResponse.getString("status");
                String message = jsonResponse.getString("message");
                final Intent rating_changedata = new Intent();

                switch (status) {
                    case "0":
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        break;
                    case "1":
                        switch (taskIdentifier){
                            case "addToCart":

                                JSONObject jObj = jsonResponse.getJSONObject("data");
                                noOfItems = jObj.getString("noOfItems");
                                String shoppingcart_id = jObj.getString("shoppingcart_id");

                                // updateHotCount(Integer.parseInt(shoppingcart_id)+Integer.parseInt(noOfItems)+cartitemcount);
                                // updateHotCount(Integer.parseInt(noOfItems)+cartitemcount);
                                updateHotCount(Integer.parseInt(noOfItems));
                                Intent count=new Intent();
                                count.putExtra("updateCount", "" + noOfItems);
                                setResult(12, count);
                                NatureSouqPrefrences.setShoppingcartId(ProductDetail.this, shoppingcart_id);
                                NatureSouqPrefrences.setCartCounter(ProductDetail.this, noOfItems);
                                if(!TextUtils.isEmpty(noOfItems)){
                                    updateHotCount(Integer.parseInt(noOfItems));
                                }
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

                                break;

                            case "setFavoriteItem":

                                String favouriteStatus = jsonResponse.getString("is_fev");
                                dataChange="true";
                                switch (favouriteStatus){
                                    case "1":
                                        //added
                                        ImageView favorite = (ImageView) findViewById(R.id.img_favorite);
                                        favorite.setImageResource(R.drawable.favorite_product);

                                        break;
                                    case "0":
                                        //Removed
                                        ImageView unfavorite = (ImageView) findViewById(R.id.img_favorite);
                                        unfavorite.setImageResource(R.drawable.unfavorite_product);
                                        break;
//                                    default:
//                                        ImageView defaultImage = (ImageView) findViewById(R.id.img_favorite);
//                                        defaultImage.setImageResource(R.drawable.unfavorite_product);
//                                        break;
                                }
                                MainActivity.fav.clear();
                                if(set!=null){
                                    set.clear();
                                }

                                JSONArray jsonArray =jsonResponse.getJSONArray("data");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject =jsonArray.getJSONObject(i);
                                    String productId=jsonObject.getString("product_id");
                                    MainActivity.fav.add(productId);
                                }

                                set.addAll(MainActivity.fav);

                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putStringSet("key", set);
                                editor.commit();
                                Toast.makeText(ProductDetail.this,message,Toast.LENGTH_LONG).show();

//                                for (Iterator<String> it = set.iterator(); it.hasNext(); ) {
//                                    String f = it.next();
//                                    if (f.equals(product_id)){
//                                        Toast.makeText(getApplicationContext(),""+f,Toast.LENGTH_LONG).show();
//                                    }else{
//                                        Toast.makeText(getApplicationContext(),""+f,Toast.LENGTH_LONG).show();
//                                    }
//
//                                }


//                            case "alreadySetFavoriteItem":
//                                String favouriteStatus1 = jsonResponse.getString("message");
//                                Intent changedata1 =new Intent();
//                                changedata1.putExtra("ChangesDone","true");
//                                setResult(12, changedata1);
//                                switch (favouriteStatus1){
//                                    case "added":
//                                        ImageView favorite = (ImageView) findViewById(R.id.img_favorite);
//                                        favorite.setImageResource(R.drawable.favorite_product);
//                                        break;
//                                    default:
//                                        ImageView defaultImage = (ImageView) findViewById(R.id.img_favorite);
//                                        defaultImage.setImageResource(R.drawable.unfavorite_product);
//                                }
//                                rating_changedata.putExtra("MY_ALREADY_FAVOURITE", product_id);
//                                break;
//                            default:
                        }
                        setResult(12, rating_changedata);
                        break;
//                    case "2":
//                        switch (taskIdentifier){
//                            case "addToCart":
//                                String cartStatus = jsonResponse.getString("message");
//                                JSONObject jObj = jsonResponse.getJSONObject("data");
//                                String noOfItems = jObj.getString("noOfItems");
//                                String shoppingcart_id = jObj.getString("shoppingcart_id");
//
//                                updateHotCount(Integer.parseInt(noOfItems));
//                                Intent count=new Intent();
//                                count.putExtra("updateCount",""+noOfItems);
//                                setResult(12, count);
//                                NatureSouqPrefrences.setShoppingcartId(ProductDetail.this, shoppingcart_id);
//                                NatureSouqPrefrences.setCartCounter(ProductDetail.this, noOfItems);
//
//                                Toast.makeText(getApplicationContext(), cartStatus, Toast.LENGTH_SHORT).show();
//                                break;
//                            case "alreadySetFavoriteItem":
//                                String favouriteStatus1 = jsonResponse.getString("message");
//                                Toast.makeText(getApplicationContext(), favouriteStatus1, Toast.LENGTH_SHORT).show();
//                                break;
//                        }
//                        break;
//                    case "3":
//                        switch (taskIdentifier){
//                            case "alreadySetFavoriteItem":
//                                String favouriteStatus1 = jsonResponse.getString("message");
//                                Toast.makeText(getApplicationContext(), favouriteStatus1, Toast.LENGTH_SHORT).show();
//                                break;
//                        }
//                        break;
                    default:
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                        break;
                }

                if(progressBar!=null)
                    progressBar.setVisibility(View.GONE);

            }catch (Exception e){
                if(progressBar!=null)
                    progressBar.setVisibility(View.GONE);
                e.printStackTrace();
            }
        }
    }

    public class ImageAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
        Context context;
        String urlArray[];

        ImageAdapter(Context context, String[] urlArr) {
            this.context = context;
            this.urlArray = new String[urlArr.length];

            for ( int arrlen = 0 ; arrlen < this.urlArray.length; arrlen++) {
                this.urlArray[arrlen] = urlArr[arrlen];
            }
        }

        @Override
        public int getCount() {
            return urlArray.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            //Load images using Picasso .
            if ( !TextUtils.isEmpty(urlArray[position])) {
                progressBar.setVisibility(View.VISIBLE);
                Picasso.with(getApplicationContext()).load(urlArray[position]).error(R.drawable.placeholder_detail).
                        placeholder(R.drawable.placeholder_pdoduct_image).into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            } else {
                //progress.setVisibility(View.VISIBLE);
                Picasso.with(getApplicationContext()).load(R.drawable.placeholder_pdoduct_image).resize(96, 96).into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.GONE);
                    }
                });

            }

            ((ViewPager) container).addView(imageView, 0);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageSelected(int position) {
            //circleAnimImageView.setImageResource(anim_circle[position]);
        }
    }

//    @Override
//    protected void onDestroy() {
//        NatureSouqPrefrences.getSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
//        super.onDestroy();
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String counter= NatureSouqPrefrences.getCartCounter(ProductDetail.this);
        if(!TextUtils.isEmpty(counter))
            updateHotCount(Integer.parseInt(counter));
        else
            updateHotCount(0);

        if(data!=null){
            if(resultCode==3){
                String value=data.getStringExtra("status");
                if(!TextUtils.isEmpty(value)){
                    if(value.equalsIgnoreCase("success")){
                        finish();
                    }
                }
            }

        }



//        if(requestCode == resultCode){
//            String requiredValue = data.getStringExtra("updateCount");
//            if(!TextUtils.isEmpty(requiredValue)){
//
//                updateHotCount(Integer.parseInt(requiredValue));
//                Intent i =new Intent();
//                i.putExtra("updateCount", requiredValue);
//                setResult(12,i);
//                finish();
//            }
//
//        }
    }

}
