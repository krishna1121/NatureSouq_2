package com.naturesouq.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.naturesouq.R;
import com.naturesouq.model.CategoryProductsItem;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by SI_Android_Binit on 10/21/2015.
 */
public class CategoryProductsBaseAdapter extends  RecyclerView.Adapter<CategoryProductsBaseAdapter.CustomViewHolder> {


    private Context mContext;
    LinkedList<CategoryProductsItem> data;

    public CategoryProductsBaseAdapter(LinkedList<CategoryProductsItem> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_item, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        CategoryProductsItem feedItem = data.get(i);
        if(!(feedItem.getImage_url().equals(""))){
            Picasso.with(mContext).load(feedItem.getImage_url()).error(R.drawable.placeholder_item)
                    .placeholder(R.drawable.placeholder_pdoduct_image).into(customViewHolder.productImage);
        }

        customViewHolder.productName.setText(feedItem.getName());
        customViewHolder.productDesc.setText(feedItem.getShort_description());
        customViewHolder.productPrice.setText("AED " +feedItem.getPrice());
        customViewHolder.productImage.setTag(customViewHolder);

        //customViewHolder.productImage.setOnClickListener(clickListener);
        customViewHolder.productImage.setTag(customViewHolder);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView productImage;
        protected TextView productName;
        protected TextView productDesc;
        protected TextView productPrice;
        public CustomViewHolder(View itemView) {
            super(itemView);
            this.productImage = (ImageView)itemView.findViewById(R.id.product_img);
            this.productName = (TextView)itemView.findViewById(R.id.product_name);
            this.productDesc = (TextView)itemView.findViewById(R.id.product_dec);
            this.productPrice = (TextView)itemView.findViewById(R.id.product_price);
        }
    }


    public interface ViewClickListener {
        void onImageClicked(int position);
    }

//    public void setViewClickListener (ViewClickListener viewClickListener) {
//         = viewClickListener;
//    }

    public LinkedList<CategoryProductsItem> getBestSellerProductsItems() {
        return data;
    }

    public void setBestSellerProductsItems(LinkedList<CategoryProductsItem> rowItems) {
        data = rowItems;
    }
}



