package com.naturesouq.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.naturesouq.R;
import com.naturesouq.model.FilterActivityItem;
import com.naturesouq.model.VlauesItem;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by SI-Andriod on 29-Feb-16 .
 *
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {

    private List<FilterActivityItem> filterItems;
    private List<FilterActivityItem> filterItems10Only;
    private List<FilterActivityItem> filterItemsCut;
    private Context mContext;
    private ViewClickListener mViewClickListener;

    public FilterAdapter(Context mContext, List<FilterActivityItem> filterItems) {

        this.mContext = mContext;
        this.filterItems = filterItems;


    }

    @Override
    public void onBindViewHolder(final FilterViewHolder holder, final int position) {

        try{
            final FilterActivityItem filterItem = filterItems.get(position);
            //ArrayList<VlauesItem> item1 = filterItem.getList();
            List<VlauesItem> item1 = filterItem.getList();

            switch (filterItem.getViewType()) {
                case 0 :

                    holder.textView.setText(filterItem.getFiletrLabel());

                    for(int cbCount = 0 ; cbCount < item1.size() ; cbCount++ ){
                        VlauesItem item =  item1.get(cbCount);
                        switch (cbCount){
                            case 0 :
                                holder.checkBox0.setText(item.getValueItemLabel());
                                holder.checkBox0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),0,isChecked);

                                    }
                                });
                                break;
                            case 1 :
                                holder.checkBox1.setText(item.getValueItemLabel());
                                holder.checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(), 1,isChecked);
                                    }
                                });

                                break;
                            case 2 :
                                holder.checkBox2.setText(item.getValueItemLabel());

                                holder.checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),2,isChecked);
                                    }
                                });

                                break;
                            case 3 :
                                holder.checkBox3.setText(item.getValueItemLabel());
                                holder.checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),3,isChecked);
                                    }
                                });

                                break;
                            case 4 :
                                holder.checkBox4.setText(item.getValueItemLabel());
                                holder.checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),4,isChecked);
                                    }
                                });

                                break;
                            case 5 :
                                holder.checkBox5.setText(item.getValueItemLabel());
                                holder.checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),5,isChecked);
                                    }
                                });

                                break;
                            case 6 :
                                holder.checkBox6.setText(item.getValueItemLabel());
                                holder.checkBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(), 6,isChecked);
                                    }
                                });

                                break;
                            case 7 :
                                holder.checkBox7.setText(item.getValueItemLabel());
                                holder.checkBox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),7,isChecked);
                                    }
                                });

                                break;
                            case 8 :
                                holder.checkBox8.setText(item.getValueItemLabel());
                                holder.checkBox8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),8,isChecked);
                                    }
                                });

                                break;
                            case 9 :
                                holder.checkBox9.setText(item.getValueItemLabel());
                                holder.checkBox9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),9,isChecked);
                                    }
                                });
                                break;
                            default:
                        }
                    }
                    break;
                case 1 :
                    holder.textView.setText(filterItem.getFiletrLabel());
                    for(int cbCount = 0 ; cbCount < item1.size() ; cbCount++ ){
                        VlauesItem item =  item1.get(cbCount);
                        switch (cbCount){
                            case 0 :
                                holder.checkBox0.setText(item.getValueItemLabel());
                                holder.checkBox0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),0,isChecked);

                                    }
                                });
                                break;
                            case 1 :
                                holder.checkBox1.setText(item.getValueItemLabel());
                                holder.checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(), 1,isChecked);
                                    }
                                });

                                break;
                            case 2 :
                                holder.checkBox2.setText(item.getValueItemLabel());

                                holder.checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),2,isChecked);
                                    }
                                });

                                break;
                            case 3 :
                                holder.checkBox3.setText(item.getValueItemLabel());
                                holder.checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),3,isChecked);
                                    }
                                });

                                break;
                            case 4 :
                                holder.checkBox4.setText(item.getValueItemLabel());
                                holder.checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),4,isChecked);
                                    }
                                });

                                break;
                            case 5 :
                                holder.checkBox5.setText(item.getValueItemLabel());
                                holder.checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),5,isChecked);
                                    }
                                });

                                break;
                            case 6 :
                                holder.checkBox6.setText(item.getValueItemLabel());
                                holder.checkBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(), 6,isChecked);
                                    }
                                });

                                break;
                            case 7 :
                                holder.checkBox7.setText(item.getValueItemLabel());
                                holder.checkBox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),7,isChecked);
                                    }
                                });

                                break;
                            case 8 :
                                holder.checkBox8.setText(item.getValueItemLabel());
                                holder.checkBox8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),8,isChecked);
                                    }
                                });

                                break;
                            case 9 :
                                holder.checkBox9.setText(item.getValueItemLabel());
                                holder.checkBox9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),9,isChecked);
                                    }
                                });
                                break;
                            default:
                        }
                    }
                    break;
                case 2 :

                    holder.textView.setText(filterItem.getFiletrLabel());

                    for(int cbCount = 0 ; cbCount < item1.size() ; cbCount++ ){
                        VlauesItem item =  item1.get(cbCount);
                        switch (cbCount){
                            case 0 :
                                holder.checkBox0.setText(item.getValueItemLabel());
                                holder.checkBox0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),0,isChecked);

                                    }
                                });
                                break;
                            case 1 :
                                holder.checkBox1.setText(item.getValueItemLabel());
                                holder.checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(), 1,isChecked);
                                    }
                                });

                                break;
                            case 2 :
                                holder.checkBox2.setText(item.getValueItemLabel());

                                holder.checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),2,isChecked);
                                    }
                                });

                                break;
                            case 3 :
                                holder.checkBox3.setText(item.getValueItemLabel());
                                holder.checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),3,isChecked);
                                    }
                                });

                                break;
                            case 4 :
                                holder.checkBox4.setText(item.getValueItemLabel());
                                holder.checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),4,isChecked);
                                    }
                                });

                                break;
                            case 5 :
                                holder.checkBox5.setText(item.getValueItemLabel());
                                holder.checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),5,isChecked);
                                    }
                                });

                                break;
                            case 6 :
                                holder.checkBox6.setText(item.getValueItemLabel());
                                holder.checkBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(), 6,isChecked);
                                    }
                                });

                                break;
                            case 7 :
                                holder.checkBox7.setText(item.getValueItemLabel());
                                holder.checkBox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),7,isChecked);
                                    }
                                });

                                break;
                            case 8 :
                                holder.checkBox8.setText(item.getValueItemLabel());
                                holder.checkBox8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),8,isChecked);
                                    }
                                });

                                break;
                            case 9 :
                                holder.checkBox9.setText(item.getValueItemLabel());
                                holder.checkBox9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),9,isChecked);
                                    }
                                });
                                break;
                            default:
                        }
                    }
                    break;
                case 3 :

                    holder.textView.setText(filterItem.getFiletrLabel());

                    for(int cbCount = 0 ; cbCount < item1.size() ; cbCount++ ){
                        VlauesItem item =  item1.get(cbCount);
                        switch (cbCount){
                            case 0 :
                                holder.checkBox0.setText(item.getValueItemLabel());
                                holder.checkBox0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),0,isChecked);

                                    }
                                });
                                break;
                            case 1 :
                                holder.checkBox1.setText(item.getValueItemLabel());
                                holder.checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(), 1,isChecked);
                                    }
                                });

                                break;
                            case 2 :
                                holder.checkBox2.setText(item.getValueItemLabel());

                                holder.checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),2,isChecked);
                                    }
                                });

                                break;
                            case 3 :
                                holder.checkBox3.setText(item.getValueItemLabel());
                                holder.checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),3,isChecked);
                                    }
                                });

                                break;
                            case 4 :
                                holder.checkBox4.setText(item.getValueItemLabel());
                                holder.checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),4,isChecked);
                                    }
                                });

                                break;
                            case 5 :
                                holder.checkBox5.setText(item.getValueItemLabel());
                                holder.checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),5,isChecked);
                                    }
                                });

                                break;
                            case 6 :
                                holder.checkBox6.setText(item.getValueItemLabel());
                                holder.checkBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(), 6,isChecked);
                                    }
                                });

                                break;
                            case 7 :
                                holder.checkBox7.setText(item.getValueItemLabel());
                                holder.checkBox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),7,isChecked);
                                    }
                                });

                                break;
                            case 8 :
                                holder.checkBox8.setText(item.getValueItemLabel());
                                holder.checkBox8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),8,isChecked);
                                    }
                                });

                                break;
                            case 9 :
                                holder.checkBox9.setText(item.getValueItemLabel());
                                holder.checkBox9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),9,isChecked);
                                    }
                                });
                                break;
                            default:
                        }
                    }
                    break;
                case 4 :

                    holder.textView.setText(filterItem.getFiletrLabel());

                    for(int cbCount = 0 ; cbCount < item1.size() ; cbCount++ ){
                        VlauesItem item =  item1.get(cbCount);
                        switch (cbCount){
                            case 0 :
                                holder.checkBox0.setText(item.getValueItemLabel());
                                holder.checkBox0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),0,isChecked);

                                    }
                                });
                                break;
                            case 1 :
                                holder.checkBox1.setText(item.getValueItemLabel());
                                holder.checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(), 1,isChecked);
                                    }
                                });

                                break;
                            case 2 :
                                holder.checkBox2.setText(item.getValueItemLabel());

                                holder.checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),2,isChecked);
                                    }
                                });

                                break;
                            case 3 :
                                holder.checkBox3.setText(item.getValueItemLabel());
                                holder.checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),3,isChecked);
                                    }
                                });

                                break;
                            case 4 :
                                holder.checkBox4.setText(item.getValueItemLabel());
                                holder.checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),4,isChecked);
                                    }
                                });

                                break;
                            case 5 :
                                holder.checkBox5.setText(item.getValueItemLabel());
                                holder.checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),5,isChecked);
                                    }
                                });

                                break;
                            case 6 :
                                holder.checkBox6.setText(item.getValueItemLabel());
                                holder.checkBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(), 6,isChecked);
                                    }
                                });

                                break;
                            case 7 :
                                holder.checkBox7.setText(item.getValueItemLabel());
                                holder.checkBox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),7,isChecked);
                                    }
                                });

                                break;
                            case 8 :
                                holder.checkBox8.setText(item.getValueItemLabel());
                                holder.checkBox8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),8,isChecked);
                                    }
                                });

                                break;
                            case 9 :
                                holder.checkBox9.setText(item.getValueItemLabel());
                                holder.checkBox9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),9,isChecked);
                                    }
                                });
                                break;
                            default:
                        }
                    }
                    break;
                case 5 :

                    holder.textView.setText(filterItem.getFiletrLabel());

                    for(int cbCount = 0 ; cbCount < item1.size() ; cbCount++ ){
                        VlauesItem item =  item1.get(cbCount);
                        switch (cbCount){
                            case 0 :
                                holder.checkBox0.setText(item.getValueItemLabel());
                                holder.checkBox0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),0,isChecked);

                                    }
                                });
                                break;
                            case 1 :
                                holder.checkBox1.setText(item.getValueItemLabel());
                                holder.checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(), 1,isChecked);
                                    }
                                });

                                break;
                            case 2 :
                                holder.checkBox2.setText(item.getValueItemLabel());

                                holder.checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),2,isChecked);
                                    }
                                });

                                break;
                            case 3 :
                                holder.checkBox3.setText(item.getValueItemLabel());
                                holder.checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),3,isChecked);
                                    }
                                });

                                break;
                            case 4 :
                                holder.checkBox4.setText(item.getValueItemLabel());
                                holder.checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),4,isChecked);
                                    }
                                });

                                break;
                            case 5 :
                                holder.checkBox5.setText(item.getValueItemLabel());
                                holder.checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),5,isChecked);
                                    }
                                });

                                break;
                            case 6 :
                                holder.checkBox6.setText(item.getValueItemLabel());
                                holder.checkBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(), 6,isChecked);
                                    }
                                });

                                break;
                            case 7 :
                                holder.checkBox7.setText(item.getValueItemLabel());
                                holder.checkBox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),7,isChecked);
                                    }
                                });

                                break;
                            case 8 :
                                holder.checkBox8.setText(item.getValueItemLabel());
                                holder.checkBox8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),8,isChecked);
                                    }
                                });

                                break;
                            case 9 :
                                holder.checkBox9.setText(item.getValueItemLabel());
                                holder.checkBox9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),9,isChecked);
                                    }
                                });
                                break;
                            default:
                        }
                    }
                    break;
                case 6 :

                    holder.textView.setText(filterItem.getFiletrLabel());

                    for(int cbCount = 0 ; cbCount < item1.size() ; cbCount++ ){
                        VlauesItem item =  item1.get(cbCount);
                        switch (cbCount){
                            case 0 :
                                holder.checkBox0.setText(item.getValueItemLabel());
                                holder.checkBox0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),0,isChecked);

                                    }
                                });
                                break;
                            case 1 :
                                holder.checkBox1.setText(item.getValueItemLabel());
                                holder.checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(), 1,isChecked);
                                    }
                                });

                                break;
                            case 2 :
                                holder.checkBox2.setText(item.getValueItemLabel());

                                holder.checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),2,isChecked);
                                    }
                                });

                                break;
                            case 3 :
                                holder.checkBox3.setText(item.getValueItemLabel());
                                holder.checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),3,isChecked);
                                    }
                                });

                                break;
                            case 4 :
                                holder.checkBox4.setText(item.getValueItemLabel());
                                holder.checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),4,isChecked);
                                    }
                                });

                                break;
                            case 5 :
                                holder.checkBox5.setText(item.getValueItemLabel());
                                holder.checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),5,isChecked);
                                    }
                                });

                                break;
                            case 6 :
                                holder.checkBox6.setText(item.getValueItemLabel());
                                holder.checkBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(), 6,isChecked);
                                    }
                                });

                                break;
                            case 7 :
                                holder.checkBox7.setText(item.getValueItemLabel());
                                holder.checkBox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),7,isChecked);
                                    }
                                });

                                break;
                            case 8 :
                                holder.checkBox8.setText(item.getValueItemLabel());
                                holder.checkBox8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),8,isChecked);
                                    }
                                });

                                break;
                            case 9 :
                                holder.checkBox9.setText(item.getValueItemLabel());
                                holder.checkBox9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),9,isChecked);
                                    }
                                });
                                break;
                            default:
                        }
                    }
                    break;
                case 7 :

                    holder.textView.setText(filterItem.getFiletrLabel());

                    for(int cbCount = 0 ; cbCount < item1.size() ; cbCount++ ){
                        VlauesItem item =  item1.get(cbCount);
                        switch (cbCount){
                            case 0 :
                                holder.checkBox0.setText(item.getValueItemLabel());
                                holder.checkBox0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),0,isChecked);

                                    }
                                });
                                break;
                            case 1 :
                                holder.checkBox1.setText(item.getValueItemLabel());
                                holder.checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(), 1,isChecked);
                                    }
                                });

                                break;
                            case 2 :
                                holder.checkBox2.setText(item.getValueItemLabel());

                                holder.checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),2,isChecked);
                                    }
                                });

                                break;
                            case 3 :
                                holder.checkBox3.setText(item.getValueItemLabel());
                                holder.checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),3,isChecked);
                                    }
                                });

                                break;
                            case 4 :
                                holder.checkBox4.setText(item.getValueItemLabel());
                                holder.checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),4,isChecked);
                                    }
                                });

                                break;
                            case 5 :
                                holder.checkBox5.setText(item.getValueItemLabel());
                                holder.checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),5,isChecked);
                                    }
                                });

                                break;
                            case 6 :
                                holder.checkBox6.setText(item.getValueItemLabel());
                                holder.checkBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(), 6,isChecked);
                                    }
                                });

                                break;
                            case 7 :
                                holder.checkBox7.setText(item.getValueItemLabel());
                                holder.checkBox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),7,isChecked);
                                    }
                                });

                                break;
                            case 8 :
                                holder.checkBox8.setText(item.getValueItemLabel());
                                holder.checkBox8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),8,isChecked);
                                    }
                                });

                                break;
                            case 9 :
                                holder.checkBox9.setText(item.getValueItemLabel());
                                holder.checkBox9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),9,isChecked);
                                    }
                                });
                                break;
                            default:
                        }
                    }
                    break;
                case 8 :

                    holder.textView.setText(filterItem.getFiletrLabel());

                    for(int cbCount = 0 ; cbCount < item1.size() ; cbCount++ ){
                        VlauesItem item =  item1.get(cbCount);
                        switch (cbCount){
                            case 0 :
                                holder.checkBox0.setText(item.getValueItemLabel());
                                holder.checkBox0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),0,isChecked);

                                    }
                                });
                                break;
                            case 1 :
                                holder.checkBox1.setText(item.getValueItemLabel());
                                holder.checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(), 1,isChecked);
                                    }
                                });

                                break;
                            case 2 :
                                holder.checkBox2.setText(item.getValueItemLabel());

                                holder.checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),2,isChecked);
                                    }
                                });

                                break;
                            case 3 :
                                holder.checkBox3.setText(item.getValueItemLabel());
                                holder.checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),3,isChecked);
                                    }
                                });

                                break;
                            case 4 :
                                holder.checkBox4.setText(item.getValueItemLabel());
                                holder.checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),4,isChecked);
                                    }
                                });

                                break;
                            case 5 :
                                holder.checkBox5.setText(item.getValueItemLabel());
                                holder.checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),5,isChecked);
                                    }
                                });

                                break;
                            case 6 :
                                holder.checkBox6.setText(item.getValueItemLabel());
                                holder.checkBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(), 6,isChecked);
                                    }
                                });

                                break;
                            case 7 :
                                holder.checkBox7.setText(item.getValueItemLabel());
                                holder.checkBox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),7,isChecked);
                                    }
                                });

                                break;
                            case 8 :
                                holder.checkBox8.setText(item.getValueItemLabel());
                                holder.checkBox8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),8,isChecked);
                                    }
                                });

                                break;
                            case 9 :
                                holder.checkBox9.setText(item.getValueItemLabel());
                                holder.checkBox9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),9,isChecked);
                                    }
                                });
                                break;
                            default:
                        }
                    }
                    break;
                case 9 :

                    holder.textView.setText(filterItem.getFiletrLabel());

                    for(int cbCount = 0 ; cbCount < item1.size() ; cbCount++ ){
                        VlauesItem item =  item1.get(cbCount);
                        switch (cbCount){
                            case 0 :
                                holder.checkBox0.setText(item.getValueItemLabel());
                                holder.checkBox0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),0,isChecked);

                                    }
                                });
                                break;
                            case 1 :
                                holder.checkBox1.setText(item.getValueItemLabel());
                                holder.checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(), 1,isChecked);
                                    }
                                });

                                break;
                            case 2 :
                                holder.checkBox2.setText(item.getValueItemLabel());

                                holder.checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),2,isChecked);
                                    }
                                });

                                break;
                            case 3 :
                                holder.checkBox3.setText(item.getValueItemLabel());
                                holder.checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),3,isChecked);
                                    }
                                });

                                break;
                            case 4 :
                                holder.checkBox4.setText(item.getValueItemLabel());
                                holder.checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),4,isChecked);
                                    }
                                });

                                break;
                            case 5 :
                                holder.checkBox5.setText(item.getValueItemLabel());
                                holder.checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),5,isChecked);
                                    }
                                });

                                break;
                            case 6 :
                                holder.checkBox6.setText(item.getValueItemLabel());
                                holder.checkBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(), 6,isChecked);
                                    }
                                });

                                break;
                            case 7 :
                                holder.checkBox7.setText(item.getValueItemLabel());
                                holder.checkBox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),7,isChecked);
                                    }
                                });

                                break;
                            case 8 :
                                holder.checkBox8.setText(item.getValueItemLabel());
                                holder.checkBox8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),8,isChecked);
                                    }
                                });

                                break;
                            case 9 :
                                holder.checkBox9.setText(item.getValueItemLabel());
                                holder.checkBox9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        mViewClickListener.onItemClicked(filterItem.getViewType(),9,isChecked);
                                    }
                                });
                                break;
                            default:
                        }
                    }
                    break;

                default:
            }

        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //LinearLayout topLayout = new LinearLayout(mContext);
        //topLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout ll = null ;
        if (filterItems != null) {

                ll = new LinearLayout(mContext);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.setBackgroundColor(mContext.getResources().getColor(R.color.filter_background));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(10, 20, 10, 10);
                ll.setLayoutParams(layoutParams);

                FilterActivityItem filterItem = filterItems.get(viewType);


                TextView textView = new TextView(mContext);
                textView.setTag(viewType + 11);
                textView.setTypeface(null, Typeface.BOLD);
                LinearLayout.LayoutParams layoutParamsTv = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParamsTv.setMargins(10, 10, 0, 0);
                textView.setLayoutParams(layoutParamsTv);

                ll.addView(textView);

                for (int cbCount = 0; cbCount < filterItem.getList().size(); cbCount++) {
                    VlauesItem item1 = filterItem.getList().get(cbCount);
                    CheckBox cb = new CheckBox(mContext);
                    cb.setTag(cbCount);
                    cb.setTextSize(12);
                    cb.setTextColor(Color.parseColor("#515252"));
                    ll.addView(cb);
                }

                //topLayout.addView(ll);

        }else{
            //topLayout.addView(null);
        }

        FilterViewHolder viewHolder = new FilterViewHolder(ll,viewType);
        return viewHolder;
    }


    @Override
    public int getItemViewType(int position) {

        int viewTypePosition = 0;
        int viewType = filterItems.get(position).getViewType();

        switch (viewType) {
            case 0 :
                viewTypePosition = viewType ;
                break;
            case 1 :
                viewTypePosition = viewType ;
                break;
            case 2 :
                viewTypePosition = viewType ;
                break;
            case 3 :
                viewTypePosition = viewType ;
                break;
            case 4 :
                viewTypePosition = viewType ;
                break;
            case 5 :
                viewTypePosition = viewType ;
                break;
            case 6 :
                viewTypePosition = viewType ;
                break;
            case 7 :
                viewTypePosition = viewType ;
                break;
            case 8 :
                viewTypePosition = viewType ;
                break;
            case 9 :
                viewTypePosition = viewType ;
                break;


            default:
        }

        return viewTypePosition;
    }

    @Override
    public int getItemCount() {
        return filterItems.size();
    }

    @Override
    public long getItemId(int position) {
        return filterItems.indexOf(getItemId(position));
    }

    public void delete(int position) { //removes the row
        filterItems.remove(position);
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox0 ;CheckBox checkBox1 ;CheckBox checkBox2 ;CheckBox checkBox3 ;CheckBox checkBox4 ;CheckBox checkBox5 ;CheckBox checkBox6 ;CheckBox checkBox7 ;CheckBox checkBox8 ;CheckBox checkBox9 ;
        TextView textView ;

        public FilterViewHolder(View itemView , int viewType) {
            super(itemView);

            FilterActivityItem filterItem = filterItems.get(viewType);
            List<VlauesItem> item1 = filterItem.getList();

            switch (viewType) {
                case 0 :

                    textView =  (TextView) itemView.findViewWithTag(viewType+11);

                    for(int cbCount = 0 ; cbCount < item1.size() ; cbCount++ ){

                        switch (cbCount){
                            case 0 :
                                checkBox0 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 1 :
                                checkBox1 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 2 :
                                checkBox2 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 3 :
                                checkBox3 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 4 :
                                checkBox4 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 5 :
                                checkBox5 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 6 :
                                checkBox6 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 7 :
                                checkBox7 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 8 :
                                checkBox8 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 9 :
                                checkBox9 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            default:

                        }

                    }

                    break;
                case 1 :

                    textView =  (TextView) itemView.findViewWithTag(viewType+11);

                    for(int cbCount = 0 ; cbCount < item1.size() ; cbCount++ ){

                        switch (cbCount){
                            case 0 :
                                checkBox0 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 1 :
                                checkBox1 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 2 :
                                checkBox2 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 3 :
                                checkBox3 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 4 :
                                checkBox4 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 5 :
                                checkBox5 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 6 :
                                checkBox6 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 7 :
                                checkBox7 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 8 :
                                checkBox8 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 9 :
                                checkBox9 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            default:

                        }

                    }
                    break;
                case 2 :
                    textView =  (TextView) itemView.findViewWithTag(viewType+11);
                    for(int cbCount = 0 ; cbCount < item1.size() ; cbCount++ ){

                        switch (cbCount){
                            case 0 :
                                checkBox0 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 1 :
                                checkBox1 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 2 :
                                checkBox2 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 3 :
                                checkBox3 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 4 :
                                checkBox4 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 5 :
                                checkBox5 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 6 :
                                checkBox6 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 7 :
                                checkBox7 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 8 :
                                checkBox8 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 9 :
                                checkBox9 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            default:

                        }

                    }
                    break;
                case 3 :
                    textView =  (TextView) itemView.findViewWithTag(viewType+11);
                    for(int cbCount = 0 ; cbCount < item1.size() ; cbCount++ ){

                        switch (cbCount){
                            case 0 :
                                checkBox0 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 1 :
                                checkBox1 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 2 :
                                checkBox2 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 3 :
                                checkBox3 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 4 :
                                checkBox4 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 5 :
                                checkBox5 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 6 :
                                checkBox6 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 7 :
                                checkBox7 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 8 :
                                checkBox8 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 9 :
                                checkBox9 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            default:

                        }

                    }
                    break;
                case 4 :
                    textView =  (TextView) itemView.findViewWithTag(viewType+11);
                    for(int cbCount = 0 ; cbCount < item1.size() ; cbCount++ ){

                        switch (cbCount){
                            case 0 :
                                checkBox0 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 1 :
                                checkBox1 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 2 :
                                checkBox2 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 3 :
                                checkBox3 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 4 :
                                checkBox4 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 5 :
                                checkBox5 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 6 :
                                checkBox6 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 7 :
                                checkBox7 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 8 :
                                checkBox8 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 9 :
                                checkBox9 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            default:

                        }

                    }
                    break;
                case 5 :
                    textView =  (TextView) itemView.findViewWithTag(viewType+11);
                    for(int cbCount = 0 ; cbCount < item1.size() ; cbCount++ ){

                        switch (cbCount){
                            case 0 :
                                checkBox0 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 1 :
                                checkBox1 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 2 :
                                checkBox2 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 3 :
                                checkBox3 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 4 :
                                checkBox4 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 5 :
                                checkBox5 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 6 :
                                checkBox6 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 7 :
                                checkBox7 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 8 :
                                checkBox8 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 9 :
                                checkBox9 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            default:

                        }

                    }
                    break;
                case 6 :
                    textView =  (TextView) itemView.findViewWithTag(viewType+11);
                    for(int cbCount = 0 ; cbCount < item1.size() ; cbCount++ ){

                        switch (cbCount){
                            case 0 :
                                checkBox0 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 1 :
                                checkBox1 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 2 :
                                checkBox2 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 3 :
                                checkBox3 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 4 :
                                checkBox4 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 5 :
                                checkBox5 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 6 :
                                checkBox6 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 7 :
                                checkBox7 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 8 :
                                checkBox8 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 9 :
                                checkBox9 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            default:

                        }

                    }
                    break;
                case 7 :
                    textView =  (TextView) itemView.findViewWithTag(viewType+11);
                    for(int cbCount = 0 ; cbCount < item1.size() ; cbCount++ ){

                        switch (cbCount){
                            case 0 :
                                checkBox0 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 1 :
                                checkBox1 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 2 :
                                checkBox2 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 3 :
                                checkBox3 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 4 :
                                checkBox4 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 5 :
                                checkBox5 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 6 :
                                checkBox6 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 7 :
                                checkBox7 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 8 :
                                checkBox8 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 9 :
                                checkBox9 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            default:

                        }

                    }
                    break;
                case 8 :
                    textView =  (TextView) itemView.findViewWithTag(viewType+11);
                    for(int cbCount = 0 ; cbCount < item1.size() ; cbCount++ ){

                        switch (cbCount){
                            case 0 :
                                checkBox0 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 1 :
                                checkBox1 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 2 :
                                checkBox2 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 3 :
                                checkBox3 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 4 :
                                checkBox4 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 5 :
                                checkBox5 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 6 :
                                checkBox6 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 7 :
                                checkBox7 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 8 :
                                checkBox8 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 9 :
                                checkBox9 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            default:
                        }
                    }
                    break;
                case 9 :
                    textView =  (TextView) itemView.findViewWithTag(viewType+11);
                    for(int cbCount = 0 ; cbCount < item1.size() ; cbCount++ ){

                        switch (cbCount){
                            case 0 :
                                checkBox0 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 1 :
                                checkBox1 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 2 :
                                checkBox2 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 3 :
                                checkBox3 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 4 :
                                checkBox4 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 5 :
                                checkBox5 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 6 :
                                checkBox6 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 7 :
                                checkBox7 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 8 :
                                checkBox8 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            case 9 :
                                checkBox9 =  (CheckBox) itemView.findViewWithTag(cbCount);
                                break;
                            default:
                        }
                    }
                    break;
                default:
            }

        }
    }

    public interface ViewClickListener {
        void onItemClicked(int positionOrViewType, int cbCount, boolean isChecked);

    }

    public void setViewClickListener(ViewClickListener viewClickListener) {
        mViewClickListener = viewClickListener;
    }


    public List<FilterActivityItem> getFilterItems() {
        return filterItems;
    }

    public void setFilterItems(List<FilterActivityItem> filterItems) {
        //this.filterItems = filterItems;

        filterItemsCut = new ArrayList<FilterActivityItem>();

        if(filterItems.size() > 10){
            this.filterItems10Only = filterItems.subList(0,10);

            for(FilterActivityItem item : filterItems10Only){
                FilterActivityItem itemCut ;
                if(item.getList().size() > 10){
                    itemCut = new FilterActivityItem(item.getFiletrLabel(), item.getList().subList(0,10), item.getFilterCode(), item.getViewType());
                }else{
                    itemCut = item ;
                }
                filterItemsCut.add(itemCut);
            }
            this.filterItems = filterItemsCut ;
        }else{
            this.filterItems10Only = filterItems ;

            for(FilterActivityItem item : filterItems10Only){
                FilterActivityItem itemCut ;
                if(item.getList().size() > 10){
                    itemCut = new FilterActivityItem(item.getFiletrLabel(), item.getList().subList(0,10), item.getFilterCode(), item.getViewType());
                }else{
                    itemCut = item ;
                }
                filterItemsCut.add(itemCut);
            }

            this.filterItems = filterItemsCut ;
        }
    }
}

