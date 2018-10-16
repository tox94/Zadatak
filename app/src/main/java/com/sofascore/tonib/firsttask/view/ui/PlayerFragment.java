package com.sofascore.tonib.firsttask.view.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
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
import com.sofascore.tonib.firsttask.service.model.entities.Player;
import com.sofascore.tonib.firsttask.service.model.entities.Team;
import com.sofascore.tonib.firsttask.view.adapter.PlayersAdapter;
import com.sofascore.tonib.firsttask.viewmodel.PlayersListViewModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

import static com.sofascore.tonib.firsttask.view.ui.MainActivity.ANIMATION_DURATION;

public class PlayerFragment extends Fragment {

    private PlayersListViewModel playersListViewModel;
    private RecyclerView recyclerView;
    private PlayersAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Observer<List<Player>> apiPlayersObserver;
    private Observer<List<Player>> dbPlayersObserver;

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
        checkForInternetConnection();
    }

    private void initViews() {
        recyclerView = getActivity().findViewById(R.id.playerRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(adapter);
        scaleAdapter.setDuration(ANIMATION_DURATION);
        scaleAdapter.setInterpolator(new OvershootInterpolator());
        scaleAdapter.setFirstOnly(false);
        recyclerView.setAdapter(scaleAdapter);

        swipeRefreshLayout = getActivity().findViewById(R.id.playerSwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Log.d("REFRESH_PLAYER", "Tu sam");
            swipeRefreshLayout.setRefreshing(true);
            initLiveData();
        });
    }

    private void initLiveData() {
        apiPlayersObserver = players -> {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            Log.d("UPDATEANI_IGRACI", "Sad");
            adapter.updateApiPlayers(players);
        };
        dbPlayersObserver = players -> {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            adapter.updateDbPlayers(players);
        };
        getDataFromApi().observe(this, apiPlayersObserver);
        getDataFromDb().observe(this, dbPlayersObserver);
    }

    public void checkForInternetConnection() {
        if (InternetUtils.isInternetAvailable(getActivity())) {
            getDataFromApi();
        } else {
            Toast.makeText(getActivity(), "Please turn on internet access.", Toast.LENGTH_SHORT).show();
        }
    }

    private LiveData<List<Player>> getDataFromApi() {
        return playersListViewModel.getPlayersFromAPI();
    }

    public LiveData<List<Player>> getDataFromDb() {
        return playersListViewModel.getPlayersFromDB();
    }
}
