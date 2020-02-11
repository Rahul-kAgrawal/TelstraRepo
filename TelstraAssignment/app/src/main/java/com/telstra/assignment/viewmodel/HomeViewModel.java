package com.telstra.assignment.viewmodel;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.telstra.assignment.R;
import com.telstra.assignment.model.DataItem;
import com.telstra.assignment.network.ApiService;
import com.telstra.assignment.network.RestApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends AndroidViewModel {
    private Context mContext;
    private MutableLiveData<DataItem> mDataItem;
    private ApiService mApiService;
    public HomeViewModel(@NonNull Application application) {
        super(application);


//        The view model should only know about the application context. the application context can:
//
//        Start a service
//
//        Bind to a service
//
//        Send a broadcast
//
//        Register a broadcast receiver
//
//        Load resource values
//
//        It cannot:
//
//        Show a dialog
//
//        Start an activity
//
//        Inflate a layout
//
//
                mContext = application.getApplicationContext();
    }

    public LiveData<DataItem> getData() {
        if (mDataItem == null)
        {
            mDataItem = new MutableLiveData<DataItem>();
        }
        loadData();
        return mDataItem;
    }


    private void loadData() {
        setupApiClient();
        Call<DataItem> call = mApiService.getData();

        call.enqueue(new Callback<DataItem>() {
            @Override
            public void onResponse(Call<DataItem> call, Response<DataItem> response) {
                if(null == response) {
                    response.body().setSuccess(false);
                }
                response.body().setSuccess(true);
                mDataItem.setValue(response.body());
            }

            @Override
            public void onFailure(Call<DataItem> call, Throwable t) {
                DataItem item = new DataItem();
                item.setSuccess(false);
                item.setErrorMessage(mContext.getString(R.string.response_failure));
                mDataItem.setValue(item);
            }
        });
    }

    private void setupApiClient() {
        mApiService = RestApiClient.getApiClient(mContext).getService();
    }
}
