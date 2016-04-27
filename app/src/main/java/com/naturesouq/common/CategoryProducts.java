package com.naturesouq.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.naturesouq.R;
import com.naturesouq.adapter.CategoryProductsBaseAdapter;
import com.naturesouq.model.CategoryProductsItem;
import com.naturesouq.model.HomeDataProvider;
import com.naturesouq.navigation.HomeFragment;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Created by SI_Android_Binit on 10/21/2015.
 */

public class CategoryProducts extends Activity {

    private static final String BEST_SELLER_URL = Utility.baseURL + "categoryProductList.php";
    private static final String DEALS_OF_THE_DAY_URL = Utility.baseURL + "deals.php";
    private static final String SORT_BY_NEW_PRODUCT_URL = Utility.baseURL + "sortby.php";
    private static final String PRODUCT_FILTER_URL = Utility.baseURL + "gfilter.php";
    private static final int REQUEST_FILTER_CODE = 200;
    public static final int RESULT_FILTER_CODE = 201;
    RecyclerView listView;
    CategoryProductsBaseAdapter adapter;
    LinkedList<CategoryProductsItem> gridArray;
    ProgressBar progressBar;
    private int cartitemcount = 0;
    private TextView catagory, sorytby, countTextView = null;
    Activity activity;
    String categoryId, cat, compareType, productprice;
    TextView newproducts, popularity, price_range_HighToLow, price_range_LowToHigh, cancel;
    BestSellerTask bestSellerTask;
    LinearLayout filter, sortBy, main;
    ImageView dealplaceholder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_products);
        main = (LinearLayout) findViewById(R.id.main);
        dealplaceholder = (ImageView) findViewById(R.id.dealplaceholder);

        activity = this;
        Intent intent = getIntent();
        cat = intent.getStringExtra("cat");

        if (!TextUtils.isEmpty(cat)) {
            categoryId = intent.getStringExtra("categoryId");
        } else {
            cat = "Deals of the Day";
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        catagory = (TextView) findViewById(R.id.catagoryItem);
        sorytby = (TextView) findViewById(R.id.sortbyItem);
        filter = (LinearLayout) findViewById(R.id.filter);
        sortBy = (LinearLayout) findViewById(R.id.sortBy);
        filter.setVisibility(View.GONE);
        sortBy.setVisibility(View.GONE);
        catagory.setVisibility(View.GONE);
        sorytby.setVisibility(View.GONE);


        main.setVisibility(View.GONE);
        listView = (RecyclerView) findViewById(R.id.grid);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CategoryProducts.this, 2, GridLayoutManager.VERTICAL, false);
        //NpaGridLayoutManager gridLayoutManager=new NpaGridLayoutManager(CategoryProducts.this,2,GridLayoutManager.VERTICAL,false);
        listView.addItemDecoration(new VerticalSpaceItemDecoration(8));
        listView.setLayoutManager(gridLayoutManager);

        listView.setNestedScrollingEnabled(false);
        listView.smoothScrollBy(0, 0);

        gridArray = new LinkedList<CategoryProductsItem>();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle(cat);

        try {
            JSONObject jobj = new JSONObject();
            if (!cat.equalsIgnoreCase("Deals of the Day")) {
                jobj.put("category_id", categoryId);
                jobj.put("apikey", "naturesouq#123@apikey");
                bestSellerTask = new BestSellerTask(jobj);
                bestSellerTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, BEST_SELLER_URL);
            } else {
                jobj.put("apikey", "naturesouq#123@apikey");
                bestSellerTask = new BestSellerTask(jobj);
                bestSellerTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DEALS_OF_THE_DAY_URL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        catagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent filterIntent = new Intent(CategoryProducts.this, FilterItemActivity.class);
                filterIntent.putExtra("categoryId", categoryId);
                filterIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(filterIntent, REQUEST_FILTER_CODE);
            }
        });

        sorytby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create custom dialog object
                final Dialog convertView = new Dialog(CategoryProducts.this);
                // Include dialog.xml file
                convertView.setContentView(R.layout.dialog_sortby);
                // Set dialog title
                convertView.setTitle("Sort By...");
                popularity = (TextView) convertView.findViewById(R.id.popularity_lbl);
                price_range_HighToLow = (TextView) convertView.findViewById(R.id.price_range_HighToLow_lbl);
                price_range_LowToHigh = (TextView) convertView.findViewById(R.id.price_range_LowToHigh_lbl);
                newproducts = (TextView) convertView.findViewById(R.id.newproduct_lbl);
                cancel = (TextView) convertView.findViewById(R.id.cancel_lbl);

                Typeface face1 = Typeface.createFromAsset(getAssets(), "font/Lato_Bold.ttf");
                newproducts.setTypeface(face1);
                popularity.setTypeface(face1);
                price_range_HighToLow.setTypeface(face1);
                price_range_LowToHigh.setTypeface(face1);
                cancel.setTypeface(face1);
                // set values for custom dialog components - text, image and button
                convertView.show();

                newproducts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            JSONObject jobj = new JSONObject();
                            jobj.put("sortby", "newproducts");
                            jobj.put("category_id", categoryId);
                            jobj.put("apikey", "naturesouq#123@apikey");
                            bestSellerTask = new BestSellerTask(jobj);
                            bestSellerTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, SORT_BY_NEW_PRODUCT_URL);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        convertView.dismiss();
                    }
                });
                popularity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            JSONObject jobj = new JSONObject();
                            jobj.put("sortby", "popularity");
                            jobj.put("category_id", categoryId);
                            jobj.put("apikey", "naturesouq#123@apikey");
                            bestSellerTask = new BestSellerTask(jobj);
                            bestSellerTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, SORT_BY_NEW_PRODUCT_URL);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        convertView.dismiss();
                    }
                });
                price_range_HighToLow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (gridArray.size() >= 1) {
                            compareType = "HighToLow";
                            Collections.sort(gridArray, new CustomComparator());
                            for (int i = 0; i < gridArray.size(); i++) {
                                adapter.setBestSellerProductsItems(gridArray);
                                adapter.notifyDataSetChanged();
                            }
                        }
                        convertView.dismiss();
                    }
                });
                price_range_LowToHigh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (gridArray.size() >= 1) {
                            compareType = "LowToHigh";
                            Collections.sort(gridArray, new CustomComparator());
                            for (int i = 0; i < gridArray.size(); i++) {
                                adapter.setBestSellerProductsItems(gridArray);
                                adapter.notifyDataSetChanged();
                            }
                        }
                        convertView.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        convertView.cancel();
                    }
                });
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        String counter = NatureSouqPrefrences.getCartCounter(CategoryProducts.this);
        if (!TextUtils.isEmpty(counter)) {
            updateHotCount(Integer.parseInt(counter));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //Cancel AsyncTask when user presses back button from Device.
        if (bestSellerTask != null)
            bestSellerTask.cancel(true);

    }

    public class BestSellerTask extends AsyncTask<String, Void, String> {
        JSONObject obj;
        boolean isRunning = true;

        //Show registration progress.
        public BestSellerTask(JSONObject jobj) {
            obj = jobj;
        }

        @Override
        protected String doInBackground(String... urls) {
            return ConnectAsynchronously.connectAsynchronously(urls[0], obj);
        }

        @Override
        protected void onPreExecute() {

            try {

                progressBar.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }

            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String result) {
            try {

                if (isRunning) {
                    JSONObject jsonResponse = new JSONObject(result);
                    String status = jsonResponse.getString("status");
                    String message = jsonResponse.getString("message");
                    if (status.equals("1")) {
                        //Clean gridArray
                        gridArray.clear();
                        JSONArray jsonArray = jsonResponse.getJSONArray("data");

                        //JSONArray jsonArray = new JSONArray();
                        if (jsonArray.length() > 0) {
                            for (int itemCount = 0; itemCount < jsonArray.length(); itemCount++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(itemCount);
                                String product_id = jsonObject.getString("product_id");
                                String type = jsonObject.getString("type");
                                String set = jsonObject.getString("set");
                                String sku = jsonObject.getString("sku");
                                String position = jsonObject.getString("position");
                                String rating = jsonObject.getString("rating");
                                String review = jsonObject.getString("review");
                                String price = jsonObject.getString("price");
                                String name = jsonObject.getString("name");
                                String description = jsonObject.getString("description");
                                String short_description = jsonObject.getString("short_description");
                                String image_url = jsonObject.getString("image_url");
                                String SmallImage = jsonObject.getString("SmallImage");
                                String Thumbnail = jsonObject.getString("Thumbnail");

                                JSONArray categoryGallery = jsonObject.getJSONArray("gallery");
                                JSONArray categorySpecificationKeyArray = jsonObject.getJSONArray("specificationKey");
                                JSONArray categorySpecificationArray = jsonObject.getJSONArray("specification");

                                CategoryProductsItem bestseller = new CategoryProductsItem(product_id, type, set, sku, position, rating, review, price, name, description, short_description, image_url, SmallImage, Thumbnail, categoryGallery, categorySpecificationKeyArray, categorySpecificationArray);
                                gridArray.add(bestseller);
                            }
                            adapter = new CategoryProductsBaseAdapter(gridArray, CategoryProducts.this);//                    adapter.setViewClickListener(TrailsFragment.this);
                            listView.setAdapter(adapter);

                            if (gridArray.size() > 0 && (!cat.equalsIgnoreCase("Deals of the Day"))) {
                                catagory.setVisibility(View.VISIBLE);
                                sorytby.setVisibility(View.VISIBLE);
                                filter.setVisibility(View.VISIBLE);
                                sortBy.setVisibility(View.VISIBLE);
                                main.setVisibility(View.VISIBLE);
                            }

                            listView.addOnItemTouchListener(
                                    new RecyclerItemClickListener(CategoryProducts.this, new RecyclerItemClickListener.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            // do whatever
                                            CategoryProductsItem feedItem = gridArray.get(position);
                                            Intent detailIntent = new Intent(CategoryProducts.this, ProductDetail.class);
                                            detailIntent.putExtra("GridViewToDetail", "particularCategory");
                                            detailIntent.putExtra("feedItemCategoryDetail", feedItem);
                                            detailIntent.putExtra("product_id", feedItem.getProduct_id());
                                            detailIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                            HomeFragment.provider1 = new HomeDataProvider(feedItem.getHomeGallery());
                                            HomeFragment.provider1.setProductGallery(feedItem.getHomeGallery());
                                            HomeFragment.provider2 = new HomeDataProvider(feedItem.getHomeSpecificationKey());
                                            HomeFragment.provider2.setProductSpecificationKey(feedItem.getHomeSpecificationKey());
                                            HomeFragment.provider3 = new HomeDataProvider(feedItem.getHomeSpecification());
                                            HomeFragment.provider3.setProductSpecification(feedItem.getHomeSpecification());
                                            startActivityForResult(detailIntent, 12);
                                        }
                                    })
                            );
                        } else {
                            if (cat.equals("Deals of the Day")) {
                                //Deal of the day placeholder .
                                dealplaceholder.setVisibility(View.VISIBLE);
                                dealplaceholder.setImageResource(R.drawable.deal_placeholder);
                            } else {
                                //No Product Placeholder
                                dealplaceholder.setVisibility(View.VISIBLE);
                                dealplaceholder.setImageResource(R.drawable.noproduct_placeholder);
                            }
                        }
                    } else if (status.equals("2")) {
                        if (cat.equals("Deals of the Day")) {
                            //Deal of the day placeholder .
                            dealplaceholder.setVisibility(View.VISIBLE);
                            dealplaceholder.setImageResource(R.drawable.deal_placeholder);
                        } else {
                            //No Product Placeholder
                            dealplaceholder.setVisibility(View.VISIBLE);
                            dealplaceholder.setImageResource(R.drawable.noproduct_placeholder);
                        }
                    } else if (status.equals("0")) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                //Enable Filter and Sort By.
                catagory.setEnabled(true);
                sorytby.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
                dealplaceholder.setVisibility(View.INVISIBLE);
                e.printStackTrace();
            }
            progressBar.setVisibility(View.INVISIBLE);
            super.onPostExecute(result);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            isRunning = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bestseller_menuitem, menu);

        final View menu_hotlist = menu.findItem(R.id.cart).getActionView();
        countTextView = (TextView) menu_hotlist.findViewById(R.id.hotlist_hot);
        String counter = NatureSouqPrefrences.getCartCounter(this);
        if (!TextUtils.isEmpty(counter)) {
            updateHotCount(Integer.parseInt(counter));
        } else {
            countTextView.setVisibility(View.INVISIBLE);
        }

        new MyMenuItemStuffListener(menu_hotlist, "Show hot message") {
            @Override
            public void onClick(View v) {
                //onHotlistSelected();
                //Open New Activity here .
                Intent cartintent = new Intent(getApplicationContext(), Cart.class);
                cartintent.putExtra("cartitemcount", cartitemcount);
                startActivityForResult(cartintent, 10);
            }
        };

        return true;
    }


    // call the updating code on the main thread,
