package com.telstra.assignment.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServiceData {

    @SerializedName("title")
    private String title;

    @SerializedName("rows")
    private List<AdapterRow> rowList;


    public String getTitle() {
        return title;
    }

    public List<AdapterRow> getRowList() {
        return rowList;
    }
}
