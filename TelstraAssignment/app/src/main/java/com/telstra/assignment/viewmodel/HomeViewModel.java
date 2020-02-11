package com.telstra.assignment.viewmodel;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.telstra.assignment.R;
import com.telstra.assignment.model.DataItem;
import com.telstra.assignment.network.ApiInterface;
import com.telstra.assignment.network.RestApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends AndroidViewModel {
    private Context mContext;
    private MutableLiveData<DataItem> mDataItem;
    private ApiInterface mApiInterface;
    public HomeViewModel(@NonNull Application application) {
        super(application);
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
        Call<DataItem> call = mApiInterface.getData();

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
        mApiInterface = RestApiClient.getRetrofit(mContext).create(ApiInterface.class);
    }
}
