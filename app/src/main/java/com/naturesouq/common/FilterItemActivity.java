package com.naturesouq.common;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.naturesouq.R;
import com.naturesouq.adapter.FilterAdapter;
import com.naturesouq.model.FilterActivityItem;
import com.naturesouq.model.FilterChooserItem;
import com.naturesouq.model.VlauesItem;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by SI_Android_Binit on 12/28/2015.
 */

public class FilterItemActivity extends Activity implements FilterAdapter.ViewClickListener {

    public static final String FILTER_URL = Utility.baseURL+"filter.php";
    FilterItemActivity activity;
    String categoryId;
    public static final int RESULT_FILTER_CODE = 201;
    FilterAdapter filterAdapter;
    List<FilterActivityItem> filterItems;
    FilterTask task;
    ArrayList<FilterChooserItem> AllfilterChooserList;
    ArrayList<FilterChooserItem> filterChooserList;
    HashSet<String> uniqueCode;
    JSONArray jArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);
        filterItems = new ArrayList<>();
        AllfilterChooserList = new ArrayList<>();

        filterChooserList = new ArrayList<>();
        uniqueCode = new HashSet<String>();

        RecyclerView filterRecyclerVew = (RecyclerView) findViewById(R.id.filterRecyclerVew);
        filterRecyclerVew.setLayoutManager(new LinearLayoutManager(this));
        filterAdapter = new FilterAdapter(this, filterItems);
        filterAdapter.setViewClickListener(this);
        filterRecyclerVew.setAdapter(filterAdapter);

        if (getIntent() != null) {
            categoryId = getIntent().getStringExtra("categoryId");
        }

        activity = this;
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setTitle("Filter");

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("apikey", "naturesouq#123@apikey");
            jsonObject.put("cat_id", categoryId);

            task = new FilterTask(jsonObject);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, FILTER_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClicked(int positionOrViewType, int cbCount, boolean isChecked) {
        try {

            List<FilterActivityItem> aFilterItems = filterAdapter.getFilterItems();
            FilterActivityItem aFilterItem = aFilterItems.get(positionOrViewType);
            //ArrayList<VlauesItem> valuesItems = aFilterItem.getList();
            List<VlauesItem> valuesItems = aFilterItem.getList();
            VlauesItem selectedItem = valuesItems.get(cbCount);

            //String filterCode = aFilterItem.getFiletrLabel();
            String filterCode = aFilterItem.getFilterCode();
            String filterItemId = selectedItem.getId();

            //Add checked items to AllfilterChooserList .
            if(isChecked){
                selectedItem.setIsChecked(true);
                FilterChooserItem filterChooserItem = new FilterChooserItem();
                filterChooserItem.setCode(filterCode);
                filterChooserItem.setId(filterItemId);
                AllfilterChooserList.add(filterChooserItem);
                //Put All codes to HashSet .
                uniqueCode.add(filterCode);
            }else{
                selectedItem.setIsChecked(false);
                FilterChooserItem filterChooserItem = new FilterChooserItem();
                filterChooserItem.setCode(filterCode);
                filterChooserItem.setId(filterItemId);

                //Remove Unchecked items from AllfilterChooserList .
                for (int i = 0; i < AllfilterChooserList.size(); i++){
                    FilterChooserItem item = AllfilterChooserList.get(i);
                    if(item.getCode().equals(filterCode) && item.getId().equals(filterItemId)){
                        AllfilterChooserList.remove(i);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSubmit(View view) {
        //do some things
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        try{
            switch (item.getItemId()) {
                case android.R.id.home:
                    //Cancel Filter AsyncTask when user presses back from App .

                    if(task != null){
                        task.cancel(true);
                    }

                    finish();
                    break;

                case R.id.done:
                    //Create JSONArray to hold filter items .
                    jArr = new JSONArray();
                    //Populate jArr with checked filters .
                    Iterator<String> itr1 = uniqueCode.iterator();
                    while (itr1.hasNext()) {
                        String nextCode = itr1.next();
                        String existingIds = "";

                        for (FilterChooserItem chooserItem : AllfilterChooserList){
                            if(chooserItem.getCode().equals(nextCode)){
                                existingIds = existingIds + "," + chooserItem.getId();
                            }
                        }
                        if (existingIds.startsWith(","))
                            existingIds = existingIds.substring(1, existingIds.length());
                        try {
                            if(existingIds.length() > 0){
                                JSONObject jobj = new JSONObject();
                                jobj.put("code", nextCode);
                                jobj.put("id", existingIds);
                                jArr.put(jobj);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if(jArr.length()>0){
                        Intent data = new Intent();
                        data.putExtra("apikey", "naturesouq#123@apikey");
                        data.putExtra("apply_filter", jArr.toString());
                        data.putExtra("cat_id", categoryId);
                        data.putExtra("current_page", 1);
                        setResult(RESULT_FILTER_CODE, data);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext() , "Select Filters " ,Toast.LENGTH_SHORT).show();
                    }

                    break;
                default:
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //Cancel Filter AsyncTask when user presses back from Device .
        if (task != null) {
            task.cancel(true);
        }
    }

    public class FilterTask extends AsyncTask<String, Void, String> {

        JSONObject obj;
        boolean isRunning = true;

        //Show registration progress.
        public FilterTask(JSONObject jobj) {
            obj = jobj;
        }

        @Override
        protected String doInBackground(String... urls) {
            return ConnectAsynchronously.connectAsynchronously(urls[0], obj);
        }

        @Override
        protected void onPreExecute() {
            try {
                //progressBar.setVisibility(View.VISIBLE);
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
                        ArrayList<FilterActivityItem> filterItemList = new ArrayList<>();
                        JSONArray arr = jsonResponse.getJSONArray("filters");

                        for (int filterSize = 0; filterSize < arr.length(); filterSize++) {
                            JSONObject obj = arr.getJSONObject(filterSize);
                            String filter_Code = obj.getString("code");
                            String filetrLabel = obj.getString("label");
                            JSONArray valuesArr = obj.getJSONArray("values");
                            ArrayList<VlauesItem> list = new ArrayList<>();

                            for (int valuesSize = 0; valuesSize < valuesArr.length(); valuesSize++) {
                                JSONObject objValues = valuesArr.getJSONObject(valuesSize);
                                String count = objValues.getString("count");
                                String valueItemLabel = objValues.getString("label");


                                if(count.length()>0)
                                valueItemLabel = valueItemLabel + " (" + count + ")" ;

                                String filter_ID = objValues.getString("id");
                                VlauesItem valuesItem = new VlauesItem(count, valueItemLabel, filter_ID,false);
                                list.add(valuesItem);
                                //Create a model classs and Add Items to it .
                            }
                            FilterActivityItem filetrItem = new FilterActivityItem(filetrLabel, list, filter_Code, filterSize);
                            filterItemList.add(filetrItem);
                        }
                        filterAdapter.setFilterItems(filterItemList);
                        filterAdapter.notifyDataSetChanged();
                    } else if (status.equals("0")) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            super.onPostExecute(result);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            isRunning = false;
        }
    }

}

