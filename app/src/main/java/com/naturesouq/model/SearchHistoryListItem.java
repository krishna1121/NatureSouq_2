package com.naturesouq.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SI_Android_Binit on 2/10/2016.
 */
public class SearchHistoryListItem implements Parcelable {
    private String name;
    private String searchDate;
    private String searchTime;
    private String search_url;

    public SearchHistoryListItem(String name, String searchDate, String searchTime, String search_url) {
        this.name = name;
        this.searchDate = searchDate;
        this.searchTime = searchTime;
        this.search_url = search_url;
    }

    public SearchHistoryListItem(String s) {
        this.name = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

    public String getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime;
    }

    public String getSearch_url() {
        return search_url;
    }

    public void setSearch_url(String search_url) {
        this.search_url = search_url;
    }

    @Override
    public String toString() {
        return "SearchHistoryListItem{" +
                "name='" + name + '\'' +
                ", searchDate='" + searchDate + '\'' +
                ", searchTime='" + searchTime + '\'' +
                ", search_url='" + search_url + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.searchDate);
        dest.writeString(this.searchTime);
        dest.writeString(this.search_url);
    }

    public SearchHistoryListItem(Parcel in) {
        this.name = in.readString();
        this.searchDate = in.readString();
        this.searchTime = in.readString();
        this.search_url = in.readString();
    }

    public static final Parcelable.Creator<SearchHistoryListItem> CREATOR = new Parcelable.Creator<SearchHistoryListItem>() {
        public SearchHistoryListItem createFromParcel(Parcel source) {
            return new SearchHistoryListItem(source);
        }

        public SearchHistoryListItem[] newArray(int size) {
            return new SearchHistoryListItem[size];
        }
    };
}
