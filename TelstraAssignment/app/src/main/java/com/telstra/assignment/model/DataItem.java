package com.telstra.assignment.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataItem {
    @SerializedName("title")
    private String title;

    @SerializedName("rows")
    private List<DataListItem> mListItem;

    private boolean isSuccess;

    private String errorMessage;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getTitle() {
        return title;
    }

    public List<DataListItem> getItemList() {
        return mListItem;
    }


}
