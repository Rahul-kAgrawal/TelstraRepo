package com.telstra.assignment.model;

public class AdapterRow {
    private String title;
    private String description;
    private String imageHref;

    public AdapterRow(String title, String description, String imageHref) {
        this.title = title;
        this.description = description;
        this.imageHref = imageHref;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageHref() {
        return imageHref;
    }
}
