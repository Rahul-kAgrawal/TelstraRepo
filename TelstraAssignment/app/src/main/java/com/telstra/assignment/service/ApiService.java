package com.telstra.assignment.service;

import com.telstra.assignment.model.DataItem;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("facts.json")
    Call<DataItem> getData();
}
