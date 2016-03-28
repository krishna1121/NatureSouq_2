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
import android.widget.Toast;

import com.naturesouq.R;
import com.naturesouq.model.MyFavoriteListItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SI_Android_Binit on 1/27/2016.
 */
public class MyFavoriteAdapter extends  RecyclerView.Adapter<MyFavoriteAdapter.CustomViewHolder> {

    private List<MyFavoriteListItem> myFavoriteItems;
    private Context mContext;
    private ViewClickListener mViewClickListener;
    final View newView[]={null};

    public MyFavoriteAdapter(Context mContext, List<MyFavoriteListItem> myFavoriteItems) {
        this.myFavoriteItems = myFavoriteItems;
        this.mContext = mContext;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_favorite_list_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder,final int position) {

        final MyFavoriteListItem feedItem = myFavoriteItems.get(position);
        if(!(feedItem.getImage_url().equals(""))){
            Picasso.with(mContext).load(feedItem.getImage_url()).error(R.drawable.placeholder_item).into(holder.productImage);
        }

        holder.productName.setText(feedItem.getName());
        holder.productDesc.setText(feedItem.getShortDescription());
        holder.productPrice.setText("AED " + feedItem.getPrice());
       // holder.productImage.setTag(holder);
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewClickListener.onItemClicked(position);

            }
        });

        holder.productSettingLayout.setTag(holder);
        holder.productSettingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewClickListener.onSettingClicked(position, myFavoriteItems.get(position), holder.frameLayoutButton);

            }
        });
        holder.removeFev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext,""+position,Toast.LENGTH_LONG).show();
                mViewClickListener.onRemoveFromFavourite(position,myFavoriteItems.get(position),holder.frameLayoutButton);
            }
        });

        holder.favoriteImage.setImageResource(R.drawable.favorite_product);
    }


    @Override
    public int getItemCount() {
        return myFavoriteItems.size();
    }

    @Override
    public long getItemId(int position) {
        return myFavoriteItems.indexOf(getItemId(position));
    }

    public void delete(int position) { //removes the row
        myFavoriteItems.remove(position);
        notifyItemRemoved(position);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView productImage, favoriteImage;
        protected TextView productName, productDesc, productPrice;
        FrameLayout frameLayoutButton;
        RelativeLayout itemLayout, favoriteImageLayout, productSettingLayout,removeFev;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.productImage = (ImageView)itemView.findViewById(R.id.productImage);
            this.productName = (TextView)itemView.findViewById(R.id.name);
            this.productDesc = (TextView)itemView.findViewById(R.id.discription);
            this.productPrice = (TextView)itemView.findViewById(R.id.price);
            this.favoriteImage = (ImageView)itemView.findViewById(R.id.fevorite_img);
            this.favoriteImageLayout = (RelativeLayout)itemView.findViewById(R.id.fevorite_imgLBL);
            this.productSettingLayout = (RelativeLayout)itemView.findViewById(R.id.listmenu);
            this.frameLayoutButton = (FrameLayout)itemView.findViewById(R.id.removeItem);
            this.itemLayout = (RelativeLayout)itemView.findViewById(R.id.describ);
            this.removeFev=(RelativeLayout)itemView.findViewById(R.id.removeFev);

            //frameLayoutButton.setVisibility(View.INVISIBLE);

        }

    }

    public List<MyFavoriteListItem> getFavoriteItems() {
        return myFavoriteItems;
    }

    public interface ViewClickListener {
        void onItemClicked(int position);
        void onSettingClicked(int position, MyFavoriteListItem item,View view);
        void onRemoveFromFavourite(int position,MyFavoriteListItem item,View view);
    }

    public void setViewClickListener (ViewClickListener viewClickListener) {
        mViewClickListener = viewClickListener;
    }

}
