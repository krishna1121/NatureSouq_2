package com.naturesouq.common;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.naturesouq.R;
import com.naturesouq.adapter.MyFavoriteAdapter;
import com.naturesouq.model.HomeDataProvider;
import com.naturesouq.model.MyFavoriteListItem;
import com.naturesouq.navigation.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by SI_Android_Binit on 12/28/2015.
 */
public class MyFavorite extends Activity implements MyFavoriteAdapter.ViewClickListener {

    private static final String LOG_TAG = MyFavorite.class.getSimpleName();

    private static final String FAVOURITE_LIST_URL = Utility.baseURL + "whishlistproduct.php";
    private static final String RemoveFromFev_URL = Utility.baseURL + "addRemoveFavoritet.php";
    RecyclerView myFavorite;
    Button delete;
    LinearLayoutManager layoutManager;
    List<MyFavoriteListItem> rowItems;
    MyFavoriteListItem item;
    MyFavoriteAdapter favoriteAdapter;
    ProgressBar bar;
    String customer_id;
    int cartitemcount;
    ImageView favoriteplaceholder;
    JSONArray myFavoriteSpecificationArray, myFavoriteGallery, myFavoriteSpecificationKeyArray;
    int pos;
    View hideFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_favorite);

        bar = (ProgressBar) findViewById(R.id.progressBar);
        favoriteplaceholder = (ImageView) findViewById(R.id.favoriteplaceholder);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle("My Favorite");

        Intent intent = getIntent();
        cartitemcount = intent.getIntExtra("cartitemcount", 0);

        //delete = (Button)findViewById(R.id.addNewAddress);
        myFavorite = (RecyclerView) findViewById(R.id.my_favorite_recycler);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myFavorite.setLayoutManager(layoutManager);
        myFavorite.addItemDecoration(new VerticalSpaceItemDecoration(5));

        // Typeface face1= Typeface.createFromAsset(getAssets(), "font/Lato_Regular.ttf");
        Typeface face2 = Typeface.createFromAsset(getAssets(), "font/Lato_Black.ttf");
        //delete.setTypeface(face2);

        boolean networkStatus = new DialogBox(this).checkInternetConnection();
        rowItems = new ArrayList<MyFavoriteListItem>();
        if (networkStatus) {
            try {
                customer_id = NatureSouqPrefrences.getCustomer_id(this);

                JSONObject jobj = new JSONObject();
                jobj.put("customer_id", customer_id);
                jobj.put("apikey", "naturesouq#123@apikey");

                new FavoriteItemTask(jobj, "Total_List").execute(FAVOURITE_LIST_URL);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemClicked(int position) {
        MyFavoriteListItem feedItem = favoriteAdapter.getFavoriteItems().get(position);
        Intent detailPage = new Intent(MyFavorite.this, ProductDetail.class);
        detailPage.putExtra("feedItemMyFavorite", feedItem);
        detailPage.putExtra("cartitemcount", cartitemcount);
        detailPage.putExtra("GridViewToDetail", "myFavorite");
        detailPage.putExtra("product_id", feedItem.getProduct_id());
        detailPage.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        HomeFragment.provider1 = new HomeDataProvider(feedItem.getHomeGallery());
        HomeFragment.provider1.setProductGallery(feedItem.getHomeGallery());
        HomeFragment.provider2 = new HomeDataProvider(feedItem.getHomeSpecificationKey());
        HomeFragment.provider2.setProductSpecificationKey(feedItem.getHomeSpecificationKey());
        HomeFragment.provider3 = new HomeDataProvider(feedItem.getHomeSpecification());
        HomeFragment.provider3.setProductSpecification(feedItem.getHomeSpecification());
        startActivityForResult(detailPage, 12);
    }

    final View viewFinal[] = {null};
    MyFavoriteListItem itemsFinal = new MyFavoriteListItem();

    @Override
    public void onSettingClicked(int position, MyFavoriteListItem item, View view) {
        hideFrame = view;

        if (viewFinal[0] != null) {
            viewFinal[0].setVisibility(View.INVISIBLE);
            itemsFinal.setStateOfDelete(false);
            favoriteAdapter.notifyDataSetChanged();
        }
        item.setStateOfDelete(true);
        if (item.getStateOfDelete() == true) {
            view.setVisibility(View.VISIBLE);
        }
        viewFinal[0] = view;
        itemsFinal = item;
    }

    @Override
    public void onRemoveFromFavourite(int position, MyFavoriteListItem item, View view) {
        pos = position;
        hideFrame = view;
        //Toast.makeText(getApplicationContext(),""+pos,Toast.LENGTH_LONG).show();
        String customerId = NatureSouqPrefrences.getCustomer_id(MyFavorite.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apikey", "naturesouq#123@apikey");
            jsonObject.put("customer_id", customerId);
            jsonObject.put("product_id", item.getProduct_id());

            new FavoriteItemTask(jsonObject, "REMOVE_FROM_FEV").execute(RemoveFromFev_URL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class FavoriteItemTask extends AsyncTask<String, Void, String> {
        JSONObject obj;
        String actionValue;

        //Show registration progress.
        public FavoriteItemTask(JSONObject jobj, String actionValue) {
            obj = jobj;
            this.actionValue = actionValue;
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
                        Toast.makeText(MyFavorite.this, message, Toast.LENGTH_SHORT).show();
                        break;
                    case "1":
                        if (actionValue.equalsIgnoreCase("Total_List")) {

                            JSONArray jsonArray = jsonResponse.getJSONArray("data");
                            if (jsonArray.length() > 0) {
                                //Get data from MyFavorite web service url .
                                for (int itemCount = 0; itemCount < jsonArray.length(); itemCount++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(itemCount);

                                    String product_id = jsonObject.getString("product_id");
                                    String sku = jsonObject.getString("sku");
                                    String name = jsonObject.getString("name");
                                    String set = jsonObject.getString("set");
                                    String type = jsonObject.getString("type");
                                    String rating = jsonObject.getString("rating");
                                    String review = jsonObject.getString("review");
                                    String price = jsonObject.getString("price");
                                    String image_url = jsonObject.getString("image_url");
                                    String shortDescription = jsonObject.getString("shortDescription");

                                    myFavoriteGallery = jsonObject.getJSONArray("gallery");
                                    myFavoriteSpecificationKeyArray = jsonObject.getJSONArray("specificationKey");
                                    myFavoriteSpecificationArray = jsonObject.getJSONArray("specification");

                                    item = new MyFavoriteListItem(product_id, sku, name, set, type, rating, review, price, image_url, shortDescription, false, myFavoriteGallery, myFavoriteSpecificationKeyArray, myFavoriteSpecificationArray);
                                    rowItems.add(item);
                                }

                                favoriteAdapter = new MyFavoriteAdapter(MyFavorite.this.getApplicationContext(), rowItems);
                                favoriteAdapter.setViewClickListener(MyFavorite.this);
                                myFavorite.setAdapter(favoriteAdapter);
                            } else {
                                favoriteplaceholder.setVisibility(View.VISIBLE);
                            }

                        } else if (actionValue.equalsIgnoreCase("REMOVE_FROM_FEV")) {
                            Set<String> set;
                            SharedPreferences sharedPreferences = null;
                            set = new HashSet<String>();
                            sharedPreferences = getApplicationContext().getSharedPreferences("com.naturesouq.common", MODE_PRIVATE);
                            set = sharedPreferences.getStringSet("key", new HashSet<String>());

                            if (set != null) {

                                set.clear();
                            }
                            MainActivity.fav.clear();
                            hideFrame.setVisibility(View.INVISIBLE);
                            rowItems.remove(pos);
                            favoriteAdapter.notifyDataSetChanged();

                            if(rowItems.size()==0){
                                favoriteplaceholder.setVisibility(View.VISIBLE);
                                favoriteplaceholder.setImageResource(R.drawable.favoriteplaceholder);
                            }else{
                                favoriteplaceholder.setVisibility(View.INVISIBLE);
                            }

                            for (int i = 0; i < rowItems.size(); i++) {
                                String productId = rowItems.get(i).getProduct_id();
                                MainActivity.fav.add(productId);
                            }
                            set.addAll(MainActivity.fav);

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putStringSet("key", set);
                            editor.commit();
                            Toast.makeText(MyFavorite.this, message, Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case "2":
                        favoriteplaceholder.setVisibility(View.VISIBLE);
                        favoriteplaceholder.setImageResource(R.drawable.favoriteplaceholder);
                        //Toast.makeText(MyFavorite.this, message, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (hideFrame != null)
            hideFrame.setVisibility(View.INVISIBLE);
        if (requestCode == 12 && resultCode == 12) {
            String IsRefreshPage = data.getStringExtra("IsRefreshPage");
            if (!TextUtils.isEmpty(IsRefreshPage)) {
                if (IsRefreshPage.equalsIgnoreCase("true")) {
                    try {
                        if (rowItems.size() >= 1)
                            rowItems.clear();
                        customer_id = NatureSouqPrefrences.getCustomer_id(this);

                        JSONObject jobj = new JSONObject();
                        jobj.put("customer_id", customer_id);
                        jobj.put("apikey", "naturesouq#123@apikey");

                        new FavoriteItemTask(jobj, "Total_List").execute(FAVOURITE_LIST_URL);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }
}
