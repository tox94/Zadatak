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

import java.util.HashMap;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {
    private List<Team> apiTeams;
    private List<Team> dbTeams;
    private TeamListViewModel teamListViewModel;


    public TeamAdapter(TeamListViewModel teamListViewModel) {
        this.teamListViewModel = teamListViewModel;
    }

    public void updateApiList(List<Team> list) {
        apiTeams = list;
        Log.d("ADAPTER_BROJ", "Broj timova: " + apiTeams.size() + ", prvi: " + apiTeams.get(0).getTeamName() + "\nzadnji: " + apiTeams.get(apiTeams.size() - 1).getTeamName());
        notifyDataSetChanged();
    }

    public void updateDbList(List<Team> list) {
        this.dbTeams = list;
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
        final Team team;
        if (apiTeams != null) {
            team = apiTeams.get(position);
        } else {
            team = dbTeams.get(position);
        }
        if (team != null) {
            TextView tv = viewHolder.detailsTextView;
            CheckBox cb = viewHolder.checkBox;
            String details = team.getTeamName();
            if (team.getManager() != null) {
                details += " - " + team.getManager().getManagerName();
            }
            tv.setText(details);
            Boolean contains = false;
            if (dbTeams != null) {
                contains = dbTeams.contains(team);
            }
            if (contains) {
                cb.setChecked(true);
            } else {
                cb.setChecked(false);
            }
            cb.setOnClickListener(v -> {
                if (((CheckBox) v).isChecked()) {
                    Log.d("CHECKBOXCLICK", "Dodaj " + team.getTeamName());
                    teamListViewModel.insertTeam(team);
                } else {
                    Log.d("CHECKBOXCLICK", "Brisi " + team.getTeamName());
                    teamListViewModel.deleteTeam(team);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (apiTeams != null) {
            return apiTeams.size();
        } else if (dbTeams != null){
            return dbTeams.size();
        } else {
            return 0;
        }
    }
}
