package com.sofascore.tonib.firsttask.view.ui;

import android.app.Activity;
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
import com.sofascore.tonib.firsttask.view.adapter.TeamAdapter;
import com.sofascore.tonib.firsttask.viewmodel.EmptyViewModel;
import com.sofascore.tonib.firsttask.viewmodel.FavoritesListViewModel;

import java.util.HashMap;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private FavoritesListViewModel favoritesListViewModel;
    private RecyclerView favoritesRecyclerView;
    private FavoritesAdapter favoritesAdapter;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorites_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        favoritesListViewModel = ViewModelProviders.of(this).get(FavoritesListViewModel.class);
        favoritesAdapter = new FavoritesAdapter(favoritesListViewModel);

        initViews();
        initLiveData();
        getData();
    }

    private void initViews() {
        favoritesRecyclerView = getActivity().findViewById(R.id.favoritesRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        favoritesRecyclerView.setLayoutManager(layoutManager);
        favoritesRecyclerView.setHasFixedSize(true);
        favoritesRecyclerView.setAdapter(favoritesAdapter);
    }

    private void initLiveData() {
        final Observer<List<Team>> favoriteTeamsObserver = teams -> favoritesAdapter.updateFavoriteTeams(teams);
        final Observer<List<Player>> favoritePlayersObserver = players -> favoritesAdapter.updateFavoritePlayers(players);
        favoritesListViewModel.getFavoriteTeams().observe(this, favoriteTeamsObserver);
        favoritesListViewModel.getFavoritePlayers().observe(this, favoritePlayersObserver);
    }

    public void getData(){
        favoritesListViewModel.fetchTeamsFromDB(favoritesAdapter);
        favoritesListViewModel.fetchPlayersFromDB(favoritesAdapter);
    }
}
