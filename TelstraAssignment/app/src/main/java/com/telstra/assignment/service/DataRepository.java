package com.telstra.assignment.service;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.telstra.assignment.model.DataItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {

    private ApiService mApiService;
    private static DataRepository dataRepository;

    private DataRepository(Context context){
        mApiService = RestApiService.getApiClient(context).getService();
    }

    public synchronized static DataRepository getInstance(Context context) {
        if (dataRepository == null)
            dataRepository = new DataRepository(context);
        return dataRepository;
    }

    public LiveData<DataItem> getData() {
        Call<DataItem> call = mApiService.getData();
        final MutableLiveData<DataItem> dataItem = new MutableLiveData<>();
        call.enqueue(new Callback<DataItem>() {
            @Override
            public void onResponse(Call<DataItem> call, Response<DataItem> response) {
                if(null == response) {
                    response.body().setSuccess(false);
                }
                response.body().setSuccess(true);
                dataItem.setValue(response.body());
            }

            @Override
            public void onFailure(Call<DataItem> call, Throwable t) {
                DataItem item = new DataItem();
                item.setSuccess(false);
                item.setErrorMessage("Something Went Wrong.");
                dataItem.setValue(item);
            }
        });
        return dataItem;
    }
}
