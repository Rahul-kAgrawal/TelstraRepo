package com.telstra.assignment.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.telstra.assignment.network.ConnectionInterceptor;
import com.telstra.assignment.network.ConnectionListener;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiService {

    public static final String BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/";
    private static Retrofit retrofit = null;
    private static RestApiService client;
    private Context context;

    private RestApiService(Context context){
        this.context = context;
    }
    public static RestApiService getApiClient(Context context) {
        if(null == client)
            client = new RestApiService(context);
        return client;
    }
    public ApiService getService() {
        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .client(provideOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
    private OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.addInterceptor(new ConnectionInterceptor() {
            @Override
            public boolean isInternetAvailable() {
                return isNetworkAvailable(context);
            }
            @Override
            public void onInternetUnavailable() {
                ConnectionListener.getInstance().notifyNetworkChange(false);
            }
        });
        return httpClient.build();
    }
    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
