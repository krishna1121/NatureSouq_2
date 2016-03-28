package com.naturesouq.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.naturesouq.R;
import com.naturesouq.model.NavigationItem;
import java.util.LinkedHashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    int[] navIconArray ;
    private static final int[] EMPTY_STATE_SET = {};
    private static final int[] GROUP_EXPANDED_STATE_SET =
            {android.R.attr.state_expanded};

    private static final int[][] GROUP_STATE_SETS = {
            EMPTY_STATE_SET, // 0
            GROUP_EXPANDED_STATE_SET // 1
    };


    private Context context;
    private List<String> expandableListTitle;
    private LinkedHashMap<String, List<NavigationItem>> expandableListDetail;

    public ExpandableListAdapter(Context context, List<String> expandableListTitle, LinkedHashMap<String, List<NavigationItem>> expandableListDetail ,int[] navIconArray) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.navIconArray = navIconArray ;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        NavigationItem feedItem = (NavigationItem) getChild(listPosition, expandedListPosition);
       // final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView.findViewById(R.id.expandedListItem);
        expandedListTextView.setText(feedItem.getName());
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }

        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.listTitle);
        ImageView indicator = (ImageView) convertView.findViewById(R.id.explist_indicator);
        ImageView itemidentifier = (ImageView) convertView.findViewById(R.id.itemidentifier);

        try {
            //itemidentifier.setImageResource(navIconArray[listPosition]);
            //itemidentifier.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Skip image for Categories .
        if(listPosition == 2){
            //itemidentifier.setImageDrawable(null);
            //itemidentifier.setPadding(10,10,0,10);
            listTitleTextView.setPadding(0,10,0,10);
        }

        if (indicator != null) {
            if (getChildrenCount(listPosition) == 0) {
                indicator.setVisibility(View.INVISIBLE);
            } else {
                indicator.setVisibility(View.VISIBLE);
                int stateSetIndex = (isExpanded ? 1 : 0);

                try {
                    if(stateSetIndex == 1){
                        indicator.setImageResource(R.drawable.arrow_down);
                        //indicator.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    }else{
                        indicator.setImageResource(R.drawable.arrow_right);
                        //indicator.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }


        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }


}