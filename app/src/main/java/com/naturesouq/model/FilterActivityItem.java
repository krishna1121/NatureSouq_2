package com.naturesouq.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by SI-Andriod on 24-Feb-16.
 *
 */

public class FilterActivityItem {

    private List<VlauesItem> list ;
    private String filetrLabel ;
    private String filterCode ;
    private int viewType;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }



    public String getFilterCode() {
        return filterCode;
    }

    public void setFilterCode(String filterCode) {
        this.filterCode = filterCode;
    }

    public String getFiletrLabel() {
        return filetrLabel;
    }

    public void setFiletrLabel(String filetrLabel) {
        this.filetrLabel = filetrLabel;
    }


    public List<VlauesItem> getList() {
        return list;
    }

    public void setList(ArrayList<VlauesItem> list) {
        this.list = list;
    }

   public FilterActivityItem(String filetrLabel, List<VlauesItem> list, String filterCode, int viewType) {
        this.filetrLabel = filetrLabel ;
        this.list = list ;
        this.filterCode = filterCode;
        this.viewType = viewType;

    }


}