// so we can call this asynchronously
    public void updateHotCount(final int new_hot_number) {
        cartitemcount = new_hot_number;
        if (countTextView == null)
            return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (new_hot_number == 0) {
                    countTextView.setVisibility(View.INVISIBLE);
                } else {
                    String counter = NatureSouqPrefrences.getCartCounter(CategoryProducts.this);
                    countTextView.setVisibility(View.VISIBLE);
                    countTextView.setText(counter);
                }
            }
        });
    }

    public class CustomComparator implements Comparator<CategoryProductsItem> {
        int returnValue;

        @Override
        public int compare(CategoryProductsItem price, CategoryProductsItem nextprice) {
            if (compareType.equals("HighToLow")) {
                Float price1 = Float.parseFloat(price.getPrice());
                Float price2 = Float.parseFloat(nextprice.getPrice());
                returnValue = price2.compareTo(price1);
            } else if (compareType.equals("LowToHigh")) {
                Float price1 = Float.parseFloat(price.getPrice());
                Float price2 = Float.parseFloat(nextprice.getPrice());
                returnValue = price1.compareTo(price2);
            }
            return returnValue;
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
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
                startActivityForResult(cartintent, 10);
                break;

            case android.R.id.home:

                //Cancel AsyncTask when user presses back button from App.
                if (bestSellerTask != null)
                    bestSellerTask.cancel(true);

                finish();
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FILTER_CODE && resultCode == RESULT_FILTER_CODE) {

            String apply_filter = data.getStringExtra("apply_filter");
            String cat_id = data.getStringExtra("cat_id");
            int current_page = data.getIntExtra("current_page", 1);

            try {
                //JSONObject jObj = new JSONObject(apply_filter);
                JSONArray jArr = new JSONArray(apply_filter);
                //jArr.put(jObj);
                JSONObject jobj = new JSONObject();
                jobj.put("apikey", "naturesouq#123@apikey");
                jobj.put("apply_filter", jArr);
                jobj.put("cat_id", cat_id);
                jobj.put("current_page", current_page);

                new BestSellerTask(jobj).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, PRODUCT_FILTER_URL);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            String counter = NatureSouqPrefrences.getCartCounter(CategoryProducts.this);
            if (!TextUtils.isEmpty(counter)) {
                updateHotCount(Integer.parseInt(counter));
            }
        }

    }

    @Override
    protected void onDestroy() {
        //  NatureSouqPrefrences.getSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();

        if (bestSellerTask != null)
            bestSellerTask.cancel(true);


    }
}
