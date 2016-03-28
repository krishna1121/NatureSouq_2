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
import com.naturesouq.model.SearchHistoryListItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by SI_Android_Binit on 2/10/2016.
 */
public class SearchHistoryBaseAdapter extends  RecyclerView.Adapter<SearchHistoryBaseAdapter.CustomViewHolder> {

    private List<SearchHistoryListItem> mySearchHistoryItems;
    private Context mContext;
    private ViewClickListener mViewClickListener;
    private ArrayList<SearchHistoryListItem> arraylist;

    public SearchHistoryBaseAdapter(Context mContext, List<SearchHistoryListItem> mySearchHistoryItems) {
        this.mySearchHistoryItems = mySearchHistoryItems;
        this.mContext = mContext;
        this.arraylist = new ArrayList<SearchHistoryListItem>();
        this.arraylist.addAll(mySearchHistoryItems);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_history_list_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder,final int position) {

        final SearchHistoryListItem feedItem = mySearchHistoryItems.get(position);

        holder.searchProductName.setText(feedItem.getName());
        // holder.productImage.setTag(holder);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewClickListener.onCancleClicked(position);
            }
        });
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mySearchHistoryItems.clear();
        if (charText.length() == 0) {
            mySearchHistoryItems.addAll(arraylist);
        }
        else
        {
            for (SearchHistoryListItem sh : arraylist)
            {
                if (sh.getName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    mySearchHistoryItems.add(sh);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mySearchHistoryItems.size();
    }

    @Override
    public long getItemId(int position) {
        return mySearchHistoryItems.indexOf(getItemId(position));
    }

    public void delete(int position) { //removes the row
        mySearchHistoryItems.remove(position);
        notifyItemRemoved(position);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView searchProductName;
        protected RelativeLayout layout;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.searchProductName = (TextView)itemView.findViewById(R.id.historyResult);
            this.layout = (RelativeLayout)itemView.findViewById(R.id.cleare);
        }

    }

    public List<SearchHistoryListItem> getSearchHistoryItems() {
        return mySearchHistoryItems;
    }

    public interface ViewClickListener {
        void onItemClicked(int position, SearchHistoryListItem item);
        void onCancleClicked(int position);
    }

    public void setViewClickListener (ViewClickListener viewClickListener) {
        mViewClickListener = viewClickListener;
    }
}
