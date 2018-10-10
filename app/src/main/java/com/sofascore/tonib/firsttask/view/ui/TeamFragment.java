package com.sofascore.tonib.firsttask.view.ui;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sofascore.tonib.firsttask.R;
import com.sofascore.tonib.firsttask.service.InternetUtils;
import com.sofascore.tonib.firsttask.view.adapter.TeamAdapter;
import com.sofascore.tonib.firsttask.viewmodel.TeamListViewModel;

public class TeamFragment extends Fragment {

    private static final int MY_PERIOD = 30000;

    private TeamListViewModel teamListViewModel;
    private RecyclerView recyclerView;
    private TeamAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler;
    private Runnable runnable;

    public static TeamFragment newInstance() {
        return new TeamFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.team_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        teamListViewModel = ViewModelProviders.of(this).get(TeamListViewModel.class);
        adapter = new TeamAdapter(teamListViewModel);

        initViews();

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                checkForInternetConnection();
                handler.postDelayed(this, MY_PERIOD);
            }
        };
        handler.post(runnable);
    }

    private void initViews() {
        recyclerView = getActivity().findViewById(R.id.teamRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = getActivity().findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            handler.removeCallbacksAndMessages(null);
            checkForInternetConnection();
            handler.postDelayed(runnable, MY_PERIOD);
        });
    }

    public void checkForInternetConnection() {
        if (InternetUtils.isInternetAvailable(getActivity())) {
            getDataFromApi();
        } else {
            Toast.makeText(getActivity(), "Please turn on internet access.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDataFromApi() {
        teamListViewModel.fetchTeamsFromAPI(adapter, swipeRefreshLayout);
        teamListViewModel.fetchTeamsFromDB(adapter, swipeRefreshLayout);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        swipeRefreshLayout.setRefreshing(true);
        handler.removeCallbacksAndMessages(null);
        handler.post(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        teamListViewModel.compositeDisposable.clear();
    }
}
