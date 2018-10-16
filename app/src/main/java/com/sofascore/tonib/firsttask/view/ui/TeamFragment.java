package com.sofascore.tonib.firsttask.view.ui;

import android.arch.lifecycle.LiveData;
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
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.sofascore.tonib.firsttask.R;
import com.sofascore.tonib.firsttask.service.InternetUtils;
import com.sofascore.tonib.firsttask.service.model.entities.Team;
import com.sofascore.tonib.firsttask.view.adapter.TeamAdapter;
import com.sofascore.tonib.firsttask.viewmodel.TeamListViewModel;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

import static com.sofascore.tonib.firsttask.view.ui.MainActivity.ANIMATION_DURATION;

public class TeamFragment extends Fragment {

    private TeamListViewModel teamListViewModel;
    private RecyclerView teamRecyclerView;
    private TeamAdapter teamAdapter;
    private SwipeRefreshLayout teamSwipeRefreshLayout;

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
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(teamAdapter);
        scaleAdapter.setDuration(ANIMATION_DURATION);
        scaleAdapter.setInterpolator(new OvershootInterpolator());
        scaleAdapter.setFirstOnly(false);
        teamRecyclerView.setAdapter(scaleAdapter);

        teamSwipeRefreshLayout = getActivity().findViewById(R.id.teamSwipeRefreshLayout);
        teamSwipeRefreshLayout.setOnRefreshListener(() -> {
            Log.d("REFRESH_TEAM", "Tu sam");
            teamSwipeRefreshLayout.setRefreshing(true);
            //initLiveData();
        });
    }

    private void initLiveData() {
        final Observer<List<Team>> apiTeamObserver = teams -> {
            if (teamSwipeRefreshLayout.isRefreshing()) {
                teamSwipeRefreshLayout.setRefreshing(false);
            }
            Log.d("UPDATEANI_TIMOVI", "Sad");
            teamAdapter.updateApiList(teams);
        };

        final Observer<List<Team>> dbTeamObserver = teams -> {
            if (teamSwipeRefreshLayout.isRefreshing()) {
                teamSwipeRefreshLayout.setRefreshing(false);
            }
            teamAdapter.updateDbList(teams);
        };

        getDataFromApi().observe(this, apiTeamObserver);
        getDataFromDb().observe(this, dbTeamObserver);
    }

    public void checkForInternetConnection() {
        if (InternetUtils.isInternetAvailable(getActivity())) {
            Log.d("REFRESH_TEAM", "Postavljam");
            getDataFromApi();
        } else {
            Toast.makeText(getActivity(), "Please turn on internet access.", Toast.LENGTH_SHORT).show();
        }
    }

    private LiveData<List<Team>> getDataFromApi() {
        return teamListViewModel.getTeamsFromAPI();
    }

    public LiveData<List<Team>> getDataFromDb() {
        return teamListViewModel.getTeamsFromDB();
    }
}
