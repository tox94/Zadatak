package com.sofascore.tonib.firsttask.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sofascore.tonib.firsttask.R;
import com.sofascore.tonib.firsttask.service.model.entities.Player;
import com.sofascore.tonib.firsttask.service.model.entities.Team;
import com.sofascore.tonib.firsttask.view.adapter.FavoritesAdapter;
import com.sofascore.tonib.firsttask.view.adapter.PlayersAdapter;
import com.sofascore.tonib.firsttask.viewmodel.FavoritesListViewModel;
import com.sofascore.tonib.firsttask.viewmodel.PlayersListViewModel;

import java.util.List;

public class PlayerFragment extends Fragment {

    private PlayersListViewModel playersListViewModel;
    private RecyclerView recyclerView;
    private PlayersAdapter adapter;

    public static PlayerFragment newInstance() {
        return new PlayerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorites_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        playersListViewModel = ViewModelProviders.of(this).get(PlayersListViewModel.class);
        adapter = new PlayersAdapter(playersListViewModel);

        initViews();
        initLiveData();
        getData();
    }

    private void initViews() {
        recyclerView = getActivity().findViewById(R.id.favoritesRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void initLiveData() {
        final Observer<List<Player>> playersObserver = players -> adapter.updatePlayers(players);
        playersListViewModel.getPlayers().observe(this, playersObserver);
    }

    public void getData(){
        playersListViewModel.fetchPlayersFromDB(null);
    }
}
