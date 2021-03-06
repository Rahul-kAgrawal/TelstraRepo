package com.telstra.assignment;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.telstra.assignment.model.DataItem;
import com.telstra.assignment.service.ApiService;
import com.telstra.assignment.service.RestApiService;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ViewModelTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ApiService mApiService = RestApiService.getApiClient(appContext).getService();

        Call<DataItem> call = mApiService.getData();
        try {
            Response<DataItem> response = call.execute();
            assertTrue(response.isSuccessful());
            assertNotEquals(null, response.body());
            assertNotEquals(null, response.body().getItemList());
            assertTrue(response.body().getItemList().size() > 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
