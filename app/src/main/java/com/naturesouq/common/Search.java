package com.naturesouq.common;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;
import com.naturesouq.R;
import com.naturesouq.adapter.SearchBaseAdapter;
import com.naturesouq.adapter.SearchHistoryBaseAdapter;
import com.naturesouq.model.HomeDataProvider;
import com.naturesouq.model.SearchHistoryListItem;
import com.naturesouq.model.SearchListItem;
import com.naturesouq.navigation.HomeFragment;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SI_Android_Binit on 2/8/2016.
 */
public class Search extends Activity implements SearchBaseAdapter.ViewClickListener {

    private static final String SEARCH_LIST_URL = Utility.baseURL+"productsearch.php";
    RecyclerView mySearch;
    LinearLayoutManager layoutManager;
    List<SearchListItem> rowItems;
    SearchListItem item;
    SearchBaseAdapter searchAdapter;
    ProgressBar bar;
    int cartitemcount ;
    SearchView searchView;
    String[] storeHistory;
    SearchHistoryBaseAdapter searchHistoryBaseAdapter;
    ArrayList<SearchHistoryListItem> searchHistoryListItems;
    String query;
    JSONArray searchSpecificationArray,searchGallery,searchSpecificationKeyArray;
    ImageView placeHolder;
    LinearLayout main ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle("Search");
        bar = (ProgressBar) findViewById(R.id.progressBar);
        placeHolder=(ImageView)findViewById(R.id.placeHolder);
        main = (LinearLayout)findViewById(R.id.main);

        placeHolder.setVisibility(View.INVISIBLE);

        Intent intent = getIntent() ;
        cartitemcount = intent.getIntExtra("cartitemcount", 0);

        //delete = (Button)findViewById(R.id.addNewAddress);
        mySearch = (RecyclerView)findViewById(R.id.history_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(Search.this, 2, GridLayoutManager.VERTICAL, false);
        mySearch.addItemDecoration(new VerticalSpaceItemDecoration(8));
        mySearch.setLayoutManager(gridLayoutManager);
        mySearch.setNestedScrollingEnabled(false);
        mySearch.smoothScrollBy(0, 0);


        /*//listView = (RecyclerView) findViewById(R.id.grid);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CategoryProducts.this, 2, GridLayoutManager.VERTICAL, false);
        //NpaGridLayoutManager gridLayoutManager=new NpaGridLayoutManager(CategoryProducts.this,2,GridLayoutManager.VERTICAL,false);
        listView.addItemDecoration(new VerticalSpaceItemDecoration(8));
        listView.setLayoutManager(gridLayoutManager);
        listView.setNestedScrollingEnabled(false);
        listView.smoothScrollBy(0, 0);*/


        // Typeface face1= Typeface.createFromAsset(getAssets(), "font/Lato_Regular.ttf");
        Typeface face2= Typeface.createFromAsset(getAssets(), "font/Lato_Black.ttf");
        //delete.setTypeface(face2);
        rowItems = new ArrayList<SearchListItem>();

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView)findViewById(R.id.searchView);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                   // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                    JSONObject jobj = new JSONObject();
                    jobj.put("searchkeyword", s);
                    jobj.put("apikey", "naturesouq#123@apikey");

                    new SearchItemTask(jobj).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,SEARCH_LIST_URL);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                Intent intent  = getIntent();
                if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                    //query = intent.getStringExtra(SearchManager.QUERY);
                    SearchRecentSuggestions suggestions = new SearchRecentSuggestions(Search.this, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
                    suggestions.saveRecentQuery(s, null);
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });

    }

    @Override
    public void onItemClicked(int position) {
        SearchListItem feedItem = searchAdapter.getSearchItems().get(position);
        Intent detailPage = new Intent(Search.this, ProductDetail.class);
        detailPage.putExtra("feedItemSearch", feedItem);
        detailPage.putExtra("cartitemcount", cartitemcount);
        detailPage.putExtra("GridViewToDetail", "mySearch");
        detailPage.putExtra("product_id", feedItem.getProduct_id());
        detailPage.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        HomeFragment.provider1=new HomeDataProvider(feedItem.getSearchGallery());
        HomeFragment.provider1.setProductGallery(feedItem.getSearchGallery());
        HomeFragment.provider2=new HomeDataProvider(feedItem.getSearchSpecificationKey());
        HomeFragment.provider2.setProductSpecificationKey(feedItem.getSearchSpecificationKey());
        HomeFragment.provider3=new HomeDataProvider(feedItem.getSearchSpecification());
        HomeFragment.provider3.setProductSpecification(feedItem.getSearchSpecification());
        startActivityForResult(detailPage, 12);
    }

    public class SearchItemTask extends AsyncTask<String, Void, String> {
        JSONObject obj;

        //Show search progress.
        public SearchItemTask(JSONObject jobj) {
            obj = jobj;
        }

        @Override
        protected String doInBackground(String... urls) {
            return ConnectAsynchronously.connectAsynchronously(urls[0], obj);
        }

        @Override
        protected void onPreExecute() {
            try {
                if (bar != null){
                    bar.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            rowItems = null;
            try {
                JSONObject jsonResponse = new JSONObject(result);
                String status = jsonResponse.getString("status");
                String message = jsonResponse.getString("message");

                switch (status) {
                    case "0":
                        Toast.makeText(Search.this, message, Toast.LENGTH_SHORT).show();
                        break;
                    case "1":
                        JSONArray jsonArray = jsonResponse.getJSONArray("data");
                        rowItems = new ArrayList<SearchListItem>(jsonArray.length());

                        //Get data from Search web service url .
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
                            String shortDescription = jsonObject.getString("short_description");
                            String description = jsonObject.getString("description");
                            String SmallImage = jsonObject.getString("SmallImage");

                            searchGallery=jsonObject.getJSONArray("gallery");
                            searchSpecificationKeyArray=jsonObject.getJSONArray("specificationKey");
                            searchSpecificationArray=jsonObject.getJSONArray("specification");

                            SearchListItem item = new SearchListItem(product_id, sku, name, set, type, rating, review, price, image_url, shortDescription, description, SmallImage,searchGallery,searchSpecificationKeyArray,searchSpecificationArray);
                            rowItems.add(item);
                        }

                        if (rowItems.size()==0){
                            //set PalceHolder Image
                            placeHolder.setVisibility(View.VISIBLE);
                            mySearch.setAdapter(null);
                            //main.setVisibility(View.GONE);

                        }else {
                            //main.setVisibility(View.VISIBLE);
                            placeHolder.setVisibility(View.INVISIBLE);
                            searchAdapter = new SearchBaseAdapter(Search.this.getApplicationContext(), rowItems);
                            searchAdapter.setViewClickListener(Search.this);
                            mySearch.setAdapter(searchAdapter);
                        }
                        break;
                    default:

                }
                if (bar != null){
                    bar.setVisibility(View.GONE);
                }
            }catch (Exception e) {
                if (bar != null){
                    bar.setVisibility(View.GONE);
                }
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(result);
        }
    }

    public void onSubmit(View view){
        //do some things
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}
