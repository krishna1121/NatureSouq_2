package com.naturesouq.common;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.naturesouq.R;
import com.naturesouq.adapter.ExpandableListAdapter;
import com.naturesouq.model.HomeDataProvider;
import com.naturesouq.model.NavigationItem;
import com.naturesouq.navigation.HomeFragment;
import com.naturesouq.navigation.MyAccount;
import com.naturesouq.navigation.ChildFragment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class MainActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitleSection;
    private CharSequence mTitleApp;
    public Fragment mFragment = null;
    static MainActivity drawerActivity;
    ExpandableListView expandableListView;
    public static MainActivity instance;
    //HashMap expandableListDetail;
    ExpandableListAdapter expandableListAdapter;
    ArrayList<String> expandableListTitle;
    ArrayList<NavigationItem> childListTitle;
    LinkedHashMap<String, List<NavigationItem>> expandableListDetail;
    int[] navIconArray;
    final String HOMEURL = Utility.baseURL+"home.php";
    private int cartitemcount = 0;
    private TextView countTextView = null;
    ProgressBar progressBar;
    String product_id = "", name = "";
    public static HomeDataProvider homeDataProvider;
    static ArrayList<String> fav;
    private static final String COUNTRIES_LIST_URL = Utility.baseURL + "countrydata.php" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean networkStatus = new DialogBox(this).checkInternetConnection();
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        instance = this;
        childListTitle = new ArrayList<>();
        List<NavigationItem> home = new ArrayList<>();
        List<NavigationItem> myaccount = new ArrayList<>();
        List<NavigationItem> categories = new ArrayList<>();
        expandableListDetail = new LinkedHashMap<String, List<NavigationItem>>();
        expandableListDetail.put("Home", home);
        expandableListDetail.put("My Account", myaccount);
        expandableListDetail.put("CATEGORIES", categories);
        fav=new ArrayList<>();

        //Fire AddToCart webservice to get items added in webservice .
        if (networkStatus) {
            //Fire AddToCart webservice to get items added in webservice .
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("apikey", "naturesouq#123@apikey");
                new HomeTask(jsonObject ,"").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, HOMEURL);
                //Call countries list Url
                new HomeTask(jsonObject , "countriesList").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, COUNTRIES_LIST_URL);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        drawerActivity = this;

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        expandableListView = (ExpandableListView) findViewById(R.id.list_slidermenu);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expandableListView.setIndicatorBounds(350 - 43, 350 - GetPixelFromDips(2));
        } else {
            expandableListView.setIndicatorBoundsRelative(350 - 43, 350 - GetPixelFromDips(2));
        }

        expandableListTitle = new ArrayList(expandableListDetail.keySet());
        navIconArray = new int[]{R.drawable.home, R.drawable.account, R.drawable.account, R.drawable.women, R.drawable.monitor_tablet, R.drawable.perfume, R.drawable.men, R.drawable.electronics, R.drawable.furniture, R.drawable.tedy, R.drawable.sports};

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (groupPosition >= 3) {

                    try{
                       NavigationItem feedItem = (NavigationItem) parent.getExpandableListAdapter().getChild(groupPosition,childPosition);//.getItem(parent);//getChild(listPosition, expandedListPosition);
                       displayView(groupPosition, childPosition,feedItem.getId(),feedItem.getName());
                    }catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int position, long l) {
                if (position < 3) {
                    displayView(position, -1,"","");
                }

                return false;
            }
        });

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setHomeButtonEnabled(true);

        //  mDrawerList.setItemChecked(0, true);
        mTitleSection = getString(R.string.Home);
        mTitleApp = getTitle();

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.split_actionbar, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitleSection);
                invalidateOptionsMenu();
               
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle("NatureSouq");
                invalidateOptionsMenu();

            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String counter=NatureSouqPrefrences.getCartCounter(this);
        if(!TextUtils.isEmpty(counter)){
            updateHotCount(Integer.parseInt(counter));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final View menuOnMainActivity = menu.findItem(R.id.cart).getActionView();
        countTextView = (TextView) menuOnMainActivity.findViewById(R.id.hotlist_hot);
        updateHotCount(cartitemcount);

        new MyMenuItemStuffListener(menuOnMainActivity, "Show hot message") {
            @Override
            public void onClick(View v) {
                //onHotlistSelected();
                //Open New Activity here .
                Intent cartintent = new Intent(getApplicationContext(), Cart.class);
                cartintent.putExtra("cartitemcount", cartitemcount);
                startActivityForResult(cartintent, 3);
            }
        };

        return super.onCreateOptionsMenu(menu);
    }

    // call the updating code on the main thread,
    // so we can call this asynchronously
    public void updateHotCount(final int new_hot_number) {
        cartitemcount = new_hot_number;
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

   /* private void setNotifCount(int count){
        mNotifCount = count;
        invalidateOptionsMenu();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {

            case R.id.searchItem:
                Intent searchIntent = new Intent(MainActivity.this, Search.class);
                startActivity(searchIntent);
                break;

            case R.id.cart:
                //Open New Activity here .
//                Intent cartintent = new Intent(this, Cart.class);
//                cartintent.putExtra("cartitemcount", cartitemcount);
//                startActivityForResult(cartintent, 3);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayView(int position, int childPosition,String id,String Title) {
        Bundle bundle = new Bundle();
        switch (position) {

            case 0:
                mFragment = new HomeFragment();
                mTitleSection="Home";
                bundle.putInt("cartitemcount", cartitemcount);
                bundle.putString("product_id", product_id);
                mFragment.setArguments(bundle);
                break;

            case 1:
                mFragment = new MyAccount();
                mTitleSection="My Account";

                break;

            case 2:
                break;
            default:

                switch (childPosition) {
                    default:
                        mFragment = new ChildFragment();
                        mTitleSection=Title;
                        bundle.putString("ProductCategoryId", id);
                        mFragment.setArguments(bundle);
                        break;
                }

                break;
        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, mFragment).commit();

        getActionBar().setTitle(mTitleSection);

        mDrawerLayout.closeDrawer(expandableListView);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }


    public class HomeTask extends AsyncTask<String, Void, String> {
        JSONObject obj;
        String identifier ;


        public HomeTask(JSONObject jobj , String identifier) {
            obj = jobj;
            this.identifier = identifier ;
        }

        @Override
        protected String doInBackground(String... urls) {
            return ConnectAsynchronously.connectAsynchronously(urls[0], obj);
        }

        @Override
        protected void onPreExecute() {
            try {
                super.onPreExecute();

                if (progressBar != null)
                    progressBar.setVisibility(View.VISIBLE);

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

                    if(identifier.equals("countriesList")) {
                        JSONArray jsonArray = jsonResponse.getJSONArray("data");
                        ArrayList<CountriesList> countriesList = new ArrayList<>();

                        for(int countryCount = 0 ; countryCount < jsonArray.length() ; countryCount++){
                            JSONObject jsonObject = jsonArray.getJSONObject(countryCount) ;
                            String country_id = jsonObject.getString("country_id") ;
                            String name = jsonObject.getString("name");
                            String shipping_cost = jsonObject.getString("shipping_cost");
                            CountriesList countriesListItem = new CountriesList(name,country_id ,shipping_cost);
                            countriesList.add(countriesListItem) ;
                        }

                        //Set countriesList in Application class so that other activities can access it .
                        NatureSouqPrefrences.setCountriesList(countriesList);

                    }else{

                        JSONObject object = jsonResponse.getJSONObject("data");
                        JSONArray jsonArray = object.getJSONArray("category");

                        expandableListTitle = new ArrayList(expandableListDetail.keySet());
                        expandableListAdapter = new ExpandableListAdapter(MainActivity.this, expandableListTitle, expandableListDetail, navIconArray);
                        expandableListView.setAdapter(expandableListAdapter);

                        for (int navsize = 0; navsize < jsonArray.length(); navsize++) {

                            childListTitle=new ArrayList<>();
                            JSONObject jsonObject = jsonArray.getJSONObject(navsize);
                            String category_id = jsonObject.getString("category_id");


                            if(!category_id.equals("482")) {
                                String parent_id = jsonObject.getString("parent_id");
                                name = jsonObject.getString("name");
                                String is_active = jsonObject.getString("is_active");
                                String position = jsonObject.getString("position");
                                String level = jsonObject.getString("level");

                                expandableListTitle.add(name);

                                JSONArray jsonArray1 = jsonObject.getJSONArray("children");
                                int j = jsonArray1.length();

                                for (int child = 0; child < jsonArray1.length(); child++) {

                                    JSONObject object1 = jsonArray1.getJSONObject(child);
                                    String category_id1 = object1.getString("category_id");
                                    String parent_id1 = object1.getString("parent_id");
                                    String name1 = object1.getString("name");
                                    String is_active1 = object1.getString("is_active");
                                    String position1 = object1.getString("position");
                                    String level1 = object1.getString("level");

                                    NavigationItem item =new NavigationItem(category_id1,name1);
                                    childListTitle.add(item);
                                }

                                expandableListDetail.put(name, childListTitle);

                                expandableListAdapter.notifyDataSetChanged();
                            }
                        }

                        //for Home data
                        JSONArray jsonArray_home = object.getJSONArray("home");
                        homeDataProvider=new HomeDataProvider(jsonArray_home);
                        homeDataProvider.setHomeData(jsonArray_home);

                        displayView(0, -1,"","");

                        if (progressBar != null)
                            progressBar.setVisibility(View.GONE);
                    }

                } else {
                    if (progressBar != null)
                        progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

                if (progressBar != null)
                    progressBar.setVisibility(View.GONE);

                e.printStackTrace();
                Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(result);
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }

    public static MainActivity getInstance() {
        return instance;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null) {
            if (requestCode == resultCode) {

                String requiredValue = data.getStringExtra("updateCount");
                if(!TextUtils.isEmpty(requiredValue))
                updateHotCount(Integer.parseInt(requiredValue));
                else
                    updateHotCount(0);
            }
        }
    }


    @Override
    protected void onDestroy() {
       // NatureSouqPrefrences.getSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }
}
