package com.sofascore.tonib.firsttask.view.ui;

import android.arch.lifecycle.Observer;
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
import com.sofascore.tonib.firsttask.service.model.entities.Player;
import com.sofascore.tonib.firsttask.service.model.entities.Team;
import com.sofascore.tonib.firsttask.view.adapter.FavoritesAdapter;
import com.sofascore.tonib.firsttask.view.adapter.PlayersAdapter;
import com.sofascore.tonib.firsttask.viewmodel.FavoritesListViewModel;
import com.sofascore.tonib.firsttask.viewmodel.PlayersListViewModel;

import java.util.List;

public class PlayerFragment extends Fragment {

    private static final int MY_PERIOD = 30000;

    private PlayersListViewModel playersListViewModel;
    private RecyclerView recyclerView;
    private PlayersAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler;
    private Runnable runnable;

    public static PlayerFragment newInstance() {
        return new PlayerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.player_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        playersListViewModel = ViewModelProviders.of(this).get(PlayersListViewModel.class);
        adapter = new PlayersAdapter(playersListViewModel);

        initViews();
        initLiveData();

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
        recyclerView = getActivity().findViewById(R.id.playerRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = getActivity().findViewById(R.id.playerSwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            handler.removeCallbacksAndMessages(null);
            checkForInternetConnection();
            handler.postDelayed(runnable, MY_PERIOD);
        });
    }

    private void initLiveData() {
        final Observer<List<Player>> apiPlayersObserver = players -> adapter.updateApiPlayers(players);
        final Observer<List<Player>> dbPlayersObserver = players -> adapter.updateDbPlayers(players);

        playersListViewModel.getApiPlayers().observe(this, apiPlayersObserver);
        playersListViewModel.getDbPlayers().observe(this, dbPlayersObserver);
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
        playersListViewModel.fetchPlayersFromAPI(swipeRefreshLayout);
    }

    public void getDataFromDb() {
        playersListViewModel.fetchPlayersFromDB(swipeRefreshLayout);
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
        playersListViewModel.compositeDisposable.clear();
    }
}
