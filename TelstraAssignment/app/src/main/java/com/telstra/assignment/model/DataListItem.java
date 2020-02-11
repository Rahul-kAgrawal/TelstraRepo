package com.telstra.assignment.model;

import com.google.gson.annotations.SerializedName;

public class DataListItem {

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("imageHref")
    private String url;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
