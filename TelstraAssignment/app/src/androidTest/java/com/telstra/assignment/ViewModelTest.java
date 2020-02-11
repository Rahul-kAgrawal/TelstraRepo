package com.telstra.assignment;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.telstra.assignment.model.DataItem;
import com.telstra.assignment.network.ApiInterface;
import com.telstra.assignment.network.RestApiClient;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

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
        ApiInterface mApiInterface = RestApiClient.getRetrofit(appContext).create(ApiInterface.class);

        Call<DataItem> call = mApiInterface.getData();
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
