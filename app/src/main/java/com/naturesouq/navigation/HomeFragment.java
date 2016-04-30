package com.naturesouq.navigation;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.naturesouq.R;
import com.naturesouq.adapter.MyHorizontalAdapter;
import com.naturesouq.adapter.MyVerticalAdapter;
import com.naturesouq.adapter.MyVerticalAdapter.ViewClickListener;
import com.naturesouq.common.CategoryProducts;
import com.naturesouq.common.ConnectAsynchronously;
import com.naturesouq.common.DialogBox;
import com.naturesouq.common.MainActivity;
import com.naturesouq.common.NatureSouqPrefrences;
import com.naturesouq.common.ProductDetail;
import com.naturesouq.common.ScrollingLinearLayoutManager;
import com.naturesouq.common.Utility;
import com.naturesouq.common.VerticalSpaceItemDecoration;
import com.naturesouq.model.HomeDataProvider;
import com.naturesouq.model.HomeProductsItem;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by SI-Andriod on 15-Oct-15.
 */

public class HomeFragment extends Fragment implements ViewClickListener {

    private static final String LOG_TAG = HomeFragment.class.getSimpleName();
    final String SLIDER_IMAGES = Utility.baseURL + "slider.php";
    ProgressBar progressBar;
    int[] anim_circle;
    ImageView circleAnimImageView;
    String slider1, slider2, slider3;
    static int cartitemcount;
    private TextView countTextView = null;
    ViewPager viewPager;
    private MyVerticalAdapter verticalAdapter;
    private MyHorizontalAdapter horizontalAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView myRecyclerView;
    private ArrayList<HomeProductsItem> data;
    String status = "" ,product_id = "", type = "", set = "", sku = "", position = "", rating = "", review = "", price = "", name = "", description = "", short_description = "", image_url = "", SmallImage = "", Thumbnail = "", specification_price, specification_sku;
    public static String categoryName[], category_id[];
    TextView DealoftheDay;
    JSONArray homeSpecificationArray, homeSpecificationKeyArray, homeGallery;
    public static HomeDataProvider provider1, provider2, provider3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.homefragment, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        //Add Recycler Item for New Products
        myRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        DealoftheDay = (TextView) view.findViewById(R.id.DealoftheDayView);

