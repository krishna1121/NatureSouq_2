package com.naturesouq.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.naturesouq.R;
import com.naturesouq.model.CartRowItems;
import com.naturesouq.model.CategoryProductsItem;
import com.naturesouq.model.HomeProductsItem;
import com.naturesouq.model.MyFavoriteListItem;
import com.naturesouq.model.MyOrderProductListItem;
import com.naturesouq.model.SearchListItem;

/**
 * Created by SI_Android_Binit on 2/24/2016.
 */
public class SpecificationTab extends Activity {

    HomeProductsItem object;
    CategoryProductsItem obj1;
    CartRowItems obj2;
    MyFavoriteListItem obj3;
    SearchListItem obj4;
    MyOrderProductListItem obj5;
    TableLayout table_layout;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.specification_tab);
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);

        Intent intent= getIntent();
        String flow = intent.getStringExtra("ProductDetailToSpecification");
        if (flow.equalsIgnoreCase("homeFragment")) {
            //For Home Fragment .
            object = this.getIntent().getExtras().getParcelable("feedItemHomeFragment");

            if (!TextUtils.isEmpty(object.getDescription())) {
                String rowstring = object.getDescription().toString();

                int rows = Integer.parseInt("2");
                int cols = Integer.parseInt("2");
                table_layout.removeAllViews();
                BuildTable(rows, cols);

            } else {
                // productdetail.setText("N/A");
            }
        }else if (flow.equalsIgnoreCase("particularCategory")) {
            //For particularCategory .
            obj1 = this.getIntent().getExtras().getParcelable("feedItemCategoryDetail");

            if (!TextUtils.isEmpty(obj1.getDescription())) {
                // productdetail.setText(obj1.getDescription());
            } else {
                // productdetail.setText("N/A");
            }
        }else if (flow.equalsIgnoreCase("cart")) {
            //For cart .
            obj2 = this.getIntent().getExtras().getParcelable("feedItemCart");

            if (!TextUtils.isEmpty(obj2.getProduct_shortDesc())) {
                // productdetail.setText(obj2.getProduct_shortDesc());
            } else {
                // productdetail.setText("N/A");
            }
        }else if (flow.equalsIgnoreCase("myFavorite")) {
            //For myFavorite .
            obj3 = this.getIntent().getExtras().getParcelable("feedItemMyFavorite");

            if (!TextUtils.isEmpty(obj3.getShortDescription())) {
                // productdetail.setText(obj3.getShortDescription());
            } else {
                // productdetail.setText("N/A");
            }
        }else if (flow.equalsIgnoreCase("mySearch")) {
            //For mySearch .
            obj4 = this.getIntent().getExtras().getParcelable("feedItemSearch");

            if (!TextUtils.isEmpty(obj4.getDescription())) {
                // productdetail.setText(obj4.getDescription());
            } else {
                // productdetail.setText("N/A");
            }
        }else if (flow.equalsIgnoreCase("myOrderProduct")) {
            //For myOrderProduct .
            obj5 = this.getIntent().getExtras().getParcelable("feedItemMyOrderProduct");

            if (!TextUtils.isEmpty(obj5.getDescription())) {
                // productdetail.setText(obj5.getDescription());
            } else {
                // productdetail.setText("N/A");
            }
        }
    }

    private void BuildTable(int rows, int cols) {

        // outer for loop
        for (int i = 1; i <= rows; i++) {

            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            // Inner for loop
            for (int j = 1; j <= 2; j++) {

                TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                tv.setBackgroundResource(R.drawable.cell_shape);
                tv.setPadding(10, 10, 10, 10);

                if(j==1)
                tv.setText("KEY");
                else
                tv.setText("VALUE");

                row.addView(tv);

            }

            table_layout.addView(row);

        }
    }
}