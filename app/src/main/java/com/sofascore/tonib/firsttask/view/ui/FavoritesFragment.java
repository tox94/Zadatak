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
import android.view.animation.OvershootInterpolator;

import com.sofascore.tonib.firsttask.R;
import com.sofascore.tonib.firsttask.service.model.entities.Player;
import com.sofascore.tonib.firsttask.service.model.entities.Team;
import com.sofascore.tonib.firsttask.view.adapter.PlayersAdapter;
import com.sofascore.tonib.firsttask.view.adapter.TeamAdapter;
import com.sofascore.tonib.firsttask.viewmodel.PlayersListViewModel;
import com.sofascore.tonib.firsttask.viewmodel.TeamListViewModel;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

import static com.sofascore.tonib.firsttask.view.ui.MainActivity.ANIMATION_DURATION;

public class FavoritesFragment extends Fragment {

    private TeamListViewModel favoriteTeamsListViewModel;
    private PlayersListViewModel favoritePlayersListViewModel;
    private RecyclerView favoriteTeamsRecyclerView;
    private RecyclerView favoritePlayersRecyclerView;
    private TeamAdapter favoriteTeamsAdapter;
    private PlayersAdapter favoritePlayersAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorites_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        favoriteTeamsListViewModel = ViewModelProviders.of(this).get(TeamListViewModel.class);
        favoritePlayersListViewModel = ViewModelProviders.of(this).get(PlayersListViewModel.class);
        favoriteTeamsAdapter = new TeamAdapter(favoriteTeamsListViewModel);
        favoritePlayersAdapter = new PlayersAdapter(favoritePlayersListViewModel);

        initViews();
        initLiveData();
        getData();
    }

    private void initViews() {
        favoriteTeamsRecyclerView = getActivity().findViewById(R.id.favoriteTeamsRecyclerView);
        RecyclerView.LayoutManager teamsLayoutManager = new LinearLayoutManager(getActivity());
        favoriteTeamsRecyclerView.setLayoutManager(teamsLayoutManager);
        favoriteTeamsRecyclerView.setHasFixedSize(true);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(favoriteTeamsAdapter);
        scaleAdapter.setDuration(ANIMATION_DURATION);
        scaleAdapter.setInterpolator(new OvershootInterpolator());
        scaleAdapter.setFirstOnly(false);
        favoriteTeamsRecyclerView.setAdapter(scaleAdapter);

        favoritePlayersRecyclerView = getActivity().findViewById(R.id.favoritePlayersRecyclerView);
        RecyclerView.LayoutManager playersLayoutManager = new LinearLayoutManager(getActivity());
        favoritePlayersRecyclerView.setLayoutManager(playersLayoutManager);
        favoritePlayersRecyclerView.setHasFixedSize(true);
        ScaleInAnimationAdapter scaleAdapter2 = new ScaleInAnimationAdapter(favoritePlayersAdapter);
        scaleAdapter2.setDuration(ANIMATION_DURATION);
        scaleAdapter2.setInterpolator(new OvershootInterpolator());
        scaleAdapter2.setFirstOnly(false);
        favoritePlayersRecyclerView.setAdapter(scaleAdapter2);
    }

    private void initLiveData() {
        final Observer<List<Team>> favoriteTeamsObserver = teams -> favoriteTeamsAdapter.updateDbList(teams);
        final Observer<List<Player>> favoritePlayersObserver = players -> favoritePlayersAdapter.updateDbPlayers(players);
        favoriteTeamsListViewModel.getTeamsFromDB().observe(this, favoriteTeamsObserver);
        favoritePlayersListViewModel.getPlayersFromDB().observe(this, favoritePlayersObserver);
    }

    public void getData() {
        favoriteTeamsListViewModel.fetchTeamsFromDB();
        favoritePlayersListViewModel.fetchPlayersFromDB();
    }
}
