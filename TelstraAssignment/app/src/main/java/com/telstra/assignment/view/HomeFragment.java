package com.telstra.assignment.view;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.telstra.assignment.R;
import com.telstra.assignment.model.DataItem;
import com.telstra.assignment.model.DataListItem;
import com.telstra.assignment.network.ConnectionListener;
import com.telstra.assignment.viewmodel.HomeViewModel;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.functions.Consumer;

@SuppressLint("Registered")
public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mDataListView;
    private SwipeRefreshLayout mRefreshLayout;;
    private HomeViewModel mViewModel;
    private DataAdapter mAdapter;
    private CompositeDisposable disposable;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.home_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRefreshLayout = view.findViewById(R.id.swipeRefreshLayout );
        mDataListView = view.findViewById(R.id.dataListView );
        mDataListView = view.findViewById(R.id.dataListView );
        disposable = new CompositeDisposable();
        addInternetConnectionListener();
        setupDataList();
        getData();
    }

    private void setupDataList() {
        mViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        mRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
        mDataListView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider));
        mDataListView.addItemDecoration(itemDecorator);
    }

    private void addInternetConnectionListener() {
        disposable.add(ConnectionListener.getInstance()
                .listenNetworkChange().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override public void accept(Boolean aBoolean) throws Exception {
                        onInternetUnavailable();
                    }
                }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.dispose();
    }

    protected void onInternetUnavailable() {
        Toast.makeText(getContext(), R.string.network_failure, Toast.LENGTH_LONG).show();
    }
    protected void showSnackBar(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }


    private void getData() {
        mViewModel.getObservable().observe(this, new Observer<DataItem>() {
            @Override
            public void onChanged(DataItem response) {
                if(response.isSuccess()) {
                    removeEmptyRow(response);
                    ((HomeActivity)getActivity()).getSupportActionBar().setTitle(response.getTitle());
                }else {
                    Toast.makeText(getActivity(), response.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void removeEmptyRow(DataItem response){
        ArrayList<DataListItem> itemList =new ArrayList<>();
        for (DataListItem item : response.getItemList()) {
            if(!TextUtils.isEmpty(item.getTitle()))
                itemList.add(item);
        }
        if(null == mAdapter) {
            mAdapter = new DataAdapter(itemList, getActivity());
            mDataListView.setAdapter(mAdapter);
        } else {
            mAdapter.setData(itemList);
            mAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.refresh:
                getData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.setRefreshing(false);
        getData();
    }
}
