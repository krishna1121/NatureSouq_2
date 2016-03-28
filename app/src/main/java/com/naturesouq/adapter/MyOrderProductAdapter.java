package com.naturesouq.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.naturesouq.R;
import com.naturesouq.model.MyOrderProductListItem;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by SI_Android_Binit on 2/16/2016.
 */
public class MyOrderProductAdapter extends  RecyclerView.Adapter<MyOrderProductAdapter.CustomViewHolder> {


    private Context mContext;
    List<MyOrderProductListItem> data;

    public MyOrderProductAdapter(List<MyOrderProductListItem> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_order_product_list_item, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, final int i) {
        final MyOrderProductListItem feedItem = data.get(i);

        if(!(feedItem.getImage_url().equals(""))){
            Picasso.with(mContext).load(feedItem.getImage_url()).error(R.drawable.placeholder_item)
                    .placeholder(R.drawable.placeholder_pdoduct_image).into(customViewHolder.productImage);
        }

        customViewHolder.productName.setText(feedItem.getName());
        customViewHolder.productDesc.setText(feedItem.getShort_description());
        customViewHolder.productPrice.setText("AED " +feedItem.getPrice());
        customViewHolder.orderDate.setText(feedItem.getOrder_date());
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
        protected TextView productName, productDesc, productPrice, orderDate;
        public CustomViewHolder(View itemView) {
            super(itemView);
            this.productImage = (ImageView)itemView.findViewById(R.id.productImage);
            this.productName = (TextView)itemView.findViewById(R.id.name);
            this.productDesc = (TextView)itemView.findViewById(R.id.discription);
            this.productPrice = (TextView)itemView.findViewById(R.id.price);
            this.orderDate = (TextView)itemView.findViewById(R.id.orderDate);
        }
    }


    public interface ViewClickListener {
        void onImageClicked(int position);
    }

//    public void setViewClickListener (ViewClickListener viewClickListener) {
//         = viewClickListener;
//    }

    public List<MyOrderProductListItem> getMyOrderProductListItem() {
        return data;
    }
}
