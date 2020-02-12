package com.telstra.assignment.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.telstra.assignment.model.DataItem;
import com.telstra.assignment.service.DataRepository;

public class HomeViewModel extends AndroidViewModel {
    private final LiveData<DataItem> liveData;
    public HomeViewModel(@NonNull Application application) {
        super(application);
        liveData = DataRepository.getInstance(application.getApplicationContext()).getData();
    }

    public LiveData<DataItem> getObservable() {
        return liveData;
    }
}
