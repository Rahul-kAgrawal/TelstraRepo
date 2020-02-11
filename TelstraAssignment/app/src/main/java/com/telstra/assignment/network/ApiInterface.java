package com.telstra.assignment.network;

import com.telstra.assignment.model.DataItem;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("facts.json")
    Call<DataItem> getData();

}
