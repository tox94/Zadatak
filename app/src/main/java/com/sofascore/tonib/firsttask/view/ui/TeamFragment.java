package com.sofascore.tonib.firsttask.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sofascore.tonib.firsttask.R;
import com.sofascore.tonib.firsttask.service.InternetUtils;
import com.sofascore.tonib.firsttask.service.model.entities.Team;
import com.sofascore.tonib.firsttask.view.adapter.TeamAdapter;
import com.sofascore.tonib.firsttask.viewmodel.TeamListViewModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class TeamFragment extends Fragment {

    private TeamListViewModel teamListViewModel;
    private RecyclerView teamRecyclerView;
    private TeamAdapter teamAdapter;
    private SwipeRefreshLayout teamSwipeRefreshLayout;

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
        teamAdapter = new TeamAdapter(teamListViewModel);

        initViews();
        initLiveData();

        checkForInternetConnection();
    }

    private void initViews() {
        teamRecyclerView = getActivity().findViewById(R.id.teamRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        teamRecyclerView.setLayoutManager(layoutManager);
        teamRecyclerView.setHasFixedSize(true);
        teamRecyclerView.setAdapter(teamAdapter);

        teamSwipeRefreshLayout = getActivity().findViewById(R.id.teamSwipeRefreshLayout);
        teamSwipeRefreshLayout.setOnRefreshListener(() -> {
            Log.d("REFRESH_TEAM", "Tu sam");
            teamSwipeRefreshLayout.setRefreshing(true);
            restartCompositeDisposable();
        });
    }

    private void initLiveData() {
        final Observer<List<Team>> apiTeamObserver = teams -> {
            if (teamSwipeRefreshLayout.isRefreshing()) {
                teamSwipeRefreshLayout.setRefreshing(false);
            }
            teamAdapter.updateApiList(teams);
        };

        final Observer<List<Team>> dbTeamObserver = teams -> {
            if (teamSwipeRefreshLayout.isRefreshing()) {
                teamSwipeRefreshLayout.setRefreshing(false);
            }
            teamAdapter.updateDbList(teams);
        };

        teamListViewModel.getApiTeams().observe(this, apiTeamObserver);
        teamListViewModel.getDbTeams().observe(this, dbTeamObserver);
    }

    public void checkForInternetConnection() {
        if (InternetUtils.isInternetAvailable(getActivity())) {
            getDataFromApi();
            getDataFromDb();
        } else {
            Toast.makeText(getActivity(), "Please turn on internet access.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDataFromApi() {
        teamListViewModel.fetchTeamsFromAPI();
    }

    public void getDataFromDb() {
        teamListViewModel.fetchTeamsFromDB();
    }

    private void restartCompositeDisposable(){
        teamListViewModel.teamsCompositeDisposable.dispose();
        teamListViewModel.teamsCompositeDisposable = new CompositeDisposable();
        checkForInternetConnection();
    }

    @Override
    public void onPause() {
        super.onPause();
        teamListViewModel.teamsCompositeDisposable.dispose();
    }

    @Override
    public void onResume() {
        super.onResume();
        restartCompositeDisposable();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        teamListViewModel.teamsCompositeDisposable.dispose();
    }
}
