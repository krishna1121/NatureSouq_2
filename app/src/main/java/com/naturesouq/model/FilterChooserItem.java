package com.naturesouq.model;

/**
 * Created by SI-Andriod on 02-Mar-16.
 */
public class FilterChooserItem {

    private String code ;
    private String id ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public FilterChooserItem(){

    }

    public FilterChooserItem(String code ,String id ){
        this.code = code ;
        this.id = id ;
    }
}
