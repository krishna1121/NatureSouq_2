package com.naturesouq.navigation;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.naturesouq.R;
import com.naturesouq.adapter.CategoryProductsBaseAdapter;
import com.naturesouq.common.ConnectAsynchronously;
import com.naturesouq.common.FilterItemActivity;
import com.naturesouq.common.NatureSouqPrefrences;
import com.naturesouq.common.ProductDetail;
import com.naturesouq.common.RecyclerItemClickListener;
import com.naturesouq.common.Utility;
import com.naturesouq.common.VerticalSpaceItemDecoration;
import com.naturesouq.model.CategoryProductsItem;
import com.naturesouq.model.HomeDataProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Created by SI-Andriod on 17-Oct-15.
 */
public class ChildFragment extends Fragment {
    private static final String CHILD_CATEGORY_URL = Utility.baseURL + "categoryProductList.php";
    private static final String PRODUCT_FILTER_URL = Utility.baseURL + "gfilter.php";
    private static final String SORT_BY_NEW_PRODUCT_URL = Utility.baseURL + "sortby.php";
    private static final int REQUEST_FILTER_CODE = 200;
    public static final int RESULT_FILTER_CODE = 201;
    RecyclerView listView;
    CategoryProductsBaseAdapter adapter;
    LinkedList<CategoryProductsItem> gridArray;
    ProgressBar progressBar;
    private TextView category, sorytby;
    String compareType, categoryId;
    TextView newproducts, popularity, price_range_HighToLow, price_range_LowToHigh, cancel;
    JSONArray categoryGallery, categorySpecificationKeyArray, categorySpecificationArray;
    ChildTask cTask;
    LinearLayout filter, sortBy;
    ImageView dealplaceholder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.category_products, container, false);

        dealplaceholder = (ImageView) view.findViewById(R.id.dealplaceholder);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        category = (TextView) view.findViewById(R.id.catagoryItem);
        sorytby = (TextView) view.findViewById(R.id.sortbyItem);
        filter = (LinearLayout) view.findViewById(R.id.filter);
        sortBy = (LinearLayout) view.findViewById(R.id.sortBy);
        filter.setVisibility(View.GONE);
        sortBy.setVisibility(View.GONE);

        listView = (RecyclerView) view.findViewById(R.id.grid);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        //NpaGridLayoutManager gridLayoutManager=new NpaGridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        listView.addItemDecoration(new VerticalSpaceItemDecoration(8));
        listView.setLayoutManager(gridLayoutManager);

        listView.setNestedScrollingEnabled(false);
        listView.smoothScrollBy(0, 0);
        gridArray = new LinkedList<CategoryProductsItem>();

        categoryId = getArguments().getString("ProductCategoryId");
        if (!TextUtils.isEmpty(categoryId)) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("category_id", categoryId);
                jsonObject.put("apikey", "naturesouq#123@apikey");

                cTask = new ChildTask(jsonObject);
                cTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, CHILD_CATEGORY_URL);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
        }

        category.setVisibility(View.GONE);
        sorytby.setVisibility(View.GONE);

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent filterIntent = new Intent(getActivity(), FilterItemActivity.class);
                filterIntent.putExtra("categoryId", categoryId);
                filterIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(filterIntent, REQUEST_FILTER_CODE);

            }
        });

        sorytby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create custom dialog object
                final Dialog convertView = new Dialog(getActivity());
                // Include dialog.xml file
                convertView.setContentView(R.layout.dialog_sortby);
                // Set dialog title
                convertView.setTitle("Sort By...");
                popularity = (TextView) convertView.findViewById(R.id.popularity_lbl);
                price_range_HighToLow = (TextView) convertView.findViewById(R.id.price_range_HighToLow_lbl);
                price_range_LowToHigh = (TextView) convertView.findViewById(R.id.price_range_LowToHigh_lbl);
                newproducts = (TextView) convertView.findViewById(R.id.newproduct_lbl);
                cancel = (TextView) convertView.findViewById(R.id.cancel_lbl);

                Typeface face1 = Typeface.createFromAsset(getActivity().getAssets(), "font/Lato_Bold.ttf");
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
                            cTask = new ChildTask(jobj);
                            cTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, SORT_BY_NEW_PRODUCT_URL);
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
                            cTask = new ChildTask(jobj);
                            cTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, SORT_BY_NEW_PRODUCT_URL);
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
                            listView.smoothScrollToPosition(0);
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
                            listView.smoothScrollToPosition(0);
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

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (cTask != null) {
            cTask.cancel(true);
        }
    }

    public class ChildTask extends AsyncTask<String, Void, String> {
        JSONObject obj;

        //Show registration progress.
        public ChildTask(JSONObject jobj) {
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
                JSONObject jsonResponse = new JSONObject(result);
                String status = jsonResponse.getString("status");
                String message = jsonResponse.getString("message");
                if (status.equals("1")) {


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

                            categoryGallery = jsonObject.getJSONArray("gallery");
                            categorySpecificationKeyArray = jsonObject.getJSONArray("specificationKey");
                            categorySpecificationArray = jsonObject.getJSONArray("specification");

                            CategoryProductsItem bestseller = new CategoryProductsItem(product_id, type, set, sku, position, rating, review, price, name, description, short_description, image_url, SmallImage, Thumbnail, categoryGallery, categorySpecificationKeyArray, categorySpecificationArray);
                            gridArray.add(bestseller);
                        }
                        adapter = new CategoryProductsBaseAdapter(gridArray, getActivity());
                        listView.setAdapter(adapter);

                        category.setVisibility(View.VISIBLE);
                        sorytby.setVisibility(View.VISIBLE);
                        filter.setVisibility(View.VISIBLE);
                        sortBy.setVisibility(View.VISIBLE);


                        listView.addOnItemTouchListener(
                                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        // do whatever
                                        CategoryProductsItem feedItem = gridArray.get(position);
                                        Intent detailIntent = new Intent(getActivity(), ProductDetail.class);
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
                                        //Toast.makeText(getApplicationContext(), feedItem.getName(), Toast.LENGTH_SHORT).show();
                                    }
                                })
                        );
                    } else {
                        //No Product Placeholder
                        dealplaceholder.setVisibility(View.VISIBLE);
                        dealplaceholder.setImageResource(R.drawable.noproduct_placeholder);
                    }
                } else if (status.equals("2")) {
                    //No Product Placeholder
                    dealplaceholder.setVisibility(View.VISIBLE);
                    dealplaceholder.setImageResource(R.drawable.noproduct_placeholder);
                } else if (status.equals("0")) {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                e.printStackTrace();
            }
            progressBar.setVisibility(View.INVISIBLE);
            super.onPostExecute(result);
        }
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

                cTask = new ChildTask(jobj);
                cTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, PRODUCT_FILTER_URL);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            String counter = NatureSouqPrefrences.getCartCounter(getActivity());
            if (!TextUtils.isEmpty(counter)) {
                //updateHotCount(Integer.parseInt(counter));
            }
        }

    }


}
