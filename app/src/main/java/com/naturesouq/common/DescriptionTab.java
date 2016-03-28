package com.naturesouq.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
public class DescriptionTab extends Activity {

    private TextView productdetail;
    HomeProductsItem object;
    CategoryProductsItem obj1;
    CartRowItems obj2;
    MyFavoriteListItem obj3;
    SearchListItem obj4;
    MyOrderProductListItem obj5;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_tab);

        productdetail = (TextView) findViewById(R.id.productDetail);


        Intent intent= getIntent();
        String flow = intent.getStringExtra("ProductDetailToDescription");

        if (flow.equalsIgnoreCase("homeFragment")) {
            //For Home Fragment .
            object = this.getIntent().getExtras().getParcelable("feedItemHomeFragment");

            if (!TextUtils.isEmpty(object.getDescription())) {
                productdetail.setText(object.getDescription());
            } else {
                productdetail.setText("N/A");
            }
        }else if (flow.equalsIgnoreCase("particularCategory")) {
            //For particularCategory .
            obj1 = this.getIntent().getExtras().getParcelable("feedItemCategoryDetail");

            if (!TextUtils.isEmpty(obj1.getDescription())) {
                productdetail.setText(obj1.getDescription());
            } else {
                productdetail.setText("N/A");
            }
        }else if (flow.equalsIgnoreCase("cart")) {
            //For cart .
            obj2 = this.getIntent().getExtras().getParcelable("feedItemCart");

            if (!TextUtils.isEmpty(obj2.getProduct_shortDesc())) {
                productdetail.setText(obj2.getProduct_shortDesc());
            } else {
                productdetail.setText("N/A");
            }
        }else if (flow.equalsIgnoreCase("myFavorite")) {
            //For myFavorite .
            obj3 = this.getIntent().getExtras().getParcelable("feedItemMyFavorite");

            if (!TextUtils.isEmpty(obj3.getShortDescription())) {
                productdetail.setText(obj3.getShortDescription());
            } else {
                productdetail.setText("N/A");
            }
        }else if (flow.equalsIgnoreCase("mySearch")) {
            //For mySearch .
            obj4 = this.getIntent().getExtras().getParcelable("feedItemSearch");

            if (!TextUtils.isEmpty(obj4.getDescription())) {
                productdetail.setText(obj4.getDescription());
            } else {
                productdetail.setText("N/A");
            }
        }else if (flow.equalsIgnoreCase("myOrderProduct")) {
            //For myOrderProduct .
            obj5 = this.getIntent().getExtras().getParcelable("feedItemMyOrderProduct");

            if (!TextUtils.isEmpty(obj5.getDescription())) {
                productdetail.setText(obj5.getDescription());
            } else {
                productdetail.setText("N/A");
            }
        }

    }
}
