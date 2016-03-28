package com.naturesouq.model;

/**
 * Created by shahid on 2/20/2016.
 */
public class NavigationItem {

    private String id;
    private String name;

    public NavigationItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
