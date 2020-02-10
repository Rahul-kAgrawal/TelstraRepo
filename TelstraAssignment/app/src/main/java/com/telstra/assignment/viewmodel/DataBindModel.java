package com.telstra.assignment.viewmodel;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.telstra.assignment.R;
import com.telstra.assignment.model.ServiceData;
import com.telstra.assignment.network.ServiceApiClient;
import com.telstra.assignment.network.ServiceApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataBindModel extends AndroidViewModel {

    public Context mContext;
    public MutableLiveData<String> showSnackBarMessage;
    public MutableLiveData<ServiceData> serviceData;
    private ServiceApiInterface serviceApiInterface;

    public DataBindModel(@NonNull Application application) {
        super(application);
        mContext = application.getApplicationContext();
        showSnackBarMessage = new MutableLiveData<>();
    }


    public LiveData<ServiceData> getData() {
        if (serviceData == null)
        {
            serviceData = new MutableLiveData<ServiceData>();
            loadData();
        }
        return serviceData;
    }


    public void loadData() {
        setupApiClient();
        Call<ServiceData> call = serviceApiInterface.getData();

        call.enqueue(new Callback<ServiceData>() {
            @Override
            public void onResponse(Call<ServiceData> call, Response<ServiceData> response) {

                serviceData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ServiceData> call, Throwable t) {
                showSnackBarMessage.setValue(mContext.getString(R.string.response_failure));
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    

    public MutableLiveData<String> getSnackBarMessage() {
        return showSnackBarMessage;
    }

    public void setupApiClient() {
        serviceApiInterface = ServiceApiClient.getRetrofit(mContext).create(ServiceApiInterface.class);
    }
}
