package com.naturesouq.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.naturesouq.R;
import com.naturesouq.model.MyOrderListItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SI_Android_Binit on 2/16/2016.
 */
public class MyOrderAdapter extends  RecyclerView.Adapter<MyOrderAdapter.CustomViewHolder> {

    private List<MyOrderListItem> myOrderItems;
    private Context mContext;
    private ViewClickListener mViewClickListener;

    public MyOrderAdapter(List<MyOrderListItem> myOrderItems, Context mContext) {
        this.myOrderItems = myOrderItems;
        this.mContext = mContext;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_viewall_list_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder,final int position) {

        final MyOrderListItem feedItem = myOrderItems.get(position);
        //if(!(feedItem.getImage_url().equals(""))){
        //    Picasso.with(mContext).load(feedItem.getImage_url()).placeholder(R.drawable.placeholder_item).error(R.drawable.placeholder_item).into(holder.orderImage);
        //}

        holder.orderId.setText(feedItem.getOrder_id());
        holder.customerEmail.setText(feedItem.getCustomer_email());
        holder.orderTotal.setText("AED " +feedItem.getSub_total());
        holder.orderDate.setText(feedItem.getOrder_date());
        holder.orderStatus.setText(feedItem.getOrder_status());
        holder.productQut.setText(feedItem.getNoofitems());

        if(feedItem.getStateOfDelete()==true){
            holder.frameLayoutButton.setVisibility(View.VISIBLE);
        }else{

            holder.frameLayoutButton.setVisibility(View.INVISIBLE);
        }

        //holder.orderImage.setTag(holder);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewClickListener.onItemClicked(position);

            }
        });

       // holder.productSettingLayout.setTag(holder);

        holder.productSettingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewClickListener.onSettingClicked(position, myOrderItems.get(position), holder.frameLayoutButton);
            }
        });

        holder.frameLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewClickListener.onOrderCancelClicked(position,feedItem,holder.frameLayoutButton);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myOrderItems.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView orderImage;
        protected TextView orderId, customerEmail, orderDate, orderStatus, orderTotal, productQut;
        protected RelativeLayout relativeLayout, productSettingLayout;
        FrameLayout frameLayoutButton;

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
            this.productSettingLayout = (RelativeLayout)itemView.findViewById(R.id.listmenu);
            this.frameLayoutButton = (FrameLayout)itemView.findViewById(R.id.cancelOrder);
        }
    }

    public List<MyOrderListItem> getMyOrderItems() {
        return myOrderItems;
    }

    public interface ViewClickListener {
        void onItemClicked(int position);
        void onSettingClicked(int position, MyOrderListItem item,View view);
        void onOrderCancelClicked(int position, MyOrderListItem item,View view);
    }

    public void setViewClickListener (ViewClickListener viewClickListener) {
        mViewClickListener = viewClickListener;
    }

}
