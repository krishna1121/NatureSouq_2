package com.naturesouq.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.naturesouq.R;
import com.naturesouq.model.MyAccountOrderItems;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SI_Android_Binit on 12/10/2015.
 */
public class MyAccountOrderAdapter extends  RecyclerView.Adapter<MyAccountOrderAdapter.CustomViewHolder> {

    private List<MyAccountOrderItems> myAccountOrderItems;
    private Context mContext;
    private ViewClickListener mViewClickListener;

    public MyAccountOrderAdapter(List<MyAccountOrderItems> myAccountOrderItems, Context mContext) {
        this.myAccountOrderItems = myAccountOrderItems;
        this.mContext = mContext;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_list_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder,final int position) {

        MyAccountOrderItems feedItem = myAccountOrderItems.get(position);
        //if(!(feedItem.getImage_url().equals(""))){
         //   Picasso.with(mContext).load(feedItem.getImage_url()).placeholder(R.drawable.placeholder_item).error(R.drawable.placeholder_item).into(holder.orderImage);
        //}

        holder.orderId.setText(feedItem.getOrder_id());
        holder.customerEmail.setText(feedItem.getCustomer_email());
        holder.orderTotal.setText("AED " +feedItem.getSub_total());
        holder.orderDate.setText(feedItem.getOrder_date());
        holder.orderStatus.setText(feedItem.getOrder_status());
        holder.productQut.setText(feedItem.getNumber_of_item());
        holder.orderImage.setTag(holder);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewClickListener.onItemClicked(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return myAccountOrderItems.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView orderImage;
        protected TextView orderId, customerEmail, orderDate, orderStatus, orderTotal, productQut;
        protected RelativeLayout relativeLayout;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.orderImage = (ImageView)itemView.findViewById(R.id.orderImage);
            this.orderId = (TextView)itemView.findViewById(R.id.name);
            this.customerEmail = (TextView)itemView.findViewById(R.id.discription);
            this.orderTotal = (TextView)itemView.findViewById(R.id.price);
            this.orderDate = (TextView)itemView.findViewById(R.id.orderDate);
            this.orderStatus = (TextView)itemView.findViewById(R.id.orderStatus);
            this.productQut = (TextView)itemView.findViewById(R.id.number_item);
            this.relativeLayout = (RelativeLayout)itemView.findViewById(R.id.describ);
        }
    }

    public List<MyAccountOrderItems> getMyAccountOrderItems() {
        return myAccountOrderItems;
    }

    public interface ViewClickListener {
        void onItemClicked(int position);
    }

    public void setViewClickListener (ViewClickListener viewClickListener) {
        mViewClickListener = viewClickListener;
    }

}
