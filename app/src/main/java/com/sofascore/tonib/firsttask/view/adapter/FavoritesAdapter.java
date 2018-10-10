package com.sofascore.tonib.firsttask.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sofascore.tonib.firsttask.R;
import com.sofascore.tonib.firsttask.service.model.entities.Team;
import com.sofascore.tonib.firsttask.viewmodel.FavoritesListViewModel;
import com.sofascore.tonib.firsttask.viewmodel.TeamListViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.TeamViewHolder> {
    private List<Team> teams;
    private FavoritesListViewModel favoritesListViewModel;


    public FavoritesAdapter(FavoritesListViewModel favoritesListViewModel) {
        this.favoritesListViewModel = favoritesListViewModel;
        teams = new ArrayList<Team>();
    }

    public void updateFavoritesList(List<Team> list) {
        this.teams = list;
        notifyDataSetChanged();
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder {
        private TextView detailsTextView;
        private CheckBox checkBox;

        public TeamViewHolder(View v) {
            super(v);
            detailsTextView = v.findViewById(R.id.teamDetailsTextView);
            checkBox = v.findViewById(R.id.saveCheckBox);
        }
    }

    @Override
    public TeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeamViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.team_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(TeamViewHolder viewHolder, int position) {
        final Team team = teams.get(position);
        TextView tv = viewHolder.detailsTextView;
        CheckBox cb = viewHolder.checkBox;
        tv.setText(team.getDetails());
        cb.setChecked(true);
        cb.setOnClickListener(v -> {
            Log.d("CHECKBOXCLICK", "Brisi " + team.getTeamName());
            favoritesListViewModel.deleteTeam(team.getTeamId(), this);
        });
    }

    @Override
    public int getItemCount() {
        if (teams != null) {
            return teams.size();
        } else {
            return 0;
        }
    }
}
