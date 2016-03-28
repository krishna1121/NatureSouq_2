package com.naturesouq.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.naturesouq.R;
import com.naturesouq.model.SearchListItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SI_Android_Binit on 2/8/2016.
 */
public class SearchBaseAdapter extends  RecyclerView.Adapter<SearchBaseAdapter.CustomViewHolder> {

    private List<SearchListItem> mySearchItems;
    private Context mContext;
    private ViewClickListener mViewClickListener;

    public SearchBaseAdapter(Context mContext, List<SearchListItem> mySearchItems) {
        this.mySearchItems = mySearchItems;
        this.mContext = mContext;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder,final int position) {

        final SearchListItem feedItem = mySearchItems.get(position);
        if(!(feedItem.getImage_url().equals(""))){
            Picasso.with(mContext).load(feedItem.getImage_url()).error(R.drawable.placeholder_item).placeholder(R.drawable.placeholder_cart).into(holder.productImage);
        }

        holder.productName.setText(feedItem.getName());
        holder.productDesc.setText(feedItem.getShortDescription());
        holder.productPrice.setText("AED " +feedItem.getPrice());
        // holder.productImage.setTag(holder);
        holder.layout_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewClickListener.onItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mySearchItems.size();
    }

    @Override
    public long getItemId(int position) {
        return mySearchItems.indexOf(getItemId(position));
    }

    public void delete(int position) { //removes the row
        mySearchItems.remove(position);
        notifyItemRemoved(position);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView productImage;
        protected TextView productName, productDesc, productPrice;
        RelativeLayout layout_main;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.productImage = (ImageView)itemView.findViewById(R.id.productImage);
            this.productName = (TextView)itemView.findViewById(R.id.name);
            this.productDesc = (TextView)itemView.findViewById(R.id.discription);
            this.productPrice = (TextView)itemView.findViewById(R.id.price);
            this.layout_main=(RelativeLayout)itemView.findViewById(R.id.layout_main);
        }

    }

    public List<SearchListItem> getSearchItems() {
        return mySearchItems;
    }

    public interface ViewClickListener {
        void onItemClicked(int position);
    }

    public void setViewClickListener (ViewClickListener viewClickListener) {
        mViewClickListener = viewClickListener;
    }
}
