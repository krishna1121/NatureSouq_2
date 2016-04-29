package com.naturesouq.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.naturesouq.R;
import com.naturesouq.model.HomeProductsItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SI_Android_Binit on 12/28/2015.
 */
public class MyHorizontalAdapter extends  RecyclerView.Adapter<MyHorizontalAdapter.CustomViewHolder> {
    private List<HomeProductsItem> homeProductsItems;
    private Context mContext;
    public ViewClickListener mViewClickListener;

    public MyHorizontalAdapter(Context context, List<HomeProductsItem> homeProductsItems, ViewClickListener viewClickListener) {
        this.homeProductsItems = homeProductsItems;
        this.mContext = context;
        this.mViewClickListener = viewClickListener;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_item, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, final int i) {
        HomeProductsItem feedItem = homeProductsItems.get(i);
        if(!(feedItem.getImage_url().equals(""))){
            Picasso.with(mContext).load(feedItem.getImage_url()).error(R.drawable.placeholder_item)
                    .placeholder(R.drawable.placeholder_pdoduct_image).into(customViewHolder.productImage);
        }
        customViewHolder.productName.setText(feedItem.getName());
        customViewHolder.productDesc.setText(feedItem.getShort_description());
        customViewHolder.productPrice.setText("AED " +feedItem.getPrice());

        customViewHolder.productImage.setTag(customViewHolder);

        customViewHolder.relativeLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(mContext, "BinitSingh" + String.valueOf(i), Toast.LENGTH_SHORT).show();
                mViewClickListener.onImageClicked(i, "productItem");
            }
        });

       // customViewHolder.category_name.setText(feedItem.getCategoryName());

    }

    @Override
    public int getItemCount() {
        return homeProductsItems.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView productImage;
        protected TextView productName;
        protected TextView productDesc;
        protected TextView productPrice;
        public RelativeLayout relativeLayoutItem;
        public CustomViewHolder(View itemView) {
            super(itemView);

            this.productImage = (ImageView)itemView.findViewById(R.id.product_img);
            this.productName = (TextView)itemView.findViewById(R.id.product_name);
            this.productDesc = (TextView)itemView.findViewById(R.id.product_dec);
            this.productPrice = (TextView)itemView.findViewById(R.id.product_price);
            this.relativeLayoutItem = (RelativeLayout)itemView.findViewById(R.id.product_item);
        }
    }

    public List<HomeProductsItem> getHomeProductsItems() {
        return homeProductsItems;
    }

    public interface ViewClickListener {
        void onImageClicked(int position, String item);
    }

}