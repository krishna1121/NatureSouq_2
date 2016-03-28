package com.naturesouq.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.naturesouq.R;
import com.naturesouq.model.HomeProductsItem;
import com.naturesouq.navigation.HomeFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SI_Android_Binit on 12/28/2015.
 */
public class MyVerticalAdapter extends RecyclerView.Adapter<MyVerticalAdapter.ViewHolder> {
    private ArrayList<ArrayList<HomeProductsItem>> dataset;
    private Context context;
    private Map<Integer, Parcelable> scrollStatePositionsMap = new HashMap<>();
    public LayoutInflater mInflater;
    private ViewClickListener mViewClickListener;
    public MyHorizontalAdapter horizontalAdapter;
    public List<HomeProductsItem> homeProductsItems;


    public MyVerticalAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<ArrayList<HomeProductsItem>>();
        mInflater = LayoutInflater.from(context);
        horizontalAdapter = new MyHorizontalAdapter(context, homeProductsItems, new MyHorizontalAdapter.ViewClickListener() {
            @Override
            public void onImageClicked(int position, String item) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    @Override
    public MyVerticalAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        holder.recyclerView.setLayoutManager(linearLayoutManager);
        MyHorizontalAdapter adapter = new MyHorizontalAdapter(context ,dataset.get(position),new MyHorizontalAdapter.ViewClickListener(){

            @Override
            public void onImageClicked(int index, String item) {
                mViewClickListener.onItemClick(position,dataset.get(position).get(index));
            }
        });
        holder.recyclerView.setAdapter(adapter);

        holder.setPosition(position);
        if (scrollStatePositionsMap.containsKey(position)) {
            holder.recyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    holder.recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                    holder.recyclerView.getLayoutManager().onRestoreInstanceState(scrollStatePositionsMap.get(position));
                    return false;
                }
            });
        }

        holder.category_name.setText(HomeFragment.categoryName[position]);

        holder.category_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, "Binit" + String.valueOf(position), Toast.LENGTH_SHORT).show();
//                mViewClickListener.onItemClicked(position, "categoryName");
                mViewClickListener.onCategoryClicked(position, ((TextView) v).getText().toString(), HomeFragment.category_id[position]);
            }
        });

    }

    public void add(ArrayList<ArrayList<HomeProductsItem>> dataset){
        this.dataset = dataset ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public RecyclerView recyclerView;
        public TextView category_name;

        public void setPosition(int position) {
            this.position = position;
        }

        public int position;

        public ViewHolder(final View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.horizontal_grid_view);
            category_name = (TextView)itemView.findViewById(R.id.productCategoryName);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        scrollStatePositionsMap.put(position, recyclerView.getLayoutManager().onSaveInstanceState());
                    }
                }
            });
        }

    }

    public ArrayList<ArrayList<HomeProductsItem>> getHomeProductsItems() {
        return dataset;
    }

    public List<HomeProductsItem> getHomeProductListItem(){
        return homeProductsItems;
    }

    public interface ViewClickListener {
        void onCategoryClicked(int position,String category, String categoryId);
        void onItemClick(int position, HomeProductsItem item);
    }

    public void setViewClickListener (ViewClickListener viewClickListener) {
        mViewClickListener = viewClickListener;
    }
}