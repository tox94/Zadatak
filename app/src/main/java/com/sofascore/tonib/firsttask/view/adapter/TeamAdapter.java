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
import com.sofascore.tonib.firsttask.viewmodel.TeamListViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {
    private List<Team> apiTeams;
    private HashMap<Integer, Team> dbTeams;
    private TeamListViewModel teamListViewModel;


    public TeamAdapter(TeamListViewModel teamListViewModel) {
        this.teamListViewModel = teamListViewModel;
    }

    public void updateApiList(List<Team> list) {
        apiTeams = new ArrayList<>();
        if (list != null) {
            for (Team t : list) {
                if (!(this.apiTeams.contains(t))) {
                    this.apiTeams.add(t);
                }
            }
            Collections.sort(apiTeams,
                    (t1, t2) -> t1.getTeamName().compareToIgnoreCase(t2.getTeamName()));
            Log.d("ADAPTER_BROJ", "Broj timova: " + apiTeams.size() + ", prvi: " + apiTeams.get(0).getTeamName() + "\nzadnji: " + apiTeams.get(apiTeams.size() - 1).getTeamName());
            notifyDataSetChanged();
        }
    }

    public void updateDbList(HashMap<Integer, Team> dbTeams) {
        this.dbTeams = dbTeams;
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
        final Team team = apiTeams.get(position);
        TextView tv = viewHolder.detailsTextView;
        CheckBox cb = viewHolder.checkBox;
        tv.setText(team.getDetails());
        Boolean contains = false;
        if (dbTeams != null) {
            contains = dbTeams.containsKey(team.getTeamId());
        }
        if (contains) {
            cb.setChecked(true);
        } else {
            cb.setChecked(false);
        }
        cb.setOnClickListener(v -> {
            if (((CheckBox) v).isChecked()) {
                Log.d("CHECKBOXCLICK", "Dodaj " + team.getTeamName());
                teamListViewModel.insertTeam(team, this);
            } else {
                Log.d("CHECKBOXCLICK", "Brisi " + team.getTeamName());
                teamListViewModel.deleteTeam(team.getTeamId(), this);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (apiTeams != null) {
            return apiTeams.size();
        } else {
            return 0;
        }
    }
}
