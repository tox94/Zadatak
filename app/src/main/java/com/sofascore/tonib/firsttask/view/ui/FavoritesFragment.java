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
import com.sofascore.tonib.firsttask.service.model.entities.Team;
import com.sofascore.tonib.firsttask.view.adapter.FavoritesAdapter;
import com.sofascore.tonib.firsttask.view.adapter.TeamAdapter;
import com.sofascore.tonib.firsttask.viewmodel.EmptyViewModel;
import com.sofascore.tonib.firsttask.viewmodel.FavoritesListViewModel;

import java.util.HashMap;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private FavoritesListViewModel favoritesListViewModel;
    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;

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
        adapter = new FavoritesAdapter(favoritesListViewModel);

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
        final Observer<List<Team>> favoritesObserver = teams -> adapter.updateFavoritesList(teams);
        favoritesListViewModel.getFavoriteTeams().observe(this, favoritesObserver);
    }

    public void getData(){
        favoritesListViewModel.fetchTeamsFromDB(adapter);
    }
}
