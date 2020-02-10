package com.telstra.assignment.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.telstra.assignment.R;
import com.telstra.assignment.model.ServiceData;
import com.telstra.assignment.viewmodel.DataBindModel;

public class HomeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private ViewAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    DataBindModel dataBindModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dataBindModel = ViewModelProviders.of(this).get(DataBindModel.class);
        refreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout.setOnRefreshListener(this);
        getData();
    }

    private void getData() {
        dataBindModel.getData().observe(this, new Observer<ServiceData>() {
            @Override
            public void onChanged(ServiceData response) {
                getSupportActionBar().setTitle(response.getTitle());
                adapter = new ViewAdapter(response.getRowList(), HomeActivity.this);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
        getData();
    }
}
