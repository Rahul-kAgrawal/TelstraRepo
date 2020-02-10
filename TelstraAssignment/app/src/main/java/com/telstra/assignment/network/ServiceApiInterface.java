package com.telstra.assignment.network;

import com.telstra.assignment.model.ServiceData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceApiInterface {

    @GET("facts.json")
    Call<ServiceData> getData();

}
