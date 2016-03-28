package com.naturesouq.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.naturesouq.R;
import com.naturesouq.model.ChangeAddressListItem;
import java.util.List;

/**
 * Created by SI_Android on 2/1/2016.
 */

public class ChangeAddressAdapter extends RecyclerView.Adapter<ChangeAddressAdapter.CustomViewHolder> {

    private List<ChangeAddressListItem> myAddressListItems;
    private Context mContext;
    private ViewClickListener mViewClickListener;
    View viewFinal[] = {null};
    int prevPos;

    public ChangeAddressAdapter(Context mContext, List<ChangeAddressListItem> myAddressListItems) {
        this.myAddressListItems = myAddressListItems;
        this.mContext = mContext;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_address_list_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {

        final ChangeAddressListItem feedItem = myAddressListItems.get(position);
        holder.addressLBL.setText("ADDRESS" + " " + (position + 1));
        String addressCon = feedItem.getStreet() + ", " + feedItem.getCity() + "\n" + feedItem.getState() + ", " + feedItem.getCountry_id() + ", " + feedItem.getPostcode() + "\n" + feedItem.getTelephone();
        holder.address.setText(addressCon);

        if (feedItem.isStateOfActivite()) {
            holder.frameLayoutButton.setVisibility(View.VISIBLE);
        } else {
            holder.frameLayoutButton.setVisibility(View.INVISIBLE);
        }

        holder.addressSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.frameLayoutButton.setVisibility(View.VISIBLE);

                ChangeAddressListItem item = new ChangeAddressListItem(feedItem.getAddress_id(), feedItem.getFirstname(), feedItem.getLastname(), feedItem.getCompany(), feedItem.getCity(), feedItem.getCountry_id(), feedItem.getState(), feedItem.getPostcode(), feedItem.getTelephone(), feedItem.getStreet(), feedItem.getCustomer_id(), true);
                myAddressListItems.remove(position);
                myAddressListItems.add(position, item);

                for (int exceptPos = 0; exceptPos < myAddressListItems.size(); exceptPos++) {
                    if (!(position == exceptPos)) {

                        ChangeAddressListItem exceptItem = myAddressListItems.get(exceptPos);
                        ChangeAddressListItem itemexcept = new ChangeAddressListItem(exceptItem.getAddress_id(), exceptItem.getFirstname(), exceptItem.getLastname(), exceptItem.getCompany(), exceptItem.getCity(), exceptItem.getCountry_id(), exceptItem.getState(), exceptItem.getPostcode(), exceptItem.getTelephone(), exceptItem.getStreet(), exceptItem.getCustomer_id(), false);
                        myAddressListItems.remove(exceptPos);
                        myAddressListItems.add(exceptPos, itemexcept);

                        notifyDataSetChanged();

                    } else {

                    }
                }
            }
        });

        //setOnClickListener when Edit and Delete are visvible .
        holder.editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewClickListener.onEditClicked(position);
            }
        });

        holder.deleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewClickListener.onDeleteClicked(position);
            }
        });

        holder.addressLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewClickListener.onItemClicked(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return myAddressListItems.size();
    }

    @Override
    public long getItemId(int position) {
        return myAddressListItems.indexOf(getItemId(position));
    }

    public void delete(int position) {
        //removes the row
        myAddressListItems.remove(position);
        notifyItemRemoved(position);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected LinearLayout addressSetting, addressLabel;
        protected TextView addressLBL, address;
        FrameLayout frameLayoutButton;
        RelativeLayout editAddress, deleteAddress;
        //RadioButton selected;
        //RadioGroup radioGroup;
        //boolean checked;
        int id;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.addressLBL = (TextView) itemView.findViewById(R.id.lbl1);
            this.address = (TextView) itemView.findViewById(R.id.shipping_address);
            this.addressSetting = (LinearLayout) itemView.findViewById(R.id.listmenu);
            this.frameLayoutButton = (FrameLayout) itemView.findViewById(R.id.options);
            this.editAddress = (RelativeLayout) itemView.findViewById(R.id.editAddress);
            this.deleteAddress = (RelativeLayout) itemView.findViewById(R.id.deleteAddress);
            addressLabel = (LinearLayout) itemView.findViewById(R.id.addressLabel);
            //this.selected = (RadioButton) itemView.findViewById(R.id.selectAddress);
        }
    }

    public List<ChangeAddressListItem> getAddressListItems() {
        return myAddressListItems;
    }

    public void setMyAddressListItems(List<ChangeAddressListItem> myAddressListItems) {
        this.myAddressListItems = myAddressListItems;
    }

    public interface ViewClickListener {
        void onItemClicked(int position);

        void onSettingClicked(int position, View view);

        void onEditClicked(int position);

        void onDeleteClicked(int position);
    }

    public void setViewClickListener(ViewClickListener viewClickListener) {
        mViewClickListener = viewClickListener;
    }
}