        int duration = 1000;
        mLayoutManager = new ScrollingLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false, duration);
        myRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(8));
        myRecyclerView.setLayoutManager(mLayoutManager);
        myRecyclerView.setNestedScrollingEnabled(false);
        verticalAdapter = new MyVerticalAdapter(getActivity());
        horizontalAdapter = new MyHorizontalAdapter(getActivity(), data, new MyHorizontalAdapter.ViewClickListener() {
            @Override
            public void onImageClicked(int position, String item) {
            }
        });

        DealoftheDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(getActivity(), CategoryProducts.class);
                data.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                data.putExtra("data", "Deals of the day");
                startActivity(data);
            }
        });

        try {
            ArrayList<ArrayList<HomeProductsItem>> dataTop = new ArrayList<ArrayList<HomeProductsItem>>();
            JSONArray jsonArray_home = MainActivity.homeDataProvider.getHomeData();
            categoryName = new String[jsonArray_home.length()];
            category_id = new String[jsonArray_home.length()];

            for (int home_catagory = 0; home_catagory < jsonArray_home.length(); home_catagory++) {
                data = new ArrayList<HomeProductsItem>();
                JSONArray jsonArray_catagory = jsonArray_home.getJSONArray(home_catagory);

                for (int catagory = 0; catagory < jsonArray_catagory.length(); catagory++) {

                    JSONObject catagoryObject = jsonArray_catagory.getJSONObject(catagory);
                    if ((catagory != (jsonArray_catagory.length() - 1))) {
                        //status = catagoryObject.getString("status") ;
                        product_id = catagoryObject.getString("product_id");
                        type = catagoryObject.getString("type");
                        set = catagoryObject.getString("set");
                        sku = catagoryObject.getString("sku");
                        position = catagoryObject.getString("position");
                        rating = catagoryObject.getString("rating");
                        review = catagoryObject.getString("review");
                        price = catagoryObject.getString("price");
                        name = catagoryObject.getString("name");
                        description = catagoryObject.getString("description");
                        short_description = catagoryObject.getString("short_description");
                        image_url = catagoryObject.getString("image_url");
                        SmallImage = catagoryObject.getString("SmallImage");
                        Thumbnail = catagoryObject.getString("Thumbnail");

                        homeGallery = catagoryObject.getJSONArray("gallery");
                        homeSpecificationKeyArray = catagoryObject.getJSONArray("specificationKey");
                        homeSpecificationArray = catagoryObject.getJSONArray("specification");

                        HomeProductsItem homeProductsItem = new HomeProductsItem(product_id, type, set, sku, position, rating, review, price, name, description, short_description, image_url, SmallImage, Thumbnail, "", "", homeGallery, homeSpecificationKeyArray, homeSpecificationArray);
                        data.add(homeProductsItem);

                    }
                    if (catagory == (jsonArray_catagory.length() - 1)) {
                        categoryName[home_catagory] = catagoryObject.getString("CategoryName");
                        category_id[home_catagory] = catagoryObject.getString("category_id");
                    }
                }

                dataTop.add(data);
                verticalAdapter.add(dataTop);
                verticalAdapter.setViewClickListener(HomeFragment.this);
                verticalAdapter.notifyDataSetChanged();
                myRecyclerView.setAdapter(verticalAdapter);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "" + e, Toast.LENGTH_LONG).show();
        }

        circleAnimImageView = (ImageView) view.findViewById(R.id.circleAnim);
        // Locate the ViewPager in viewpager_main.xml
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        boolean networkStatus = new DialogBox(getActivity()).checkInternetConnection();
        if (networkStatus) {

            // homeRecyclerProduct();
            //call webservice to load slider images.
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("apikey", "naturesouq#123@apikey");
                new HomePagerTask(jsonObject, "setSliderImage").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, SLIDER_IMAGES);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "" + e, Toast.LENGTH_LONG).show();
            }
        }

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);

        final View menuOnMainActivity = menu.findItem(R.id.cart).getActionView();
        countTextView = (TextView) menuOnMainActivity.findViewById(R.id.hotlist_hot);
        updateHotCount(cartitemcount);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCategoryClicked(int position, String category, String categoryId) {
        Log.i(LOG_TAG, "onCategoryClicked, " + position + " : " + category + ", " + categoryId);
        HomeProductsItem feedItem = data.get(position);
        Intent detailPage = new Intent(getActivity(), CategoryProducts.class);
        detailPage.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        detailPage.putExtra("feedItemHomeFragment", feedItem);
        detailPage.putExtra("categoryId", categoryId);
        detailPage.putExtra("cat", category);
        startActivity(detailPage);
    }

    @Override
    public void onItemClick(int position, HomeProductsItem item) {
        Log.i(LOG_TAG, "onProductClicked, " + position + " : " + item.getName() + "," + item.getCategoryName());
        Intent detailPage = new Intent(getActivity(), ProductDetail.class);
        detailPage.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        detailPage.putExtra("feedItemHomeFragment", item);
        detailPage.putExtra("GridViewToDetail", "homeFragment");
        detailPage.putExtra("product_id", item.getProduct_id());
        detailPage.putExtra("categoryName", item.getCategoryName());
        provider1 = new HomeDataProvider(item.getHomeGallery());
        provider1.setProductGallery(item.getHomeGallery());
        provider2 = new HomeDataProvider(item.getHomeSpecificationKey());
        provider2.setProductSpecificationKey(item.getHomeSpecificationKey());
        provider3 = new HomeDataProvider(item.getHomeSpecification());
        provider3.setProductSpecification(item.getHomeSpecification());
        startActivity(detailPage);

    }

    @Override
    public void onResume() {
        super.onResume();
        String counter = NatureSouqPrefrences.getCartCounter(getActivity());
        if (!TextUtils.isEmpty(counter)) {
            updateHotCount(Integer.parseInt(counter));
        }
    }

    public class HomePagerTask extends AsyncTask<String, Void, String> {
        JSONObject obj;
        String taskIdentifier;

        public HomePagerTask(JSONObject jobj, String taskIdentifier) {
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
                progressBar.setVisibility(View.VISIBLE);
                super.onPreExecute();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Exception occur on Loading.. ", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                // String taskIdentifier1 = taskIdentifier.getString("taskIdentifier");
                JSONObject jsonResponse = new JSONObject(result);
                String status = jsonResponse.getString("status");
                String message = jsonResponse.getString("message");

                if (status.equals("1")) {

                    if (taskIdentifier.equals("setSliderImage")) {
                        JSONArray jsonArray = jsonResponse.getJSONArray("data");
                        //Add ViewPager On hOme page
                        String id[] = new String[jsonArray.length()];
                        String content[] = new String[jsonArray.length()];

                        for (int slider = 0; slider < jsonArray.length(); slider++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(slider);
                            String sliderImage = jsonObject.getString("image_url");
                            id[slider] = jsonObject.getString("id");
                            content[slider] = jsonObject.getString("content");

                            if (slider == 0) {
                                slider1 = sliderImage;
                            } else if (slider == 1) {
                                slider2 = sliderImage;
                            } else if(slider == 2){
                                slider3 = sliderImage;

                                //Add ViewPager On hOme page
                                String[] backgroundImage = new String[]{slider1, slider2, slider3};
                                anim_circle = new int[]{R.drawable.circle1, R.drawable.circle2, R.drawable.circle3};

                                // Pass results to ViewPagerAdapter Class
                                ImageAdapter adapter = new ImageAdapter(getActivity(), backgroundImage, id, content);
                                // Binds the Adapter to the ViewPager
                                viewPager.setAdapter(adapter);
                                viewPager.setOnPageChangeListener(adapter);
                            }
                        }
                    } else if (taskIdentifier.equals("setHomeItem")) {
                        JSONObject object = jsonResponse.getJSONObject("data");
                        JSONArray jsonArray = object.getJSONArray("category");

                        ArrayList<ArrayList<HomeProductsItem>> dataTop = new ArrayList<ArrayList<HomeProductsItem>>();


                        //for Home data
                        JSONArray jsonArray_home = object.getJSONArray("home");
                        categoryName = new String[jsonArray.length()];
                        category_id = new String[jsonArray.length()];
                        for (int home_catagory = 0; home_catagory < jsonArray_home.length(); home_catagory++) {

                            JSONArray jsonArray_catagory = jsonArray_home.getJSONArray(home_catagory);
                            // newProductProductsItemList = new ArrayList<HomeProductsItem>();
                            data = new ArrayList<HomeProductsItem>();
                            for (int catagory = 0; catagory < jsonArray_catagory.length(); catagory++) {

                                JSONObject catagoryObject = jsonArray_catagory.getJSONObject(catagory);

                                if ((catagory != (jsonArray_catagory.length() - 1))) {
                                    product_id = catagoryObject.getString("product_id");
                                    type = catagoryObject.getString("type");
                                    set = catagoryObject.getString("set");
                                    sku = catagoryObject.getString("sku");
                                    position = catagoryObject.getString("position");
                                    rating = catagoryObject.getString("rating");
                                    review = catagoryObject.getString("review");
                                    price = catagoryObject.getString("price");
                                    name = catagoryObject.getString("name");
                                    description = catagoryObject.getString("description");
                                    short_description = catagoryObject.getString("short_description");
                                    image_url = catagoryObject.getString("image_url");
                                    SmallImage = catagoryObject.getString("SmallImage");
                                    Thumbnail = catagoryObject.getString("Thumbnail");

                                    homeGallery = catagoryObject.getJSONArray("gallery");
                                    homeSpecificationKeyArray = catagoryObject.getJSONArray("specificationKey");
                                    homeSpecificationArray = catagoryObject.getJSONArray("specification");

                                    HomeProductsItem homeProductsItem = new HomeProductsItem(product_id, type, set, sku, position, rating, review, price, name, description, short_description, image_url, SmallImage, Thumbnail, "", "", homeGallery, homeSpecificationKeyArray, homeSpecificationArray);
                                    data.add(homeProductsItem);
                                }
                                if (catagory == (jsonArray_catagory.length() - 1)) {
                                    categoryName[home_catagory] = catagoryObject.getString("CategoryName");
                                    category_id[home_catagory] = catagoryObject.getString("category_id");

                                }
                                HomeProductsItem productsItem = new HomeProductsItem();

                            }
                            //
                            dataTop.add(data);
                            verticalAdapter.add(dataTop);
                            verticalAdapter.setViewClickListener(HomeFragment.this);
                            verticalAdapter.notifyDataSetChanged();
                        }
                    }
                    if (progressBar != null)
                        progressBar.setVisibility(View.GONE);

                } else {
                    if (progressBar != null)
                        progressBar.setVisibility(View.GONE);

                    Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {
                if (progressBar != null)
                    progressBar.setVisibility(View.GONE);

                e.printStackTrace();
                Toast.makeText(getActivity(), "" + e, Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(result);
        }
    }

    public class ImageAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
        Context context;
        String urlArray[];
        String id[];
        String contentTitle[];
        ImageView imageView;

        ImageAdapter(Context context, String[] urlArr, String[] id, String contentTitle[]) {
            this.context = context;
            this.urlArray = new String[urlArr.length];
            this.id = new String[id.length];
            this.contentTitle = new String[contentTitle.length];

            for (int arrlen = 0; arrlen < this.urlArray.length; arrlen++) {
                this.urlArray[arrlen] = urlArr[arrlen];
                this.id[arrlen] = id[arrlen];
                this.contentTitle[arrlen] = contentTitle[arrlen];
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
        public Object instantiateItem(ViewGroup container, final int position) {
            imageView = new ImageView(context);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent data = new Intent(getActivity(), CategoryProducts.class);
                    data.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    data.putExtra("categoryId", id[position]);
                    data.putExtra("cat", contentTitle[position]);
                    startActivity(data);

                }
            });

            //Load images using Picasso .
            if (!TextUtils.isEmpty(urlArray[position])) {
                progressBar.setVisibility(View.VISIBLE);

                Log.d("Position", String.valueOf(position));
                Log.d("URL", String.valueOf(urlArray[position]) );

                Picasso.with(getActivity().getApplicationContext()).load(urlArray[position]).error(R.drawable.placeholder_detail).into(imageView, new com.squareup.picasso.Callback() {
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
                Picasso.with(getActivity().getApplicationContext()).load(R.drawable.placeholder_detail).into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
//
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
            circleAnimImageView.setImageResource(anim_circle[position]);
        }

    }

    public void updateHotCount(final int new_hot_number) {
        cartitemcount = new_hot_number;
        if (countTextView == null) return;
        getActivity().runOnUiThread(new Runnable() {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (data != null) {
                if (requestCode == 12) {
                    String requiredValue = data.getStringExtra("updateCount");
                    String MY_RATING = data.getStringExtra("MY_RATING");
                    String MY_FAVOURITE = data.getStringExtra("MY_FAVOURITE");

                    Intent i = new Intent();

                    if (!TextUtils.isEmpty(requiredValue)) {
                        updateHotCount(Integer.parseInt(requiredValue));
                        i.putExtra("updateCount", requiredValue);
                    }
                    if (!TextUtils.isEmpty(MY_RATING) || !TextUtils.isEmpty(MY_FAVOURITE)) {
                        i.putExtra("MY_RATING", MY_RATING);
                        i.putExtra("MY_FAVOURITE", MY_FAVOURITE);
                    }
                    getActivity().setResult(12, i);
                }
            }

        } catch (Exception ex) {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}
